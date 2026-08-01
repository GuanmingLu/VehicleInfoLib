package me.guanming.vehicle.infolib;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 车辆服务守护进程。
 *
 * <p>本类是 vehicle-infolib 的 daemon 端实现，通过 {@code app_process} 在车机上以 shell 权限启动后：
 * <ol>
 *   <li>反射获取 BYD 系统 binder 服务 "autoservice"
 *   <li>调用 iBinder.getInterfaceDescriptor() 动态获取 BYD 服务的 descriptor
 *   <li>注册自己的 binder 服务 {@link VehicleProtocol#SERVICE_NAME} 到 ServiceManager
 *   <li>在 onTransact 中转发 {@link Vehicle#read} / {@link Vehicle#write} 请求到 BYD autoservice
 * </ol>
 *
 * <p>本类不参与 App 进程内的调用链，仅作为独立进程的入口：
 * <pre>
 *   CLASSPATH=/path/to/app.apk setsid app_process /system/bin \
 *     --nice-name=vehicleinfo_helper \
 *     me.guanming.vehicle.infolib.VehicleDaemon $APP_UID \
 *     &lt;/dev/null &gt;&gt;/data/local/tmp/vehicleinfo_helper.log 2&gt;&amp;1 &amp;
 * </pre>
 *
 * <p>由 {@link Vehicle#init} 通过 ADB shell 拉起，外部应用无需直接调用本类。
 *
 * <p>注意：android.os.ServiceManager 是隐藏 API，编译期不可见，故全程使用反射。
 */
public class VehicleDaemon {

    /**
     * 自定义 Binder 实现。持有 BYD autoservice 的 IBinder 引用，
     * 转发客户端的 TX_READ / TX_WRITE 请求。
     */
    private static class VehicleBinder extends Binder {
        private final IBinder bydBinder;
        private final String bydDescriptor;

        VehicleBinder(IBinder bydBinder, String bydDescriptor) {
            this.bydBinder = bydBinder;
            this.bydDescriptor = bydDescriptor;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            if (reply == null) return false;
            try {
                switch (code) {
                    case VehicleProtocol.TX_PING: {
                        data.enforceInterface(VehicleProtocol.DESCRIPTOR);
                        // 不调用 writeNoException()，直接 writeInt(0)
                        reply.writeInt(0);
                        return true;
                    }
                    case VehicleProtocol.TX_READ: {
                        data.enforceInterface(VehicleProtocol.DESCRIPTOR);
                        int device = data.readInt();
                        int fid = data.readInt();
                        int transact = data.readInt();
                        int[] rv = readByd(device, fid, transact);
                        log("INFO: READ dev=" + device + " fid=" + fid + " tx=" + transact
                                + " -> status=" + rv[0] + " value=" + rv[1]);
                        // 注意：不调用 writeNoException()！协议直接 writeInt(status) + writeInt(value)
                        // 客户端也不调用 readException()
                        reply.writeInt(rv[0]);
                        reply.writeInt(rv[1]);
                        return true;
                    }
                    case VehicleProtocol.TX_WRITE: {
                        data.enforceInterface(VehicleProtocol.DESCRIPTOR);
                        int device = data.readInt();
                        int fid = data.readInt();
                        int value = data.readInt();
                        int status = writeByd(device, fid, value);
                        log("INFO: WRITE dev=" + device + " fid=" + fid + " value=" + value
                                + " -> status=" + status);
                        // 不调用 writeNoException()，直接 writeInt(status)
                        // 客户端也不调用 readException()
                        reply.writeInt(status);
                        return true;
                    }
                    case VehicleProtocol.TX_READ_BATCH: {
                        data.enforceInterface(VehicleProtocol.DESCRIPTOR);
                        int count = data.readInt();
                        int n = Math.min(count, VehicleProtocol.MAX_READ_BATCH_SIZE);
                        for (int i = 0; i < n; i++) {
                            int device = data.readInt();
                            int fid = data.readInt();
                            int transact = data.readInt();
                            int[] rv = readByd(device, fid, transact);
                            reply.writeInt(rv[0]);
                            reply.writeInt(rv[1]);
                        }
                        return true;
                    }
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } catch (Exception e) {
                log("ERR: onTransact code=" + code + " failed: " + e.getClass().getName()
                        + ": " + e.getMessage());
                reply.writeNoException();
                reply.writeInt(-1);
                reply.writeInt(0);
                return true;
            }
        }

        /**
         * 转发读请求到 BYD autoservice。
         *
         * <p>请求 Parcel：
         * <pre>
         *   writeInterfaceToken(bydDescriptor)
         *   writeInt(device)         // 设备 id
         *   writeInt(fid)            // 字段 id
         *   transact(transact, data, reply, 0)
         * </pre>
         *
         * <p>transact code 含义（autoservice 协议）：
         * <ul>
         *   <li>{@link VehicleProtocol#BYD_TX_READ_INT} = 5：返回 int 值
         *   <li>{@link VehicleProtocol#BYD_TX_READ_FLOAT} = 7：返回 int 位模式，需 Float.intBitsToFloat() 转换
         *   <li>{@link VehicleProtocol#BYD_TX_READ_STRING} = 9：返回字符串（如 VIN）
         * </ul>
         *
         * @return 长度 2 的 int 数组：[status, value]
         */
        private int[] readByd(int device, int fid, int transact) {
            Parcel req = Parcel.obtain();
            Parcel rep = Parcel.obtain();
            try {
                req.writeInterfaceToken(bydDescriptor);
                req.writeInt(device);
                req.writeInt(fid);
                bydBinder.transact(transact, req, rep, 0);
                int dataAvail = rep.dataAvail();
                int status = dataAvail >= 4 ? rep.readInt() : -999;
                int value = dataAvail >= 8 ? rep.readInt() : 0;
                log("INFO:   BYD transact(tx=" + transact + ", dev=" + device + ", fid=" + fid
                        + ") -> dataAvail=" + dataAvail + " status=" + status + " value=" + value);
                return new int[]{status, value};
            } catch (Exception e) {
                log("WARN: readByd dev=" + device + " fid=" + fid + " tx=" + transact
                        + " failed: " + e.getClass().getName() + ": " + e.getMessage());
                e.printStackTrace(System.err);
                System.err.flush();
                return new int[]{-1, 0};
            } finally {
                req.recycle();
                rep.recycle();
            }
        }

        /**
         * 转发写请求到 BYD autoservice。
         *
         * <p>请求 Parcel：
         * <pre>
         *   writeInterfaceToken(bydDescriptor)
         *   writeInt(device)
         *   writeInt(fid)
         *   writeInt(value)          ← 写入的值
         *   transact(6, data, reply, 0)   ← BYD autoservice 的 WRITE_INT code
         * </pre>
         *
         * <p>注意：本应用不做任何值校验，不进行回读，直接透传 value 到 BYD autoservice。
         *
         * @return status（>=0 为成功，<0 为失败）
         */
        private int writeByd(int device, int fid, int value) {
            Parcel req = Parcel.obtain();
            Parcel rep = Parcel.obtain();
            try {
                req.writeInterfaceToken(bydDescriptor);
                req.writeInt(device);
                req.writeInt(fid);
                req.writeInt(value);
                bydBinder.transact(VehicleProtocol.BYD_TX_WRITE_INT, req, rep, 0);
                int dataAvail = rep.dataAvail();
                int status = dataAvail >= 4 ? rep.readInt() : -999;
                log("INFO:   BYD transact(tx=" + VehicleProtocol.BYD_TX_WRITE_INT
                        + ", dev=" + device + ", fid=" + fid + ", value=" + value
                        + ") -> dataAvail=" + dataAvail + " status=" + status);
                return status;
            } catch (Exception e) {
                log("WARN: writeByd dev=" + device + " fid=" + fid + " value=" + value
                        + " failed: " + e.getClass().getName() + ": " + e.getMessage());
                e.printStackTrace(System.err);
                System.err.flush();
                return -1;
            } finally {
                req.recycle();
                rep.recycle();
            }
        }
    }

    /**
     * Daemon 入口。
     *
     * <p>命令行启动方式：
     * <pre>
     *   CLASSPATH=/path/to/app.apk setsid app_process /system/bin \
     *     --nice-name=vehicleinfo_helper \
     *     me.guanming.vehicle.infolib.VehicleDaemon $UID \
     *     &lt;/dev/null &gt;&gt;/data/local/tmp/vehicleinfo_helper.log 2&gt;&amp;1 &amp;
     * </pre>
     */
    public static void main(String[] args) {
        // 全程使用 System.err.println 输出日志（重定向到 /data/local/tmp/vehicleinfo_helper.log）
        // 不使用 android.util.Log，因为 app_process 启动的进程无 Application Context，
        // 在某些 ROM 上 Log.i/e 可能会因 __android_log_write 失败而抛异常
        try {
            mainInternal(args);
        } catch (Throwable t) {
            System.err.println("FATAL: daemon crashed: " + t.getClass().getName() + ": " + t.getMessage());
            t.printStackTrace(System.err);
            System.err.flush();
            System.exit(99);
        }
    }

    private static void mainInternal(String[] args) {
        int uid = (args != null && args.length > 0) ? parseIntOr(args[0], 1000) : 1000;
        log("INFO: VehicleDaemon starting, uid=" + uid + ", args=" + Arrays.toString(args));
        log("INFO: pid=" + android.os.Process.myPid() + ", tid=" + android.os.Process.myTid());
        log("INFO: classpath=" + System.getProperty("java.class.path"));

        // 关键：daemon 由 app_process 启动，运行在 shell uid 下，没有经过 zygote
        // 必须先调用 HiddenApiBypass 解除隐藏 API 限制，否则反射调用 ServiceManager.getService
        // 会抛 NoSuchMethodException 或 IllegalAccessException
        log("INFO: applying HiddenApiBypass...");
        try {
            org.lsposed.hiddenapibypass.HiddenApiBypass.addHiddenApiExemptions("L");
            log("INFO: HiddenApiBypass applied successfully");
        } catch (Throwable e) {
            log("ERR: HiddenApiBypass failed: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
            System.err.flush();
            System.exit(4);
            return;
        }

        // 注意：不调用 closeInheritedFds()！
        // 会关掉 binder 驱动的 FD（通常是 fd 3），导致 ServiceManager.getService()
        // 抛 "Bad file descriptor" 异常。ADB shell 流已被 shell 重定向到 /dev/null 和 log 文件，
        // daemon 只继承 0/1/2 三个标准 FD，不会卡住客户端的读取。

        // ===== 步骤 1：向 BYD 白名单服务注册 uid =====
        // daemon 是 shell uid 2000，传入的 uid 是 App uid
        // 同时尝试注册 App uid 和 daemon 自己的 uid，覆盖两种可能
        int myUid = android.os.Process.myUid();
        log("INFO: myUid (daemon process)=" + myUid + ", targetUid (app uid)=" + uid);
        Set<Integer> uidsToRegister = new LinkedHashSet<>(Arrays.asList(uid, myUid, 1000, 2000));
        for (int u : uidsToRegister) {
            log("INFO: registering uid=" + u + " to byd_datacached whitelist (old desc)...");
            boolean r1a = registerBydWhitelist(u, "byd_datacached", "android.os.IBYDDataCachedManager");
            log("INFO: byd_datacached (old desc, uid=" + u + "): " + r1a);
            if (!r1a) {
                log("INFO: retrying byd_datacached with new descriptor (uid=" + u + ")...");
                boolean r1b = registerBydWhitelist(u, "byd_datacached", "android.os.appstartup.IAppStartupDataCachedManager");
                log("INFO: byd_datacached (new desc, uid=" + u + "): " + r1b);
            }
            log("INFO: registering uid=" + u + " to bg_datacache whitelist...");
            boolean r2 = registerBydWhitelist(u, "bg_datacache", "android.os.IAppOpsDataCachedManager");
            log("INFO: bg_datacache whitelist (uid=" + u + "): " + r2);
        }

        // ===== 步骤 2：获取 BYD autoservice binder =====
        // 按顺序尝试所有候选名，第一个非 null 即使用
        List<String> candidates = Arrays.asList("autoservice", "car_amplifier", "byd_car_amplifier", "amplifier");
        IBinder bydBinder = null;
        String bydServiceName = "";
        for (String name : candidates) {
            log("INFO: trying ServiceManager.getService(\"" + name + "\")...");
            IBinder b = reflectGetService(name);
            if (b != null) {
                bydBinder = b;
                bydServiceName = name;
                log("INFO: found BYD service '" + name + "': " + b);
                break;
            }
            log("INFO: service '" + name + "' not found");
        }

        if (bydBinder == null) {
            log("ERR: no BYD service found in candidates: " + candidates);
            log("INFO: dumping all ServiceManager services (try service list shell cmd)...");
            dumpAllServices();
            log("INFO: exiting with code 2");
            System.err.flush();
            System.exit(2);
            return;
        }

        String bydDescriptor;
        try {
            bydDescriptor = bydBinder.getInterfaceDescriptor();
            if (bydDescriptor == null) bydDescriptor = "";
        } catch (Exception e) {
            log("WARN: getInterfaceDescriptor failed: " + e.getMessage());
            bydDescriptor = "";
        }
        log("INFO: BYD service '" + bydServiceName + "' descriptor: '" + bydDescriptor + "'");

        VehicleBinder binder = new VehicleBinder(bydBinder, bydDescriptor);
        // attachInterface 让 binder 知道自己的 descriptor
        binder.attachInterface(null, VehicleProtocol.DESCRIPTOR);
        log("INFO: VehicleBinder created, descriptor='" + VehicleProtocol.DESCRIPTOR + "'");

        // 注册 binder 服务（反射调用 ServiceManager.addService）
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            Method addService = sm.getMethod("addService", String.class, IBinder.class);
            addService.invoke(null, VehicleProtocol.SERVICE_NAME, binder);
            log("INFO: Registered service '" + VehicleProtocol.SERVICE_NAME + "'");
        } catch (Exception e) {
            log("ERR: addService failed: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
            System.err.flush();
            System.exit(3);
            return;
        }

        // 写入 alive 文件，供主 App 检测
        try {
            File aliveFile = new File(VehicleProtocol.ALIVE_PATH);
            try (java.io.FileWriter w = new java.io.FileWriter(aliveFile)) {
                w.write("alive\npid=" + android.os.Process.myPid()
                        + "\nservice=" + bydServiceName
                        + "\ndesc=" + bydDescriptor + "\n");
            }
            log("INFO: alive file written");
        } catch (Exception e) {
            log("WARN: write alive file failed: " + e.getMessage());
        }

        // 阻塞主线程以保持 daemon 运行
        log("INFO: VehicleDaemon ready, entering wait loop");
        System.err.flush();
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            log("INFO: Interrupted, exiting");
        }
    }

    private static int parseIntOr(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    /** 统一日志输出，每次输出都立即 flush 到文件 */
    private static void log(String msg) {
        System.err.println(msg);
        System.err.flush();
    }

    /** dump 所有已注册的 ServiceManager 服务（仅用于诊断） */
    private static void dumpAllServices() {
        // 方法 1：尝试 ServiceManager.listServices() 反射调用（API 27+）
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            try {
                Method m = sm.getMethod("listServices");
                Object result = m.invoke(null);
                if (result instanceof List) {
                    List<?> list = (List<?>) result;
                    log("INFO: Found " + list.size() + " registered services (via listServices):");
                    for (int i = 0; i < list.size(); i++) {
                        log("  [" + i + "] " + list.get(i));
                    }
                    return;
                }
            } catch (NoSuchMethodException ignored) {
            }
        } catch (Exception e) {
            log("INFO: listServices reflection failed: " + e.getMessage());
        }

        // 方法 2：通过 Runtime.exec("service list") 执行命令
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"sh", "-c", "service list 2>&1"});
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            int count = 0;
            log("INFO: services via 'service list':");
            while ((line = input.readLine()) != null) {
                log("  " + line);
                count++;
                if (count >= 300) {
                    log("  ... (truncated)");
                    break;
                }
            }
            proc.waitFor();
            log("INFO: total " + count + " lines from service list");
        } catch (Exception e) {
            log("INFO: service list exec failed: " + e.getMessage());
        }
    }

    /**
     * 向 BYD 白名单服务注册 uid。
     *
     * <p>协议：
     * <pre>
     *   Parcel: writeInterfaceToken(descriptor) + writeString(uid) + writeInt(0)
     *   transact(code=1, data, reply, 0)
     *   reply.readException()
     * </pre>
     *
     * @return true 表示注册成功
     */
    private static boolean registerBydWhitelist(int uid, String serviceName, String descriptor) {
        try {
            IBinder iBinder = reflectGetService(serviceName);
            if (iBinder == null) {
                log("WARN: service '" + serviceName + "' not found for whitelist");
                return false;
            }
            log("INFO: whitelist service '" + serviceName + "' acquired, registering uid=" + uid + "...");
            Parcel req = Parcel.obtain();
            Parcel rep = Parcel.obtain();
            try {
                req.writeInterfaceToken(descriptor);
                req.writeString(Integer.toString(uid));
                req.writeInt(0);
                iBinder.transact(1, req, rep, 0);
                rep.readException();
                log("INFO: whitelist '" + serviceName + "' transact(1) success");
                return true;
            } finally {
                req.recycle();
                rep.recycle();
            }
        } catch (Exception e) {
            log("WARN: registerBydWhitelist('" + serviceName + "') failed: "
                    + e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    private static IBinder reflectGetService(String name) {
        // ServiceManager.getService 是隐藏 API
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method m = cls.getMethod("getService", String.class);
            m.setAccessible(true);
            Object result = m.invoke(null, name);
            if (result instanceof IBinder) {
                return (IBinder) result;
            }
            if (result == null) {
                log("WARN: reflectGetService('" + name + "') returned null");
                return null;
            }
            log("WARN: reflectGetService('" + name + "') returned non-IBinder: " + result.getClass().getName());
            return null;
        } catch (java.lang.reflect.InvocationTargetException e) {
            // 解包目标方法抛出的真实异常
            Throwable cause = e.getCause();
            log("WARN: reflectGetService('" + name + "') target threw: "
                    + (cause != null ? cause.getClass().getName() : "?")
                    + ": " + (cause != null ? cause.getMessage() : "?"));
            if (cause != null) cause.printStackTrace(System.err);
            System.err.flush();
            return null;
        } catch (Exception e) {
            log("WARN: reflectGetService('" + name + "') failed: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
            System.err.flush();
            return null;
        }
    }
}
