package me.guanming.vehicle.infolib;

/**
 * 车辆服务 Binder 协议常量。
 *
 * <p>主 App 与 daemon 之间通过 binder 通信：
 * <ul>
 *   <li>服务名：{@code vehicleinfo_helper}
 *   <li>descriptor：{@code me.guanming.vehicle.infolib.IVehicle}
 *   <li>事务码：TX_PING=1, TX_READ=2, TX_WRITE=3, TX_READ_BATCH=4
 * </ul>
 *
 * <p>daemon 通过 BYD autoservice 的 transact(5/7/9) 读取、transact(6) 写入。
 */
final class VehicleProtocol {

    private VehicleProtocol() {}

    /** Binder 描述符 */
    static final String DESCRIPTOR = "me.guanming.vehicle.infolib.IVehicle";

    /** ServiceManager 中的服务名 */
    static final String SERVICE_NAME = "vehicleinfo_helper";

    /** 后台 daemon 进程名 */
    static final String PROCESS_NAME = "vehicleinfo_helper";

    /** Binder 事务码 */
    static final int TX_PING = 1;
    static final int TX_READ = 2;
    static final int TX_WRITE = 3;
    static final int TX_READ_BATCH = 4;

    /** BYD autoservice 的事务码 */
    static final int BYD_TX_READ_INT = 5;
    static final int BYD_TX_READ_FLOAT = 7;
    static final int BYD_TX_READ_STRING = 9;
    static final int BYD_TX_WRITE_INT = 6;

    /** 批量读取上限 */
    static final int MAX_READ_BATCH_SIZE = 128;

    /** daemon 的入口类全限定名 */
    static final String DAEMON_CLASS = "me.guanming.vehicle.infolib.VehicleDaemon";

    /** daemon 日志/状态文件路径 */
    static final String LOG_PATH = "/data/local/tmp/vehicleinfo_helper.log";
    static final String ALIVE_PATH = "/data/local/tmp/vehicleinfo_helper.alive";
}
