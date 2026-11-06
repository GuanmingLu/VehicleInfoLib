package me.guanming.vehicle.infolib;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * 传感器读取结果。包含原始值、解码后的数值，以及可定制的格式化方法。
 *
 * <p>典型用法：
 * <pre>
 *   SensorValue v = Sensor.speed();
 *   // v.raw   = 1115684864  (int 位模式)
 *   // v.value = 60.0        (Float.intBitsToFloat 解码)
 *   v.format();          // "60 km/h"
 *   v.format(1, null);   // "60 km/h"
 *   v.format(0, null);   // "60 km/h"
 *   v.format(-1, "km/h");// "60km/h"  (自定义单位不加空格)
 *   v.format(-1, "");    // "60"      (无单位)
 *
 *   SensorValue g = Sensor.gear();
 *   // g.raw   = 4
 *   // g.value = 4.0
 *   g.format();          // "D"
 * </pre>
 *
 * <p>当读取失败或值为 sentinel 时，{@link #raw} 为 {@code null}，
 * {@link #value} 为 {@code null}，{@link #format()} 返回 "读取失败" 或 "无效 (raw=...)"。
 */
public final class SensorValue {

    /** 原始 int 值（transact=7 时为 IEEE 754 位模式），null 表示读取失败 */
    public final Integer raw;

    /** 解码后的数值，null 表示无效（sentinel/越界/NaN） */
    public final Double value;

    /** 是否为枚举类型（INT_ENUM） */
    private final boolean isEnum;

    /** 枚举映射（仅 isEnum=true 时有效） */
    private final Map<Integer, String> enumMap;

    /** 默认单位（仅数值类型有效，可为空串） */
    private final String defaultUnit;

    SensorValue(Integer raw, Double value, boolean isEnum,
                Map<Integer, String> enumMap, String defaultUnit) {
        this.raw = raw;
        this.value = value;
        this.isEnum = isEnum;
        this.enumMap = enumMap == null ? Collections.emptyMap() : enumMap;
        this.defaultUnit = defaultUnit == null ? "" : defaultUnit;
    }

    /** 读取失败的快捷构造 */
    static SensorValue failed() {
        return new SensorValue(null, null, false, null, null);
    }

    /**
     * 供新的 abilities 命名空间构造解码结果。
     *
     * <p>普通调用方一般不需要使用；它是包外无法访问构造器时的唯一公开入口。
     *
     * @param raw        原始 int 值，null 表示读取失败
     * @param value      解码后的数值，null 表示无效
     * @param isEnum     是否为枚举/布尔类型
     * @param enumMap    枚举映射，可为 null
     * @param defaultUnit 默认单位，可为 null
     * @return SensorValue
     */
    public static SensorValue of(Integer raw, Double value, boolean isEnum,
                                 Map<Integer, String> enumMap, String defaultUnit) {
        return new SensorValue(raw, value, isEnum, enumMap, defaultUnit);
    }

    /**
     * 格式化为显示字符串（默认参数：小数位不限，使用默认单位并自动添加空格）。
     *
     * @return 显示字符串
     */
    public String format() {
        return format(-1, null);
    }

    /**
     * 格式化为显示字符串（指定小数位，使用默认单位并自动添加空格）。
     *
     * @param maxDecimals 最多保留几位小数，-1 表示不限
     * @return 显示字符串
     */
    public String format(int maxDecimals) {
        return format(maxDecimals, null);
    }

    /**
     * 格式化为显示字符串。
     *
     * <p>对枚举类型（INT_ENUM），直接返回枚举标签，忽略 {@code maxDecimals} 和 {@code customUnit}。
     *
     * @param maxDecimals 最多保留几位小数，-1 表示不限
     * @param customUnit  自定义单位：
     *                    <ul>
     *                    <li>{@code null}：使用默认单位，数值与单位间自动添加空格</li>
     *                    <li>空串 {@code ""}：无单位</li>
     *                    <li>其他字符串：使用该单位，不自动添加空格</li>
     *                    </ul>
     * @return 显示字符串
     */
    public String format(int maxDecimals, String customUnit) {
        if (raw == null) return "读取失败";
        if (value == null) return "无效 (raw=" + raw + ")";

        if (isEnum) {
            String label = enumMap.get(raw);
            if (label != null) return label;
            if (!enumMap.isEmpty()) return "未知 (raw=" + raw + ")";
            return "raw=" + raw;
        }

        String numStr = formatDouble(value, maxDecimals);

        if (customUnit == null) {
            // 使用默认单位，自动添加空格
            if (!defaultUnit.isEmpty()) {
                return numStr + " " + defaultUnit;
            }
            return numStr;
        } else if (customUnit.isEmpty()) {
            // 无单位
            return numStr;
        } else {
            // 自定义单位，不添加空格
            return numStr + customUnit;
        }
    }

    /** 格式化数值：maxDecimals<0 不限（四舍五入到 3 位去浮点噪声），=0 取整，>0 四舍五入后去尾 0 */
    private static String formatDouble(double d, int maxDecimals) {
        if (maxDecimals < 0) {
            // 用四舍五入而非 floor：负数 floor 会向远离 0 的方向多进一位（-1.200000047 -> -1.201）
            double rounded = Math.round(d * 1000) / 1000.0;
            return trimTrailingZeros(rounded);
        }
        if (maxDecimals == 0) {
            return Long.toString((long) Math.round(d));
        }
        String s = String.format(Locale.US, "%." + maxDecimals + "f", d);
        return trimTrailingZeros(s);
    }

    private static String trimTrailingZeros(double d) {
        if (d == Math.floor(d)) {
            return Long.toString((long) d);
        }
        return Double.toString(d);
    }

    private static String trimTrailingZeros(String s) {
        if (s.contains(".")) {
            s = s.replaceAll("0+$", "").replaceAll("\\.$", "");
        }
        return s;
    }

    @Override
    public String toString() {
        return "SensorValue{raw=" + raw + ", value=" + value + ", format='" + format() + "'}";
    }
}
