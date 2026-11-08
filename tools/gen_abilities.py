# -*- coding: utf-8 -*-
"""从 assets/byd/devices/*.json 生成 me.guanming.vehicle.infolib.abilities 下的静态能力类。

用法：
  python tools/gen_abilities.py --catalog <devices目录> --out <java源码根> --doc <API文档路径>

生成内容：
  * abilities/Runtime.java                 共享运行时（含 framework 常量批量采集）
  * abilities/<一级id>.java              27 个顶层类，每个含若干静态嵌套类，共 206 个能力入口
  * ABILITIES_API.md                       宿主编码时查阅的签名/参数文档
  * tools/gen_abilities_report.json       生成统计与运行时说明

设计约定（已与用户确认）：
  * 只生成能力类，不保留通用 read/write 入口；目录数据全部编译成 Java 代码，运行时无 JSON 解析。
  * selector 只接受短名（driver / celsius），传全名抛异常。
  * read 返回 SensorValue，失败返回 SensorValue.failed()；write 返回 void，失败抛异常。
  * 一级 id 是顶层类（不是包），其余每段是 public static final 嵌套类；不压缩多段 id。
    例：bodywork.door.open -> abilities.bodywork 类 -> door 嵌套类 -> open 嵌套类 -> read(area)。
  * device 一律使用本次目录的 deviceType；原 Sensor/Control 不改动。
  * 目录里缺数字 id 的 feature：编译期列出 (name, owner, auditedId)，Runtime 初始化时
    一次性反射 android.hardware.bydauto.BYDAutoFeatureIds 系列静态字段批量采集，
    结果只供本次运行时使用；不导出静态常量表、不落盘。
  * 含 profiles 车型分支的能力：需宿主先调用 Runtime.setVehicleProfile(...)，
    否则明确抛异常，不再按 match 静默取首个 case。
"""
import argparse, json, glob, os, collections, re, keyword

KEEP_CASE = ("parameters", "match", "profiles", "results", "reads", "writes",
             "writeBatches", "constants", "echo", "acceptedCodes")
SENTINELS = (-10011, -10013, 65535, 1048575)


def short(v):
    s = str(v)
    return s.rsplit(".", 1)[1] if "." in s else s


def jstr(s):
    return json.dumps(str(s), ensure_ascii=False)


def is_ident(s):
    return bool(re.match(r'^[A-Za-z_$][A-Za-z0-9_$]*$', s)) and not keyword.iskeyword(s)


def load_catalog(catalog_dir):
    caps = {}
    name2id = {}
    for path in sorted(glob.glob(os.path.join(catalog_dir, "*.json"))):
        doc = json.load(open(path, encoding="utf-8"))
        for dev in doc["devices"]:
            for cap in dev.get("capabilities", []):
                ops = dict(cap.get("operations", {}))
                # observe -> get（已有 get 则忽略）
                if "observe" in ops and "get" not in ops:
                    cases = []
                    for c in ops["observe"].get("cases", []):
                        nc = {k: v for k, v in c.items() if k in KEEP_CASE and k != "reads"}
                        nc["reads"] = c.get("events") or []
                        cases.append(nc)
                    ops["get"] = {"cases": cases}
                ops = {k: v for k, v in ops.items() if k in ("get", "set")}
                caps[cap["id"]] = {"cap": cap, "ops": ops, "file": os.path.basename(path)}
                # 目录中已审计的数字 id（供反射失败时兜底）
                for op in ops.values():
                    for c in op.get("cases", []):
                        for w in (c.get("reads") or []) + (c.get("writes") or []):
                            f = w.get("feature") or {}
                            if isinstance(f.get("id"), int) and f.get("name"):
                                name2id.setdefault(f["name"], f["id"])
    return caps, name2id


def collect_feature_index(caps, name2id):
    """编译期清单：所有只写了 framework 常量名、没写数字 id 的 feature。"""
    rows = {}
    for cid, it in caps.items():
        for op in it["ops"].values():
            for c in op.get("cases", []):
                for k in ("reads", "writes"):
                    for w in c.get(k) or []:
                        f = w.get("feature") or {}
                        if isinstance(f.get("id"), int):
                            continue
                        name = f.get("name")
                        if not name:
                            continue
                        owner = f.get("owner") or ""
                        audited = name2id.get(name) or 0
                        key = (name, owner)
                        if key not in rows or (rows[key] == 0 and audited):
                            rows[key] = audited
    return [(n, o, a) for (n, o), a in sorted(rows.items())]


def fid_expr(w, var):
    """返回 (额外语句行, fid 表达式)。原始 id 存在则用字面量，否则走 Runtime.fid()。"""
    f = w.get("feature") or {}
    fid = f.get("id")
    if isinstance(fid, int):
        return [], str(fid)
    name = f.get("name") or ""
    owner = f.get("owner") or ""
    return [f"int {var} = Runtime.fid({jstr(name)}, {jstr(owner)});"], var


def dev_of(w):
    return (w.get("feature") or {}).get("deviceType")


def param_java_type(p):
    vals = p.get("values") or []
    if vals and any(isinstance(v.get("value"), str) for v in vals):
        return "String"
    if vals and all(isinstance(v.get("value"), (int, float)) and not isinstance(v["value"], bool)
                    for v in vals):
        return "int"
    if p.get("role") in ("selector", "unit"):
        return "String"
    if p.get("type") == "boolean" and not vals:
        return "boolean"
    return "int"


def collect_params(op):
    """返回 [(name, type, spec)]，按目录首次出现顺序；合并所有 case 的 values/ranges。"""
    out = collections.OrderedDict()
    for c in op.get("cases", []):
        for p in c.get("parameters") or []:
            n = p.get("name")
            if not n:
                continue
            if n not in out:
                spec = dict(p)
                spec["values"] = list(p.get("values") or [])
                spec["ranges"] = list(p.get("ranges") or [])
                out[n] = (param_java_type(p), spec)
            else:
                _, spec = out[n]
                for key in ("values", "ranges"):
                    seen = {json.dumps(v, sort_keys=True, ensure_ascii=False) for v in spec[key]}
                    for v in p.get(key) or []:
                        k = json.dumps(v, sort_keys=True, ensure_ascii=False)
                        if k not in seen:
                            spec[key].append(v)
                            seen.add(k)
    return list(out.items())


def validations(params, indent="        "):
    lines = []
    for name, (jt, p) in params:
        vals = p.get("values") or []
        ranges = p.get("ranges") or []
        if jt == "String" and vals:
            allowed = ", ".join(jstr(short(v["value"])) for v in vals)
            lines.append(f'{indent}Runtime.checkSelector("{name}", {name}{(", " + allowed) if allowed else ""});')
        elif jt == "int" and vals:
            nums = [v["value"] for v in vals if isinstance(v.get("value"), (int, float)) and not isinstance(v["value"], bool)]
            if nums:
                lines.append(f'{indent}Runtime.checkEnum("{name}", {name}, {", ".join(str(int(x)) for x in nums)});')
        elif jt == "int" and ranges:
            bounds = []
            for r in ranges:
                if isinstance(r.get("min"), (int, float)) and isinstance(r.get("max"), (int, float)):
                    bounds += [str(int(r["min"])), str(int(r["max"]))]
            if len(bounds) == 2:
                lines.append(f'{indent}Runtime.checkRange("{name}", {name}, {bounds[0]}, {bounds[1]});')
            elif len(bounds) >= 4:
                lines.append(f'{indent}Runtime.checkAnyRange("{name}", {name}, {", ".join(bounds)});')
    return lines


def case_condition(c, params, indent=""):
    by_key = {}
    for pc in c.get("parameters") or []:
        if isinstance(pc.get("key"), int):
            by_key[pc["key"]] = pc
    conds = []
    for m in c.get("match") or []:
        p = by_key.get(m.get("key"))
        if not p:
            continue
        name, jt = p.get("name"), param_java_type(p)
        vals = m.get("values") or []
        if not vals or not is_ident(name or ""):
            continue
        if jt == "String":
            lits = ", ".join(jstr(short(v)) for v in vals)
            conds.append(f'Runtime.inStr({name}{(", " + lits) if lits else ""})')
        elif jt == "boolean":
            lits = ", ".join("true" if v in (True, 1, "true", "1") else "false" for v in vals)
            conds.append(f'Runtime.inBool({name}{(", " + lits) if lits else ""})')
        else:
            lits = ", ".join(str(int(v)) for v in vals if isinstance(v, (int, float)) and not isinstance(v, bool))
            if lits:
                conds.append(f'Runtime.inInt({name}, {lits})')
    # 车型分支：与 match 同为 AND 条件，未配置车型时该 case 不会匹配
    for field, vals in (c.get("profiles") or {}).items():
        nums = [int(v) for v in vals if isinstance(v, (int, float)) and not isinstance(v, bool)]
        if nums:
            conds.append(f'Runtime.profileAllows({jstr(field)}, {", ".join(str(x) for x in nums)})')
    return " && ".join(conds) if conds else "true"


def encode_expr(expr, codec, jt):
    """生成 int raw = <编码表达式/语句块>；{VAR}/{OK} 由调用方替换。"""
    numexpr = f"({expr} ? 1 : 0)" if jt == "boolean" else expr
    if not codec:
        return [f"int {{VAR}} = {numexpr};"]
    if "values" in codec:
        vs = codec["values"]
        lines = ["int {VAR} = 0;", "boolean {OK} = false;"]
        for v in vs:
            lv, raw = v.get("value"), v.get("raw")
            if isinstance(lv, str):
                cond = f'{jstr(short(lv))}.equals({expr})'
            elif isinstance(lv, bool):
                cond = f'({expr} == {str(lv).lower()})' if jt == "boolean" \
                       else f'({numexpr} == {1 if lv else 0})'
            else:
                cond = f'({numexpr} == {lv})'
            lines.append(f"if ({cond}) {{ {{VAR}} = {int(raw)}; {{OK}} = true; }}")
        lines.append(f'if (!{{OK}}) throw new IllegalArgumentException("Invalid enum value: " + {expr});')
        return lines
    if isinstance(codec.get("offset"), (int, float)):
        return [f"int {{VAR}} = (int) ({numexpr} - ({codec['offset']}));"]
    if isinstance(codec.get("scale"), (int, float)):
        return [f"int {{VAR}} = Math.round((float) (({numexpr}) / ({codec['scale']})));"]
    return [f"int {{VAR}} = {numexpr};"]


def decode_expr(var, codec, tx):
    if codec and "values" in codec:
        vs = codec["values"]
        lines = [f"double {var} = 0;", f"boolean {var}_ok = false;"]
        for v in vs:
            lv, raw = v.get("value"), v.get("raw")
            if isinstance(lv, bool):
                val = 1.0 if lv else 0.0
            elif isinstance(lv, (int, float)):
                val = float(lv)
            else:
                continue
            lines.append(f"if ({{RAW}} == {int(raw)}) {{ {var} = {val}; {var}_ok = true; }}")
        return lines
    if codec and isinstance(codec.get("offset"), (int, float)):
        return [f"double {var} = {{RAW}} + ({codec['offset']});"]
    if codec and isinstance(codec.get("scale"), (int, float)):
        return [f"double {var} = {{RAW}} * ({codec['scale']});"]
    if tx == 7:
        return [f"double {var} = Float.intBitsToFloat({{RAW}});"]
    return [f"double {var} = {{RAW}};"]


def gen_method_read(cap_id, op, params, stats, need_profile):
    lines = [f"    public static SensorValue read({', '.join(f'{t} {n}' for n, (t, _) in params)}) {{"]
    if need_profile:
        lines.append(f"        Runtime.requireProfile({jstr(cap_id)});")
    lines += validations(params)
    cases = op.get("cases", [])
    first = True
    for c in cases:
        cond = case_condition(c, params)
        lines.append(f'        {"if" if first else "else if"} ({cond}) {{')
        first = False
        reads = list(c.get("reads") or [])
        valid = []
        for w in reads:
            dev = dev_of(w)
            if not isinstance(dev, int):
                stats["skipped_read_features"] += 1
                continue
            valid.append((w, dev))
        if not valid:
            lines.append("            return SensorValue.failed();")
            lines.append("        }")
            continue
        results = {r.get("key"): r for r in (c.get("results") or [])}
        unit, labels = "null", []
        for w, dev in valid:
            r = results.get(w.get("key")) or {}
            u = r.get("unit")
            if not u:
                for rg in r.get("ranges") or []:
                    if rg.get("unit"):
                        u = rg["unit"]
                        break
            if u and unit == "null":
                unit = jstr(short(u))
            for v in r.get("values") or []:
                if isinstance(v.get("value"), (int, float)) and not isinstance(v["value"], bool):
                    labels.append((int(v["value"]), v.get("name") or str(v["value"])))
            if labels:
                break
        text = "null"
        if labels:
            expr = "null"
            for k, n in reversed(labels[:16]):
                expr = f"(r0 == {k} ? {jstr(n)} : {expr})"
            text = expr
        raws, decoded, names = [], [], []
        for j, (w, dev) in enumerate(valid):
            r = results.get(w.get("key")) or {}
            tx = 7 if (r.get("type") == "double" or (w.get("codec") or {}).get("nativeType") == "double") else 5
            rv = f"r{j}"
            extra, fidvar = fid_expr(w, f"f{j}")
            for e in extra:
                lines.append("            " + e)
                stats["runtime_fid_reads"] += 1
            lines.append(f"            int {rv} = Runtime.readFid({dev}, {fidvar}, {tx});")
            lines.append(f"            if (Runtime.isSentinel({rv})) return SensorValue.failed();")
            for dl in decode_expr(f"d{j}", w.get("codec"), tx):
                lines.append("            " + dl.replace("{RAW}", rv))
            raws.append(rv); decoded.append(f"d{j}")
            names.append(jstr((w.get("feature") or {}).get("name") or ""))
        feats = f"new int[]{{{', '.join(raws)}}}, new String[]{{{', '.join(names)}}}"
        lines.append(f"            return Runtime.value({raws[0]}, {decoded[0]}, {unit}, {text}, {feats});")
        lines.append("        }")
    lines.append("        else {")
    lines.append(f'            throw new IllegalArgumentException("No matching case for {cap_id} with given params");')
    lines.append("        }")
    lines.append("    }")
    return lines


def gen_method_write(cap_id, op, params, stats, need_profile):
    lines = [f"    public static void write({', '.join(f'{t} {n}' for n, (t, _) in params)}) {{"]
    if need_profile:
        lines.append(f"        Runtime.requireProfile({jstr(cap_id)});")
    lines += validations(params)
    cases = op.get("cases", [])
    first = True
    for c in cases:
        cond = case_condition(c, params)
        lines.append(f'        {"if" if first else "else if"} ({cond}) {{')
        first = False
        writes = list(c.get("writes") or [])
        for b in c.get("writeBatches") or []:
            writes += b if isinstance(b, list) else (b.get("writes") or [])
        params_of_case = c.get("parameters") or []
        names = {p["name"]: param_java_type(p) for p in params_of_case if p.get("name")}
        key2name = {p.get("key"): p.get("name") for p in params_of_case if p.get("name")}
        emitted = 0
        for j, w in enumerate(writes):
            dev = dev_of(w)
            if not isinstance(dev, int):
                stats["skipped_write_features"] += 1
                continue
            extra, fidvar = fid_expr(w, f"f{j}")
            var = f"v{j}"
            if "constant" in w:
                code = [f"int {var} = {int(w['constant'])};"]
            else:
                pname = w.get("parameter")
                if isinstance(pname, int):
                    pname = key2name.get(pname)
                if pname not in names:
                    stats["skipped_write_features"] += 1
                    stats["skip_no_param"] += 1
                    continue
                jt = names[pname]
                code = [l.replace("{VAR}", var).replace("{OK}", var + "_ok")
                        for l in encode_expr(pname, w.get("codec"), jt)]
            for e in extra:
                lines.append("            " + e)
                stats["runtime_fid_writes"] += 1
            for l in code:
                lines.append("            " + l)
            lines.append(f"            Runtime.writeFid({dev}, {fidvar}, {var});")
            emitted += 1
        if emitted == 0:
            stats["empty_write_cases"] += 1
            lines.append('            throw new IllegalStateException("No writable fid in this case");')
        lines.append("        }")
    lines.append("        else {")
    lines.append(f'            throw new IllegalArgumentException("No matching case for {cap_id} with given params");')
    lines.append("        }")
    lines.append("    }")
    return lines


def cap_notes(cid, ops):
    """类 Javadoc 里需要提示的信息。"""
    info = []
    if any(c.get("profiles") for op in ops.values() for c in op.get("cases", [])):
        info.append("含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)")
    has_runtime_fid = False
    for op in ops.values():
        for c in op.get("cases", []):
            for k in ("reads", "writes"):
                for w in c.get(k) or []:
                    if not isinstance((w.get("feature") or {}).get("id"), int):
                        has_runtime_fid = True
    if has_runtime_fid:
        info.append("部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析")
    return info


def clean_generated(pkg_root):
    """删除上一版生成的能力类（保留 Runtime.java），避免旧子包与新顶层类同名冲突。"""
    removed = 0
    for root, dirs, files in os.walk(pkg_root, topdown=False):
        for f in files:
            if f.endswith(".java") and f != "Runtime.java":
                os.remove(os.path.join(root, f))
                removed += 1
        if root != pkg_root and not os.listdir(root):
            os.rmdir(root)
    return removed


def build_tree(caps):
    """capabilityId -> 嵌套类树。一级 id 是顶层类，其余每段是静态嵌套类。"""
    tree = {}
    for cid in caps:
        parts = cid.split(".")
        node = tree.setdefault(parts[0], {"cap": None, "children": {}})
        for seg in parts[1:]:
            node = node["children"].setdefault(seg, {"cap": None, "children": {}})
        node["cap"] = cid
    return tree


def indent_lines(lines, depth):
    pad = "    " * depth
    return [(pad + l) if l else l for l in lines]


def gen_node(name, node, depth, caps, stats, notes_by_cap, profile_caps):
    """递归生成一个类（depth=0 顶层类，否则 public static final 嵌套类）。"""
    pad = "    " * depth
    kind = "public final class" if depth == 0 else "public static final class"
    cid = node["cap"]
    if cid:
        title = caps[cid]["cap"].get("displayName", cid)
        jd = [f"/** {title}（{cid}）— 由目录自动生成，请勿手改。 */"]
        notes = notes_by_cap.get(cid) or []
        if notes:
            jd = [f"/** {title}（{cid}）— 由目录自动生成，请勿手改。", " *"]
            jd += [f" * 注：{x}" for x in notes]
            jd.append(" */")
    else:
        jd = [f"/** {name} — 能力分组容器。 */"]
    lines = [pad + x for x in jd]
    lines.append(f"{pad}{kind} {name} {{")
    lines.append(f"{pad}    private {name}() {{}}")
    if cid:
        ops = caps[cid]["ops"]
        need_profile = cid in profile_caps
        if "get" in ops:
            lines.append("")
            lines += indent_lines(
                gen_method_read(cid, ops["get"], collect_params(ops["get"]), stats, need_profile),
                depth)
        if "set" in ops:
            lines.append("")
            lines += indent_lines(
                gen_method_write(cid, ops["set"], collect_params(ops["set"]), stats, need_profile),
                depth)
    if node["children"]:
        for child in sorted(node["children"]):
            lines.append("")
            lines += gen_node(child, node["children"][child], depth + 1,
                              caps, stats, notes_by_cap, profile_caps)
    lines.append("")
    lines.append(f"{pad}}}")
    return lines


def md(s):
    """清洗目录文案，避免换行/反引号破坏 Markdown 结构。"""
    if s is None:
        return ""
    t = str(s).replace("\r", " ").replace("\n", " ")
    return re.sub(r"\s+", " ", t).strip()


def collect_doc_params(item):
    """汇总 read/write 的参数（跨 case、跨操作去重），保留说明与取值域。"""
    out = collections.OrderedDict()
    for opn in ("get", "set"):
        op = item["ops"].get(opn)
        if not op:
            continue
        for name, (jt, spec) in collect_params(op):
            key = (name, jt)
            e = out.get(key)
            if e is None:
                e = {"name": name, "jt": jt, "role": spec.get("role"),
                     "description": md(spec.get("description")), "unit": spec.get("unit"),
                     "values": [], "ranges": [], "ops": []}
                out[key] = e
            label = "read" if opn == "get" else "write"
            if label not in e["ops"]:
                e["ops"].append(label)
            for slot, src in (("values", spec.get("values") or []), ("ranges", spec.get("ranges") or [])):
                seen = {json.dumps(x, sort_keys=True, ensure_ascii=False) for x in e[slot]}
                for x in src:
                    k = json.dumps(x, sort_keys=True, ensure_ascii=False)
                    if k not in seen:
                        e[slot].append(x)
                        seen.add(k)
    return list(out.values())


def collect_doc_results(item):
    """汇总 get 的返回字段（按 name 去重），保留说明/单位/枚举/值域。"""
    out = collections.OrderedDict()
    op = item["ops"].get("get")
    if not op:
        return []
    for c in op.get("cases", []):
        for r in c.get("results") or []:
            if r.get("role") == "selector":   # selector 是入参回显，不是 SensorValue 的返回字段
                continue
            name = r.get("name") or r.get("key") or "value"
            e = out.get(name)
            if e is None:
                e = {"name": name, "description": md(r.get("description")),
                     "unit": r.get("unit"), "role": r.get("role"),
                     "values": [], "ranges": []}
                out[name] = e
            for slot in ("values", "ranges"):
                seen = {json.dumps(x, sort_keys=True, ensure_ascii=False) for x in e[slot]}
                for x in r.get(slot) or []:
                    k = json.dumps(x, sort_keys=True, ensure_ascii=False)
                    if k not in seen:
                        e[slot].append(x)
                        seen.add(k)
    return list(out.values())


def param_value_text(v, jt):
    """按 API 实际接受的字面量展示参数值 + 该值的意义。"""
    raw = v.get("value")
    if jt == "String":
        lit = short(raw)
    elif isinstance(raw, bool):
        lit = "true" if raw else "false"
    else:
        lit = str(raw)
    meaning = md(v.get("description") or v.get("name"))
    return "`%s` %s" % (lit, ("— " + meaning) if meaning else "")


def range_text(rg):
    bits = []
    if rg.get("unit"):
        bits.append(short(rg["unit"]))
    if rg.get("step") not in (None, 1):
        bits.append("步长 %s" % rg["step"])
    return "`%s~%s`%s" % (rg.get("min"), rg.get("max"),
                          ("（" + "，".join(bits) + "）") if bits else "")


def enum_labels(results):
    labels = []
    for r in results:
        for v in r.get("values") or []:
            raw = v.get("value")
            if isinstance(raw, str) and "." in raw:   # selector 前缀值不是返回枚举
                continue
            name = md(v.get("name"))
            desc = md(v.get("description"))
            text = name or desc
            if desc and desc != name:
                text = "%s（%s）" % (name, desc) if name else desc
            labels.append("`%s`→%s" % (raw, text))
    return labels


def capability_section(cid, item, notes_by_cap):
    """一条能力 = 一个 ### 段落：简介 / 签名 / 返回 / 参数与其取值意义 / 备注。"""
    cap, ops = item["cap"], item["ops"]
    lines = ["### `abilities.%s` · %s" % (cid, md(cap.get("displayName") or cid)), ""]
    desc = md(cap.get("description"))
    if desc:
        lines += [desc, ""]
    lines.append("- **能力 id**：`%s`　·　**目录文件**：`%s`" % (cid, item["file"]))
    phrases = [md(x) for x in (cap.get("phrases") or []) if md(x)]
    if phrases:
        lines.append("- **常见说法**：" + "、".join("`%s`" % x for x in phrases[:12]))
    for opn, verb, ret in (("get", "read", "SensorValue"), ("set", "write", "void")):
        op = ops.get(opn)
        if not op:
            lines.append("- **%s**：不提供" % verb)
            continue
        sig = "%s(%s)" % (verb, ", ".join("%s %s" % (t, n) for n, (t, _) in collect_params(op)))
        d = md(op.get("description"))
        lines.append("- **%s** → `%s`：`%s`%s" % (verb, ret, sig, (" — " + d) if d else ""))

    results = collect_doc_results(item)
    if results:
        fields = "；".join("`%s` %s" % (r["name"], r["description"].rstrip("。"))
                          for r in results[:8] if r["description"])
        units = [md(short(r["unit"])) for r in results if r.get("unit")]
        for r in results:
            for rg in r.get("ranges") or []:
                if rg.get("unit"):
                    units.append(md(short(rg["unit"])))
        units = sorted(set(x for x in units if x))
        tail = ""
        if fields:
            tail += "。目录返回字段：" + fields + "。"
        if units:
            tail += "单位 `" + "`/`".join(units) + "`。"
        lines.append("- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、"
                     "`featureRaws`/`featureNames` 全部读字段原始值）" + (tail or "。"))
        labels = enum_labels(results)
        if labels:
            lines.append("- **返回值含义**：" + "、".join(labels[:24]) + ("…" if len(labels) > 24 else ""))

    params = collect_doc_params(item)
    if params:
        lines.append("- **参数**：")
        for p in params:
            head = "`%s`（%s" % (p["name"], p["jt"])
            if p.get("role"):
                head += "，%s" % p["role"]
            head += "，%s）：%s" % ("/".join(p["ops"]), p["description"])
            lines.append("  - " + head)
            detail = []
            if p.get("unit"):
                detail.append("单位 `" + md(short(p["unit"])) + "`")
            if p["values"]:
                vals = [param_value_text(v, p["jt"]) for v in p["values"]]
                detail.append("可选值 " + "；".join(vals[:20]) + ("…" if len(vals) > 20 else ""))
            if p["ranges"]:
                detail.append("合法范围 " + "、".join(range_text(r) for r in p["ranges"]))
            if not p["values"] and not p["ranges"]:
                if p["jt"] == "boolean":
                    detail.append("可选值 `true`（是）/ `false`（否），具体含义见参数说明")
                else:
                    detail.append("目录未定义取值约束，按 `%s` 传入" % p["jt"])
            if detail:
                lines.append("    - " + "；".join(detail))
    else:
        lines.append("- **参数**：无（调用时不需要传参）。")

    notes = [md(x) for x in (cap.get("notes") or []) if md(x)]
    notes += [md(x) for x in (notes_by_cap.get(cid) or [])]
    if notes:
        lines.append("- **备注**：" + "；".join(x.rstrip("。；") for x in notes) + "。")
    lines.append("")
    return lines


RUNTIME_TEMPLATE = '''package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;
import me.guanming.vehicle.infolib.Vehicle;
import me.guanming.vehicle.infolib.VehicleProtocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 能力类共享的底层运行时。自动生成，请勿手改。
 *
 * <p>目录中有一部分 feature 只写了 framework 常量名、没有数字 id。首次加载任一能力类时，
 * 本类的静态初始化会一次性反射解析这些常量，结果只保存在本次运行的内存里，
 * 不导出静态表、不落盘。
 */
public final class Runtime {
    private Runtime() {}

    private static final String BASE = "android.hardware.bydauto.BYDAutoFeatureIds";

    /** 编译期清单：name|owner|auditedId(0 表示目录中无审计值)。 */
    private static final String[] FEATURE_INDEX = {
__FEATURE_INDEX__
    };

    private static final ConcurrentHashMap<String, Integer> IDS =
            new ConcurrentHashMap<String, Integer>();

    private static volatile int profileZones = -1;
    private static volatile int profileSeatHeat = -1;
    private static volatile int profileSteerHeat = -1;

    /** 初始化：批量采集一次，供本次运行时使用。 */
    static {
        collect();
    }

    /**
     * 反射读取 {@code BYDAutoFeatureIds} / {@code BYDAutoFeatureIds$<owner>} 中的
     * {@code public static final int} 常量。与原应用 nu.java / ps.java 的解析顺序一致：
     * 先顶层类，再 owner 内部类，最后回退到目录里的审计 id。
     */
    static synchronized void collect() {
        ClassLoader cl = Runtime.class.getClassLoader();
        if (cl == null) cl = ClassLoader.getSystemClassLoader();
        Class<?> top = loadClass(BASE, cl);
        Map<String, Class<?>> nested = new HashMap<String, Class<?>>();
        for (String entry : FEATURE_INDEX) {
            String[] p = entry.split("\\\\|", -1);
            String name = p[0], owner = p[1];
            int audited = Integer.parseInt(p[2]);
            Integer id = readField(top, name);
            if (id == null) {
                if (!nested.containsKey(owner)) {
                    nested.put(owner, loadClass(BASE + "$" + owner, cl));
                }
                id = readField(nested.get(owner), name);
            }
            if ((id == null || id.intValue() == 0 || id.intValue() == -1) && audited > 0) {
                id = Integer.valueOf(audited);
            }
            if (id != null && id.intValue() != 0 && id.intValue() != -1) {
                IDS.put(name + "@" + owner, id);
            }
        }
    }

    private static Class<?> loadClass(String name, ClassLoader cl) {
        try {
            return Class.forName(name, false, cl);
        } catch (Throwable t) {
            return null;
        }
    }

    private static Integer readField(Class<?> cls, String field) {
        if (cls == null) return null;
        try {
            Field f = cls.getField(field);
            Class<?> t = f.getType();
            if (t != Integer.TYPE && t != Integer.class) return null;
            return Integer.valueOf(f.getInt(null));
        } catch (Throwable e) {
            return null;
        }
    }

    /** 取得 feature 数字 id。查不到时重新采集一次（应对初始化早于 Vehicle.init 的情况）。 */
    public static int fid(String name, String owner) {
        String key = name + "@" + owner;
        Integer id = IDS.get(key);
        if (id == null) {
            collect();
            id = IDS.get(key);
        }
        if (id == null) {
            throw new IllegalStateException("BYD feature id unavailable: " + name
                    + " (owner=" + owner + ") - framework constant not found and no audited id");
        }
        return id.intValue();
    }

    /** 一次性设置车型配置；含 profiles 分支的能力必须先调用。0 表示 UNKNOWN/通用。 */
    public static void setVehicleProfile(int climateZones, int seatHeating, int steeringWheelHeating) {
        if (climateZones < 0 || seatHeating < 0 || steeringWheelHeating < 0) {
            throw new IllegalArgumentException("VehicleProfile values must be >= 0");
        }
        profileZones = climateZones;
        profileSeatHeat = seatHeating;
        profileSteerHeat = steeringWheelHeating;
    }

    /** 含 profiles 分支的能力在方法入口调用，避免静默选错车型分支。 */
    public static void requireProfile(String capabilityId) {
        if (profileZones < 0) {
            throw new IllegalStateException("VehicleProfile not set: call "
                    + "Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating) "
                    + "before using " + capabilityId);
        }
    }

    public static boolean profileAllows(String field, int... allowed) {
        int cur;
        if ("climateZones".equals(field)) cur = profileZones;
        else if ("seatHeating".equals(field)) cur = profileSeatHeat;
        else if ("steeringWheelHeating".equals(field)) cur = profileSteerHeat;
        else throw new IllegalArgumentException("Unknown VehicleProfile field: " + field);
        if (cur < 0) return false;
        for (int a : allowed) if (a == cur) return true;
        return false;
    }

    public static int readFid(int dev, int fid, int tx) {
        Integer v = Vehicle.read(dev, fid, tx == 7 ? VehicleProtocol.BYD_TX_READ_FLOAT
                                                  : VehicleProtocol.BYD_TX_READ_INT);
        if (v == null) throw new IllegalStateException(
                "Vehicle.read failed (not initialized?): dev=" + dev + " fid=" + fid);
        return v;
    }

    public static void writeFid(int dev, int fid, int raw) {
        if (!Vehicle.write(dev, fid, raw)) throw new IllegalStateException(
                "Vehicle.write failed: dev=" + dev + " fid=" + fid + " raw=" + raw);
    }

    public static boolean isSentinel(int raw) {
        return raw == -10011 || raw == -10013 || raw == 65535 || raw == 1048575;
    }

    public static void checkSelector(String name, String v, String... allowed) {
        if (v != null) for (String a : allowed) if (a.equals(v)) return;
        StringBuilder sb = new StringBuilder("Invalid ").append(name).append("='").append(v)
                .append("'; allowed: ");
        for (int i = 0; i < allowed.length; i++) sb.append(i == 0 ? "" : ", ").append(allowed[i]);
        throw new IllegalArgumentException(sb.toString());
    }

    public static void checkRange(String name, int v, int min, int max) {
        if (v < min || v > max) throw new IllegalArgumentException(
                name + "=" + v + " out of range [" + min + ", " + max + "]");
    }

    public static void checkEnum(String name, int v, int... allowed) {
        for (int a : allowed) if (a == v) return;
        StringBuilder sb = new StringBuilder("Invalid ").append(name).append("=").append(v)
                .append("; allowed: ");
        for (int i = 0; i < allowed.length; i++) sb.append(i == 0 ? "" : ", ").append(allowed[i]);
        throw new IllegalArgumentException(sb.toString());
    }

    public static void checkAnyRange(String name, int v, int... bounds) {
        for (int i = 0; i + 1 < bounds.length; i += 2)
            if (v >= bounds[i] && v <= bounds[i + 1]) return;
        throw new IllegalArgumentException(name + "=" + v + " outside all allowed ranges");
    }

    public static boolean inStr(String v, String... set) { for (String s : set) if (s.equals(v)) return true; return false; }
    public static boolean inInt(int v, int... set) { for (int s : set) if (s == v) return true; return false; }
    public static boolean inBool(boolean v, boolean... set) { for (boolean s : set) if (s == v) return true; return false; }

    public static SensorValue value(int raw, double decoded, String unit, String text,
                                    int[] featureRaws, String[] featureNames) {
        return SensorValue.of(raw, decoded, unit, text, featureRaws, featureNames);
    }
}
'''


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--catalog", required=True)
    ap.add_argument("--out", required=True)
    ap.add_argument("--doc", required=True)
    ap.add_argument("--report", required=True)
    a = ap.parse_args()

    caps, name2id = load_catalog(a.catalog)
    feature_index = collect_feature_index(caps, name2id)
    stats = collections.Counter()
    notes_by_cap = {}
    profile_caps = []
    for cid, it in caps.items():
        notes_by_cap[cid] = cap_notes(cid, it["ops"])
        if any(c.get("profiles") for op in it["ops"].values() for c in op.get("cases", [])):
            profile_caps.append(cid)

    pkg_root = os.path.join(a.out, "me", "guanming", "vehicle", "infolib", "abilities")
    os.makedirs(pkg_root, exist_ok=True)

    # Runtime.java —— 注入编译期常量清单
    index_lines = ",\n".join("        %s" % jstr("%s|%s|%d" % (n, o, aud))
                             for n, o, aud in feature_index)
    runtime = RUNTIME_TEMPLATE.replace("__FEATURE_INDEX__", index_lines)
    open(os.path.join(pkg_root, "Runtime.java"), "w", encoding="utf-8").write(runtime)

    # 清理上一版生成物：旧结构是 abilities/<一级id>/<Class>.java，
    # 新结构里一级 id 变成顶层类，同名子包必须删除，否则 javac 会报包/类冲突。
    stats["removed_old_files"] = clean_generated(pkg_root)

    tree = build_tree(caps)
    top_files = []
    for top in sorted(tree):
        if not is_ident(top):
            stats["invalid_ident"] += 1
            continue
        body = ["package me.guanming.vehicle.infolib.abilities;", "",
                "import me.guanming.vehicle.infolib.SensorValue;", ""]
        body += gen_node(top, tree[top], 0, caps, stats, notes_by_cap, profile_caps)
        open(os.path.join(pkg_root, top + ".java"), "w", encoding="utf-8").write("\n".join(body) + "\n")
        top_files.append(top)

    # API 文档
    doc = ["# abilities API 参考", "",
           f"共 **{len(caps)}** 个能力，收敛在 **{len(top_files)}** 个顶层静态类中。"
           "所有类均为 `final`、构造私有，嵌套类为 `public static final`，方法全部 `static`。", "",
           "## 类结构", "",
           "`abilities` 是唯一包；capabilityId 的**第一段是顶层类**，其余每段是一个静态嵌套类，"
           "多段 id 不再压缩。例如 `bodywork.door.open` → "
           "`abilities` 包 → `bodywork` 类 → `door` 嵌套类 → `open` 嵌套类 → `read(area)` 方法。", "",
           "```javascript", "runtime.loadDex('./classes.dex');",
           "const A = Packages.me.guanming.vehicle.infolib.abilities;",
           "const R = Packages.me.guanming.vehicle.infolib.abilities.Runtime;",
           "R.setVehicleProfile(4, 3, 3);              // 含 profiles 分支的能力需要，启动时设一次",
           "",
           "A.ac.cabin_temperature.read();              // ac=类，cabin_temperature=嵌套类",
           "A.ac.temperature.write('driver', 24, 'celsius');",
           "A.bodywork.door.open.read(area);            // bodywork=类，door=嵌套类，open=嵌套类",
           "```", "",
           "旧版把 3 段 id 压成两级（如 `bodywork.door_open`），现已全部展开；"
           "13 个 3 段 id 都在 `bodywork` 下。类路径就是 `abilities.<capabilityId>`。", "",
           "> Auto.js / Rhino 通常用点号访问嵌套类。若你的运行时版本不支持，"
           "可尝试 JVM 二进制名 `A['bodywork$door$open']` 或逐层取 `A.bodywork.door`；"
           "这一点需在目标 Auto.js 版本上实测（本机无法验证 Rhino 解析）。", "",
           "## 调用约定", "",
           "- `read(...)` 返回 `SensorValue`；读取失败/sentinel 返回 `SensorValue.failed()`（不抛异常）。",
           "- `write(...)` 返回 `void`；参数非法或底层写入失败直接抛异常。",
           "- selector / enum 字符串**只接受短名**（`driver`、`celsius`），传全名会抛异常并在消息里列出合法值。",
           "- 数字参数按目录 `values` / `ranges` 校验；越界抛异常。", "",
           "## 初始化与车型配置", "",
           f"目录里有 **{len(feature_index)}** 个 feature 只写了 BYD framework 常量名，没有数字 id。"
           "首次加载任一能力类时，`Runtime` 的静态初始化会自动批量反射解析一次：", "",
           "```java",
           "// 解析顺序与原应用一致：BYDAutoFeatureIds -> BYDAutoFeatureIds$<owner> -> 目录里已审计的 id",
           "// 结果只保存在本次运行时的内存缓存中，不导出静态表、不落盘、不产生额外部署步骤。",
           "```", "",
           "该反射读取的是 framework 的 `public static final int` 静态常量，不需要平台签名；"
           "建议宿主仍先调用 `Vehicle.init(context)`（它会解除隐藏 API 限制并启动 daemon）。"
           "若某个常量在初始化时不可用，首次用到它的 `read/write` 会重新采集一次；仍失败则抛"
           "`IllegalStateException` 并带上常量名与 owner，不会静默跳过字段。", "",
           f"另有 **{len(profile_caps)}** 个能力含车型 `profiles` 分支（climateZones / seatHeating /"
           " steeringWheelHeating）。这些信息原本由调用方通过协议传入，本库无法从系统属性推断，"
           "因此需宿主在启动时一次性设置：", "",
           "```javascript",
           "const R = Packages.me.guanming.vehicle.infolib.abilities.Runtime;",
           "R.setVehicleProfile(4, 3, 3);   // 4 温区 / 座椅加热最高 3 档 / 方向盘加热最高 3 档",
           "```", "",
           "未设置时这些能力的 `read/write` 会抛明确异常，而不是像旧版那样静默取首个匹配 case。"
           "参数含义：`climateZones` 取 2/3/4/5（0 表示 UNKNOWN，目录里个别通用分支用 0），"
           "`seatHeating` / `steeringWheelHeating` 取该车型的最高档位数。", "",
           "## 需要车型配置的能力", "",
           f"以下 **{len(profile_caps)}** 个能力含车型 `profiles` 分支，未设置车型配置时 "
           "`read`/`write` 会抛异常：", ""]
    for cid in sorted(profile_caps):
        doc.append(f"- `{cid}`")
    doc += ["", "## 能力详解", "",
            f"按 capabilityId 字典序排列，共 **{len(caps)}** 条。每条给出能力简介、`read`/`write` 签名、"
            "返回值说明、参数介绍、每个参数值的含义、常见说法与备注。参数值一律按 **API 实际接受的字面量**"
            "书写：字符串 selector/enum 用短名（如 `driver`、`celsius`），数字型 selector 用目录里的整数"
            "（如 `1`）；`—` 后面是该值的中文含义。", ""]
    for cid in sorted(caps):
        if not all(is_ident(s) for s in cid.split(".")):
            continue
        doc += capability_section(cid, caps[cid], notes_by_cap)
    doc += ["---", "",
            "3 段 id（如 `bodywork.window.position`、`bodywork.door.open`）展开为两级嵌套类，"
            "不再压缩成 `window_position` / `door_open`。", ""]
    open(a.doc, "w", encoding="utf-8").write("\n".join(doc) + "\n")

    report = {"capabilities": len(caps), "topLevelClasses": len(top_files),
              "stats": dict(stats),
              "runtimeFeatureIndex": [{"name": n, "owner": o, "auditedId": aud}
                                      for n, o, aud in feature_index],
              "profileCapabilities": sorted(profile_caps),
              "notes": {k: v for k, v in notes_by_cap.items() if v},
              "classes": [{"class": "abilities." + cid, "capabilityId": cid}
                          for cid in sorted(caps)]}
    os.makedirs(os.path.dirname(a.report), exist_ok=True)
    json.dump(report, open(a.report, "w", encoding="utf-8"), ensure_ascii=False, indent=2)
    print("capabilities:", len(caps), "top-level classes:", len(top_files))
    print("runtime feature index:", len(feature_index))
    print("profile capabilities:", len(profile_caps))
    print("stats:", dict(stats))
    print("doc:", a.doc)


if __name__ == "__main__":
    main()
