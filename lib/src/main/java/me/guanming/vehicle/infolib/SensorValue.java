package me.guanming.vehicle.infolib;

/**
 * 传感器读取结果。包含原始值、解码后的数值、显示字符串。
 *
 * <p>典型用法：
 * <pre>
 *   SensorValue v = Sensor.speed();
 *   // v.raw   = 1115684864  (int 位模式)
 *   // v.value = 60.0        (Float.intBitsToFloat 解码)
 *   // v.str   = "60 km/h"
 *
 *   SensorValue g = Sensor.gear();
 *   // g.raw   = 4
 *   // g.value = 4.0
 *   // g.str   = "D"
 * </pre>
 *
 * <p>当读取失败或值为 sentinel 时，{@link #raw} 为 {@code null}，
 * {@link #value} 为 {@code null}，{@link #str} 为 "读取失败" 或 "无效"。
 */
public final class SensorValue {

    /** 原始 int 值（transact=7 时为 IEEE 754 位模式），null 表示读取失败 */
    public final Integer raw;

    /** 解码后的数值，null 表示无效（sentinel/越界/NaN） */
    public final Double value;

    /** 显示字符串（含单位或枚举名） */
    public final String str;

    public SensorValue(Integer raw, Double value, String str) {
        this.raw = raw;
        this.value = value;
        this.str = str;
    }

    /** 读取失败的快捷构造 */
    static SensorValue failed() {
        return new SensorValue(null, null, "读取失败");
    }

    @Override
    public String toString() {
        return "SensorValue{raw=" + raw + ", value=" + value + ", str='" + str + "'}";
    }
}
