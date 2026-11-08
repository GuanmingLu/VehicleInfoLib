package me.guanming.vehicle.infolib.abilities;

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
        "AC_CTRL_SOURCE_SET|Ac|0",
        "AC_SECOND_ROW_LEFT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL|Ac|0",
        "AC_SECOND_ROW_LEFT_BLOW_TO_PERSON_MODE_SIGNAL|Ac|0",
        "AC_SECOND_ROW_LEFT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL|Ac|0",
        "AC_SECOND_ROW_RIGHT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL|Ac|0",
        "AC_SECOND_ROW_RIGHT_BLOW_TO_PERSON_MODE_SIGNAL|Ac|0",
        "AC_SECOND_ROW_RIGHT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL|Ac|0",
        "AC_TEMPERATURE_UNIT|Ac|0",
        "AC_TEMPERATURE_UNIT_SET|Ac|0",
        "AUDIO_MUTE_STATUS|Audio|0",
        "AUDIO_MUTE_STATUS_SET|Audio|0",
        "AUDIO_NAVI_MUTE_STATUS|Audio|0",
        "AUDIO_NAVI_VOLUME|Audio|0",
        "AUDIO_VOLUME|Audio|0",
        "CHARGING_CHARGE_WIRELESS_CHARGING_SWITCH_SET|Charging|0",
        "CHARGING_DISCHARGE_TO_CAR_STATE_SET|Charging|0",
        "ENGINE_CHARGE_POWER|Engine|0",
        "GEARBOX_AUTO_MODE_TYPE|Gearbox|555745336",
        "GEARBOX_EPB_STATE|Gearbox|0",
        "GEARBOX_MANUAL_MODE_LEVEL|Gearbox|0",
        "GEARBOX_PARK_BRAKE_SWITCH|Gearbox|0",
        "GEARBOX_TYPE|Gearbox|0",
        "LIGHT_DAY_RUNNING_LIGHT_AUTO_STATE_SET|Light|1125122118",
        "SENSOR_RAIN_FALL_VALUE|Sensor|0",
        "SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH|Setting|0",
        "SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH_SET|Setting|0",
        "SET_CMD_HEADLAMP_HEIGHT_LEVEL_SET|Setting|0",
        "SET_IAL_ALL_BRIGHTNESS|Setting|0",
        "SET_IAL_BACK_BRIGHTNESS|Setting|0",
        "SET_IAL_FRONT_BRIGHTNESS|Setting|1121976328",
        "SET_INTERIOR_ATMOSPHERE_LAMP_AREA_SET|Setting|0",
        "SET_INTERIOR_ATMOSPHERE_LAMP_BRIGHTNESS_SET|Setting|1069547536",
        "SET_INTERIOR_ATMOSPHERE_LAMP_COLOR_SET|Setting|0",
        "SET_INTERIOR_ATMOSPHERE_LAMP_SOURCE_SET|Setting|0"
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
            String[] p = entry.split("\\|", -1);
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
