# abilities API 参考

共 **206** 个能力，收敛在 **27** 个顶层静态类中。所有类均为 `final`、构造私有，嵌套类为 `public static final`，方法全部 `static`。

## 类结构

`abilities` 是唯一包；capabilityId 的**第一段是顶层类**，其余每段是一个静态嵌套类，多段 id 不再压缩。例如 `bodywork.door.open` → `abilities` 包 → `bodywork` 类 → `door` 嵌套类 → `open` 嵌套类 → `read(area)` 方法。

```javascript
runtime.loadDex('./classes.dex');
const A = Packages.me.guanming.vehicle.infolib.abilities;
const R = Packages.me.guanming.vehicle.infolib.abilities.Runtime;
R.setVehicleProfile(4, 3, 3);              // 含 profiles 分支的能力需要，启动时设一次

A.ac.cabin_temperature.read();              // ac=类，cabin_temperature=嵌套类
A.ac.temperature.write('driver', 24, 'celsius');
A.bodywork.door.open.read(area);            // bodywork=类，door=嵌套类，open=嵌套类
```

旧版把 3 段 id 压成两级（如 `bodywork.door_open`），现已全部展开；13 个 3 段 id 都在 `bodywork` 下。类路径就是 `abilities.<capabilityId>`。

> Auto.js / Rhino 通常用点号访问嵌套类。若你的运行时版本不支持，可尝试 JVM 二进制名 `A['bodywork$door$open']` 或逐层取 `A.bodywork.door`；这一点需在目标 Auto.js 版本上实测（本机无法验证 Rhino 解析）。

## 调用约定

- `read(...)` 返回 `SensorValue`；读取失败/sentinel 返回 `SensorValue.failed()`（不抛异常）。
- `write(...)` 返回 `void`；参数非法或底层写入失败直接抛异常。
- selector / enum 字符串**只接受短名**（`driver`、`celsius`），传全名会抛异常并在消息里列出合法值。
- 数字参数按目录 `values` / `ranges` 校验；越界抛异常。

## 初始化与车型配置

目录里有 **34** 个 feature 只写了 BYD framework 常量名，没有数字 id。首次加载任一能力类时，`Runtime` 的静态初始化会自动批量反射解析一次：

```java
// 解析顺序与原应用一致：BYDAutoFeatureIds -> BYDAutoFeatureIds$<owner> -> 目录里已审计的 id
// 结果只保存在本次运行时的内存缓存中，不导出静态表、不落盘、不产生额外部署步骤。
```

该反射读取的是 framework 的 `public static final int` 静态常量，不需要平台签名；建议宿主仍先调用 `Vehicle.init(context)`（它会解除隐藏 API 限制并启动 daemon）。若某个常量在初始化时不可用，首次用到它的 `read/write` 会重新采集一次；仍失败则抛`IllegalStateException` 并带上常量名与 owner，不会静默跳过字段。

另有 **10** 个能力含车型 `profiles` 分支（climateZones / seatHeating / steeringWheelHeating）。这些信息原本由调用方通过协议传入，本库无法从系统属性推断，因此需宿主在启动时一次性设置：

```javascript
const R = Packages.me.guanming.vehicle.infolib.abilities.Runtime;
R.setVehicleProfile(4, 3, 3);   // 4 温区 / 座椅加热最高 3 档 / 方向盘加热最高 3 档
```

未设置时这些能力的 `read/write` 会抛明确异常，而不是像旧版那样静默取首个匹配 case。参数含义：`climateZones` 取 2/3/4/5（0 表示 UNKNOWN，目录里个别通用分支用 0），`seatHeating` / `steeringWheelHeating` 取该车型的最高档位数。

## 需要车型配置的能力

以下 **10** 个能力含车型 `profiles` 分支，未设置车型配置时 `read`/`write` 会抛异常：

- `ac.airflow_mode`
- `ac.temperature`
- `ac.zone_fan_level`
- `ac.zone_power`
- `setting.seat_heating`
- `setting.seat_heating_off`
- `setting.seat_ventilation`
- `setting.seat_ventilation_off`
- `setting.steering_wheel_heating`
- `setting.steering_wheel_heating_level`

## 能力详解

按 capabilityId 字典序排列，共 **206** 条。每条给出能力简介、`read`/`write` 签名、返回值说明、参数介绍、每个参数值的含义、常见说法与备注。参数值一律按 **API 实际接受的字面量**书写：字符串 selector/enum 用短名（如 `driver`、`celsius`），数字型 selector 用目录里的整数（如 `1`）；`—` 后面是该值的中文含义。

### `abilities.ac.airflow_mode` · 吹风模式

选择出风口对着人吹、避开人吹，或左右扫风。

- **能力 id**：`ac.airflow_mode`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`吹风模式`、`对人吹`、`避人吹`、`扫风`
- **read** → `SensorValue`：`read(String area)` — 读取吹风模式。
- **write** → `void`：`write(String area, int value)` — 设置吹风模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`direct` 是否对人吹；`avoid` 是否避人吹；`sweep` 是否扫风。
- **参数**：
  - `area`（String，selector，read/write）：空调区域。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
  - `value`（int，value，write）：吹风模式。
    - 可选值 `1` — 对人吹；`2` — 避人吹；`3` — 扫风
- **备注**：三种模式各自返回是否开启，设置某一模式时不会主动关闭其他模式；只能直接选择某一种模式，不支持切换到下一个；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.cabin_temperature` · 车内温度

车内当前温度（摄氏度）。

- **能力 id**：`ac.cabin_temperature`　·　**目录文件**：`ac_temperature.json`
- **常见说法**：`车内温度`
- **read** → `SensorValue`：`read()` — 读取车内温度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 车内温度；`unit` 温度单位（摄氏度）。单位 `celsius`。
- **参数**：无（调用时不需要传参）。

### `abilities.ac.close_passenger_vent` · 关闭副驾出风

关闭副驾一侧的出风口。

- **能力 id**：`ac.close_passenger_vent`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`关闭副驾出风`、`关闭副驾风口`
- **read**：不提供
- **write** → `void`：`write()` — 关闭副驾出风。
- **参数**：无（调用时不需要传参）。

### `abilities.ac.compressor` · AC 模式

打开或关闭空调的 AC 制冷功能，不会关闭空调本身。

- **能力 id**：`ac.compressor`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`AC 模式`、`AC 开关`、`压缩机`
- **read** → `SensorValue`：`read()` — 读取 AC 模式是否开启。
- **write** → `void`：`write(boolean value)` — 打开或关闭 AC 模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启 AC 模式。
- **参数**：
  - `value`（boolean，value，write）：是否开启 AC 模式。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.control_mode` · 自动模式

打开或关闭空调自动模式，由空调自动调节风量和出风。

- **能力 id**：`ac.control_mode`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`自动模式`、`空调自动`
- **read** → `SensorValue`：`read(String area)` — 读取自动模式。
- **write** → `void`：`write(String area, int value)` — 设置自动模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前空调模式。
- **返回值含义**：`1`→AUTO（自动）、`2`→MANUAL（手动）
- **参数**：
  - `area`（String，selector，read/write）：空调区域。
    - 可选值 `frontPair` — 前排；`rearCabin` — 后排
  - `value`（int，value，write）：空调模式。
    - 可选值 `1` — 自动；`2` — 手动
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.cycle_mode` · 内外循环

切换空调内循环、外循环或自动循环。

- **能力 id**：`ac.cycle_mode`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`内外循环`、`内循环`、`外循环`、`自动循环`
- **read** → `SensorValue`：`read()` — 读取内外循环。
- **write** → `void`：`write(int value)` — 设置内外循环。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前循环模式。
- **返回值含义**：`1`→OUTSIDE_AIR（外循环）、`2`→RECIRCULATION（内循环）、`3`→AUTO（自动循环）
- **参数**：
  - `value`（int，value，write）：循环模式。
    - 可选值 `1` — 外循环；`2` — 内循环；`3` — 自动循环
- **备注**：自动循环需要车辆支持；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.defrost` · 除霜

打开或关闭前挡风玻璃除霜或后窗电除霜。

- **能力 id**：`ac.defrost`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`除霜`、`前除霜`、`后除霜`、`电除霜`
- **read** → `SensorValue`：`read(String area)` — 读取除霜是否开启。
- **write** → `void`：`write(String area, boolean value)` — 打开或关闭除霜。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启除霜。
- **参数**：
  - `area`（String，selector，read/write）：除霜位置。
    - 可选值 `frontWindshield` — 前挡风玻璃；`rearWindshield` — 后窗
  - `value`（boolean，value，write）：是否开启除霜。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分车型开启后窗除霜时会同时加热后视镜；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.maximum_cooling` · 最大制冷

打开或关闭最大制冷，空调以最强制冷运行。

- **能力 id**：`ac.maximum_cooling`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`最大制冷`、`快速制冷`
- **read** → `SensorValue`：`read()` — 读取最大制冷是否开启。
- **write** → `void`：`write(boolean value)` — 打开或关闭最大制冷。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启最大制冷。
- **参数**：
  - `value`（boolean，value，write）：是否开启最大制冷。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.ac.mirror_heating` · 后视镜加热

打开或关闭后视镜加热。

- **能力 id**：`ac.mirror_heating`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`后视镜加热`、`外后视镜加热`、`打开镜加热`、`关闭镜加热`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 打开或关闭后视镜加热。
- **参数**：
  - `value`（boolean，value，write）：是否开启后视镜加热。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：使用后窗电除霜通道，可能同时控制后窗加热；后视镜加热效果取决于车型；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.outside_temperature` · 车外温度

车外当前温度（摄氏度）。

- **能力 id**：`ac.outside_temperature`　·　**目录文件**：`ac_temperature.json`
- **常见说法**：`车外温度`、`室外温度`
- **read** → `SensorValue`：`read()` — 读取车外温度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 车外温度；`unit` 温度单位（摄氏度）。单位 `celsius`。
- **参数**：无（调用时不需要传参）。

### `abilities.ac.perfume_concentration` · 香氛浓度

设置香氛浓度，或打开、关闭香氛。

- **能力 id**：`ac.perfume_concentration`　·　**目录文件**：`ac_fragrance.json`
- **常见说法**：`香氛浓度`、`香氛开关`
- **read** → `SensorValue`：`read()` — 读取香氛浓度。
- **write** → `void`：`write(int value)` — 设置香氛浓度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前香氛浓度或开关状态。
- **返回值含义**：`1`→LOWEST（最低浓度）、`2`→ON（开启）、`3`→HIGHEST（最高浓度）、`4`→OFF（关闭）
- **参数**：
  - `value`（int，value，write）：香氛浓度或开关。
    - 可选值 `1` — 最低浓度；`2` — 开启；`3` — 最高浓度；`4` — 关闭

### `abilities.ac.perfume_type` · 香氛种类

选择使用 1 号、2 号或 3 号香氛。

- **能力 id**：`ac.perfume_type`　·　**目录文件**：`ac_fragrance.json`
- **常见说法**：`香氛种类`、`切换香氛`
- **read** → `SensorValue`：`read()` — 读取当前使用的香氛。
- **write** → `void`：`write(int value)` — 选择使用的香氛。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前使用的香氛。
- **返回值含义**：`1`→TYPE_1（1 号香氛）、`2`→TYPE_2（2 号香氛）、`3`→TYPE_3（3 号香氛）
- **参数**：
  - `value`（int，value，write）：要使用的香氛。
    - 可选值 `1` — 1 号香氛；`2` — 2 号香氛；`3` — 3 号香氛
- **备注**：具体香型取决于车内安装的香氛。

### `abilities.ac.power` · 空调开关

打开或关闭空调。

- **能力 id**：`ac.power`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`空调开关`、`打开空调`、`关闭空调`、`后排空调`
- **read** → `SensorValue`：`read(String area)` — 读取空调是否开启。
- **write** → `void`：`write(String area, boolean value)` — 打开或关闭空调。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启空调。
- **参数**：
  - `area`（String，selector，read/write）：空调区域。
    - 可选值 `frontPair` — 前排空调；`rearCabin` — 后排空调
  - `value`（boolean，value，write）：是否开启空调。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：后排空调需要车辆配备后排空调；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.rear_lock_state` · 后排空调锁

锁定或解锁后排空调面板，锁定后后排无法操作空调。

- **能力 id**：`ac.rear_lock_state`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`后排空调锁`、`锁定后排空调`
- **read** → `SensorValue`：`read()` — 读取后排空调是否锁定。
- **write** → `void`：`write(boolean value)` — 锁定或解锁后排空调。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否锁定后排空调。
- **参数**：
  - `value`（boolean，value，write）：是否锁定后排空调。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.ac.rear_max_fan_level` · 后排最高风量档位

后排空调支持的最高风量档位。

- **能力 id**：`ac.rear_max_fan_level`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`后排最高风量档位`
- **read** → `SensorValue`：`read()` — 读取后排最高风量档位。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 后排最高风量档位。
- **返回值含义**：`1`→MAX_LEVEL_4（最高 4 档）、`2`→MAX_LEVEL_7（最高 7 档）
- **参数**：无（调用时不需要传参）。

### `abilities.ac.separate_control` · 分控模式

打开或关闭温度分控，开启后各区域可以设置不同温度。

- **能力 id**：`ac.separate_control`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`分控模式`、`温度分控`
- **read** → `SensorValue`：`read()` — 读取分控模式。
- **write** → `void`：`write(boolean value)` — 打开或关闭分控模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前分控状态。
- **返回值含义**：`0`→OFF（关闭）、`1`→ON（开启）、`3`→SINGLE_RANGE（单一温控）
- **参数**：
  - `value`（boolean，value，write）：是否开启分控。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.synchronization` · 整车同步

打开或关闭整车温度同步。

- **能力 id**：`ac.synchronization`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`整车同步`、`温度同步`
- **read** → `SensorValue`：`read()` — 读取整车同步是否开启。
- **write** → `void`：`write(boolean value)` — 打开或关闭整车同步。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启整车同步。
- **参数**：
  - `value`（boolean，value，write）：是否开启整车同步。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：同步的范围取决于车型。

### `abilities.ac.temperature` · 空调温度

设置或读取空调目标温度，可按区域分别设置。

- **能力 id**：`ac.temperature`　·　**目录文件**：`ac_temperature.json`
- **常见说法**：`空调温度`、`调温度`、`主驾温度`、`副驾温度`
- **read** → `SensorValue`：`read(String area)` — 读取空调目标温度。
- **write** → `void`：`write(String area, int value, String unit)` — 设置空调目标温度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 目标温度；`unit` 温度单位。单位 `celsius`/`fahrenheit`。
- **参数**：
  - `area`（String，selector，read/write）：空调区域。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearCabin` — 后排；`rearLeft` — 左后；`rearRight` — 右后；`thirdRow` — 第三排；`frontPair` — 前排；`allCabin` — 全车
  - `value`（int，value，write）：目标温度。
    - 合法范围 `17~33`（celsius）、`64~91`（fahrenheit）
  - `unit`（String，unit，write）：温度单位。
    - 可选值 `celsius` — 摄氏度；`fahrenheit` — 华氏度
- **备注**：只调整所选区域，不会自动打开分控或整车同步；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.temperature_unit` · 温度单位

车辆当前使用的温度单位。

- **能力 id**：`ac.temperature_unit`　·　**目录文件**：`ac_temperature.json`
- **常见说法**：`温度单位`、`摄氏度`、`华氏度`
- **read** → `SensorValue`：`read()` — 读取温度单位。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`unit` 温度单位。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.ventilation` · 驻车通风

打开或关闭驻车通风，停车时为车内通风换气。

- **能力 id**：`ac.ventilation`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`驻车通风`
- **read** → `SensorValue`：`read()` — 读取驻车通风是否开启。
- **write** → `void`：`write(boolean value)` — 打开或关闭驻车通风。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启驻车通风。
- **参数**：
  - `value`（boolean，value，write）：是否开启驻车通风。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.wind_mode` · 出风模式

选择空调出风位置：吹面、吹脚，或与除霜组合。

- **能力 id**：`ac.wind_mode`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`出风模式`、`吹面`、`吹脚`、`前排出风`、`后排出风`
- **read** → `SensorValue`：`read(String area)` — 读取出风模式。
- **write** → `void`：`write(String area, int value)` — 设置出风模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前出风模式。
- **返回值含义**：`1`→FACE（吹面）、`2`→FACE_FOOT（吹面吹脚）、`3`→FOOT（吹脚）、`4`→FOOT_DEFROST（除霜吹脚）、`5`→DEFROST（除霜）、`6`→FACE_FOOT_DEFROST（除霜吹面吹脚）、`7`→FACE_DEFROST（除霜吹面）
- **参数**：
  - `area`（String，selector，read/write）：空调区域。
    - 可选值 `frontPair` — 前排；`rearCabin` — 后排
  - `value`（int，value，write）：出风模式。
    - 可选值 `1` — 吹面；`2` — 吹面吹脚；`3` — 吹脚；`4` — 除霜吹脚；`5` — 除霜；`6` — 除霜吹面吹脚；`7` — 除霜吹面
- **备注**：与“吹风模式”不同：这里选的是出风位置，不是对人吹或避人吹；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.zone_fan_level` · 风量

调节空调风量档位，可按区域分别设置。

- **能力 id**：`ac.zone_fan_level`　·　**目录文件**：`ac_airflow.json`
- **常见说法**：`风量`、`风速`、`风量档位`
- **read** → `SensorValue`：`read(String area)` — 读取风量档位。
- **write** → `void`：`write(String area, int value)` — 设置风量档位。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前风量档位，0 表示风量关闭。
- **参数**：
  - `area`（String，selector，read/write）：空调区域。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearCabin` — 后排；`rearLeft` — 左后；`rearRight` — 右后；`thirdRow` — 第三排；`frontPair` — 前排；`allCabin` — 全车
  - `value`（int，value，write）：风量档位。
    - 合法范围 `1~7`
- **备注**：两温区、三温区车型的主驾和副驾共用前排风量；调节风量不会自动打开空调；关闭某个区域请用“温区开关”；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.ac.zone_power` · 温区开关

单独打开或关闭某个温区的空调。

- **能力 id**：`ac.zone_power`　·　**目录文件**：`ac_operation.json`
- **常见说法**：`温区开关`、`分区空调开关`
- **read**：不提供
- **write** → `void`：`write(String area, boolean value)` — 打开或关闭温区空调。
- **参数**：
  - `area`（String，selector，write）：空调区域。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后；`thirdRow` — 第三排
  - `value`（boolean，value，write）：是否开启该温区。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：仅四温区、五温区车型支持；第三排仅五温区；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.adas.acc_cruise_state` · 自适应巡航状态

接收自适应巡航的工作状态变化。

- **能力 id**：`adas.acc_cruise_state`　·　**目录文件**：`adas.json`
- **常见说法**：`自适应巡航状态`
- **read** → `SensorValue`：`read()`
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 自适应巡航状态。
- **返回值含义**：`0`→NOT_CRUISING_0（未在巡航）、`1`→NOT_CRUISING_1（未在巡航）、`2`→NOT_CRUISING_2（未在巡航）、`3`→CRUISING_3（巡航中）、`4`→CRUISING_4（巡航中）、`5`→NOT_CRUISING_5（未在巡航）、`6`→CRUISING_6（巡航中）、`7`→CRUISING_7（巡航中）、`8`→NOT_CRUISING_8（未在巡航）、`9`→NOT_CRUISING_9（未在巡航）
- **参数**：无（调用时不需要传参）。
- **备注**：巡航中与未在巡航各含多个工作阶段，具体阶段尚未区分。

### `abilities.adas.aeb_state` · 自动紧急制动

打开或关闭自动紧急制动，并查看其状态。

- **能力 id**：`adas.aeb_state`　·　**目录文件**：`adas.json`
- **常见说法**：`自动紧急制动`
- **read** → `SensorValue`：`read()` — 读取自动紧急制动。
- **write** → `void`：`write(boolean value)` — 打开或关闭自动紧急制动。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 自动紧急制动状态；0 为关闭，1 为开启，其他数值含义尚未确认。
- **参数**：
  - `value`（boolean，write）：是否开启自动紧急制动。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.adas.auto_hold_state` · 自动驻车

打开或关闭自动驻车，并查看驻车工作状态。

- **能力 id**：`adas.auto_hold_state`　·　**目录文件**：`adas.json`
- **常见说法**：`自动驻车`
- **read** → `SensorValue`：`read()` — 读取自动驻车。
- **write** → `void`：`write(boolean value)` — 打开或关闭自动驻车。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 自动驻车状态。
- **返回值含义**：`0`→OFF（已关闭）、`1`→ON（已开启，未驻车）、`2`→HOLDING（驻车中）
- **参数**：
  - `value`（boolean，write）：是否开启自动驻车。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：工作状态包含驻车等阶段，不能只按开关判断。

### `abilities.adas.blind_spot_warning` · 盲区预警

接收车辆左侧或右侧的盲区预警状态。

- **能力 id**：`adas.blind_spot_warning`　·　**目录文件**：`adas.json`
- **常见说法**：`盲区预警`
- **read** → `SensorValue`：`read()`
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 盲区预警状态码；各数值对应的含义尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.adas.brake_foot_sense` · 制动助力模式

选择舒适或运动模式，调整制动踏板的助力感受。

- **能力 id**：`adas.brake_foot_sense`　·　**目录文件**：`adas.json`
- **常见说法**：`制动助力模式`
- **read** → `SensorValue`：`read()` — 读取制动助力模式。
- **write** → `void`：`write(int value)` — 设置制动助力模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前制动助力模式。
- **返回值含义**：`1`→SPORT_OR_STANDARD（运动或标准）、`2`→COMFORT（舒适）
- **参数**：
  - `value`（int，write）：目标制动助力模式。
    - 可选值 `1` — 运动或标准；`2` — 舒适
- **备注**：运动模式在部分车型上称为标准模式。

### `abilities.adas.door_open_warning` · 开门预警

接收车辆左侧或右侧的开门预警状态。

- **能力 id**：`adas.door_open_warning`　·　**目录文件**：`adas.json`
- **常见说法**：`开门预警`
- **read** → `SensorValue`：`read()`
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 开门预警状态码；各数值对应的含义尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.adas.electronic_stability_control` · ESP 车身稳定

打开或关闭车身稳定控制功能。

- **能力 id**：`adas.electronic_stability_control`　·　**目录文件**：`adas.json`
- **常见说法**：`ESP 车身稳定`
- **read** → `SensorValue`：`read()` — 读取ESP 车身稳定。
- **write** → `void`：`write(boolean value)` — 打开或关闭ESP 车身稳定。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启ESP 车身稳定。
- **参数**：
  - `value`（boolean，write）：是否开启ESP 车身稳定。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.adas.forward_collision_warning` · 前向碰撞预警

选择前向碰撞预警的提示时机，或关闭预警。

- **能力 id**：`adas.forward_collision_warning`　·　**目录文件**：`adas.json`
- **常见说法**：`前向碰撞预警`
- **read** → `SensorValue`：`read()` — 读取前向碰撞预警。
- **write** → `void`：`write(int value)` — 设置前向碰撞预警。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 前向碰撞预警状态码；各数值对应的含义尚未确认。
- **参数**：
  - `value`（int，write）：目标前向碰撞预警。
    - 可选值 `1` — 关闭；`2` — 较晚；`3` — 适中；`4` — 较早

### `abilities.adas.front_cross_traffic_alert` · 前方穿行预警

打开或关闭前方穿行预警。

- **能力 id**：`adas.front_cross_traffic_alert`　·　**目录文件**：`adas.json`
- **常见说法**：`前方穿行预警`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 打开或关闭前方穿行预警。
- **参数**：
  - `value`（boolean，write）：是否开启前方穿行预警。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.adas.highway_navigation_state` · 高速领航状态

接收高速领航的工作状态变化。

- **能力 id**：`adas.highway_navigation_state`　·　**目录文件**：`adas.json`
- **常见说法**：`高速领航状态`
- **read** → `SensorValue`：`read()`
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 高速领航状态码；各数值对应的含义尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.adas.hill_descent_control` · 陡坡缓降

打开或关闭陡坡缓降，并查看其工作状态。

- **能力 id**：`adas.hill_descent_control`　·　**目录文件**：`adas.json`
- **常见说法**：`陡坡缓降`
- **read** → `SensorValue`：`read()` — 读取陡坡缓降。
- **write** → `void`：`write(boolean value)` — 打开或关闭陡坡缓降。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前陡坡缓降。
- **返回值含义**：`0`→OFF（关闭且指示灯熄灭）、`1`→ON（开启且指示灯常亮）、`2`→ON_BLINKING（开启且指示灯闪烁）、`3`→OFFLINE（离线）
- **参数**：
  - `value`（boolean，write）：是否开启陡坡缓降。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.adas.intelligent_torque_control` · iTAC 智能扭矩控制

启用或停用车辆的 iTAC 智能扭矩控制。

- **能力 id**：`adas.intelligent_torque_control`　·　**目录文件**：`adas.json`
- **常见说法**：`iTAC 智能扭矩控制`
- **read** → `SensorValue`：`read()` — 读取iTAC 智能扭矩控制。
- **write** → `void`：`write(boolean enabled)` — 设置iTAC 智能扭矩控制是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` iTAC 智能扭矩控制状态。
- **返回值含义**：`0`→ON（已启用）、`1`→OFF（已停用）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启iTAC 智能扭矩控制。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：仅适用于支持普通 iTAC 的车型；与超级 iTAC 的命令不同。

### `abilities.adas.lane_departure_mode` · 车道偏离模式

选择车道偏离预警或车道保持，或关闭该功能。

- **能力 id**：`adas.lane_departure_mode`　·　**目录文件**：`adas.json`
- **常见说法**：`车道偏离模式`
- **read** → `SensorValue`：`read()` — 读取车道偏离模式。
- **write** → `void`：`write(int equipment, int value)` — 设置车道偏离模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车道偏离模式。
- **返回值含义**：`0`→OFF（关闭）、`1`→DEPARTURE_WARNING（车道偏离预警）、`2`→LANE_KEEPING（车道保持）、`3`→ALL（全部）、`4`→ON（开启）
- **参数**：
  - `equipment`（int，selector，write）：车辆的车道辅助配置，创建规则时由用户选择。
    - 可选值 `1` — 低配：偏离预警；`2` — 高配：偏离预警及保持
  - `value`（int，write）：目标车道偏离模式。
    - 可选值 `0` — 关闭；`1` — 车道偏离预警；`2` — 车道保持
- **备注**：低配支持偏离预警，高配还支持车道保持；创建规则时由用户选择车辆配置。

### `abilities.adas.traffic_jam_assist_state` · 交通拥堵辅助

打开或关闭交通拥堵辅助，并查看其工作状态。

- **能力 id**：`adas.traffic_jam_assist_state`　·　**目录文件**：`adas.json`
- **常见说法**：`交通拥堵辅助`
- **read** → `SensorValue`：`read()` — 读取交通拥堵辅助。
- **write** → `void`：`write(boolean value)` — 打开或关闭交通拥堵辅助。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前交通拥堵辅助。
- **返回值含义**：`0`→OFF（关闭）、`1`→INACTIVE（未生效）、`2`→ACTIVE_1（激活状态 1）、`3`→ACTIVE_2（激活状态 2）、`4`→FAULT（故障）
- **参数**：
  - `value`（boolean，write）：是否开启交通拥堵辅助。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.adas.urban_navigation_state` · 城市领航状态

接收城市领航辅助的工作状态变化。

- **能力 id**：`adas.urban_navigation_state`　·　**目录文件**：`adas.json`
- **常见说法**：`城市领航状态`
- **read** → `SensorValue`：`read()`
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 城市领航状态码；各数值对应的含义尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.air_quality.pm25` · PM2.5 浓度

查看车内或车外 PM2.5 浓度，单位为微克每立方米。

- **能力 id**：`air_quality.pm25`　·　**目录文件**：`air_quality.json`
- **常见说法**：`PM2.5 浓度`
- **read** → `SensorValue`：`read(int area)` — 读取所选来源的PM2.5 浓度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前PM2.5 浓度。单位 `microgramsPerCubicMeter`。
- **参数**：
  - `area`（int，selector，read）：所选PM2.5 浓度的来源。
    - 可选值 `1` — 车内；`2` — 车外
- **备注**：只有独立读取路径；没有配置 PM2.5 浓度事件。

### `abilities.air_quality.pm25_detection` · PM2.5 检测开关

打开或关闭 PM2.5 检测。

- **能力 id**：`air_quality.pm25_detection`　·　**目录文件**：`air_quality.json`
- **常见说法**：`PM2.5 检测开关`
- **read** → `SensorValue`：`read()` — 读取PM2.5 检测开关。
- **write** → `void`：`write(boolean value)` — 打开或关闭PM2.5 检测。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启 PM2.5 检测。
- **参数**：
  - `value`（boolean，value，write）：是否开启 PM2.5 检测。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.air_quality.purification` · 空气净化

启动或停止车内空气净化，并查看净化进度状态。

- **能力 id**：`air_quality.purification`　·　**目录文件**：`air_quality.json`
- **常见说法**：`空气净化`
- **read** → `SensorValue`：`read()` — 读取空气净化。
- **write** → `void`：`write(boolean value)` — 打开或关闭空气净化。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前空气净化。
- **返回值含义**：`0`→NOT_RUNNING（未在净化）、`1`→RUNNING（净化中）、`2`→ENDED（过程结束）、`3`→TERMINATED（已停止）
- **参数**：
  - `value`（boolean，value，write）：是否启动空气净化。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.audio.external_broadcast_end` · 外放结束通知

在车外播报结束后通知音频系统。

- **能力 id**：`audio.external_broadcast_end`　·　**目录文件**：`audio.json`
- **常见说法**：`外放结束通知`
- **read**：不提供
- **write** → `void`：`write()` — 发送车外播报结束通知。
- **参数**：无（调用时不需要传参）。
- **备注**：先关闭车外播报通道，等待 100 毫秒后再发送结束通知。

### `abilities.audio.external_broadcast_position` · 车外播报通道

打开或关闭车外播报使用的音频通道。

- **能力 id**：`audio.external_broadcast_position`　·　**目录文件**：`audio.json`
- **常见说法**：`车外播报通道`
- **read**：不提供
- **write** → `void`：`write(int route, boolean value)` — 打开或关闭车外播报通道。
- **参数**：
  - `route`（int，selector，write）：车辆使用的车外播报通道类型，需按实际车辆确认。
    - 可选值 `1` — 通用广播路径；`2` — 23控制器音频路径
  - `value`（boolean，write）：是否打开车外播报通道。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：此操作只开关通道；音频播放和通道占用由调用方管理；关闭通道后需等待 100 毫秒，再发送外放结束通知。

### `abilities.audio.headrest_effect` · 头枕音效

选择头枕音响的环绕、驾享或独享音效，或关闭音效。

- **能力 id**：`audio.headrest_effect`　·　**目录文件**：`audio.json`
- **常见说法**：`头枕音效`
- **read** → `SensorValue`：`read()` — 读取头枕音效。
- **write** → `void`：`write(int value)` — 设置头枕音效。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前头枕音效。
- **返回值含义**：`1`→OFF（关闭）、`2`→SURROUND（环绕）、`3`→DRIVER（驾享）、`4`→PRIVATE（独享）
- **参数**：
  - `value`（int，write）：目标头枕音效。
    - 可选值 `1` — 关闭；`2` — 环绕；`3` — 驾享；`4` — 独享

### `abilities.audio.lock_sound_playback_flag` · 车外音效通道

打开或关闭车外音效使用的音频通道。

- **能力 id**：`audio.lock_sound_playback_flag`　·　**目录文件**：`audio.json`
- **常见说法**：`车外音效通道`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 打开或关闭车外音效通道。
- **参数**：
  - `value`（boolean，write）：是否打开车外音效通道。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：此操作只开关通道；音频播放、通道占用和结束通知由调用方管理。

### `abilities.audio.prompt_volume` · 车辆提示音量

选择车辆提示音的低、中或高音量。

- **能力 id**：`audio.prompt_volume`　·　**目录文件**：`audio.json`
- **常见说法**：`车辆提示音量`
- **read** → `SensorValue`：`read()` — 读取车辆提示音量。
- **write** → `void`：`write(int value)` — 设置车辆提示音量。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车辆提示音量。
- **返回值含义**：`1`→LOW（低音量）、`2`→MEDIUM（中音量）、`3`→HIGH（高音量）
- **参数**：
  - `value`（int，write）：目标车辆提示音量。
    - 可选值 `1` — 低音量；`2` — 中音量；`3` — 高音量

### `abilities.audio.simulated_engine_sound` · 模拟声浪

打开或关闭模拟引擎声浪。

- **能力 id**：`audio.simulated_engine_sound`　·　**目录文件**：`audio.json`
- **常见说法**：`模拟声浪`
- **read** → `SensorValue`：`read()` — 读取模拟声浪。
- **write** → `void`：`write(boolean value)` — 打开或关闭模拟声浪。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启模拟声浪。
- **参数**：
  - `value`（boolean，write）：是否开启模拟声浪。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：模拟声浪与引擎模拟音是两项独立功能。

### `abilities.audio.sound_balance` · 声场平衡

调节声音在车内左右、前后方向的分布。

- **能力 id**：`audio.sound_balance`　·　**目录文件**：`audio.json`
- **常见说法**：`声场平衡`
- **read** → `SensorValue`：`read()` — 读取声场平衡。
- **write** → `void`：`write(int x, int y)` — 一起设置左右和前后声场坐标。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`x` 左右声场坐标，7 为居中；`y` 前后声场坐标，7 为居中。
- **参数**：
  - `x`（int，write）：左右声场坐标，7 为居中。
    - 合法范围 `0~14`
  - `y`（int，write）：前后声场坐标，7 为居中。
    - 合法范围 `0~14`
- **备注**：左右、前后坐标需一起设置，7 为居中；两个方向的增加方向需按车型确认。

### `abilities.audio.sound_focus` · 声场聚焦

选择声音聚焦的座位区域或声场模式。

- **能力 id**：`audio.sound_focus`　·　**目录文件**：`audio.json`
- **常见说法**：`声场聚焦`
- **read** → `SensorValue`：`read(int audioSystem)` — 读取声场聚焦。
- **write** → `void`：`write(int audioSystem, int value)` — 设置声场聚焦。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 所选音响的声场聚焦模式。
- **返回值含义**：`1`→ALL（全车聚焦）、`2`→DRIVER（主驾聚焦）、`3`→PASSENGER（副驾聚焦）、`4`→REAR（后排聚焦）、`5`→SURROUND（环绕聚焦）、`6`→CUSTOM（自定义聚焦）、`6`→ALL（全车聚焦）、`7`→FRONT（前排聚焦）
- **参数**：
  - `audioSystem`（int，selector，read/write）：车辆的音响系统，创建规则时由用户选择。
    - 可选值 `1` — 丹拿音响；`2` — 帝瓦雷音响
  - `value`（int，write）：所选音响的声场聚焦模式。
    - 可选值 `1` — 全车聚焦；`2` — 主驾聚焦；`3` — 副驾聚焦；`4` — 后排聚焦；`5` — 环绕聚焦；`6` — 自定义聚焦；`6` — 全车聚焦；`7` — 前排聚焦
- **备注**：不同音响的聚焦模式不同，创建规则时需由用户选择实际音响系统。

### `abilities.audio.sound_profile` · 声音特色

选择音响的音色风格，例如原声、动感或人声。

- **能力 id**：`audio.sound_profile`　·　**目录文件**：`audio.json`
- **常见说法**：`声音特色`
- **read** → `SensorValue`：`read(int audioSystem)` — 读取声音特色。
- **write** → `void`：`write(int audioSystem, int value)` — 设置声音特色。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 所选音响的声音特色。
- **返回值含义**：`1`→AUTHENTIC（原声）、`2`→DYNAMIC（动感）、`3`→SOFT（柔和）、`4`→SPEECH（演说）、`5`→CUSTOM（自定义）、`1`→CLASSIC（经典）、`2`→SOFT（轻柔）、`3`→DYNAMIC（动感）、`4`→ROCK（摇滚）、`5`→VOCAL（人声）、`6`→CUSTOM（自定义）
- **参数**：
  - `audioSystem`（int，selector，read/write）：车辆的音响系统，创建规则时由用户选择。
    - 可选值 `1` — 丹拿音响；`2` — 帝瓦雷音响
  - `value`（int，write）：所选音响的声音特色。
    - 可选值 `1` — 原声；`2` — 动感；`3` — 柔和；`4` — 演说；`5` — 自定义；`1` — 经典；`3` — 动感；`5` — 人声；`6` — 自定义
- **备注**：不同音响的声音特色不同，创建规则时需由用户选择实际音响系统。

### `abilities.bodywork.charging_flap` · 充电口盖

打开或关闭右侧充电口盖。

- **能力 id**：`bodywork.charging_flap`　·　**目录文件**：`bodywork.json`
- **常见说法**：`充电口盖`
- **read**：不提供
- **write** → `void`：`write(boolean enabled)` — 设置充电口盖是否开启。
- **参数**：
  - `enabled`（boolean，value，write）：是否开启充电口盖。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：当前仅配置右侧充电口盖；不能据此控制左侧盖。

### `abilities.bodywork.door.open` · 车门与盖板开闭状态

查看指定车门、机舱盖、尾门或油箱盖是否打开。

- **能力 id**：`bodywork.door.open`　·　**目录文件**：`bodywork_doors.json`
- **常见说法**：`车门与盖板开闭状态`
- **read** → `SensorValue`：`read(int area)` — 读取车门与盖板开闭状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 车门或盖板是否打开。
- **参数**：
  - `area`（int，selector，read）：要查看的车门或盖板。
    - 可选值 `1` — 主驾；`2` — 副驾；`3` — 左后；`4` — 右后；`9` — 机舱盖；`10` — 尾门；`11` — 油箱盖

### `abilities.bodywork.electric_door.actuator_state` · 电动车门运动状态

查看指定电动车门的运动状态。

- **能力 id**：`bodywork.electric_door.actuator_state`　·　**目录文件**：`bodywork_doors.json`
- **常见说法**：`电动车门运动状态`
- **read** → `SensorValue`：`read(int area)` — 读取电动车门运动状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电动车门运动状态。
- **返回值含义**：`1`→MOVING_1（正在运动）、`2`→MOVING_2（正在运动）、`3`→PAUSED（已暂停）、`5`→OPEN（已打开）
- **参数**：
  - `area`（int，selector，read）：电动车门位置。
    - 可选值 `1` — 主驾；`2` — 副驾；`3` — 左后；`4` — 右后

### `abilities.bodywork.electric_door.position` · 电动车门

打开、关闭或暂停指定电动车门，并查看门的位置状态。

- **能力 id**：`bodywork.electric_door.position`　·　**目录文件**：`bodywork_doors.json`
- **常见说法**：`电动车门`
- **read** → `SensorValue`：`read(int area)` — 读取电动车门。
- **write** → `void`：`write(int area, int value)` — 打开、关闭或停止指定电动车门。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电动车门状态。
- **返回值含义**：`1`→NOT_CLOSED_1（未关闭）、`2`→CLOSED（已关闭）、`3`→NOT_CLOSED_3（未关闭）
- **参数**：
  - `area`（int，selector，read/write）：要操作或查看的电动车门。
    - 可选值 `1` — 主驾；`2` — 副驾；`3` — 左后；`4` — 右后
  - `value`（int，value，write）：目标电动车门。
    - 可选值 `1` — 打开；`2` — 关闭；`3` — 停止
- **备注**：仅在车辆静止时操作；只执行选定的开、关或停止动作，不循环切换。

### `abilities.bodywork.electric_step` · 电动踏板

打开或关闭电动踏板功能。

- **能力 id**：`bodywork.electric_step`　·　**目录文件**：`bodywork_doors.json`
- **常见说法**：`电动踏板`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 打开或关闭电动踏板。
- **参数**：
  - `value`（boolean，value，write）：是否启用电动踏板功能。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.bodywork.front_hood` · 电动前舱盖

打开或关闭电动前舱盖。

- **能力 id**：`bodywork.front_hood`　·　**目录文件**：`bodywork_trunk.json`
- **常见说法**：`电动前舱盖`
- **read**：不提供
- **write** → `void`：`write(boolean enabled)` — 设置电动前舱盖是否开启。
- **参数**：
  - `enabled`（boolean，value，write）：是否开启电动前舱盖。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：需车辆配备电动前舱盖；不适用于仅带机械锁扣的前舱盖。

### `abilities.bodywork.ground_clearance` · 离地间隙

查看车辆当前离地间隙，单位为毫米。

- **能力 id**：`bodywork.ground_clearance`　·　**目录文件**：`bodywork.json`
- **常见说法**：`离地间隙`
- **read** → `SensorValue`：`read()` — 读取离地间隙。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前离地间隙，单位为 mm。单位 `millimeter`。
- **参数**：无（调用时不需要传参）。
- **备注**：适用于支持易三方车身高度信息的车型。

### `abilities.bodywork.manual_trunk_release` · 手动后备箱解锁

释放手动后备箱锁扣，随后可手动打开后备箱。

- **能力 id**：`bodywork.manual_trunk_release`　·　**目录文件**：`bodywork_trunk.json`
- **常见说法**：`手动后备箱解锁`
- **read**：不提供
- **write** → `void`：`write()` — 释放手动后备箱锁扣。
- **参数**：无（调用时不需要传参）。
- **备注**：仅适用于原车界面支持解锁手动后备箱的车型，例如部分新款元 PLUS；不提供电动关闭。

### `abilities.bodywork.power_state` · 整车电源状态

查看整车当前处于 OFF、ACC、ON 等哪个电源档位。

- **能力 id**：`bodywork.power_state`　·　**目录文件**：`bodywork.json`
- **常见说法**：`整车电源状态`
- **read** → `SensorValue`：`read()` — 读取整车电源状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前整车电源状态。
- **返回值含义**：`0`→OFF（关闭电源（OFF））、`1`→ACC（附件电源（ACC））、`2`→ON（上电（ON））、`3`→OK（OK 电源档位）、`4`→FAKE_OK（FAKE_OK 电源档位）
- **参数**：无（调用时不需要传参）。
- **备注**：电源档位不代表车辆正在行驶。

### `abilities.bodywork.roof.command` · 天窗动作

关闭天窗、将天窗设为透气位置，或停止天窗移动。

- **能力 id**：`bodywork.roof.command`　·　**目录文件**：`bodywork_roof_and_shades.json`
- **常见说法**：`天窗动作`
- **read**：不提供
- **write** → `void`：`write(int value)` — 关闭、透气或停止天窗。
- **参数**：
  - `value`（int，value，write）：天窗关闭、透气或停止动作。
    - 可选值 `0` — 关闭；`253` — 透气；`254` — 停止

### `abilities.bodywork.roof.position` · 天窗开度

查看或设置天窗的打开百分比。

- **能力 id**：`bodywork.roof.position`　·　**目录文件**：`bodywork_roof_and_shades.json`
- **常见说法**：`天窗开度`
- **read** → `SensorValue`：`read()` — 读取天窗开度。
- **write** → `void`：`write(int value)` — 设置天窗开度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 天窗打开百分比，单位为 %。单位 `percent`。
- **参数**：
  - `value`（int，value，write）：天窗打开百分比，单位为 %。
    - 单位 `percent`；合法范围 `12~100`
- **备注**：目标开度为 12% 至 100%；关闭、透气或停止请使用“天窗动作”。

### `abilities.bodywork.sliding_door` · 电动侧滑门

打开或关闭左后、右后的电动侧滑门，并查看门的位置状态。

- **能力 id**：`bodywork.sliding_door`　·　**目录文件**：`bodywork_doors.json`
- **常见说法**：`电动侧滑门`
- **read** → `SensorValue`：`read(int area)` — 读取电动侧滑门。
- **write** → `void`：`write(int area, int value)` — 打开或关闭指定侧滑门。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电动侧滑门状态。
- **返回值含义**：`0`→CLOSED（已关闭）、`2`→OPEN（已打开）
- **参数**：
  - `area`（int，selector，read/write）：侧滑门位置。
    - 可选值 `3` — 左后；`4` — 右后
  - `value`（int，value，write）：目标电动侧滑门。
    - 可选值 `1` — 打开；`2` — 关闭
- **备注**：适用于腾势 D9、夏等配备电动侧滑门的车型。

### `abilities.bodywork.spoiler` · 电动尾翼

打开或收起电动尾翼。

- **能力 id**：`bodywork.spoiler`　·　**目录文件**：`bodywork.json`
- **常见说法**：`电动尾翼`
- **read** → `SensorValue`：`read()` — 读取电动尾翼。
- **write** → `void`：`write(boolean enabled)` — 设置电动尾翼是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电动尾翼状态。
- **返回值含义**：`5`→ON（已开启）、`6`→OFF（已关闭）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启电动尾翼。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：仅适用于配备电动尾翼且支持此控制的车型；状态码不等同于开关目标值。

### `abilities.bodywork.sunshade.position` · 车顶遮阳帘开度

查看或设置前排、后排车顶遮阳帘的打开百分比。

- **能力 id**：`bodywork.sunshade.position`　·　**目录文件**：`bodywork_roof_and_shades.json`
- **常见说法**：`车顶遮阳帘开度`
- **read** → `SensorValue`：`read(int area)` — 读取车顶遮阳帘开度。
- **write** → `void`：`write(int area, int value)` — 设置车顶遮阳帘开度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 车顶遮阳帘打开百分比，21% 为透光位置。单位 `percent`。
- **参数**：
  - `area`（int，selector，read/write）：车顶遮阳帘位置。
    - 可选值 `1` — 前排；`2` — 后排
  - `value`（int，value，write）：车顶遮阳帘打开百分比，21% 为透光位置。
    - 单位 `percent`；合法范围 `0~100`
- **备注**：0% 为关闭，100% 为全开，21% 为透光位置；前排可接收开度变化通知，后排仅支持读取和设置。

### `abilities.bodywork.sunshade.stop` · 停止前遮阳帘

停止前排车顶遮阳帘移动。

- **能力 id**：`bodywork.sunshade.stop`　·　**目录文件**：`bodywork_roof_and_shades.json`
- **常见说法**：`停止前遮阳帘`
- **read**：不提供
- **write** → `void`：`write(int value)` — 停止前排遮阳帘移动。
- **参数**：
  - `value`（int，value，write）：停止前排遮阳帘移动。
    - 可选值 `254` — 停止

### `abilities.bodywork.trunk.command` · 尾门动作

打开、关闭或停止电动尾门。

- **能力 id**：`bodywork.trunk.command`　·　**目录文件**：`bodywork_trunk.json`
- **常见说法**：`尾门动作`
- **read**：不提供
- **write** → `void`：`write(int value)` — 打开、关闭或停止尾门。
- **参数**：
  - `value`（int，value，write）：尾门的目标动作。
    - 可选值 `1` — 打开；`2` — 停止；`3` — 关闭

### `abilities.bodywork.trunk.open_height` · 尾门最大开启高度

查看或调整电动尾门开启时的高度上限。

- **能力 id**：`bodywork.trunk.open_height`　·　**目录文件**：`bodywork_trunk.json`
- **常见说法**：`尾门最大开启高度`
- **read** → `SensorValue`：`read()` — 读取尾门最大开启高度。
- **write** → `void`：`write(int value)` — 设置尾门最大开启高度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 尾门最大开启高度读数，单位尚未确认。
- **参数**：
  - `value`（int，value，write）：尾门最大开启高度读数，单位尚未确认。
    - 合法范围 `15~100`

### `abilities.bodywork.trunk.position` · 尾门位置

查看尾门当前位置。

- **能力 id**：`bodywork.trunk.position`　·　**目录文件**：`bodywork_trunk.json`
- **常见说法**：`尾门位置`
- **read** → `SensorValue`：`read()` — 读取尾门位置。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 尾门位置。
- **返回值含义**：`1`→OPEN（已打开）、`2`→NOT_OPEN_2（未打开）、`3`→NOT_OPEN_3（未打开）
- **参数**：无（调用时不需要传参）。

### `abilities.bodywork.vehicle_height` · 车身高度

查看车辆当前车身高度，单位为毫米。

- **能力 id**：`bodywork.vehicle_height`　·　**目录文件**：`bodywork.json`
- **常见说法**：`车身高度`
- **read** → `SensorValue`：`read()` — 读取车身高度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车身高度，单位为 mm。单位 `millimeter`。
- **参数**：无（调用时不需要传参）。
- **备注**：适用于支持易三方车身高度信息的车型。

### `abilities.bodywork.window.action` · 车窗动作

让指定侧窗全开、半开、透气、关闭或停止移动。

- **能力 id**：`bodywork.window.action`　·　**目录文件**：`bodywork_windows.json`
- **常见说法**：`车窗动作`
- **read**：不提供
- **write** → `void`：`write(int area, int value)` — 执行指定侧窗动作。
- **参数**：
  - `area`（int，selector，write）：要操作的侧窗。
    - 可选值 `1` — 主驾；`2` — 副驾；`3` — 左后；`4` — 右后
  - `value`（int，value，write）：侧窗的目标动作。
    - 可选值 `1` — 打开；`2` — 关闭；`3` — 停止；`4` — 半开；`5` — 透气

### `abilities.bodywork.window.command` · 车窗组合动作

同时操作前排、后排或全部侧窗，也可联动天窗和前遮阳帘。

- **能力 id**：`bodywork.window.command`　·　**目录文件**：`bodywork_windows.json`
- **常见说法**：`车窗组合动作`
- **read**：不提供
- **write** → `void`：`write(int value)` — 执行选定的车窗组合动作。
- **参数**：
  - `value`（int，value，write）：要执行的车窗组合动作。
    - 可选值 `0` — 关闭全部侧窗；`1` — 打开全部侧窗；`2` — 全部侧窗半开；`3` — 打开前排侧窗；`4` — 关闭前排侧窗；`5` — 前排侧窗半开；`6` — 前排侧窗透气；`7` — 打开后排侧窗；`8` — 关闭后排侧窗；`9` — 后排侧窗半开；`10` — 后排侧窗透气；`13` — 停止全部侧窗、天窗和前遮阳帘；`11` — 关闭全部侧窗、天窗和前遮阳帘；`12` — 全部侧窗及天窗透气
- **备注**：联动范围按所选动作确定；关闭全部开口包含四个侧窗、天窗和前遮阳帘。

### `abilities.bodywork.window.position` · 车窗开度

查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。

- **能力 id**：`bodywork.window.position`　·　**目录文件**：`bodywork_windows.json`
- **常见说法**：`车窗开度`
- **read** → `SensorValue`：`read(int area)` — 读取车窗开度。
- **write** → `void`：`write(int area, int value)` — 设置车窗目标开度，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机请使用车窗动作（全开、半开、透气、关闭）。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 侧窗打开百分比，单位为 %。单位 `percent`。
- **参数**：
  - `area`（int，selector，read/write）：侧窗位置。
    - 可选值 `1` — 主驾；`2` — 副驾；`3` — 左后；`4` — 右后
  - `value`（int，value，write）：侧窗打开百分比，单位为 %。
    - 单位 `percent`；合法范围 `0~100`
- **备注**：0% 为完全关闭，100% 为完全打开；作为规则条件时，车窗打开或未关闭表示开度大于 0%；仅完全打开表示开度等于 100%。

### `abilities.bodywork.window_sunshade` · 后侧窗遮阳帘

展开或收起左后、右后侧窗的遮阳帘，并查看其位置。

- **能力 id**：`bodywork.window_sunshade`　·　**目录文件**：`bodywork_windows.json`
- **常见说法**：`后侧窗遮阳帘`
- **read** → `SensorValue`：`read(int area)` — 读取后侧窗遮阳帘。
- **write** → `void`：`write(int area, int value)` — 展开或收起指定后侧窗遮阳帘。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 后侧窗遮阳帘状态码；各数值对应的含义尚未确认。
- **参数**：
  - `area`（int，selector，read/write）：后侧窗遮阳帘位置。
    - 可选值 `3` — 左后；`4` — 右后
  - `value`（int，value，write）：遮阳帘展开或收起动作。
    - 可选值 `1` — 展开；`2` — 收起

### `abilities.charging.battery_state` · 电池充放电状态

查看动力电池当前处于充电、放电、暂停或其他工作状态。

- **能力 id**：`charging.battery_state`　·　**目录文件**：`charging.json`
- **常见说法**：`电池充放电状态`
- **read** → `SensorValue`：`read()` — 读取电池充放电状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电池充放电状态。
- **返回值含义**：`0`→PREPARING（准备）、`1`→CHARGING（充电中）、`2`→CHARGE_COMPLETE（充电完成）、`3`→DISCHARGING（放电中）、`4`→TERMINATED（终止）、`5`→C10_FAULT（C10故障）、`6`→GUN_FAULT（充电枪故障）、`7`→CHARGER_FAULT（充电机故障）、`8`→AC_FAULT（交流故障）、`9`→SCHEDULED（预约）、`10`→CBU_DISCHARGING（CBU放电）、`11`→TIMEOUT（超时）、`12`→DISCHARGE_COMPLETE（放电完成）、`13`→PAUSED（暂停）
- **参数**：无（调用时不需要传参）。

### `abilities.charging.battery_voltage` · 动力电池电压

查看动力电池当前电压，单位为伏特。

- **能力 id**：`charging.battery_voltage`　·　**目录文件**：`charging.json`
- **常见说法**：`动力电池电压`
- **read** → `SensorValue`：`read()` — 读取动力电池电压。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前动力电池电压，单位为 V。单位 `volt`。
- **参数**：无（调用时不需要传参）。

### `abilities.charging.charger_work_state` · 充电机工作状态

查看充电机处于准备、开始、结束还是终止阶段。

- **能力 id**：`charging.charger_work_state`　·　**目录文件**：`charging.json`
- **常见说法**：`充电机工作状态`
- **read** → `SensorValue`：`read()` — 读取充电机工作状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前充电机工作状态。
- **返回值含义**：`1`→PREPARING（准备）、`2`→STARTED（开始）、`3`→FINISHED（结束）、`4`→TERMINATED（终止）
- **参数**：无（调用时不需要传参）。

### `abilities.charging.current` · 充放电电流

查看动力电池的充放电电流，单位为安培。

- **能力 id**：`charging.current`　·　**目录文件**：`charging.json`
- **常见说法**：`充放电电流`
- **read** → `SensorValue`：`read()` — 读取充放电电流。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前充放电电流，单位为 A。单位 `ampere`。
- **参数**：无（调用时不需要传参）。

### `abilities.charging.external_discharge_current` · 对外放电电流

查看车辆对外放电时的电流，单位为安培。

- **能力 id**：`charging.external_discharge_current`　·　**目录文件**：`charging.json`
- **常见说法**：`对外放电电流`
- **read** → `SensorValue`：`read()` — 读取对外放电电流。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前对外放电电流，单位为 A。单位 `ampere`。
- **参数**：无（调用时不需要传参）。

### `abilities.charging.gun_state` · 充电枪连接状态

查看充电枪是否连接，以及交流、直流或对外放电的连接类型。

- **能力 id**：`charging.gun_state`　·　**目录文件**：`charging.json`
- **常见说法**：`充电枪连接状态`
- **read** → `SensorValue`：`read()` — 读取充电枪连接状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前充电枪连接状态。
- **返回值含义**：`1`→DISCONNECTED（未连接）、`2`→AC（交流）、`3`→DC（直流）、`4`→AC_AND_DC（交流及直流）、`5`→DISCHARGING（对外放电）
- **参数**：无（调用时不需要传参）。

### `abilities.charging.power` · 充电功率

查看车辆报告的充电功率读数。

- **能力 id**：`charging.power`　·　**目录文件**：`charging.json`
- **常见说法**：`充电功率`
- **read** → `SensorValue`：`read()` — 读取充电功率。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 充电功率读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.charging.soc_save_mode` · 保电模式

选择智能保电或强制保电。

- **能力 id**：`charging.soc_save_mode`　·　**目录文件**：`charging.json`
- **常见说法**：`保电模式`
- **read** → `SensorValue`：`read()` — 读取保电模式。
- **write** → `void`：`write(int value)` — 设置保电模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前保电模式。
- **返回值含义**：`1`→SMART（智能保电）、`2`→FORCED（强制保电）
- **参数**：
  - `value`（int，value，write）：目标保电模式。
    - 可选值 `1` — 智能保电；`2` — 强制保电

### `abilities.door_lock.all_doors` · 四门锁控制

同时锁定或解锁四个车门。

- **能力 id**：`door_lock.all_doors`　·　**目录文件**：`door_lock.json`
- **常见说法**：`四门锁控制`
- **read**：不提供
- **write** → `void`：`write(int value)` — 锁定或解锁四个车门。
- **参数**：
  - `value`（int，value，write）：锁定或解锁四个车门。
    - 可选值 `1` — 解锁；`2` — 锁定
- **备注**：控制范围为四个车门，不包含尾门和儿童锁。

### `abilities.door_lock.child_lock_state` · 儿童锁

打开或解除左后、右后车门的儿童锁，并查看锁定状态。

- **能力 id**：`door_lock.child_lock_state`　·　**目录文件**：`door_lock.json`
- **常见说法**：`儿童锁`
- **read** → `SensorValue`：`read(int area)` — 读取儿童锁。
- **write** → `void`：`write(int area, int value)` — 打开或解除指定车门的儿童锁。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 儿童锁的锁定状态。
- **返回值含义**：`1`→UNLOCKED（解锁）、`2`→LOCKED（锁定）
- **参数**：
  - `area`（int，selector，read/write）：儿童锁所在的后排车门。
    - 可选值 `3` — 左后；`4` — 右后
  - `value`（int，value，write）：儿童锁的锁定状态。
    - 可选值 `1` — 解锁；`2` — 锁定

### `abilities.door_lock.lock_state` · 门锁状态

查看指定车门或尾门是否锁定。

- **能力 id**：`door_lock.lock_state`　·　**目录文件**：`door_lock.json`
- **常见说法**：`门锁状态`
- **read** → `SensorValue`：`read(int area)` — 读取门锁状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 门锁是否锁定。
- **返回值含义**：`1`→UNLOCKED（解锁）、`2`→LOCKED（锁定）
- **参数**：
  - `area`（int，selector，read）：要查看的车门或尾门。
    - 可选值 `1` — 主驾；`2` — 副驾；`3` — 左后；`4` — 右后；`10` — 尾门

### `abilities.energy.extended_range` · 长续航模式

开启或关闭长续航模式。

- **能力 id**：`energy.extended_range`　·　**目录文件**：`energy.json`
- **常见说法**：`长续航模式`
- **read** → `SensorValue`：`read()` — 读取长续航模式。
- **write** → `void`：`write(boolean enabled)` — 设置长续航模式是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 长续航模式状态。
- **返回值含义**：`1`→OFF（已关闭）、`2`→ON（已开启）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启长续航模式。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：这是独立的长续航开关，不替代 EV/HEV 能量模式选择。

### `abilities.energy.mode` · 能量模式

选择纯电、强制纯电或混动模式，并查看当前能量模式。

- **能力 id**：`energy.mode`　·　**目录文件**：`energy.json`
- **常见说法**：`能量模式`
- **read** → `SensorValue`：`read()` — 读取能量模式。
- **write** → `void`：`write(int value)` — 设置能量模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前能量模式。
- **返回值含义**：`0`→STOPPED（停止）、`1`→EV（纯电）、`2`→FORCED_EV（强制纯电）、`3`→HEV（混动）、`4`→FUEL（燃油）、`5`→HOLD（保持）
- **参数**：
  - `value`（int，value，write）：目标能量模式。
    - 可选值 `1` — 纯电；`2` — 强制纯电；`3` — 混动
- **备注**：可设置纯电、强制纯电和混动；车辆还可报告停止、燃油或保持状态。

### `abilities.energy.operation_mode` · 驾驶模式

按车辆实际驾驶模式控制系统，选择普通、经济、运动、地形或竞速模式。

- **能力 id**：`energy.operation_mode`　·　**目录文件**：`energy.json`
- **常见说法**：`驾驶模式`
- **read** → `SensorValue`：`read(int system)` — 读取驾驶模式。
- **write** → `void`：`write(int system, int value)` — 设置驾驶模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前驾驶模式。
- **返回值含义**：`1`→ECO（经济）、`2`→SPORT（运动）、`3`→NORMAL（普通）、`4`→SNOW（雪地）、`5`→MUD（泥地）、`6`→SAND（沙地）
- **参数**：
  - `system`（int，selector，read/write）：车辆实际使用的驾驶模式控制系统；需用户确认。
    - 可选值 `1` — 能量控制系统；`2` — 扩展驾驶模式系统
  - `value`（int，value，write）：目标驾驶模式。
    - 可选值 `1` — 经济；`2` — 运动；`3` — 普通；`4` — 雪地；`5` — 泥地；`6` — 沙地；`7` — 山地；`8` — 岩石；`19` — 自定义；`20` — 竞速；`23` — 暴力
- **备注**：必须由用户确认车辆采用哪种控制系统；不能根据目标模式猜测；扩展系统的读取状态码尚未解释；只有能量控制系统提供已配置的驾驶模式状态事件。

### `abilities.energy.regeneration` · 能量回收强度

将能量回收设为标准或较强。

- **能力 id**：`energy.regeneration`　·　**目录文件**：`energy.json`
- **常见说法**：`能量回收强度`
- **read** → `SensorValue`：`read(int market)` — 读取能量回收强度。
- **write** → `void`：`write(int market, int value)` — 设置能量回收强度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前能量回收强度。
- **返回值含义**：`1`→STANDARD（标准）、`2`→STRONG（较强）
- **参数**：
  - `market`（int，selector，read/write）：车辆的能量回收编码版本，创建规则时由用户选择。
    - 可选值 `1` — 国内编码；`2` — 海外编码
  - `value`（int，value，write）：目标能量回收强度。
    - 可选值 `1` — 标准；`2` — 较强
- **备注**：创建规则时由用户选择国内或海外编码版本；变化通知中的状态码尚未对应到标准或较强；读取强度需使用所选编码版本。

### `abilities.engine.charge_power` · 发动机发电功率

查看发动机用于发电的功率，单位为千瓦。

- **能力 id**：`engine.charge_power`　·　**目录文件**：`engine.json`
- **常见说法**：`发动机发电功率`
- **read** → `SensorValue`：`read()` — 读取发动机发电功率。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前发动机发电功率，单位为 kW。单位 `kilowatt`。
- **参数**：无（调用时不需要传参）。
- **备注**：部分车型不提供此读数；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.engine.indicated_torque` · 发动机扭矩

查看发动机的扭矩读数。

- **能力 id**：`engine.indicated_torque`　·　**目录文件**：`engine.json`
- **常见说法**：`发动机扭矩`
- **read** → `SensorValue`：`read()` — 读取发动机扭矩。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 发动机扭矩读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.engine.low_voltage_side_current` · 低压侧电流

查看动力系统低压侧电流，单位为安培。

- **能力 id**：`engine.low_voltage_side_current`　·　**目录文件**：`engine.json`
- **常见说法**：`低压侧电流`
- **read** → `SensorValue`：`read()` — 读取低压侧电流。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前低压侧电流，单位为 A。单位 `ampere`。
- **参数**：无（调用时不需要传参）。

### `abilities.engine.low_voltage_side_voltage` · 低压侧电压

查看动力系统低压侧电压，单位为伏特。

- **能力 id**：`engine.low_voltage_side_voltage`　·　**目录文件**：`engine.json`
- **常见说法**：`低压侧电压`
- **read** → `SensorValue`：`read()` — 读取低压侧电压。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前低压侧电压，单位为 V。单位 `volt`。
- **参数**：无（调用时不需要传参）。

### `abilities.engine.motor_input_voltage` · 电机输入电压

查看电机输入端电压，单位为伏特。

- **能力 id**：`engine.motor_input_voltage`　·　**目录文件**：`engine.json`
- **常见说法**：`电机输入电压`
- **read** → `SensorValue`：`read()` — 读取电机输入电压。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电机输入电压，单位为 V。单位 `volt`。
- **参数**：无（调用时不需要传参）。

### `abilities.engine.motor_rpm` · 电机转速

查看前电机或后电机的每分钟转数。

- **能力 id**：`engine.motor_rpm`　·　**目录文件**：`engine.json`
- **常见说法**：`电机转速`
- **read** → `SensorValue`：`read(int area)` — 读取电机转速。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电机转速，单位为 rpm。单位 `revolutionsPerMinute`。
- **参数**：
  - `area`（int，selector，read）：前电机或后电机。
    - 可选值 `40` — 前电机；`41` — 后电机
- **备注**：保留车辆报告的正负号。

### `abilities.engine.motor_temperature` · 电机温度

查看前后电机或电机功率模块的温度，单位为摄氏度。

- **能力 id**：`engine.motor_temperature`　·　**目录文件**：`engine.json`
- **常见说法**：`电机温度`
- **read** → `SensorValue`：`read(int source)` — 读取所选来源的电机温度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电机温度。单位 `celsius`。
- **参数**：
  - `source`（int，selector，read）：所选电机温度的来源。
    - 可选值 `1` — 前电机；`2` — 后电机；`3` — 前电机IPM；`4` — 后电机IPM
- **备注**：电机与 IPM 是不同温度来源，不能混用；是否有硬件读数和事件取决于当前车辆。

### `abilities.engine.motor_torque` · 电机扭矩

查看前电机或后电机的扭矩读数。

- **能力 id**：`engine.motor_torque`　·　**目录文件**：`engine.json`
- **常见说法**：`电机扭矩`
- **read** → `SensorValue`：`read(int area)` — 读取电机扭矩。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电机扭矩读数，单位尚未确认。
- **参数**：
  - `area`（int，selector，read）：前电机或后电机。
    - 可选值 `40` — 前电机；`41` — 后电机

### `abilities.engine.oil_level` · 机油液位

查看车辆报告的机油液位等级。

- **能力 id**：`engine.oil_level`　·　**目录文件**：`engine.json`
- **常见说法**：`机油液位`
- **read** → `SensorValue`：`read()` — 读取机油液位。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前机油液位。
- **参数**：无（调用时不需要传参）。

### `abilities.engine.power` · 动力功率

查看动力系统当前功率，单位为千瓦。

- **能力 id**：`engine.power`　·　**目录文件**：`engine.json`
- **常见说法**：`动力功率`
- **read** → `SensorValue`：`read()` — 读取动力功率。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前动力功率，单位为 kW。单位 `kilowatt`。
- **参数**：无（调用时不需要传参）。

### `abilities.engine.rpm` · 发动机转速

查看发动机每分钟转数。

- **能力 id**：`engine.rpm`　·　**目录文件**：`engine.json`
- **常见说法**：`发动机转速`
- **read** → `SensorValue`：`read()` — 读取发动机转速。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前发动机转速，单位为 rpm。单位 `revolutionsPerMinute`。
- **参数**：无（调用时不需要传参）。
- **备注**：保留车辆报告的正负号。

### `abilities.engine.voice_simulator` · 引擎模拟音

打开或关闭引擎模拟音。

- **能力 id**：`engine.voice_simulator`　·　**目录文件**：`engine.json`
- **常见说法**：`引擎模拟音`
- **read** → `SensorValue`：`read()` — 读取引擎模拟音。
- **write** → `void`：`write(boolean value)` — 打开或关闭引擎模拟音。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启引擎模拟音。
- **参数**：
  - `value`（boolean，value，write）：是否开启引擎模拟音。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.gearbox.gear` · 当前挡位

查看车辆当前挡位，例如 P 挡、R 挡或 D 挡。

- **能力 id**：`gearbox.gear`　·　**目录文件**：`gearbox.json`
- **常见说法**：`当前挡位`、`查询档位`、`进入D挡`
- **read** → `SensorValue`：`read()` — 读取当前挡位。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前当前挡位。
- **返回值含义**：`1`→P（P挡）、`2`→R（R挡）、`3`→N（N挡）、`4`→D（D挡）、`5`→M（M挡）、`6`→S（S挡）
- **参数**：无（调用时不需要传参）。

### `abilities.instrument.backlight_brightness` · 仪表背光亮度

按原车设置界面的百分比调节仪表背光亮度，适用于 DiLink 3、4 车机（Android 12 以下）。

- **能力 id**：`instrument.backlight_brightness`　·　**目录文件**：`instrument.json`
- **常见说法**：`仪表背光`、`仪表背光亮度`
- **read** → `SensorValue`：`read()` — 读取仪表背光亮度百分比。
- **write** → `void`：`write(int value)` — 设置仪表背光亮度百分比。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前仪表背光亮度百分比，单位为 %。单位 `percent`。
- **参数**：
  - `value`（int，value，write）：目标仪表背光亮度百分比，单位为 %，只能是 10 的倍数；最低为 0，最高为 100。
    - 单位 `percent`；合法范围 `0~100`（步长 10）
- **备注**：原车共 11 档，0%～100% 每 10% 一档；0% 是最低档，不是关闭；读回按原车设置的规则换算：原生 1～11 与 CAN-FD 的 12～22 对应同一组百分比。

### `abilities.instrument.brightness_percent` · 仪表亮度百分比

按百分比调节仪表亮度，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用仪表背光亮度。

- **能力 id**：`instrument.brightness_percent`　·　**目录文件**：`instrument.json`
- **常见说法**：`仪表亮度百分比`
- **read** → `SensorValue`：`read()` — 读取仪表亮度百分比。
- **write** → `void`：`write(int value)` — 设置仪表亮度百分比。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前仪表亮度百分比，单位为 %。单位 `percent`。
- **参数**：
  - `value`（int，value，write）：目标仪表亮度百分比，单位为 %。
    - 单位 `percent`；合法范围 `0~100`

### `abilities.light.auto_high_beam` · 自适应远光

打开或关闭自适应远光功能，并分别查看左右远光灯的工作状态。

- **能力 id**：`light.auto_high_beam`　·　**目录文件**：`light.json`
- **常见说法**：`自适应远光`、`ADB`
- **read** → `SensorValue`：`read(int area)` — 读取自适应远光状态。
- **write** → `void`：`write(boolean value)` — 打开或关闭自适应远光。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前自适应远光状态。
- **返回值含义**：`0`→INVALID（无效状态）、`1`→OFF（关闭）、`2`→STANDBY（待机）、`3`→ACTIVATION（已激活）、`4`→WORKING（工作中）、`5`→FAULT（故障）
- **参数**：
  - `area`（int，selector，read）：左侧或右侧远光灯。
    - 可选值 `0` — 左；`1` — 右
  - `value`（boolean，value，write）：是否开启自适应远光。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：开关作用于整车；读取或观察时分别报告左右远光灯状态；与“智能远近光”的自动切换功能分别控制。

### `abilities.light.daytime_light` · 日间行车灯

打开或关闭日间行车灯。

- **能力 id**：`light.daytime_light`　·　**目录文件**：`light.json`
- **常见说法**：`日间行车灯`
- **read** → `SensorValue`：`read()` — 读取日间行车灯。
- **write** → `void`：`write(boolean value)` — 打开或关闭日间行车灯。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启日间行车灯。
- **参数**：
  - `value`（boolean，value，write）：是否开启日间行车灯。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.light.double_flash` · 双闪

打开或关闭双闪警示灯。

- **能力 id**：`light.double_flash`　·　**目录文件**：`light.json`
- **常见说法**：`双闪`
- **read** → `SensorValue`：`read()` — 读取双闪。
- **write** → `void`：`write(boolean value)` — 打开或关闭双闪。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启双闪。
- **参数**：
  - `value`（boolean，value，write）：是否开启双闪。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：通过此功能开启后，应通过此功能关闭，避免与原车双闪按钮混用。

### `abilities.light.fog_switch` · 雾灯开关（怀挡）

打开或关闭怀挡车型的前雾灯或后雾灯。

- **能力 id**：`light.fog_switch`　·　**目录文件**：`light.json`
- **常见说法**：`雾灯开关（怀挡）`
- **read** → `SensorValue`：`read(int area)` — 读取雾灯开关（怀挡）。
- **write** → `void`：`write(int area, boolean value)` — 打开或关闭指定雾灯。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 所选雾灯是否开启。
- **参数**：
  - `area`（int，selector，read/write）：前雾灯或后雾灯。
    - 可选值 `1` — 前雾灯；`2` — 后雾灯
  - `value`（boolean，value，write）：所选雾灯是否开启。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：传统平台的雾灯使用“灯具状态”中的前雾灯、后雾灯控制。

### `abilities.light.headlight_mode` · 车灯控制模式

选择车灯关闭、自动、小灯或近光灯模式。

- **能力 id**：`light.headlight_mode`　·　**目录文件**：`light.json`
- **常见说法**：`车灯控制模式`
- **read** → `SensorValue`：`read()` — 读取车灯控制模式。
- **write** → `void`：`write(int value)` — 设置车灯控制模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车灯控制模式。
- **返回值含义**：`1`→OFF（关闭）、`2`→AUTO（自动）、`3`→SIDE_LIGHT（小灯）、`4`→LOW_BEAM（近光灯）
- **参数**：
  - `value`（int，value，write）：目标车灯控制模式。
    - 可选值 `1` — 关闭；`2` — 自动；`3` — 小灯；`4` — 近光灯

### `abilities.light.high_beam_assist` · 智能远近光

打开或关闭智能远近光切换，并查看工作状态。

- **能力 id**：`light.high_beam_assist`　·　**目录文件**：`light.json`
- **常见说法**：`智能远近光`
- **read** → `SensorValue`：`read()` — 读取智能远近光。
- **write** → `void`：`write(boolean value)` — 打开或关闭智能远近光。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前智能远近光。
- **返回值含义**：`0`→OFF（关闭）、`1`→INACTIVE（未生效）、`2`→STANDBY（待激活）、`3`→ACTIVE（激活）、`4`→FAULT（故障）、`5`→CAMERA_BLOCKED（摄像头遮挡）
- **参数**：
  - `value`（boolean，write）：是否开启智能远近光。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：智能远近光切换与自适应远光灯是两项独立功能。

### `abilities.light.inside_light` · 车内灯

打开或关闭车内照明灯。

- **能力 id**：`light.inside_light`　·　**目录文件**：`light.json`
- **常见说法**：`车内灯`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 打开或关闭车内灯。
- **参数**：
  - `value`（boolean，value，write）：是否开启车内灯。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.light.inside_light_on_door` · 开门自动亮灯

设置打开车门时是否自动点亮车内灯。

- **能力 id**：`light.inside_light_on_door`　·　**目录文件**：`light.json`
- **常见说法**：`开门自动亮灯`
- **read** → `SensorValue`：`read()` — 读取开门自动亮灯。
- **write** → `void`：`write(boolean value)` — 打开或关闭开门自动亮灯。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 开门时是否自动点亮车内灯。
- **参数**：
  - `value`（boolean，value，write）：开门时是否自动点亮车内灯。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.light.light_state` · 灯具状态

查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。

- **能力 id**：`light.light_state`　·　**目录文件**：`light.json`
- **常见说法**：`灯具状态`
- **read** → `SensorValue`：`read(int area)` — 读取灯具状态。
- **write** → `void`：`write(int area, boolean value)` — 打开或关闭传统平台的前雾灯或后雾灯。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 所选灯具是否开启。
- **参数**：
  - `area`（int，selector，read/write）：灯具种类。
    - 可选值 `1` — 小灯；`2` — 近光灯；`3` — 远光灯；`6` — 前雾灯；`7` — 后雾灯；`8` — 脚照灯
  - `value`（boolean，value，write）：所选灯具是否开启。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：此处的雾灯控制适用于传统平台；怀挡车型使用“雾灯开关（怀挡）”。

### `abilities.light.turn_signal` · 分侧转向灯

打开指定一侧的转向灯，或查看该侧的转向信号状态。

- **能力 id**：`light.turn_signal`　·　**目录文件**：`light.json`
- **常见说法**：`分侧转向灯`
- **read** → `SensorValue`：`read(int area)` — 读取分侧转向灯。
- **write** → `void`：`write(int area, boolean value)` — 打开或关闭分侧转向灯。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 所选一侧的转向信号状态码。
- **参数**：
  - `area`（int，selector，read/write）：左侧或右侧转向灯。
    - 可选值 `1` — 左；`2` — 右
  - `value`（boolean，value，write）：是否开启所选一侧的转向灯；关闭会关闭整体转向信号。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：关闭时会同时关闭整体转向信号，不能只关闭一侧；该侧状态码没有定义取值含义；判断左、右转向灯是否点亮请使用转向信号模式。

### `abilities.light.turn_signal_state` · 转向信号模式

选择左转向、右转向、双闪或关闭，并查看整车转向信号状态。

- **能力 id**：`light.turn_signal_state`　·　**目录文件**：`light.json`
- **常见说法**：`转向信号模式`、`转向灯`、`左转向灯`、`右转向灯`
- **read** → `SensorValue`：`read()` — 读取转向信号模式。
- **write** → `void`：`write(int value)` — 设置转向信号模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前转向信号模式。
- **返回值含义**：`1`→OFF（关闭）、`2`→LEFT_NORMAL（左转向正常闪烁）、`3`→LEFT_FAST（左转向快速闪烁）、`4`→RIGHT_NORMAL（右转向正常闪烁）、`5`→RIGHT_FAST（右转向快速闪烁）、`6`→HAZARD（双闪）、`7`→EMERGENCY（紧急闪烁）、`8`→REAR_END_WARNING（追尾预警闪烁）、`9`→FAST_FLASH（快速闪烁）
- **参数**：
  - `value`（int，value，write）：目标转向信号模式。
    - 可选值 `0` — 关闭；`1` — 双闪；`2` — 左转向；`3` — 右转向
- **备注**：判断左转向灯点亮时同时选择 LEFT_NORMAL 和 LEFT_FAST，右转向灯同时选择 RIGHT_NORMAL 和 RIGHT_FAST；HAZARD 为双闪，不属于左、右转向。

### `abilities.panorama.apa_state` · 自动泊车状态

查看自动泊车处于搜索车位、泊车、暂停或结束等哪个阶段。

- **能力 id**：`panorama.apa_state`　·　**目录文件**：`panorama.json`
- **常见说法**：`自动泊车状态`
- **read** → `SensorValue`：`read()` — 读取自动泊车状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前自动泊车状态。
- **返回值含义**：`0`→PASSIVE（被动状态）、`1`→SEARCHING（搜索车位）、`2`→PARKING（泊车中）、`3`→SUSPENDED（泊车暂停）、`4`→TERMINATED（泊车终止）、`5`→COMPLETED（泊车完成）、`6`→FAILURE（故障）、`7`→STANDBY（待命）
- **参数**：无（调用时不需要传参）。

### `abilities.panorama.display_mode` · 全景显示模式

查看全景影像当前使用全屏、小窗或其他显示模式。

- **能力 id**：`panorama.display_mode`　·　**目录文件**：`panorama.json`
- **常见说法**：`全景显示模式`
- **read** → `SensorValue`：`read()` — 读取全景显示模式。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前全景显示模式。
- **返回值含义**：`0`→PANORAMA（全景模式）、`1`→FULL_SCREEN（全屏模式）、`3`→WIDGET（小窗模式）、`4`→RF_REVERSE（RF 倒车显示模式）、`5`→REVERSE（倒车显示模式）、`6`→PANORAMA_3D（3D 全景模式）
- **参数**：无（调用时不需要传参）。
- **备注**：修改显示设置时，需一起提供视角、显示模式和横竖方向。

### `abilities.panorama.display_parameters` · 全景显示设置

一起设置全景影像的视角、显示模式和横竖方向，或读取这三项设置。

- **能力 id**：`panorama.display_parameters`　·　**目录文件**：`panorama.json`
- **常见说法**：`全景显示设置`
- **read** → `SensorValue`：`read()` — 读取全景显示设置。
- **write** → `void`：`write(int output, int mode, int rotation)` — 一起设置全景影像的视角、显示模式和方向。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`output` 全景影像的摄像头视角；`mode` 全景影像的显示模式；`rotation` 全景影像横向或纵向显示。
- **返回值含义**：`0`→INVALID（无效或尚无输出）、`1`→OFF（关闭输出）、`2`→FRONT（前视）、`3`→REAR（后视）、`4`→LEFT（左视）、`5`→RIGHT（右视）、`6`→COMPOSE（组合视图）、`7`→MATCHING（匹配视图）、`8`→FRONT_LEFT（前左）、`9`→FRONT_RIGHT（前右）、`10`→REAR_LEFT（后左）、`11`→REAR_RIGHT（后右）、`12`→FRONT_WIDE（前广角）、`13`→REAR_WIDE（后广角）、`14`→WIDTH_LIMIT（限宽视图）、`0`→PANORAMA（全景模式）、`1`→FULL_SCREEN（全屏模式）、`3`→WIDGET（小窗模式）、`4`→RF_REVERSE（RF 倒车显示模式）、`5`→REVERSE（倒车显示模式）、`6`→PANORAMA_3D（3D 全景模式）、`1`→HORIZONTAL（横向）、`2`→VERTICAL（纵向）
- **参数**：
  - `output`（int，write）：全景影像的摄像头视角。
    - 可选值 `0` — 无效或尚无输出；`1` — 关闭输出；`2` — 前视；`3` — 后视；`4` — 左视；`5` — 右视；`6` — 组合视图；`7` — 匹配视图；`8` — 前左；`9` — 前右；`10` — 后左；`11` — 后右；`12` — 前广角；`13` — 后广角；`14` — 限宽视图
  - `mode`（int，write）：全景影像的显示模式。
    - 可选值 `0` — 全景模式；`1` — 全屏模式；`3` — 小窗模式；`4` — RF 倒车显示模式；`5` — 倒车显示模式；`6` — 3D 全景模式
  - `rotation`（int，write）：全景影像横向或纵向显示。
    - 可选值 `1` — 横向；`2` — 纵向
- **备注**：三个设置需一起传入；三项读数可能来自不同时刻；只调整画面显示；显示占用、退出恢复由调用方管理，不会启动自动泊车。

### `abilities.panorama.output` · 全景输出视角

查看全景影像当前显示的摄像头视角。

- **能力 id**：`panorama.output`　·　**目录文件**：`panorama.json`
- **常见说法**：`全景输出视角`
- **read** → `SensorValue`：`read()` — 读取全景输出视角。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前全景输出视角。
- **返回值含义**：`0`→INVALID（无效或尚无输出）、`1`→OFF（关闭输出）、`2`→FRONT（前视）、`3`→REAR（后视）、`4`→LEFT（左视）、`5`→RIGHT（右视）、`6`→COMPOSE（组合视图）、`7`→MATCHING（匹配视图）、`8`→FRONT_LEFT（前左）、`9`→FRONT_RIGHT（前右）、`10`→REAR_LEFT（后左）、`11`→REAR_RIGHT（后右）、`12`→FRONT_WIDE（前广角）、`13`→REAR_WIDE（后广角）、`14`→WIDTH_LIMIT（限宽视图）
- **参数**：无（调用时不需要传参）。
- **备注**：修改显示设置时，需一起提供视角、显示模式和横竖方向；前后广角和限宽视图可读取，暂不提供相应变化通知。

### `abilities.panorama.rotation` · 全景显示方向

查看全景影像当前横向还是纵向显示。

- **能力 id**：`panorama.rotation`　·　**目录文件**：`panorama.json`
- **常见说法**：`全景显示方向`
- **read** → `SensorValue`：`read()` — 读取全景显示方向。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前全景显示方向。
- **返回值含义**：`1`→HORIZONTAL（横向）、`2`→VERTICAL（纵向）
- **参数**：无（调用时不需要传参）。
- **备注**：修改显示设置时，需一起提供视角、显示模式和横竖方向。

### `abilities.power.acc_status` · 车机 ACC 电源

查看车机 ACC 电源是否上电。

- **能力 id**：`power.acc_status`　·　**目录文件**：`power.json`
- **常见说法**：`车机 ACC 电源`
- **read** → `SensorValue`：`read()` — 读取车机 ACC 电源。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车机 ACC 电源。
- **返回值含义**：`0`→OFF（熄火）、`1`→ON（上电）
- **参数**：无（调用时不需要传参）。

### `abilities.radar.distance` · 雷达障碍物距离

查看指定雷达探头报告的障碍物距离读数。

- **能力 id**：`radar.distance`　·　**目录文件**：`radar.json`
- **常见说法**：`雷达障碍物距离`
- **read** → `SensorValue`：`read(int area)` — 读取雷达障碍物距离。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 雷达障碍物距离读数，单位尚未确认。
- **参数**：
  - `area`（int，selector，read）：雷达探头位置。
    - 可选值 `30` — 左前；`31` — 右前；`32` — 左后；`33` — 右后；`34` — 左侧；`35` — 前左中；`36` — 前右中；`37` — 后中
- **备注**：距离单位需按车型确认；读数为零不能直接理解为没有障碍物。

### `abilities.safety_belt.state` · 安全带状态

查看指定座位的安全带是否扣合。

- **能力 id**：`safety_belt.state`　·　**目录文件**：`safety_belt.json`
- **常见说法**：`安全带状态`、`是否系好安全带`、`主驾安全带`、`后排安全带`
- **read** → `SensorValue`：`read(String area)` — 读取安全带状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 安全带是否扣合；无效状态表示本次无法判断。
- **返回值含义**：`0`→UNBUCKLED（未系）、`1`→BUCKLED（已系）、`2`→INVALID（状态无效；不能作为未系或已系）
- **参数**：
  - `area`（String，selector，read）：安全带所在座位。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后；`rearCenter` — 后排中间

### `abilities.safety_belt.warning` · 副驾安全带提醒

查看副驾安全带是否正在报警提醒。

- **能力 id**：`safety_belt.warning`　·　**目录文件**：`safety_belt.json`
- **常见说法**：`副驾安全带提醒`、`副驾安全带报警`
- **read** → `SensorValue`：`read()` — 读取副驾安全带提醒。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 副驾安全带是否正在报警。
- **返回值含义**：`1`→WARNING（报警中）、`2`→NORMAL（正常）
- **参数**：无（调用时不需要传参）。

### `abilities.sensor.attitude` · 车辆姿态角

查看车辆俯仰、侧倾或倾斜角度，单位为度。

- **能力 id**：`sensor.attitude`　·　**目录文件**：`sensor.json`
- **常见说法**：`车辆姿态角`
- **read** → `SensorValue`：`read(int axis)` — 读取所选来源的车辆姿态角。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车辆姿态角。单位 `degree`。
- **参数**：
  - `axis`（int，selector，read）：所选车辆姿态角的来源。
    - 可选值 `1` — 俯仰角；`2` — 侧倾角；`3` — 倾斜角
- **备注**：俯仰与侧倾使用方程豹相关信号；只有倾斜角配置了状态事件。

### `abilities.sensor.rain_amount` · 雨量

查看雨量传感器的读数。

- **能力 id**：`sensor.rain_amount`　·　**目录文件**：`sensor.json`
- **常见说法**：`雨量`
- **read** → `SensorValue`：`read()` — 读取雨量。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 雨量读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.sensor.steering_wheel_angle` · 方向盘转角

查看方向盘当前转动角度的读数。

- **能力 id**：`sensor.steering_wheel_angle`　·　**目录文件**：`sensor.json`
- **常见说法**：`方向盘转角`
- **read** → `SensorValue`：`read()` — 读取方向盘转角。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 方向盘转角读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.sensor.steering_wheel_speed` · 方向盘转速

查看方向盘转动速度的读数。

- **能力 id**：`sensor.steering_wheel_speed`　·　**目录文件**：`sensor.json`
- **常见说法**：`方向盘转速`
- **read** → `SensorValue`：`read()` — 读取方向盘转速。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 方向盘转速读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.setting.ambient_light_area_brightness` · 分区氛围灯亮度

分别调节前排、后排或全车的氛围灯亮度。

- **能力 id**：`setting.ambient_light_area_brightness`　·　**目录文件**：`setting_ambient_lighting.json`
- **常见说法**：`分区氛围灯亮度`
- **read** → `SensorValue`：`read(int area)` — 读取分区氛围灯亮度。
- **write** → `void`：`write(int area, int value)` — 设置分区氛围灯亮度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 所选区域的氛围灯亮度，0 为关闭，1 至 5 为亮度档位。
- **参数**：
  - `area`（int，selector，read/write）：氛围灯所在区域。
    - 可选值 `1` — 前排；`2` — 后排；`3` — 全车
  - `value`（int，write）：所选区域的氛围灯亮度，0 为关闭，1 至 5 为亮度档位。
    - 合法范围 `0~5`
- **备注**：适用于支持前后排分区控制的车型；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.setting.ambient_light_brightness` · 全车氛围灯亮度

一起调节全车氛围灯亮度，也可将亮度设为零关闭。DiLink 3、4 车机（Android 12 以下）没有氛围灯总开关，打开或关闭氛围灯也使用本能力：关闭设为 0，打开可设为最高亮度 5。

- **能力 id**：`setting.ambient_light_brightness`　·　**目录文件**：`setting_ambient_lighting.json`
- **常见说法**：`全车氛围灯亮度`
- **read** → `SensorValue`：`read()` — 读取全车氛围灯亮度。
- **write** → `void`：`write(int value)` — 设置全车氛围灯亮度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 氛围灯亮度，0 为关闭，1 至 5 为亮度档位。
- **参数**：
  - `value`（int，write）：氛围灯亮度，0 为关闭，1 至 5 为亮度档位。
    - 合法范围 `0~5`
- **备注**：动态、音乐律动或多彩模式开启时，此亮度设置可能不生效；读取值为前排亮度，不能据此确认所有区域的亮度。

### `abilities.setting.ambient_light_power` · 氛围灯总开关

打开或关闭全车氛围灯，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用全车氛围灯亮度。

- **能力 id**：`setting.ambient_light_power`　·　**目录文件**：`setting_ambient_lighting.json`
- **常见说法**：`氛围灯总开关`
- **read** → `SensorValue`：`read()` — 读取氛围灯总开关。
- **write** → `void`：`write(boolean value)` — 打开或关闭氛围灯总。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 氛围灯总开关状态。
- **返回值含义**：`1`→OFF（已关闭）、`2`→ON（已打开）
- **参数**：
  - `value`（boolean，write）：是否开启氛围灯总。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.anti_motion` · 智能防晕车

打开或关闭智能防晕车功能。

- **能力 id**：`setting.anti_motion`　·　**目录文件**：`setting.json`
- **常见说法**：`智能防晕车`
- **read** → `SensorValue`：`read()` — 读取智能防晕车。
- **write** → `void`：`write(boolean value)` — 打开或关闭智能防晕车。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 智能防晕车状态。
- **返回值含义**：`1`→OFF（已关闭）、`2`→ON（已开启）
- **参数**：
  - `value`（boolean，write）：是否开启智能防晕车。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.camping_leveling` · 露营调平

开启或关闭露营调平。

- **能力 id**：`setting.camping_leveling`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`露营调平`
- **read** → `SensorValue`：`read()` — 读取露营调平。
- **write** → `void`：`write(boolean enabled)` — 设置露营调平是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 露营调平状态。
- **返回值含义**：`1`→ON（已开启）、`2`→OFF（已关闭）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启露营调平。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：需车辆配备露营调平能力；受理结果不表示车身已经调平。

### `abilities.setting.comfort_mode` · 舒适控制强度

选择舒适控制的较强、适中或偏弱档位。

- **能力 id**：`setting.comfort_mode`　·　**目录文件**：`setting.json`
- **常见说法**：`舒适控制强度`
- **read** → `SensorValue`：`read()` — 读取舒适控制强度。
- **write** → `void`：`write(int maximumLevel, int value)` — 设置舒适控制强度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前舒适控制强度。
- **返回值含义**：`1`→STRONG（较强）、`2`→MEDIUM（适中）、`3`→WEAK（偏弱）
- **参数**：
  - `maximumLevel`（int，selector，write）：车辆舒适控制的档位数，创建规则时由用户选择。
    - 可选值 `2` — 2档；`3` — 3档
  - `value`（int，write）：目标舒适控制强度。
    - 可选值 `1` — 较强；`2` — 适中；`3` — 偏弱
- **备注**：两档车型可选较强、适中；三档车型还可选偏弱。

### `abilities.setting.cruise_prompt` · 巡航提示

查看车辆当前是否有巡航提示。

- **能力 id**：`setting.cruise_prompt`　·　**目录文件**：`setting.json`
- **常见说法**：`巡航提示`
- **read** → `SensorValue`：`read()` — 读取巡航提示。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前巡航提示。
- **返回值含义**：`0`→NO_PROMPT（无提示）、`1`→PROMPT（有提示）
- **参数**：无（调用时不需要传参）。

### `abilities.setting.etc_power` · ETC 开关

打开或关闭车辆 ETC 功能。

- **能力 id**：`setting.etc_power`　·　**目录文件**：`setting.json`
- **常见说法**：`ETC 开关`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 打开或关闭ETC 。
- **参数**：
  - `value`（boolean，write）：是否开启ETC 。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.headlamp_height` · 前照灯高度

调节前照灯照射高度，并分别查看左右灯的高度档位。

- **能力 id**：`setting.headlamp_height`　·　**目录文件**：`setting.json`
- **常见说法**：`前照灯高度`
- **read** → `SensorValue`：`read(int area)` — 读取前照灯高度。
- **write** → `void`：`write(int value)` — 设置前照灯高度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 前照灯高度档位；读取到 0 表示档位无效。
- **参数**：
  - `area`（int，selector，read）：左侧或右侧前照灯。
    - 可选值 `1` — 左前；`2` — 右前
  - `value`（int，write）：前照灯高度档位；读取到 0 表示档位无效。
    - 合法范围 `1~11`
- **备注**：高度设置同时作用于左右前照灯；读取时可分别选择左右灯；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.setting.hud_brightness` · HUD 亮度

调节抬头显示的亮度档位。

- **能力 id**：`setting.hud_brightness`　·　**目录文件**：`setting.json`
- **常见说法**：`HUD 亮度`
- **read** → `SensorValue`：`read()` — 读取HUD 亮度。
- **write** → `void`：`write(int value)` — 设置HUD 亮度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前HUD 亮度。
- **参数**：
  - `value`（int，write）：目标HUD 亮度。
    - 合法范围 `1~11`

### `abilities.setting.hud_power` · HUD 开关

打开或关闭抬头显示。

- **能力 id**：`setting.hud_power`　·　**目录文件**：`setting.json`
- **常见说法**：`HUD 开关`
- **read** → `SensorValue`：`read()` — 读取HUD 开关。
- **write** → `void`：`write(boolean value)` — 打开或关闭HUD 。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` HUD 开关状态。
- **返回值含义**：`1`→ON（已开启）、`2`→OFF（已关闭）
- **参数**：
  - `value`（boolean，write）：是否开启HUD 。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.mirror_auto_brightness` · 电子后视镜自动亮度

选择电子后视镜自动调节亮度或手动调节亮度。

- **能力 id**：`setting.mirror_auto_brightness`　·　**目录文件**：`setting_mirrors.json`
- **常见说法**：`电子后视镜自动亮度`
- **read** → `SensorValue`：`read()` — 读取电子后视镜自动亮度。
- **write** → `void`：`write(boolean value)` — 选择电子后视镜自动或手动亮度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电子后视镜亮度调节方式。
- **返回值含义**：`1`→AUTO（自动亮度）、`2`→MANUAL（手动亮度）
- **参数**：
  - `value`（boolean，write）：是否自动调节亮度；关闭后使用手动亮度。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.mirror_auto_fold` · 后视镜自动折叠

查看、打开或关闭后视镜自动折叠。

- **能力 id**：`setting.mirror_auto_fold`　·　**目录文件**：`setting_mirrors.json`
- **常见说法**：`后视镜自动折叠`
- **read** → `SensorValue`：`read()` — 读取后视镜自动折叠是否开启。
- **write** → `void`：`write(boolean value)` — 打开或关闭后视镜自动折叠。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启后视镜自动折叠。
- **参数**：
  - `value`（boolean，value，write）：是否开启后视镜自动折叠。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.setting.mirror_brightness` · 电子后视镜亮度

调节左侧电子后视镜显示屏亮度。

- **能力 id**：`setting.mirror_brightness`　·　**目录文件**：`setting_mirrors.json`
- **常见说法**：`电子后视镜亮度`
- **read** → `SensorValue`：`read()` — 读取电子后视镜亮度。
- **write** → `void`：`write(int value)` — 设置电子后视镜亮度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电子后视镜亮度。
- **参数**：
  - `value`（int，write）：目标电子后视镜亮度。
    - 合法范围 `13~22`
- **备注**：只调节左侧显示屏，是否联动右侧取决于车型。

### `abilities.setting.mirror_fold_command` · 后视镜折叠动作

立即折叠或展开车外后视镜，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持立即折叠，只能设置后视镜自动折叠。

- **能力 id**：`setting.mirror_fold_command`　·　**目录文件**：`setting_mirrors.json`
- **常见说法**：`后视镜折叠动作`
- **read**：不提供
- **write** → `void`：`write(int value)` — 折叠或展开车外后视镜。
- **参数**：
  - `value`（int，write）：目标后视镜折叠动作。
    - 可选值 `1` — 折叠；`2` — 展开

### `abilities.setting.mirror_follow_up` · 后视镜随动

查看或设置车外后视镜随动模式。

- **能力 id**：`setting.mirror_follow_up`　·　**目录文件**：`setting_mirrors.json`
- **常见说法**：`后视镜随动`
- **read** → `SensorValue`：`read()` — 读取后视镜随动。
- **write** → `void`：`write(int rawCommand)` — 设置后视镜随动。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`rawState` 后视镜随动状态码；各数值对应的含义尚未确认。
- **参数**：
  - `rawCommand`（int，write）：后视镜随动模式码；0、1 对应的含义需按车型确认。
    - 合法范围 `0~1`
- **备注**：模式码为 0 或 1，具体对应含义尚未确认；不能按开启或关闭代填；部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.setting.mirror_reverse_tilt` · 倒车后视镜翻转

设置倒车时是否将后视镜翻转至记忆角度。

- **能力 id**：`setting.mirror_reverse_tilt`　·　**目录文件**：`setting_mirrors.json`
- **常见说法**：`倒车后视镜翻转`
- **read** → `SensorValue`：`read()` — 读取倒车后视镜翻转。
- **write** → `void`：`write(boolean value)` — 打开或关闭倒车后视镜翻转。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 倒车后视镜翻转状态。
- **返回值含义**：`0`→OFF（已关闭）、`1`→ON（已开启）
- **参数**：
  - `value`（boolean，write）：倒车时是否翻转后视镜。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.rear_entertainment_screen` · 后排娱乐屏

打开或关闭后排娱乐屏。

- **能力 id**：`setting.rear_entertainment_screen`　·　**目录文件**：`setting.json`
- **常见说法**：`后排娱乐屏`
- **read** → `SensorValue`：`read(int area)` — 读取后排娱乐屏。
- **write** → `void`：`write(int area, boolean enabled)` — 打开或关闭指定后排娱乐屏。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 吸顶屏状态。
- **返回值含义**：`0`→OPEN_0（已打开）、`1`→OPEN_1（已打开）、`2`→CLOSED（已关闭）
- **参数**：
  - `area`（int，selector，read/write）：后排娱乐屏的位置与类型。
    - 可选值 `1` — 吸顶屏；`2` — 2023款D9左屏；`3` — 2023款D9右屏；`4` — 2023款D9左右屏
  - `enabled`（boolean，value，write）：打开或点亮所选娱乐屏。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：吸顶屏控制展开或收起；2023 款 D9 控制左右屏亮灭。按实际屏幕选择区域；左右屏同时控制只代表一次批量下发，不保证两块屏幕同时完成。

### `abilities.setting.road_preview` · 路面预瞄

打开或关闭路面预瞄功能。

- **能力 id**：`setting.road_preview`　·　**目录文件**：`setting.json`
- **常见说法**：`路面预瞄`
- **read** → `SensorValue`：`read()` — 读取路面预瞄。
- **write** → `void`：`write(boolean value)` — 打开或关闭路面预瞄。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 路面预瞄状态。
- **返回值含义**：`0`→OFF（已关闭）、`1`→ON（已开启）
- **参数**：
  - `value`（boolean，write）：是否开启路面预瞄。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.rollover_airbag_suppression` · 气囊翻滚抑制

开启或关闭气囊翻滚抑制。

- **能力 id**：`setting.rollover_airbag_suppression`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`气囊翻滚抑制`
- **read** → `SensorValue`：`read()` — 读取气囊翻滚抑制。
- **write** → `void`：`write(boolean enabled)` — 设置气囊翻滚抑制是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 气囊翻滚抑制状态。
- **返回值含义**：`1`→ON（已打开）、`2`→OFF（已关闭）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启气囊翻滚抑制。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：仅方程豹适用，需在越野模式下使用；操作后需确认车辆实际状态。

### `abilities.setting.sand_abs` · 沙地 ABS

开启或关闭沙地 ABS。

- **能力 id**：`setting.sand_abs`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`沙地 ABS`
- **read** → `SensorValue`：`read()` — 读取沙地 ABS。
- **write** → `void`：`write(boolean enabled)` — 设置沙地 ABS是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 沙地 ABS 状态。
- **返回值含义**：`1`→OFF（已关闭）、`2`→ON（已打开）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启沙地 ABS。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：仅方程豹适用，需在越野模式下使用；操作后需确认车辆实际状态。

### `abilities.setting.seat_heating` · 座椅加热

调节指定座椅的加热档位。

- **能力 id**：`setting.seat_heating`　·　**目录文件**：`setting_seat_climate.json`
- **常见说法**：`座椅加热`
- **read** → `SensorValue`：`read(String area)` — 读取座椅加热。
- **write** → `void`：`write(String area, int value)` — 设置座椅加热。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 座椅加热当前档位。0 为关闭。
- **参数**：
  - `area`（String，selector，read/write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
  - `value`（int，write）：座椅加热目标档位。0 为关闭。
    - 合法范围 `0~2`、`0~3`、`1~5`
- **备注**：两档、三档车型可用 0 关闭；五档车型请使用“关闭五档座椅加热”；五档车型读取到 0 表示尚未设定档位，不能据此判断是否关闭；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.setting.seat_heating_off` · 关闭五档座椅加热

关闭指定座椅的加热，适用于五档车型。

- **能力 id**：`setting.seat_heating_off`　·　**目录文件**：`setting_seat_climate.json`
- **常见说法**：`关闭五档座椅加热`
- **read**：不提供
- **write** → `void`：`write(String area)` — 关闭指定座椅加热。
- **参数**：
  - `area`（String，selector，write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
- **备注**：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.setting.seat_massage_level` · 座椅按摩强度

调节指定座椅的按摩强度。

- **能力 id**：`setting.seat_massage_level`　·　**目录文件**：`setting_seat_massage.json`
- **常见说法**：`座椅按摩强度`
- **read** → `SensorValue`：`read(String area)` — 读取座椅按摩强度。
- **write** → `void`：`write(String area, int value)` — 设置座椅按摩强度。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 按摩强度，1 至 3 档。0 表示没有有效档位。
- **参数**：
  - `area`（String，selector，read/write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
  - `value`（int，write）：按摩强度，1 至 3 档。
    - 合法范围 `1~3`
- **备注**：强度分为 1 至 3 档；关闭请将“座椅按摩模式”设为关闭；读取到 0 表示没有有效档位，不能据此判断按摩是否关闭；是否关闭以座椅按摩模式为准。

### `abilities.setting.seat_massage_mode` · 座椅按摩模式

选择指定座椅的按摩模式，或关闭按摩。

- **能力 id**：`setting.seat_massage_mode`　·　**目录文件**：`setting_seat_massage.json`
- **常见说法**：`座椅按摩模式`
- **read** → `SensorValue`：`read(String area)` — 读取座椅按摩模式。
- **write** → `void`：`write(String area, int value)` — 设置座椅按摩模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前座椅按摩模式。
- **返回值含义**：`0`→off（关闭）、`1`→wave（波浪）、`2`→pulse（脉冲）、`3`→stretch（伸展）、`4`→relax（松弛）、`5`→soothe（舒缓）
- **参数**：
  - `area`（String，selector，read/write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
  - `value`（int，write）：目标座椅按摩模式。
    - 可选值 `0` — 关闭；`1` — 波浪；`2` — 脉冲；`3` — 伸展；`4` — 松弛；`5` — 舒缓

### `abilities.setting.seat_memory_save` · 保存座椅记忆

将当前座椅位置保存到指定记忆位，供以后恢复。

- **能力 id**：`setting.seat_memory_save`　·　**目录文件**：`setting_seat_position.json`
- **常见说法**：`保存座椅记忆`
- **read**：不提供
- **write** → `void`：`write(String area, int value)` — 保存当前座椅位置到指定记忆位。
- **参数**：
  - `area`（String，selector，write）：座椅位置。
    - 可选值 `frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
  - `value`（int，write）：保存位置的记忆位编号。
    - 合法范围 `1~2`
- **备注**：先将座椅调到所需位置；保存会覆盖所选记忆位；只支持副驾、左后、右后，主驾不可使用。

### `abilities.setting.seat_position` · 座椅预设位置

将指定座椅移动到常规、舒躺、平躺或折叠位置。

- **能力 id**：`setting.seat_position`　·　**目录文件**：`setting_seat_position.json`
- **常见说法**：`座椅预设位置`
- **read**：不提供
- **write** → `void`：`write(int position, String area)` — 设置座椅预设位置。
- **参数**：
  - `position`（int，value，write）：要移动到的座椅预设位置。
    - 可选值 `1` — 常规；`2` — 舒躺；`3` — 平躺；`4` — 折叠
  - `area`（String，selector，write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后；`thirdRowLeft` — 第三排左；`thirdRowRight` — 第三排右
- **备注**：仅适用于支持灵动空间的车型，例如腾势 N9；前排和第二排支持常规、舒躺、平躺；第三排支持常规和折叠。

### `abilities.setting.seat_ventilation` · 座椅通风

调节指定座椅的通风档位。

- **能力 id**：`setting.seat_ventilation`　·　**目录文件**：`setting_seat_climate.json`
- **常见说法**：`座椅通风`
- **read** → `SensorValue`：`read(String area)` — 读取座椅通风。
- **write** → `void`：`write(String area, int value)` — 设置座椅通风。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 座椅通风当前档位。0 为关闭。
- **参数**：
  - `area`（String，selector，read/write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
  - `value`（int，write）：座椅通风目标档位。0 为关闭。
    - 合法范围 `0~2`、`0~3`、`1~5`
- **备注**：两档、三档车型可用 0 关闭；五档车型请使用“关闭五档座椅通风”；五档车型读取到 0 表示尚未设定档位，不能据此判断是否关闭；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.setting.seat_ventilation_off` · 关闭五档座椅通风

关闭指定座椅的通风，适用于五档车型。

- **能力 id**：`setting.seat_ventilation_off`　·　**目录文件**：`setting_seat_climate.json`
- **常见说法**：`关闭五档座椅通风`
- **read**：不提供
- **write** → `void`：`write(String area)` — 关闭指定座椅通风。
- **参数**：
  - `area`（String，selector，write）：座椅位置。
    - 可选值 `driver` — 主驾；`frontPassenger` — 副驾；`rearLeft` — 左后；`rearRight` — 右后
- **备注**：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.setting.soc_target` · SOC 保电目标

设置希望车辆保留的电量百分比。

- **能力 id**：`setting.soc_target`　·　**目录文件**：`setting.json`
- **常见说法**：`SOC 保电目标`
- **read** → `SensorValue`：`read()` — 读取SOC 保电目标。
- **write** → `void`：`write(int value)` — 设置SOC 保电目标。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前SOC 保电目标，单位为 %。单位 `percent`。
- **参数**：
  - `value`（int，write）：目标SOC 保电目标，单位为 %。
    - 单位 `percent`；合法范围 `20~70`
- **备注**：目标范围为 20% 至 70%；设置为当前电量需要先读取电量，再传入目标。

### `abilities.setting.steering_assist_mode` · 转向助力模式

选择舒适或运动转向助力，调整方向盘的操控感受。

- **能力 id**：`setting.steering_assist_mode`　·　**目录文件**：`setting.json`
- **常见说法**：`转向助力模式`
- **read** → `SensorValue`：`read()` — 读取转向助力模式。
- **write** → `void`：`write(int value)` — 设置转向助力模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前转向助力模式。
- **返回值含义**：`1`→COMFORT（舒适）、`2`→SPORT（运动）
- **参数**：
  - `value`（int，write）：目标转向助力模式。
    - 可选值 `1` — 舒适；`2` — 运动

### `abilities.setting.steering_wheel_heating` · 方向盘加热开关

打开或关闭方向盘加热。

- **能力 id**：`setting.steering_wheel_heating`　·　**目录文件**：`setting.json`
- **常见说法**：`方向盘加热开关`
- **read** → `SensorValue`：`read()` — 读取方向盘加热开关。
- **write** → `void`：`write(boolean value)` — 打开或关闭方向盘加热。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否开启方向盘加热。
- **参数**：
  - `value`（boolean，write）：是否开启方向盘加热。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.setting.steering_wheel_heating_level` · 方向盘加热档位

调节方向盘加热强度，适用于三档或五档车型。

- **能力 id**：`setting.steering_wheel_heating_level`　·　**目录文件**：`setting.json`
- **常见说法**：`方向盘加热档位`
- **read** → `SensorValue`：`read()` — 读取方向盘加热档位。
- **write** → `void`：`write(int value)` — 设置方向盘加热档位。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 方向盘加热档位。0 表示未设定档位。
- **参数**：
  - `value`（int，write）：方向盘加热档位。请先打开方向盘加热。
    - 合法范围 `1~3`、`1~5`
- **备注**：调节档位前应先打开方向盘加热；关闭请使用“方向盘加热开关”；读取到 0 表示尚未设定档位，不能据此判断加热是否关闭；含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)。

### `abilities.setting.suspension_extreme_height` · 悬架超高或超低模式

确认或激活悬架超高、超低模式。

- **能力 id**：`setting.suspension_extreme_height`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`悬架超高或超低模式`
- **read**：不提供
- **write** → `void`：`write(int mode, int action)` — 对指定悬架高度模式执行确认或激活。
- **参数**：
  - `mode`（int，value，write）：悬架超高或超低模式。
    - 可选值 `1` — 超高；`2` — 超低
  - `action`（int，write）：要执行模式确认还是模式激活。
    - 可选值 `2` — 确认模式；`3` — 激活模式
- **备注**：确认和激活分别执行，需完成车辆要求的确认步骤；超高模式仅适用于支持该功能的腾势车型，方程豹暂不适用。

### `abilities.setting.suspension_height_command` · 悬架高度调节

将悬架调高或调低。

- **能力 id**：`setting.suspension_height_command`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`悬架高度调节`
- **read**：不提供
- **write** → `void`：`write(int nativeRoute, int value)` — 将悬架调高或调低。
- **参数**：
  - `nativeRoute`（int，selector，write）：车辆的悬架控制通道，创建规则时由用户选择。
    - 可选值 `1000` — 通用控制路由；`1004` — 底盘控制路由
  - `value`（int，write）：目标悬架高度调节。
    - 可选值 `1` — 调高；`2` — 调低
- **备注**：创建规则时需由用户确认车辆使用的悬架控制通道。

### `abilities.setting.suspension_height_lock` · 悬架高度锁定

锁定或解除悬架高度锁定。

- **能力 id**：`setting.suspension_height_lock`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`悬架高度锁定`
- **read** → `SensorValue`：`read()` — 读取悬架高度锁定。
- **write** → `void`：`write(boolean value)` — 锁定或解除悬架高度锁定。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 悬架高度锁定状态。
- **返回值含义**：`1`→UNLOCKED（已解锁）、`2`→LOCKED（已锁定）
- **参数**：
  - `value`（boolean，write）：是否锁定悬架高度。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明

### `abilities.setting.suspension_mode` · 云辇模式

选择云辇悬架的舒适或运动模式。

- **能力 id**：`setting.suspension_mode`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`云辇模式`
- **read** → `SensorValue`：`read()` — 读取云辇模式。
- **write** → `void`：`write(int value)` — 设置云辇模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前云辇模式。
- **返回值含义**：`1`→COMFORT（舒适）、`2`→SPORT（运动）
- **参数**：
  - `value`（int，write）：目标云辇模式。
    - 可选值 `1` — 舒适；`2` — 运动

### `abilities.setting.vector_control` · 矢量控制模式

选择 CVC 矢量控制的舒适或运动模式。

- **能力 id**：`setting.vector_control`　·　**目录文件**：`setting_suspension.json`
- **常见说法**：`矢量控制模式`
- **read** → `SensorValue`：`read()` — 读取矢量控制模式。
- **write** → `void`：`write(int mode)` — 设置矢量控制模式。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`mode` 当前矢量控制模式。
- **返回值含义**：`1`→comfort（舒适）、`2`→sport（运动）
- **参数**：
  - `mode`（int，value，write）：目标矢量控制模式。
    - 可选值 `1` — 舒适；`2` — 运动

### `abilities.shortcut_audio.master_mute` · 主静音状态

读取或设置车辆主静音；不能以当前焦点流静音代替。

- **能力 id**：`shortcut_audio.master_mute`　·　**目录文件**：`shortcut_audio_charging.json`
- **常见说法**：`主静音状态`
- **read** → `SensorValue`：`read()` — 读取主静音状态。
- **write** → `void`：`write(boolean value)` — 设置主静音状态，只代表命令受理。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 是否主静音。
- **参数**：
  - `value`（boolean，write）：是否主静音。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_audio.master_volume` · 主音量

读取车辆主音量，范围 0 至 100；与当前焦点流音量不一定相同。

- **能力 id**：`shortcut_audio.master_volume`　·　**目录文件**：`shortcut_audio_charging.json`
- **常见说法**：`主音量`
- **read** → `SensorValue`：`read()` — 读取主音量。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 车辆主音量，0 至 100。单位 `percent`。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_audio.navigation_mute` · 导航静音状态

读取车辆导航静音原始状态 2 或 3；具体含义以车型定义为准。

- **能力 id**：`shortcut_audio.navigation_mute`　·　**目录文件**：`shortcut_audio_charging.json`
- **常见说法**：`导航静音状态`
- **read** → `SensorValue`：`read()` — 读取导航静音状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 导航静音原始状态，2 或 3。
- **返回值含义**：`2`→state2（状态 2，具体含义由车型定义）、`3`→state3（状态 3，具体含义由车型定义）
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_audio.navigation_volume` · 导航音量

读取车辆导航音量，范围 0 至 10 档，不是百分比。

- **能力 id**：`shortcut_audio.navigation_volume`　·　**目录文件**：`shortcut_audio_charging.json`
- **常见说法**：`导航音量`
- **read** → `SensorValue`：`read()` — 读取导航音量。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 导航音量档位，0 至 10。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_charging.discharge` · 对外放电

打开或关闭对外放电；受理不代表车辆已完成。

- **能力 id**：`shortcut_charging.discharge`　·　**目录文件**：`shortcut_audio_charging.json`
- **常见说法**：`对外放电`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 设置对外放电开关。
- **参数**：
  - `value`（boolean，write）：目标开关状态：true 为开启，false 为关闭。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_charging.wireless` · 无线充电

打开或关闭无线充电；受理不代表车辆已完成。

- **能力 id**：`shortcut_charging.wireless`　·　**目录文件**：`shortcut_audio_charging.json`
- **常见说法**：`无线充电`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 设置无线充电开关。
- **参数**：
  - `value`（boolean，write）：目标开关状态：true 为开启，false 为关闭。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_gearbox.auto_mode` · 自动挡原始档位

档位归一化使用的原始状态：自动挡原始档位

- **能力 id**：`shortcut_gearbox.auto_mode`　·　**目录文件**：`shortcut_gearbox.json`
- **read** → `SensorValue`：`read()` — 读取自动挡原始档位
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 自动挡原始档位。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_gearbox.epb` · 电子手刹原始状态

档位归一化使用的原始状态：电子手刹原始状态

- **能力 id**：`shortcut_gearbox.epb`　·　**目录文件**：`shortcut_gearbox.json`
- **read** → `SensorValue`：`read()` — 读取电子手刹原始状态
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 电子手刹原始状态。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_gearbox.legacy_daylight` · 旧规则日行灯开关

旧规则日行灯关闭的执行合同。

- **能力 id**：`shortcut_gearbox.legacy_daylight`　·　**目录文件**：`shortcut_gearbox.json`
- **read**：不提供
- **write** → `void`：`write(boolean value)` — 设置日间行车灯。
- **参数**：
  - `value`（boolean，value，write）：是否开启日行灯。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_gearbox.manual_mode` · 手动挡原始档位

档位归一化使用的原始状态：手动挡原始档位

- **能力 id**：`shortcut_gearbox.manual_mode`　·　**目录文件**：`shortcut_gearbox.json`
- **read** → `SensorValue`：`read()` — 读取手动挡原始档位
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 手动挡原始档位。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_gearbox.park_switch` · 驻车制动开关原始状态

档位归一化使用的原始状态：驻车制动开关原始状态

- **能力 id**：`shortcut_gearbox.park_switch`　·　**目录文件**：`shortcut_gearbox.json`
- **read** → `SensorValue`：`read()` — 读取驻车制动开关原始状态
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 驻车制动开关原始状态。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_gearbox.type` · 变速箱类型

档位归一化使用的原始状态：变速箱类型

- **能力 id**：`shortcut_gearbox.type`　·　**目录文件**：`shortcut_gearbox.json`
- **read** → `SensorValue`：`read()` — 读取变速箱类型
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 变速箱类型。
- **参数**：无（调用时不需要传参）。
- **备注**：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析。

### `abilities.shortcut_wiper.front_speed` · 前雨刮速度

仅在前雨刮档位未知时用于判断是否工作。

- **能力 id**：`shortcut_wiper.front_speed`　·　**目录文件**：`shortcut_wiper.json`
- **read** → `SensorValue`：`read()` — 读取前雨刮速度
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 原始速度。
- **参数**：无（调用时不需要传参）。

### `abilities.shortcut_wiper_gear.front_gear` · 前雨刮原始档位

保留原始整数，由宿主判断档位是否有效。

- **能力 id**：`shortcut_wiper_gear.front_gear`　·　**目录文件**：`shortcut_wiper.json`
- **read** → `SensorValue`：`read()` — 读取前雨刮原始档位
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 原始档位。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.battery_capacity` · 动力电池容量

查看车辆报告的动力电池容量读数。

- **能力 id**：`statistic.battery_capacity`　·　**目录文件**：`statistic.json`
- **常见说法**：`动力电池容量`
- **read** → `SensorValue`：`read()` — 读取动力电池容量。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 动力电池容量读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.battery_health` · 电池健康参考值

查看动力电池健康度参考百分比。

- **能力 id**：`statistic.battery_health`　·　**目录文件**：`statistic.json`
- **常见说法**：`电池健康参考值`
- **read** → `SensorValue`：`read()` — 读取电池健康参考值。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电池健康参考值，单位为 %。单位 `percent`。
- **参数**：无（调用时不需要传参）。
- **备注**：仅供健康度参考，实际健康度需通过充放电测试确认。

### `abilities.statistic.battery_soc` · 动力电池电量

查看动力电池剩余电量百分比。

- **能力 id**：`statistic.battery_soc`　·　**目录文件**：`statistic.json`
- **常见说法**：`动力电池电量`
- **read** → `SensorValue`：`read()` — 读取动力电池电量。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前动力电池电量，单位为 %。单位 `percent`。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.battery_temperature` · 电芯温度

查看电芯最高、平均或最低温度，单位为摄氏度。

- **能力 id**：`statistic.battery_temperature`　·　**目录文件**：`statistic.json`
- **常见说法**：`电芯温度`
- **read** → `SensorValue`：`read(int area)` — 读取电芯温度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电芯温度，单位为 ℃。单位 `celsius`。
- **参数**：
  - `area`（int，selector，read）：要查看最高、平均还是最低温度。
    - 可选值 `50` — 最高；`51` — 平均；`52` — 最低

### `abilities.statistic.battery_voltage` · 电芯电压

查看电芯最高或最低电压，单位为伏特。

- **能力 id**：`statistic.battery_voltage`　·　**目录文件**：`statistic.json`
- **常见说法**：`电芯电压`
- **read** → `SensorValue`：`read(int area)` — 读取电芯电压。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前电芯电压，单位为 V。单位 `volt`。
- **参数**：
  - `area`（int，selector，read）：要查看最高还是最低电压。
    - 可选值 `50` — 最高；`52` — 最低

### `abilities.statistic.coolant_temperature` · 冷却液温度

查看发动机冷却液温度读数。

- **能力 id**：`statistic.coolant_temperature`　·　**目录文件**：`statistic.json`
- **常见说法**：`冷却液温度`
- **read** → `SensorValue`：`read()` — 读取冷却液温度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 冷却液温度读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.electric_consumption_last_200m` · 近 200 米平均电耗

查看最近 200 米的平均电耗，单位为千瓦时每百公里。

- **能力 id**：`statistic.electric_consumption_last_200m`　·　**目录文件**：`statistic.json`
- **常见说法**：`近 200 米平均电耗`
- **read** → `SensorValue`：`read()` — 读取近 200 米平均电耗。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前近 200 米平均电耗，单位为 kWh/100km。单位 `kilowattHoursPer100Kilometers`。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.electric_consumption_per_100km` · 平均电耗

查看车辆报告的平均电耗读数。

- **能力 id**：`statistic.electric_consumption_per_100km`　·　**目录文件**：`statistic.json`
- **常见说法**：`平均电耗`
- **read** → `SensorValue`：`read()` — 读取平均电耗。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 平均电耗读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.electric_range` · 纯电剩余续航

查看剩余电量预计可行驶的公里数。

- **能力 id**：`statistic.electric_range`　·　**目录文件**：`statistic.json`
- **常见说法**：`纯电剩余续航`
- **read** → `SensorValue`：`read()` — 读取纯电剩余续航。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前纯电剩余续航，单位为 km。单位 `kilometer`。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.fuel_consumption_last_200m` · 近 200 米平均油耗

查看最近 200 米的平均油耗，单位为升每百公里。

- **能力 id**：`statistic.fuel_consumption_last_200m`　·　**目录文件**：`statistic.json`
- **常见说法**：`近 200 米平均油耗`
- **read** → `SensorValue`：`read()` — 读取近 200 米平均油耗。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前近 200 米平均油耗，单位为 L/100km。单位 `litersPer100Kilometers`。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.fuel_percent` · 剩余油量

查看油箱剩余燃油百分比。

- **能力 id**：`statistic.fuel_percent`　·　**目录文件**：`statistic.json`
- **常见说法**：`剩余油量`
- **read** → `SensorValue`：`read()` — 读取剩余油量。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前剩余油量，单位为 %。单位 `percent`。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.fuel_range` · 燃油剩余续航

查看剩余燃油预计可行驶的公里数。

- **能力 id**：`statistic.fuel_range`　·　**目录文件**：`statistic.json`
- **常见说法**：`燃油剩余续航`
- **read** → `SensorValue`：`read()` — 读取燃油剩余续航。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前燃油剩余续航，单位为 km。单位 `kilometer`。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.low_voltage_battery_voltage` · 低压电池电压

查看低压蓄电池电压读数。

- **能力 id**：`statistic.low_voltage_battery_voltage`　·　**目录文件**：`statistic.json`
- **常见说法**：`低压电池电压`
- **read** → `SensorValue`：`read()` — 读取低压电池电压。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 低压电池电压读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.total_electric_consumption` · 累计电耗

查看车辆累计用电量读数。

- **能力 id**：`statistic.total_electric_consumption`　·　**目录文件**：`statistic.json`
- **常见说法**：`累计电耗`
- **read** → `SensorValue`：`read()` — 读取累计电耗。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 累计电耗读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.total_fuel_consumption` · 累计油耗

查看车辆累计燃油消耗读数。

- **能力 id**：`statistic.total_fuel_consumption`　·　**目录文件**：`statistic.json`
- **常见说法**：`累计油耗`
- **read** → `SensorValue`：`read()` — 读取累计油耗。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 累计油耗读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.statistic.total_mileage` · 总里程

查看车辆累计行驶里程读数。

- **能力 id**：`statistic.total_mileage`　·　**目录文件**：`statistic.json`
- **常见说法**：`总里程`
- **read** → `SensorValue`：`read()` — 读取总里程。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 总里程读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.tyre.pressure` · 轮胎压力

查看指定车轮的胎压读数。

- **能力 id**：`tyre.pressure`　·　**目录文件**：`tyre.json`
- **常见说法**：`轮胎压力`
- **read** → `SensorValue`：`read(int area)` — 读取轮胎压力。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 轮胎压力读数，单位尚未确认。
- **参数**：
  - `area`（int，selector，read）：车轮位置。
    - 可选值 `13` — 左前轮；`14` — 右前轮；`15` — 左后轮；`16` — 右后轮

### `abilities.vehicle.accelerator_depth` · 加速踏板深度

查看加速踏板踩下的百分比。

- **能力 id**：`vehicle.accelerator_depth`　·　**目录文件**：`speed.json`
- **常见说法**：`加速踏板深度`
- **read** → `SensorValue`：`read()` — 读取加速踏板深度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前加速踏板深度，单位为 %。单位 `percent`。
- **参数**：无（调用时不需要传参）。

### `abilities.vehicle.brake_pedal_depth` · 制动踏板深度

查看制动踏板踩下的百分比。

- **能力 id**：`vehicle.brake_pedal_depth`　·　**目录文件**：`speed.json`
- **常见说法**：`制动踏板深度`
- **read** → `SensorValue`：`read()` — 读取制动踏板深度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前制动踏板深度，单位为 %。单位 `percent`。
- **参数**：无（调用时不需要传参）。

### `abilities.vehicle.longitudinal_acceleration` · 纵向加速度

查看车辆前后方向的加速度，单位为米每二次方秒。

- **能力 id**：`vehicle.longitudinal_acceleration`　·　**目录文件**：`speed.json`
- **常见说法**：`纵向加速度`
- **read** → `SensorValue`：`read()` — 读取纵向加速度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前纵向加速度，单位为 m/s²。单位 `metersPerSecondSquared`。
- **参数**：无（调用时不需要传参）。

### `abilities.vehicle.speed` · 当前车速

查看车辆当前速度读数。

- **能力 id**：`vehicle.speed`　·　**目录文件**：`speed.json`
- **常见说法**：`当前车速`
- **read** → `SensorValue`：`read()` — 读取当前车速。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前车速读数，单位尚未确认。
- **参数**：无（调用时不需要传参）。

### `abilities.wiper.automatic` · 自动雨刮

开启或关闭自动感应雨刮。

- **能力 id**：`wiper.automatic`　·　**目录文件**：`wiper.json`
- **常见说法**：`自动雨刮`
- **read** → `SensorValue`：`read()` — 读取自动雨刮。
- **write** → `void`：`write(boolean enabled)` — 设置自动雨刮是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 自动雨刮状态。
- **返回值含义**：`1`→ON（已开启）、`2`→OFF（已关闭）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启自动雨刮。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：用于怀挡且原车界面支持自动雨刮开关的车型。

### `abilities.wiper.front_wiper_level` · 前雨刮档位

查看前雨刮当前工作档位。

- **能力 id**：`wiper.front_wiper_level`　·　**目录文件**：`wiper.json`
- **常见说法**：`前雨刮档位`
- **read** → `SensorValue`：`read()` — 读取前雨刮档位。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前前雨刮档位。
- **参数**：无（调用时不需要传参）。

### `abilities.wiper.relay_state` · 雨刮继电器状态

查看雨刮继电器是否吸合。

- **能力 id**：`wiper.relay_state`　·　**目录文件**：`wiper.json`
- **常见说法**：`雨刮继电器状态`
- **read** → `SensorValue`：`read()` — 读取雨刮继电器状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前雨刮继电器状态。
- **返回值含义**：`1`→ACTUATION（吸合）、`2`→DISCONNECT（断开）
- **参数**：无（调用时不需要传参）。

### `abilities.wiper.reset_state` · 雨刮复位状态

查看前雨刮或后雨刮是否已经回到复位位置。

- **能力 id**：`wiper.reset_state`　·　**目录文件**：`wiper.json`
- **常见说法**：`雨刮复位状态`、`前雨刮是否复位`、`后雨刮是否复位`
- **read** → `SensorValue`：`read(String area)` — 读取雨刮复位状态。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前雨刮复位状态。
- **返回值含义**：`1`→NOT_RESET（未复位）、`2`→RESET（已复位）
- **参数**：
  - `area`（String，selector，read）：前雨刮或后雨刮。
    - 可选值 `frontWindshield` — 前风挡；`rearWindshield` — 后风挡

### `abilities.wiper.sensitivity` · 雨刮灵敏度

查看雨刮当前灵敏度档位。

- **能力 id**：`wiper.sensitivity`　·　**目录文件**：`wiper.json`
- **常见说法**：`雨刮灵敏度`
- **read** → `SensorValue`：`read()` — 读取雨刮灵敏度。
- **write**：不提供
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 当前雨刮灵敏度。
- **参数**：无（调用时不需要传参）。

### `abilities.wiper.service_mode` · 雨刮检修模式

开启或退出雨刮检修模式，便于更换雨刮片。

- **能力 id**：`wiper.service_mode`　·　**目录文件**：`wiper.json`
- **常见说法**：`雨刮检修模式`
- **read** → `SensorValue`：`read()` — 读取雨刮检修模式。
- **write** → `void`：`write(boolean enabled)` — 设置雨刮检修模式是否开启。
- **返回**：`SensorValue`（`value` 解码值、`text` 枚举标签、`unit` 单位、`featureRaws`/`featureNames` 全部读字段原始值）。目录返回字段：`value` 雨刮检修模式状态。
- **返回值含义**：`1`→ON（已开启）、`2`→OFF（已关闭）
- **参数**：
  - `enabled`（boolean，value，write）：是否开启雨刮检修模式。
    - 可选值 `true`（是）/ `false`（否），具体含义见参数说明
- **备注**：使用前需关闭自动雨刮；本操作不会自动执行关闭步骤。

---

3 段 id（如 `bodywork.window.position`、`bodywork.door.open`）展开为两级嵌套类，不再压缩成 `window_position` / `door_open`。

