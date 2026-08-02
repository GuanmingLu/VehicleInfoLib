package me.guanming.vehicle.infolib;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Base64;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;

/**
 * 车辆服务入口。对外仅暴露三个静态方法：{@link #init}、{@link #read}、{@link #write}。
 *
 * <p>内部封装：
 * <ul>
 *   <li>ADB 协议客户端（连接车机本机 127.0.0.1:5555，RSA 认证）
 *   <li>app_process 守护进程拉起（VehicleDaemon，持 shell 权限）
 *   <li>Binder IPC（ServiceManager.getService("vehicleinfo_helper")）
 *   <li>BYD autoservice 协议（transact 5/7/9 读取，transact 6 写入）
 * </ul>
 *
 * <h3>使用示例</h3>
 * <pre>
 *   // 1. 初始化（仅调用一次，重复调用直接返回）
 *   Vehicle.InitResult r = Vehicle.init(context, logger);
 *   if (!r.success) { ... }
 *
 *   // 2. 读取（返回 Double，null 表示无效/sentinel）
 *   Double speed = Vehicle.read(context, 1013, -1807745016, 7);
 *
 *   // 3. 写入（不校验，不回读）
 *   boolean ok = Vehicle.write(context, 1001, 1125122104, 1);
 * </pre>
 *
 * <p>线程安全：所有方法 synchronized 内部状态，可在多线程调用。
 */
public final class Vehicle {

    private static final String TAG = "Vehicle";

    private Vehicle() {}

    /** init 返回值 */
    public static final class InitResult {
        /** 是否启动成功 */
        public final boolean success;
        /** 诊断信息（成功时为摘要，失败时为详细日志） */
        public final String message;

        public InitResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    /** 内部状态：已初始化的 context、logger、adb client、binder */
    private static Context appContext;
    private static VehicleLogger logger;
    private static VehicleAdbClient adb;
    private static volatile IBinder cachedBinder;
    private static boolean initialized = false;
    private static final Object INIT_LOCK = new Object();

    /** 显式指定的 dex 路径（动态加载场景由调用方传入，优先于自动判断） */
    private static String explicitDexPath;

    /**
     * 启动车辆服务。
     *
     * <p>步骤：
     * <ol>
     *   <li>解除隐藏 API 限制（HiddenApiBypass）
     *   <li>建立 ADB 连接（127.0.0.1:5555，RSA 持久化密钥）
     *   <li>通过 ADB shell 拉起 VehicleDaemon 进程（app_process）
     *   <li>等待 daemon 注册 binder 服务 {@code vehicleinfo_helper}
     * </ol>
     *
     * <p><b>幂等</b>：如果已成功启动，再次调用立即返回成功。
     *
     * @param context 任意 context（内部取 applicationContext）
     * @return 启动结果
     */
    public static InitResult init(Context context) {
        return init(context, null, null);
    }

    /**
     * 启动车辆服务（显式指定 dex 路径）。
     *
     * <p>动态加载场景（如 AutoJS）下，调用方明确知道 dex 文件路径，
     * 直接传入可避免自动判断 CLASSPATH 失败的问题。
     *
     * @param context 任意 context（内部取 applicationContext）
     * @param dexPath daemon 所在的 dex/jar/apk 文件路径
     * @return 启动结果
     */
    public static InitResult init(Context context, String dexPath) {
        return init(context, null, dexPath);
    }

    /**
     * 启动车辆服务（带日志回调）。
     *
     * @param context 任意 context（内部取 applicationContext）
     * @param logger  日志回调（可为 null）
     * @return 启动结果
     */
    public static InitResult init(Context context, VehicleLogger logger) {
        return init(context, logger, null);
    }

    /**
     * 启动车辆服务（带日志回调与显式 dex 路径）。
     *
     * <p>这是所有 init 重载的最终实现。无论是否有 logger，都保存 context。
     * 无论是否传入 dexPath，都保存（null 表示走自动判断）。
     *
     * @param context 任意 context（内部取 applicationContext）
     * @param logger  日志回调（可为 null）
     * @param dexPath daemon 所在的 dex/jar/apk 路径（可为 null，表示自动判断）
     * @return 启动结果
     */
    public static InitResult init(Context context, VehicleLogger logger, String dexPath) {
        synchronized (INIT_LOCK) {
            if (initialized && cachedBinder != null) {
                // 已启动，立即返回
                if (logger != null) {
                    logger.log("I", TAG, "init: already initialized, skip", null);
                }
                return new InitResult(true, "已启动");
            }
            // 无论是否有 logger，都保存 context
            Vehicle.appContext = context.getApplicationContext();
            Vehicle.logger = logger;
            Vehicle.explicitDexPath = dexPath;
            if (dexPath != null) {
                log("I", "init with explicit dexPath: " + dexPath);
            }

            // 解除隐藏 API 限制
            try {
                HiddenApiBypass.addHiddenApiExemptions("L");
                log("I", "HiddenApiBypass applied");
            } catch (Throwable e) {
                log("W", "HiddenApiBypass failed: " + e.getMessage());
            }

            // 检查 daemon 是否已经在运行（无需重新启动）
            if (refreshBinder()) {
                initialized = true;
                return new InitResult(true, "Helper 已在运行（复用）");
            }

            // 启动 daemon
            InitResult result = spawnDaemon();
            if (result.success) {
                initialized = true;
            }
            return result;
        }
    }

    /**
     * 读取车辆字段（返回原始 int 值，不解码）。
     *
     * <p>使用 init 时保存的 context。对 transact=7（FLOAT）的字段也直接返回 int 位模式，
     * 由调用方（如 {@link Sensor}）负责解码。
     *
     * @param dev      BYD autoservice 的 device 编号
     * @param fid      BYD autoservice 的 field id
     * @param transact BYD autoservice 的 transact code（5=INT, 7=FLOAT, 9=STRING）
     * @return 原始 int 值，null 表示读取失败
     */
    public static Integer read(int dev, int fid, int transact) {
        IBinder b = getBinder();
        if (b == null) {
            log("W", "read: binder unavailable (dev=" + dev + " fid=" + fid + ")");
            return null;
        }
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(VehicleProtocol.DESCRIPTOR);
            data.writeInt(dev);
            data.writeInt(fid);
            data.writeInt(transact);
            b.transact(VehicleProtocol.TX_READ, data, reply, 0);
            int status = reply.dataAvail() >= 4 ? reply.readInt() : -999;
            int value = reply.dataAvail() >= 4 ? reply.readInt() : 0;
            if (status < 0) {
                log("W", "read failed: dev=" + dev + " fid=" + fid + " tx=" + transact
                        + " status=" + status);
                return null;
            }
            return value;
        } catch (Exception e) {
            log("E", "read dev=" + dev + " fid=" + fid + " tx=" + transact + " failed: " + e.getMessage(), e);
            return null;
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    /**
     * 读取车辆字段（显式传入 context 的重载）。
     *
     * @param context  任意 context
     * @param dev      BYD autoservice 的 device 编号
     * @param fid      BYD autoservice 的 field id
     * @param transact BYD autoservice 的 transact code（5=INT, 7=FLOAT, 9=STRING）
     * @return 原始 int 值，null 表示读取失败
     */
    public static Integer read(Context context, int dev, int fid, int transact) {
        appContext = context.getApplicationContext();
        return read(dev, fid, transact);
    }

    /**
     * 写入车辆字段（控制动作）。不校验，不回读。使用 init 时保存的 context。
     *
     * @param dev   BYD autoservice 的 device 编号
     * @param fid   BYD autoservice 的 field id
     * @param value 要写入的值（任意 int，不校验范围）
     * @return true 表示写入成功（status >= 0）
     */
    public static boolean write(int dev, int fid, int value) {
        IBinder b = getBinder();
        if (b == null) {
            log("W", "write: binder unavailable (dev=" + dev + " fid=" + fid + ")");
            return false;
        }
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(VehicleProtocol.DESCRIPTOR);
            data.writeInt(dev);
            data.writeInt(fid);
            data.writeInt(value);
            b.transact(VehicleProtocol.TX_WRITE, data, reply, 0);
            int status = reply.dataAvail() >= 4 ? reply.readInt() : -999;
            log("I", "write dev=" + dev + " fid=" + fid + " value=" + value + " -> status=" + status);
            return status >= 0;
        } catch (Exception e) {
            log("E", "write dev=" + dev + " fid=" + fid + " value=" + value + " failed: " + e.getMessage(), e);
            return false;
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    /**
     * 写入车辆字段（显式传入 context 的重载）。
     */
    public static boolean write(Context context, int dev, int fid, int value) {
        appContext = context.getApplicationContext();
        return write(dev, fid, value);
    }

    // ===================== ADB 公共接口 =====================

    /**
     * 获取 ADB 客户端实例（复用 init 时建立的连接）。
     *
     * <p>若 init 已成功执行，返回的实例已连接本机 ADB，可直接执行 shell 命令。
     * 若未调用过 init 或连接已断开，将自动尝试创建并连接。
     *
     * <p>使用示例：
     * <pre>
     *   VehicleAdbClient adb = Vehicle.getAdb();
     *   if (adb != null) {
     *       String model = adb.shellQuiet("getprop ro.product.model", 10000);
     *   }
     * </pre>
     *
     * @return 已连接的 ADB 客户端，连接失败返回 null
     */
    public static VehicleAdbClient getAdb() {
        synchronized (INIT_LOCK) {
            if (adb != null && adb.isConnected()) {
                return adb;
            }
            // 尝试创建并连接
            if (appContext == null) {
                log("E", "getAdb: not initialized, call init() first");
                return null;
            }
            if (adb != null) adb.close();
            VehicleAdbKeyStore keyStore = new VehicleAdbKeyStore(appContext, logger);
            adb = new VehicleAdbClient(keyStore.get(), logger);
            if (!adb.connect()) {
                log("E", "getAdb: connect failed");
                return null;
            }
            return adb;
        }
    }

    /**
     * 便捷方法：通过 ADB 执行 shell 命令（静默模式，出错返回空串）。
     *
     * <p>内部复用 {@link #getAdb()} 的连接，无需单独调用 init。
     *
     * @param cmd       要执行的命令
     * @param timeoutMs 超时（毫秒）
     * @return 命令输出，出错或未连接时返回空串
     */
    public static String shell(String cmd, int timeoutMs) {
        VehicleAdbClient client = getAdb();
        if (client == null) {
            return "";
        }
        return client.shellQuiet(cmd, timeoutMs);
    }

    // ===================== 内部实现 =====================

    /** 获取 binder：优先用缓存，失败则尝试刷新（使用 init 保存的 context） */
    private static IBinder getBinder() {
        if (cachedBinder != null) return cachedBinder;
        // 确保隐藏 API 限制已解除
        try {
            HiddenApiBypass.addHiddenApiExemptions("L");
        } catch (Throwable ignored) {
        }
        refreshBinder();
        return cachedBinder;
    }

    /** 通过反射 ServiceManager.getService 获取 binder */
    private static boolean refreshBinder() {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method m = cls.getMethod("getService", String.class);
            Object result = m.invoke(null, VehicleProtocol.SERVICE_NAME);
            if (result instanceof IBinder) {
                cachedBinder = (IBinder) result;
                log("I", "binder acquired: " + VehicleProtocol.SERVICE_NAME);
                return true;
            }
        } catch (Exception e) {
            log("W", "refreshBinder failed: " + e.getMessage());
        }
        cachedBinder = null;
        return false;
    }

    /** 启动 daemon 进程 */
    private static InitResult spawnDaemon() {
        StringBuilder diag = new StringBuilder();
        int myUid = android.os.Process.myUid();
        diag.append("uid=").append(myUid).append('\n');

        // 建立 ADB 连接
        if (adb != null) adb.close();
        VehicleAdbKeyStore keyStore = new VehicleAdbKeyStore(appContext, logger);
        adb = new VehicleAdbClient(keyStore.get(), logger);

        if (!adb.connect()) {
            return new InitResult(false, "ADB 连接失败（请检查无线 ADB 是否已开启）\n" + diag);
        }
        diag.append("adb=connected\n");

        // 测试 shell 可用性
        String echoResult = adb.shellQuiet("echo ADB_OK", 15000).trim();
        diag.append("echo='").append(echoResult).append("'\n");
        if (!"ADB_OK".equals(echoResult)) {
            log("W", "echo failed once, reconnecting...");
            adb.close();
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            if (!adb.connect()) {
                return new InitResult(false, "ADB 重连失败\n" + diag);
            }
            String echo2 = adb.shellQuiet("echo ADB_OK", 15000).trim();
            diag.append("echo2='").append(echo2).append("'\n");
            if (!"ADB_OK".equals(echo2)) {
                return new InitResult(false, "ADB shell 不可用\n" + diag);
            }
        }

        // 解析 daemon 运行所需的 CLASSPATH
        // 动态加载场景下，VehicleDaemon 不在宿主 APK 内，需定位其实际 dex 路径
        String classpath = resolveDaemonClasspath(diag);
        if (classpath == null) {
            return new InitResult(false, "无法确定 daemon 的 CLASSPATH（动态加载的 dex 不可读？）\n" + diag);
        }

        // 清理残留 daemon
        adb.shellQuiet(
                "pkill -9 -f " + VehicleProtocol.PROCESS_NAME + " 2>/dev/null; " +
                        "rm -f " + VehicleProtocol.LOG_PATH + " " + VehicleProtocol.ALIVE_PATH + " 2>/dev/null; " +
                        "sleep 0.3; echo CLEAN_DONE",
                8000);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        // spawn daemon
        String cmd = "CLASSPATH=\"" + classpath + "\"" +
                " setsid app_process /system/bin --nice-name=" + VehicleProtocol.PROCESS_NAME +
                " " + VehicleProtocol.DAEMON_CLASS + " " + myUid +
                " </dev/null >" + VehicleProtocol.LOG_PATH + " 2>&1 &" +
                " sleep 0.5; echo SPAWNED_$?";
        log("I", "spawn: " + cmd);
        diag.append("cmd=").append(cmd).append('\n');

        String spawnOutput = adb.shellQuiet(cmd, 10000);
        diag.append("spawn_out='").append(spawnOutput).append("'\n");

        // 轮询 binder 服务可用性（最多 15 秒）
        for (int attempt = 1; attempt <= 15; attempt++) {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            if (refreshBinder()) {
                String pid = adb.shellQuiet(
                        "pidof " + VehicleProtocol.PROCESS_NAME + " 2>/dev/null", 8000).trim();
                log("I", "daemon ready, pid=" + pid + " (attempt=" + attempt + ")");
                return new InitResult(true, "Helper 启动成功 (pid=" + pid + ")");
            }
            log("D", "waiting daemon, attempt=" + attempt);
        }

        // 失败：拉取日志
        String logInfo = adb.shellQuiet(
                "if [ -f " + VehicleProtocol.LOG_PATH + " ]; then " +
                        "echo '---LOG_BEGIN---'; cat " + VehicleProtocol.LOG_PATH + "; " +
                        "echo '---LOG_END---'; " +
                        "else echo 'FILE_NOT_EXISTS'; fi",
                15000);
        diag.append("log_check:\n").append(logInfo).append('\n');

        return new InitResult(false, "Helper 启动失败\n" + diag);
    }

    // ===================== CLASSPATH 解析（动态加载支持） =====================

    /**
     * 解析 daemon 运行所需的 CLASSPATH。
     *
     * <p>静态编译场景：VehicleDaemon 类在宿主 APK 内，直接使用 APK 路径。
     * <p>动态加载场景：VehicleDaemon 类在单独的 dex/jar 中（可能位于 app 私有目录），
     *    需通过 ADB 将其推送到 /data/local/tmp/（shell uid 可读），并拼接完整 CLASSPATH。
     *
     * @param diag 诊断信息收集
     * @return CLASSPATH 字符串，或 null 表示失败
     */
    private static String resolveDaemonClasspath(StringBuilder diag) {
        String hostApk = appContext.getPackageCodePath();
        diag.append("host_apk=").append(hostApk).append('\n');
        diag.append("explicit_dex_path=").append(explicitDexPath).append('\n');

        // 场景 0：调用方显式指定了 dex 路径（动态加载场景，如 AutoJS）
        // 直接使用该路径，不再自动判断。由于 lib 编译时已将 hiddenapibypass
        // 合并进同一 dex，故只需这一个路径作为 CLASSPATH。
        if (explicitDexPath != null && !explicitDexPath.isEmpty()) {
            log("I", "using explicit dexPath: " + explicitDexPath);
            diag.append("daemon_src=").append(explicitDexPath).append(" (explicit)\n");
            // 验证本地文件存在
            File f = new File(explicitDexPath);
            if (!f.exists() || !f.canRead()) {
                log("E", "explicit dexPath not readable by app: " + explicitDexPath);
                diag.append("ERROR: explicit dexPath not readable: ").append(explicitDexPath).append('\n');
                return null;
            }
            // 确保 shell uid 可读（必要时推送到 /data/local/tmp/）
            String usable = ensureShellReadable(explicitDexPath, diag);
            if (usable == null) {
                log("E", "cannot make explicit dexPath shell-readable: " + explicitDexPath);
                return null;
            }
            return usable;
        }

        // 自动判断场景：通过反射定位 daemon 类的代码源
        String daemonPath = getCodeSourcePath(VehicleDaemon.class);
        String hiddenApiPath = getCodeSourcePath(HiddenApiBypass.class);

        diag.append("daemon_src=").append(daemonPath).append('\n');
        diag.append("hiddenapi_src=").append(hiddenApiPath).append('\n');

        // 无法确定 daemon 类的代码源（ProtectionDomain 和 ClassLoader 反射均失败）
        if (daemonPath == null) {
            log("E", "cannot determine daemon class code source "
                    + "(ProtectionDomain and ClassLoader reflection both failed), "
                    + "consider passing explicit dexPath to init()");
            diag.append("ERROR: daemon code source not found "
                    + "(pass explicit dexPath to init() for dynamic loading)\n");
            return null;
        }

        // 场景 1：静态编译，daemon 类在宿主 APK 内
        if (daemonPath.equals(hostApk)) {
            log("I", "daemon class is in host APK, using host APK as classpath");
            return hostApk;
        }

        // 场景 2：动态加载，daemon 类在单独的 dex/jar 中
        log("I", "daemon class is dynamically loaded from: " + daemonPath
                + " (host APK: " + hostApk + ")");

        // 收集所有需要的代码源路径（去重，保持顺序）
        LinkedHashSet<String> requiredPaths = new LinkedHashSet<>();
        requiredPaths.add(daemonPath);
        if (hiddenApiPath != null && !hiddenApiPath.isEmpty()) {
            requiredPaths.add(hiddenApiPath);
        }

        // 逐个处理：若 shell uid 不可读，推送到 /data/local/tmp/
        StringBuilder classpath = new StringBuilder();
        for (String path : requiredPaths) {
            String usable = ensureShellReadable(path, diag);
            if (usable == null) {
                log("E", "cannot make path shell-readable: " + path);
                return null;
            }
            if (classpath.length() > 0) classpath.append(':');
            classpath.append(usable);
        }
        return classpath.toString();
    }

    /**
     * 确保给定路径对 shell uid 可读。
     * 若已可读，直接返回原路径；否则通过 ADB 推送到 /data/local/tmp/。
     */
    private static String ensureShellReadable(String localPath, StringBuilder diag) {
        // 先检查 shell uid 是否可直接读取
        String check = adb.shellQuiet(
                "test -r '" + localPath + "' && echo READABLE || echo UNREADABLE", 5000).trim();
        if ("READABLE".equals(check)) {
            log("I", "path is shell-readable: " + localPath);
            return localPath;
        }

        // 不可读，需要推送到 /data/local/tmp/
        log("I", "path not shell-readable, pushing to /data/local/tmp/: " + localPath);
        diag.append("push ").append(localPath).append(" -> /data/local/tmp/\n");

        File localFile = new File(localPath);
        if (!localFile.exists() || !localFile.canRead()) {
            log("E", "local file not readable by app: " + localPath);
            diag.append("local file not readable: ").append(localPath).append('\n');
            return null;
        }

        String remoteName = "vil_" + localFile.getName();
        String remotePath = pushFileToTmp(localFile, remoteName);
        if (remotePath == null) {
            diag.append("push FAILED for ").append(localPath).append('\n');
            return null;
        }
        diag.append("pushed to ").append(remotePath).append('\n');
        return remotePath;
    }

    /**
     * 通过 ADB shell 将本地文件（base64 分块传输）推送到 /data/local/tmp/。
     *
     * <p>用于动态加载场景：dex 文件可能位于 app 私有目录，shell uid 无法读取。
     * 宿主 app 进程读取 dex 内容，base64 编码后通过 ADB shell 分块写入 /data/local/tmp/，
     * 再在远端解码，使 app_process（shell uid）能够加载。
     *
     * @param localFile 本地文件（由 app uid 读取）
     * @param remoteName 远端文件名（不含路径）
     * @return 远端路径，或 null 表示失败
     */
    private static String pushFileToTmp(File localFile, String remoteName) {
        String remotePath = "/data/local/tmp/" + remoteName;
        String b64Path = remotePath + ".b64";
        try {
            byte[] data = readFile(localFile);
            String b64 = Base64.encodeToString(data, Base64.NO_WRAP);
            log("I", "pushFileToTmp: " + localFile.getName()
                    + " size=" + data.length + " b64=" + b64.length());

            // 清理旧文件
            adb.shellQuiet("rm -f " + b64Path + " " + remotePath, 5000);

            // 分块写入 base64（每块 7000 字符，确保远在 ADB payload 上限以内）
            int chunkSize = 7000;
            for (int i = 0; i < b64.length(); i += chunkSize) {
                String chunk = b64.substring(i, Math.min(i + chunkSize, b64.length()));
                adb.shellQuiet("printf '%s' '" + chunk + "' >> " + b64Path, 10000);
            }

            // 解码、设置权限、验证大小
            String result = adb.shellQuiet(
                    "base64 -d " + b64Path + " > " + remotePath + " 2>&1; " +
                    "rm -f " + b64Path + "; " +
                    "chmod 644 " + remotePath + "; " +
                    "wc -c < " + remotePath, 15000);
            int remoteSize = parseIntOr(result.trim(), -1);
            log("I", "pushFileToTmp: remote size=" + remoteSize + " (expected " + data.length + ")");
            if (remoteSize == data.length) {
                return remotePath;
            }
            log("E", "pushFileToTmp: size mismatch local=" + data.length + " remote=" + remoteSize);
            return null;
        } catch (Exception e) {
            log("E", "pushFileToTmp failed: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取类的实际代码源文件路径（dex/jar/apk 的文件系统路径）。
     *
     * <p>按以下顺序尝试定位：
     * <ol>
     *   <li>ProtectionDomain.getCodeSource()（静态编译场景）
     *   <li>遍历整个 ClassLoader 链，反射 BaseDexClassLoader.pathList.dexElements
     *       （动态加载场景，如 AutoJS 的 DexClassLoader / 自定义 ClassLoader）
     * </ol>
     *
     * @param clazz 要查找的类
     * @return dex/jar/apk 的绝对路径，或 null 表示无法确定
     */
    private static String getCodeSourcePath(Class<?> clazz) {
        String hostApk = null;
        try { hostApk = appContext.getPackageCodePath(); } catch (Throwable ignored) {}

        // 方法 1：ProtectionDomain.getCodeSource()（静态编译场景）
        try {
            java.security.CodeSource cs = clazz.getProtectionDomain().getCodeSource();
            if (cs != null && cs.getLocation() != null) {
                String path = cs.getLocation().getPath();
                if (path != null && !path.isEmpty()) {
                    return java.net.URLDecoder.decode(path, "UTF-8");
                }
            }
        } catch (Throwable ignored) {}

        // 方法 2：遍历 ClassLoader 链，反射 BaseDexClassLoader.pathList.dexElements
        // AutoJS 等动态加载宿主可能使用多层自定义 ClassLoader 包装 DexClassLoader，
        // 需沿 parent 链逐层查找 BaseDexClassLoader。
        ClassLoader cl = clazz.getClassLoader();
        int clDepth = 0;
        while (cl != null) {
            String result = extractDexPathFromClassLoader(cl, clazz, hostApk, clDepth);
            if (result != null) {
                return result;
            }
            cl = cl.getParent();
            clDepth++;
        }

        log("E", "getCodeSourcePath failed for " + clazz.getName()
                + ": tried ProtectionDomain + ClassLoader chain (depth=" + clDepth + ")");
        return null;
    }

    /**
     * 从单个 ClassLoader 中提取 dex 路径。
     *
     * <p>查找该 ClassLoader 及其继承链中的 BaseDexClassLoader，
     * 反射 pathList.dexElements，返回非宿主 APK 的 dex/jar/apk 路径。
     */
    private static String extractDexPathFromClassLoader(ClassLoader cl, Class<?> target,
                                                         String hostApk, int depth) {
        Class<?> clClass = cl.getClass();
        // 沿继承链查找 BaseDexClassLoader
        while (clClass != null) {
            if ("dalvik.system.BaseDexClassLoader".equals(clClass.getName())) {
                String result = readDexElementsFromBaseDex(cl, clClass, target, hostApk, depth);
                if (result != null) return result;
                break;
            }
            clClass = clClass.getSuperclass();
        }
        return null;
    }

    /**
     * 反射 BaseDexClassLoader 的 pathList.dexElements，提取 dex 路径。
     *
     * <p>兼容不同 Android 版本：DexElement.path 可能是 File、String 或其他类型；
     * 也尝试 dexPath、file 字段作为兜底。
     */
    private static String readDexElementsFromBaseDex(ClassLoader cl, Class<?> baseDexClass,
                                                     Class<?> target, String hostApk, int depth) {
        try {
            java.lang.reflect.Field pathListField = baseDexClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathList = pathListField.get(cl);
            if (pathList == null) return null;

            java.lang.reflect.Field dexElementsField =
                    pathList.getClass().getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object[] elements = (Object[]) dexElementsField.get(pathList);
            if (elements == null || elements.length == 0) return null;

            String fallbackHostApk = null;
            for (Object element : elements) {
                if (element == null) continue;
                // 尝试多个可能的字段名：path / dexPath / file
                String dexPath = readElementField(element, "path");
                if (dexPath == null) dexPath = readElementField(element, "dexPath");
                if (dexPath == null) dexPath = readElementField(element, "file");
                if (dexPath == null || dexPath.isEmpty()) continue;

                // 跳过目录（资源元素，非 dex）
                File f = new File(dexPath);
                if (f.isDirectory()) continue;

                // 记录宿主 APK 路径作为最后兜底
                if (hostApk != null && dexPath.equals(hostApk)) {
                    if (fallbackHostApk == null) fallbackHostApk = dexPath;
                    continue;
                }
                // 找到非宿主 APK 的 dex/jar/apk，这就是动态加载的代码源
                log("I", "found " + target.getSimpleName() + " dex at clDepth=" + depth
                        + ": " + dexPath);
                return dexPath;
            }
            // 所有元素都是宿主 APK（说明类确实在宿主 APK 内，只是 ProtectionDomain 不可用）
            if (fallbackHostApk != null) {
                log("I", target.getSimpleName() + " only found in host APK (clDepth=" + depth + ")");
                return fallbackHostApk;
            }
        } catch (Throwable e) {
            log("D", "readDexElementsFromBaseDex failed at clDepth=" + depth
                    + " for " + cl.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    /** 反射 DexElement 的字段，兼容 File/String 返回类型 */
    private static String readElementField(Object element, String fieldName) {
        try {
            java.lang.reflect.Field f = element.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object val = f.get(element);
            if (val instanceof File) return ((File) val).getAbsolutePath();
            if (val instanceof String) return (String) val;
        } catch (Throwable ignored) {}
        return null;
    }

    /** 读取文件全部内容到 byte[] */
    private static byte[] readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = fis.read(buf)) > 0) {
                bos.write(buf, 0, n);
            }
            return bos.toByteArray();
        }
    }

    private static int parseIntOr(String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    private static void log(String level, String msg) {
        log(level, msg, null);
    }

    private static void log(String level, String msg, Throwable t) {
        if (logger != null) {
            logger.log(level, TAG, msg, t);
        }
    }
}
