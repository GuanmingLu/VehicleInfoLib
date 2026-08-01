package me.guanming.vehicle.infolib;

/**
 * 车辆服务日志接口。
 *
 * <p>由外部实现并传入 {@link Vehicle#init}，用于接收内部日志。
 * 内部所有日志（ADB 连接、daemon 启动、读写结果等）都会通过此接口转发。
 */
public interface VehicleLogger {

    /**
     * 记录一条日志。
     *
     * @param level 日志级别："V" (verbose) / "D" (debug) / "I" (info) / "W" (warn) / "E" (error)
     * @param tag    日志标签
     * @param msg    日志内容
     * @param t      异常（可为 null）
     */
    void log(String level, String tag, String msg, Throwable t);
}
