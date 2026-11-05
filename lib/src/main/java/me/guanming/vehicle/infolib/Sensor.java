package me.guanming.vehicle.infolib;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆传感器字段读取入口。为全部 97 个车辆字段提供类型化的读取方法。
 *
 * <p>每个方法调用 {@link Vehicle#read(int, int, int)} 获取原始 int 值，
 * 再按字段对应的 {@link Decoder} 解码为 {@link SensorValue}。
 *
 * <p>使用示例：
 * <pre>
 *   SensorValue v = Sensor.speed();
 *   v.format();          // "60 km/h"
 *   v.format(1, null);   // "60 km/h"
 *   v.format(-1, "km/h");// "60km/h"
 *
 *   SensorValue g = Sensor.gear();
 *   g.format();          // "D"
 * </pre>
 *
 * <p>{@link #ALL} 暴露全部字段元数据，便于 UI 枚举展示。
 */
public final class Sensor {

    private Sensor() {}

    // ===================== 枚举映射表 =====================

    private static final Map<Integer, String> gearMap = map(
            1, "P", 2, "R", 3, "N", 4, "D");

    private static final Map<Integer, String> driveModeMap = map(
            1, "ECO", 2, "SPORT", 3, "COMFORT", 4, "SNOW",
            5, "MUD", 6, "SAND", 7, "MOUNTAIN", 11, "RACING");

    private static final Map<Integer, String> powerStateMap = map(
            0, "OFF", 1, "ON");

    private static final Map<Integer, String> workModeMap = map(
            0, "STOP", 1, "EV", 2, "EV+", 3, "HEV");

    private static final Map<Integer, String> energyStateMap = map(
            1, "EV", 2, "EV+", 3, "HEV");

    private static final Map<Integer, String> chargeGunMap = map(
            0, "未连接", 1, "未连接", 2, "AC", 3, "DC", 4, "DC");

    private static final Map<Integer, String> chargeStatusMap = map(
            0, "未充电", 1, "未充电", 2, "已充满", 3, "已停止");

    private static final Map<Integer, String> accCruiseMap = map(
            0, "未开启", 1, "已开启", 2, "激活中");

    private static final Map<Integer, String> acStatusMap = map(
            0, "关闭", 1, "开启");

    private static final Map<Integer, String> acCircMap = map(
            0, "外循环", 1, "内循环");

    private static final Map<Integer, String> onOffMap = map(
            0, "—", 1, "开启", 2, "关闭");

    private static final Map<Integer, String> openCloseMap = map(
            0, "关闭", 1, "打开");

    private static final Map<Integer, String> closeOpenMap = map(
            1, "关闭", 2, "打开");

    private static final Map<Integer, String> fuelConUnitMap = map(
            0, "未知", 1, "L/100km", 2, "km/L", 3, "MPG", 4, "L/h");

    private static final Map<Integer, String> lockUnlockMap = map(
            1, "解锁", 2, "上锁");

    private static final Map<Integer, String> seatbeltMap = map(
            0, "未系", 1, "已系", 2, "无效");

    private static final Map<Integer, String> drlMap = map(
            0, "—", 1, "开启", 2, "关闭");

    private static final Map<Integer, String> turnSignalFlashMap = map(
            1, "关闭", 2, "左转", 4, "右转", 6, "双闪");

    private static final Map<Integer, String> adasAccModeMap = map(
            0, "STANDBY", 1, "STANDBY", 2, "STANDBY",
            3, "ACTIVE", 4, "ACTIVE", 5, "OVERRIDE",
            6, "ACTIVE", 7, "ACTIVE");

    private static final Map<Integer, String> adasGrayMap = map(
            0, "可用", 1, "灰显");

    private static final Map<Integer, String> adasStatusMap = map(
            0, "OFF_ADAS", 1, "STANDBY", 2, "ACTIVE",
            3, "OVERRIDE", 4, "FAULT");

    private static final Map<Integer, String> adasTjaMap = map(
            0, "OFF", 1, "PASSIVE", 2, "ACTIVE",
            3, "ACTIVE", 4, "FAULT", 5, "ACTIVE");

    private static final Map<Integer, String> laneKeepMap = map(
            0, "OFF", 1, "Warn", 2, "Keep", 3, "All", 4, "LKS on");

    private static final Map<Integer, String> adasApaMap = map(
            0, "OFF", 1, "SEARCHING", 2, "PARKING",
            3, "PARKOUT_READY", 4, "PARKING_ACTIVE",
            5, "PARKING_FINISH", 6, "PARKING_COMPLETE",
            7, "SELECTING");

    private static final Map<Integer, String> trunkMap = map(
            1, "打开", 2, "关闭");

    /** 按交错键值对构造不可修改的有序映射。 */
    private static Map<Integer, String> map(Object... kv) {
        Map<Integer, String> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) {
            m.put((Integer) kv[i], (String) kv[i + 1]);
        }
        return Collections.unmodifiableMap(m);
    }

    // ===================== 解码器 =====================

    private enum Decoder {
        INT_RAW, INT_DIV10, INT_SCALED, INT_PERCENT, INT_ENUM, INT_TEMP_C, INT_TEMP_C_OFS40, INT_KPA,
        FLOAT_VOLT, FLOAT_PERCENT, FLOAT_KW, FLOAT_KWH;

        Double decode(int raw, double scale) {
            if (isSentinel(raw)) return null;
            switch (this) {
                case INT_RAW: return (double) raw;
                case INT_DIV10: return raw / 10.0;
                case INT_SCALED: return raw * scale;
                case INT_PERCENT: { double v = raw; return (v < 0 || v >= 101) ? null : v; }
                case INT_ENUM: return (double) raw;
                case INT_TEMP_C: { double v = raw; return (v < -50 || v >= 81) ? null : v; }
                case INT_TEMP_C_OFS40: { double v = raw - 40.0; return (v < -50 || v >= 81) ? null : v; }
                case INT_KPA: return (double) raw;
                case FLOAT_VOLT: case FLOAT_PERCENT: case FLOAT_KW: {
                    float f = Float.intBitsToFloat(raw);
                    return (Float.isNaN(f) || Float.isInfinite(f) || f < -1.0f) ? null : (double) f;
                }
                case FLOAT_KWH: {
                    // 电耗/油耗等字段在动能回收时可为负值（如 -1.2 kWh/100km），不能按负值过滤，
                    // 仅 NaN/Infinity 视为无效。
                    float f = Float.intBitsToFloat(raw);
                    return (Float.isNaN(f) || Float.isInfinite(f)) ? null : (double) f;
                }
            }
            return null;
        }

        private boolean isSentinel(int raw) {
            return raw == -10011 || raw == -10013 || raw == 65535 || raw == 1048575;
        }

        String getUnit() {
            switch (this) {
                case INT_PERCENT: case FLOAT_PERCENT: return "%";
                case INT_TEMP_C: case INT_TEMP_C_OFS40: return "°C";
                case INT_KPA: return "kPa";
                case FLOAT_VOLT: return "V";
                case FLOAT_KW: return "kW";
                case FLOAT_KWH: return "kWh";
                default: return "";
            }
        }
    }

    // ===================== 字段元数据 =====================

    /** 描述一个车辆字段，用于 UI 枚举与读取。 */
    public static final class Field {
        public final String name;
        public final int device, fid, transact;
        public final Decoder decoder;
        public final double scale;
        public final String displayName;
        public final String category;
        public final Map<Integer, String> enumMap;
        public final String unitSuffix;

        public Field(String name, int device, int fid, int transact, Decoder decoder,
                     double scale, String displayName, String category,
                     Map<Integer, String> enumMap, String unitSuffix) {
            this.name = name;
            this.device = device;
            this.fid = fid;
            this.transact = transact;
            this.decoder = decoder;
            this.scale = scale;
            this.displayName = displayName;
            this.category = category;
            this.enumMap = enumMap == null ? Collections.emptyMap() : enumMap;
            this.unitSuffix = unitSuffix == null ? "" : unitSuffix;
        }

        @Override
        public String toString() {
            return "Field{" + name + ": " + displayName + " (" + category + ")}";
        }
    }

    // ===================== 字段常量 =====================

    // --- 动力与速度 ---
    private static final Field SPEED = new Field("speed", 1013, -1807745016, 7, Decoder.FLOAT_KW, 1.0, "车速", "动力与速度", null, "km/h");
    private static final Field GEAR = new Field("gear", 1011, 555745336, 5, Decoder.INT_ENUM, 1.0, "挡位", "动力与速度", gearMap, "");
    private static final Field POWER = new Field("power", 1012, 339738656, 5, Decoder.INT_RAW, 1.0, "功率", "动力与速度", null, "kW");
    private static final Field ACCEL_PEDAL_DEPTH = new Field("accelPedalDepth", 1013, 874512392, 5, Decoder.INT_PERCENT, 1.0, "油门开度", "动力与速度", null, "");
    private static final Field BRAKE_PEDAL_DEPTH = new Field("brakePedalDepth", 1013, 874512400, 5, Decoder.INT_PERCENT, 1.0, "刹车开度", "动力与速度", null, "");
    private static final Field DRIVE_MODE = new Field("driveMode", 1006, 555745294, 5, Decoder.INT_ENUM, 1.0, "驾驶模式", "动力与速度", driveModeMap, "");
    private static final Field DRIVE_MODE_INDICATOR = new Field("driveModeIndicator", 1007, 710934584, 5, Decoder.INT_ENUM, 1.0, "驾驶模式指示", "动力与速度", null, "");
    private static final Field WORK_MODE = new Field("workMode", 1006, 874512420, 5, Decoder.INT_ENUM, 1.0, "工作模式", "动力与速度", workModeMap, "");
    private static final Field ENERGY_STATE = new Field("energyState", 1006, 873463832, 5, Decoder.INT_ENUM, 1.0, "能量状态", "动力与速度", energyStateMap, "");
    private static final Field POWER_STATE = new Field("powerState", 1023, 315621408, 5, Decoder.INT_ENUM, 1.0, "电源挡位", "动力与速度", powerStateMap, "");
    private static final Field ACC_CRUISE_STATE = new Field("accCruiseState", 1007, 694157362, 5, Decoder.INT_ENUM, 1.0, "ACC巡航状态", "动力与速度", accCruiseMap, "");
    private static final Field ACC_CRUISING_SPEED = new Field("accCruisingSpeed", 1007, 710934536, 5, Decoder.INT_RAW, 1.0, "ACC巡航车速", "动力与速度", null, "km/h");
    private static final Field ACC_SPEED = new Field("accSpeed", 1007, 597688328, 5, Decoder.INT_RAW, 1.0, "ACC设定速度", "动力与速度", null, "km/h");
    private static final Field ENGINE_RPM = new Field("engineRpm", 1012, 282066952, 5, Decoder.INT_RAW, 1.0, "发动机转速", "动力与速度", null, "rpm");
    private static final Field ENGINE_RPM2 = new Field("engineRpm2", 1012, 339738642, 5, Decoder.INT_RAW, 1.0, "发动机转速2", "动力与速度", null, "rpm");
    private static final Field FRONT_MOTOR_RPM = new Field("frontMotorRpm", 1012, 1141899272, 5, Decoder.INT_RAW, 1.0, "前电机转速", "动力与速度", null, "rpm");
    private static final Field REAR_MOTOR_RPM = new Field("rearMotorRpm", 1012, 621805576, 5, Decoder.INT_RAW, 1.0, "后电机转速", "动力与速度", null, "rpm");
    private static final Field REAR_MOTOR_RPM2 = new Field("rearMotorRpm2", 1012, 621805592, 5, Decoder.INT_RAW, 1.0, "后电机转速2", "动力与速度", null, "rpm");

    // --- 能源与续航 ---
    private static final Field FUEL_LEVEL = new Field("fuelLevel", 1014, 1246785600, 5, Decoder.INT_PERCENT, 1.0, "油量", "能源与续航", null, "");
    private static final Field SOC = new Field("soc", 1014, 1246777400, 7, Decoder.FLOAT_PERCENT, 1.0, "电池电量(SOC)", "能源与续航", null, "");
    private static final Field VOLTAGE_12V = new Field("voltage12v", 1001, 1128267816, 7, Decoder.FLOAT_VOLT, 1.0, "12V电压", "能源与续航", null, "");
    private static final Field MAX_CELL_VOLTAGE = new Field("maxCellVoltage", 1014, 1147142192, 5, Decoder.INT_SCALED, 0.001, "最高单体电压", "能源与续航", null, "V");
    private static final Field MIN_CELL_VOLTAGE = new Field("minCellVoltage", 1014, 1147142160, 5, Decoder.INT_SCALED, 0.001, "最低单体电压", "能源与续航", null, "V");
    private static final Field MAX_BAT_TEMP = new Field("maxBatTemp", 1014, 1148190752, 5, Decoder.INT_TEMP_C_OFS40, 1.0, "电池最高温度", "能源与续航", null, "");
    private static final Field MIN_BAT_TEMP = new Field("minBatTemp", 1014, 1148190736, 5, Decoder.INT_TEMP_C_OFS40, 1.0, "电池最低温度", "能源与续航", null, "");
    private static final Field TOTAL_ELEC_CONSUMPTION = new Field("totalElecConsumption", 1014, 1032871984, 7, Decoder.FLOAT_KWH, 1.0, "总耗电量", "能源与续航", null, "");
    private static final Field ELEC_CON_PER_100KM = new Field("elecConPer100km", 1014, 1032863794, 7, Decoder.FLOAT_KWH, 1.0, "百公里电耗", "能源与续航", null, "kWh/100km");
    private static final Field FUEL_CON_PER_100KM = new Field("fuelConPer100km", 1014, 1032863782, 7, Decoder.FLOAT_KWH, 1.0, "百公里油耗", "能源与续航", null, "L/100km");
    private static final Field INSTANT_FUEL_CON = new Field("instantFuelCon", 1014, 1246760996, 7, Decoder.FLOAT_KWH, 1.0, "瞬时油耗", "能源与续航", null, "");
    private static final Field INSTANT_FUEL_CON_UNIT = new Field("instantFuelConUnit", 1007, 1246761027, 5, Decoder.INT_ENUM, 1.0, "瞬时油耗单位", "能源与续航", fuelConUnitMap, "");
    private static final Field ELEC_DRIVING_RANGE_KM = new Field("elecDrivingRangeKm", 1014, 1246765118, 5, Decoder.INT_RAW, 1.0, "纯电续航里程", "能源与续航", null, "km");
    private static final Field FUEL_DRIVING_RANGE_KM = new Field("fuelDrivingRangeKm", 1014, 1246773304, 5, Decoder.INT_RAW, 1.0, "燃油续航里程", "能源与续航", null, "km");
    private static final Field CHARGE_GUN_STATE = new Field("chargeGunState", 1009, 876609586, 5, Decoder.INT_ENUM, 1.0, "充电枪状态", "能源与续航", chargeGunMap, "");
    private static final Field CHARGING_CAP_COVER = new Field("chargingCapCover", 1009, 1081081898, 5, Decoder.INT_ENUM, 1.0, "充电口盖", "能源与续航", closeOpenMap, "");
    private static final Field CHARGING_CAP_LEGACY = new Field("chargingCapLegacy", 1009, 603979834, 5, Decoder.INT_ENUM, 1.0, "充电口盖(旧)", "能源与续航", openCloseMap, "");
    private static final Field CHARGING_STATUS = new Field("chargingStatus", 1009, 1231032336, 5, Decoder.INT_ENUM, 1.0, "充电状态", "能源与续航", chargeStatusMap, "");

    // --- 里程信息 ---
    private static final Field MILEAGE = new Field("mileage", 1014, 1246765072, 5, Decoder.INT_SCALED, 0.1, "总里程", "里程信息", null, "km");
    private static final Field TRIP_MILEAGE_1_KM = new Field("tripMileage1Km", 1014, 1246769168, 5, Decoder.INT_SCALED, 0.1, "小计里程1", "里程信息", null, "km");
    private static final Field TRIP_MILEAGE_2_KM = new Field("tripMileage2Km", 1014, 1246769188, 5, Decoder.INT_SCALED, 0.1, "小计里程2", "里程信息", null, "km");
    private static final Field TOTAL_EV_MILEAGE_KM = new Field("totalEvMileageKm", 1014, 1246773284, 5, Decoder.INT_RAW, 1.0, "EV总里程", "里程信息", null, "km");
    private static final Field TOTAL_HEV_MILEAGE_KM = new Field("totalHevMileageKm", 1014, 1246773264, 5, Decoder.INT_RAW, 1.0, "HEV总里程", "里程信息", null, "km");
    private static final Field CURRENT_TRIP_MILEAGE_KM_INT = new Field("currentTripMileageKmInt", 1014, 1245753360, 5, Decoder.INT_SCALED, 0.1, "本次行程里程(整)", "里程信息", null, "km");
    private static final Field CURRENT_TRIP_MILEAGE_KM_FLOAT = new Field("currentTripMileageKmFloat", 1014, 1245753360, 7, Decoder.FLOAT_KWH, 1.0, "本次行程里程(浮)", "里程信息", null, "km");
    private static final Field TRIP_MILEAGE_1_FLOAT = new Field("tripMileage1Float", 1014, 1246769168, 7, Decoder.FLOAT_KWH, 1.0, "小计里程1(浮)", "里程信息", null, "km");
    private static final Field CURRENT_TRIP_ELEC_KWH = new Field("currentTripElecKwh", 1014, 1246801976, 7, Decoder.FLOAT_KWH, 1.0, "本次行程电耗", "里程信息", null, "");
    private static final Field CURRENT_TRIP_FUEL_L = new Field("currentTripFuelL", 1014, 1246785576, 7, Decoder.FLOAT_KWH, 1.0, "本次行程油耗", "里程信息", null, "L");
    private static final Field CURRENT_TRIP_DRIVING_TIME_MIN = new Field("currentTripDrivingTimeMin", 1014, 1246806064, 5, Decoder.INT_RAW, 1.0, "本次行程时长(分)", "里程信息", null, "min");
    private static final Field CURRENT_TRIP_DRIVING_TIME_HOURS = new Field("currentTripDrivingTimeHours", 1014, 1246789668, 7, Decoder.FLOAT_KWH, 1.0, "本次行程时长(时)", "里程信息", null, "h");
    private static final Field CURRENT_TRIP_TRAVEL_TIME_HOURS = new Field("currentTripTravelTimeHours", 1014, 1245753382, 7, Decoder.FLOAT_KWH, 1.0, "本次行驶时长(时)", "里程信息", null, "h");
    private static final Field CURRENT_TRIP_TRAVEL_TIME_HOUR_PART = new Field("currentTripTravelTimeHourPart", 1014, 1245753382, 5, Decoder.INT_RAW, 1.0, "本次行驶时长(时部分)", "里程信息", null, "h");
    private static final Field CURRENT_TRIP_TRAVEL_TIME_MIN_FLOAT = new Field("currentTripTravelTimeMinFloat", 1014, 1245753392, 7, Decoder.FLOAT_KWH, 1.0, "本次行驶时长(分浮)", "里程信息", null, "min");
    private static final Field CURRENT_TRIP_TRAVEL_TIME_MIN_PART = new Field("currentTripTravelTimeMinPart", 1014, 1245753392, 5, Decoder.INT_RAW, 1.0, "本次行驶时长(分部分)", "里程信息", null, "min");

    // --- 仪表与小计 ---
    private static final Field INSTRUMENT_JOURNEY_MILEAGE_KM = new Field("instrumentJourneyMileageKm", 1007, 1246801948, 7, Decoder.FLOAT_KWH, 1.0, "仪表行程里程", "仪表与小计", null, "km");
    private static final Field INSTRUMENT_JOURNEY_TIME_HOURS = new Field("instrumentJourneyTimeHours", 1007, 1246801938, 7, Decoder.FLOAT_KWH, 1.0, "仪表行程时长", "仪表与小计", null, "h");

    // --- 车身与门窗 ---
    private static final Field HOOD = new Field("hood", 1001, 692060188, 5, Decoder.INT_ENUM, 1.0, "引擎盖", "车身与门窗", openCloseMap, "");
    private static final Field TRUNK = new Field("trunk", 1001, 1074790416, 5, Decoder.INT_ENUM, 1.0, "后备箱", "车身与门窗", trunkMap, "");
    private static final Field FUEL_CAP = new Field("fuelCap", 1007, 1032855586, 5, Decoder.INT_ENUM, 1.0, "油箱盖", "车身与门窗", openCloseMap, "");
    private static final Field DOOR_FL = new Field("doorFL", 1001, 692060168, 5, Decoder.INT_ENUM, 1.0, "左前门", "车身与门窗", openCloseMap, "");
    private static final Field DOOR_FR = new Field("doorFR", 1001, 692060170, 5, Decoder.INT_ENUM, 1.0, "右前门", "车身与门窗", openCloseMap, "");
    private static final Field DOOR_RL = new Field("doorRL", 1001, 692060172, 5, Decoder.INT_ENUM, 1.0, "左后门", "车身与门窗", openCloseMap, "");
    private static final Field DOOR_RR = new Field("doorRR", 1001, 692060174, 5, Decoder.INT_ENUM, 1.0, "右后门", "车身与门窗", openCloseMap, "");
    private static final Field WINDOW_FL = new Field("windowFL", 1001, 947912728, 5, Decoder.INT_PERCENT, 1.0, "左前车窗", "车身与门窗", null, "");
    private static final Field WINDOW_FR = new Field("windowFR", 1001, 1267728400, 5, Decoder.INT_PERCENT, 1.0, "右前车窗", "车身与门窗", null, "");
    private static final Field WINDOW_RL = new Field("windowRL", 1001, 947912736, 5, Decoder.INT_PERCENT, 1.0, "左后车窗", "车身与门窗", null, "");
    private static final Field WINDOW_RR = new Field("windowRR", 1001, 947912752, 5, Decoder.INT_PERCENT, 1.0, "右后车窗", "车身与门窗", null, "");
    private static final Field LOCK_FL = new Field("lockFL", 1032, 1081081864, 5, Decoder.INT_ENUM, 1.0, "左前锁", "车身与门窗", lockUnlockMap, "");
    private static final Field LOCK_FR = new Field("lockFR", 1001, 1081081866, 5, Decoder.INT_ENUM, 1.0, "右前锁", "车身与门窗", lockUnlockMap, "");
    private static final Field LOCK_RL = new Field("lockRL", 1001, 1081081868, 5, Decoder.INT_ENUM, 1.0, "左后锁", "车身与门窗", lockUnlockMap, "");
    private static final Field LOCK_RR = new Field("lockRR", 1001, 1081081870, 5, Decoder.INT_ENUM, 1.0, "右后锁", "车身与门窗", lockUnlockMap, "");

    // --- 灯光与信号 ---
    private static final Field LIGHT_SIDE = new Field("lightSide", 1004, 950009864, 5, Decoder.INT_ENUM, 1.0, "示宽灯", "灯光与信号", onOffMap, "");
    private static final Field LIGHT_LOW = new Field("lightLow", 1004, 950009866, 5, Decoder.INT_ENUM, 1.0, "近光灯", "灯光与信号", onOffMap, "");
    private static final Field LIGHT_HIGH = new Field("lightHigh", 1004, 950009868, 5, Decoder.INT_ENUM, 1.0, "远光灯", "灯光与信号", onOffMap, "");
    private static final Field LIGHT_FRONT_FOG = new Field("lightFrontFog", 1004, 1011875870, 5, Decoder.INT_ENUM, 1.0, "前雾灯", "灯光与信号", onOffMap, "");
    private static final Field LIGHT_REAR_FOG = new Field("lightRearFog", 1004, 950009872, 5, Decoder.INT_ENUM, 1.0, "后雾灯", "灯光与信号", onOffMap, "");
    private static final Field HAZARD_LIGHTS = new Field("hazardLights", 1004, 960495667, 5, Decoder.INT_ENUM, 1.0, "危险报警灯", "灯光与信号", onOffMap, "");
    private static final Field TURN_SIGNAL_LEFT = new Field("turnSignalLeft", 1004, 339738672, 5, Decoder.INT_ENUM, 1.0, "左转向灯", "灯光与信号", onOffMap, "");
    private static final Field TURN_SIGNAL_RIGHT = new Field("turnSignalRight", 1004, 339738678, 5, Decoder.INT_ENUM, 1.0, "右转向灯", "灯光与信号", onOffMap, "");
    private static final Field TURN_SIGNAL_FLASH = new Field("turnSignalFlash", 1004, 950009900, 5, Decoder.INT_ENUM, 1.0, "转向灯闪烁", "灯光与信号", turnSignalFlashMap, "");
    private static final Field DRL = new Field("drl", 1004, 1231040528, 5, Decoder.INT_ENUM, 1.0, "日间行车灯", "灯光与信号", drlMap, "");

    // --- 空调与环境 ---
    private static final Field AC_STATUS = new Field("acStatus", 1000, 1077936144, 5, Decoder.INT_ENUM, 1.0, "空调状态", "空调与环境", acStatusMap, "");
    private static final Field AC_TEMP = new Field("acTemp", 1000, 1077936168, 5, Decoder.INT_TEMP_C, 1.0, "空调设定温度", "空调与环境", null, "");
    private static final Field FAN_LEVEL = new Field("fanLevel", 1000, 1077936156, 5, Decoder.INT_RAW, 1.0, "风扇挡位", "空调与环境", null, "");
    private static final Field AC_CIRC = new Field("acCirc", 1000, 1077936148, 5, Decoder.INT_ENUM, 1.0, "内外循环", "空调与环境", acCircMap, "");
    private static final Field INSIDE_TEMP = new Field("insideTemp", 1000, 1031798832, 5, Decoder.INT_TEMP_C, 1.0, "车内温度", "空调与环境", null, "");
    private static final Field EXTERIOR_TEMP = new Field("exteriorTemp", 1000, 1077936184, 5, Decoder.INT_TEMP_C, 1.0, "车外温度", "空调与环境", null, "");

    // --- 轮胎压力 ---
    private static final Field TIRE_PRESS_FL = new Field("tirePressFL", 1016, -1728052956, 5, Decoder.INT_KPA, 1.0, "左前胎压", "轮胎压力", null, "");
    private static final Field TIRE_PRESS_FR = new Field("tirePressFR", 1016, -1728052952, 5, Decoder.INT_KPA, 1.0, "右前胎压", "轮胎压力", null, "");
    private static final Field TIRE_PRESS_RL = new Field("tirePressRL", 1016, -1728052948, 5, Decoder.INT_KPA, 1.0, "左后胎压", "轮胎压力", null, "");
    private static final Field TIRE_PRESS_RR = new Field("tirePressRR", 1016, -1728052944, 5, Decoder.INT_KPA, 1.0, "右后胎压", "轮胎压力", null, "");

    // --- 安全带 ---
    private static final Field SEATBELT_FL = new Field("seatbeltFL", 1007, 692060184, 5, Decoder.INT_ENUM, 1.0, "左前安全带", "安全带", seatbeltMap, "");
    private static final Field SEATBELT_FR = new Field("seatbeltFR", 1038, 535842862, 5, Decoder.INT_ENUM, 1.0, "右前安全带", "安全带", seatbeltMap, "");
    private static final Field SEATBELT_RL = new Field("seatbeltRL", 1042, 755989, 5, Decoder.INT_ENUM, 1.0, "左后安全带", "安全带", seatbeltMap, "");
    private static final Field SEATBELT_RR = new Field("seatbeltRR", 1042, 1196425242, 5, Decoder.INT_ENUM, 1.0, "右后安全带", "安全带", seatbeltMap, "");

    // --- ADAS辅助驾驶 ---
    private static final Field ADAS_ACC_MODE = new Field("adasAccMode", 1038, 852492340, 5, Decoder.INT_ENUM, 1.0, "ACC模式", "ADAS辅助驾驶", adasAccModeMap, "");
    private static final Field ADAS_ACC_GRAY_STATE = new Field("adasAccGrayState", 1038, 535826448, 5, Decoder.INT_ENUM, 1.0, "ACC灰色状态", "ADAS辅助驾驶", adasGrayMap, "");
    private static final Field ADAS_LCC_NCA_STATUS = new Field("adasLccNcaStatus", 1038, 440401932, 5, Decoder.INT_ENUM, 1.0, "LCC/NCA状态", "ADAS辅助驾驶", adasStatusMap, "");
    private static final Field ADAS_NOA_GRAY_STATE = new Field("adasNoaGrayState", 1038, 535826458, 5, Decoder.INT_ENUM, 1.0, "NOA灰色状态", "ADAS辅助驾驶", adasGrayMap, "");
    private static final Field ADAS_MNOA_STATUS = new Field("adasMnoaStatus", 1038, 790643093, 5, Decoder.INT_ENUM, 1.0, "MNOA状态", "ADAS辅助驾驶", adasStatusMap, "");
    private static final Field ADAS_EXEC_SPEED = new Field("adasExecSpeed", 1038, 513807420, 5, Decoder.INT_RAW, 1.0, "ADAS执行速度", "ADAS辅助驾驶", null, "km/h");
    private static final Field ADAS_TJA_STATE = new Field("adasTjaState", 1038, 828375084, 5, Decoder.INT_ENUM, 1.0, "TJA状态", "ADAS辅助驾驶", adasTjaMap, "");
    private static final Field LANE_KEEP_STATE = new Field("laneKeepState", 1038, 828375054, 5, Decoder.INT_ENUM, 1.0, "车道保持状态", "ADAS辅助驾驶", laneKeepMap, "");
    private static final Field ADAS_APA_PARKING_STATUS = new Field("adasApaParkingStatus", 1038, 749732128, 5, Decoder.INT_ENUM, 1.0, "APA泊车状态", "ADAS辅助驾驶", adasApaMap, "");

    // ===================== 全部字段 =====================

    /** 全部 102 个字段，按定义顺序排列。 */
    public static final List<Field> ALL = Collections.unmodifiableList(Arrays.asList(
            // 动力与速度
            SPEED, GEAR, POWER, ACCEL_PEDAL_DEPTH, BRAKE_PEDAL_DEPTH,
            DRIVE_MODE, DRIVE_MODE_INDICATOR, WORK_MODE, ENERGY_STATE,
            POWER_STATE, ACC_CRUISE_STATE, ACC_CRUISING_SPEED, ACC_SPEED,
            ENGINE_RPM, ENGINE_RPM2, FRONT_MOTOR_RPM, REAR_MOTOR_RPM, REAR_MOTOR_RPM2,
            // 能源与续航
            FUEL_LEVEL, SOC, VOLTAGE_12V, MAX_CELL_VOLTAGE, MIN_CELL_VOLTAGE,
            MAX_BAT_TEMP, MIN_BAT_TEMP, TOTAL_ELEC_CONSUMPTION,
            ELEC_CON_PER_100KM, FUEL_CON_PER_100KM, INSTANT_FUEL_CON,
            INSTANT_FUEL_CON_UNIT, ELEC_DRIVING_RANGE_KM, FUEL_DRIVING_RANGE_KM,
            CHARGE_GUN_STATE, CHARGING_CAP_COVER, CHARGING_CAP_LEGACY, CHARGING_STATUS,
            // 里程信息
            MILEAGE, TRIP_MILEAGE_1_KM, TRIP_MILEAGE_2_KM, TOTAL_EV_MILEAGE_KM,
            TOTAL_HEV_MILEAGE_KM, CURRENT_TRIP_MILEAGE_KM_INT,
            CURRENT_TRIP_MILEAGE_KM_FLOAT, TRIP_MILEAGE_1_FLOAT,
            CURRENT_TRIP_ELEC_KWH, CURRENT_TRIP_FUEL_L,
            CURRENT_TRIP_DRIVING_TIME_MIN, CURRENT_TRIP_DRIVING_TIME_HOURS,
            CURRENT_TRIP_TRAVEL_TIME_HOURS, CURRENT_TRIP_TRAVEL_TIME_HOUR_PART,
            CURRENT_TRIP_TRAVEL_TIME_MIN_FLOAT, CURRENT_TRIP_TRAVEL_TIME_MIN_PART,
            // 仪表与小计
            INSTRUMENT_JOURNEY_MILEAGE_KM, INSTRUMENT_JOURNEY_TIME_HOURS,
            // 车身与门窗
            HOOD, TRUNK, FUEL_CAP, DOOR_FL, DOOR_FR, DOOR_RL, DOOR_RR,
            WINDOW_FL, WINDOW_FR, WINDOW_RL, WINDOW_RR,
            LOCK_FL, LOCK_FR, LOCK_RL, LOCK_RR,
            // 灯光与信号
            LIGHT_SIDE, LIGHT_LOW, LIGHT_HIGH, LIGHT_FRONT_FOG, LIGHT_REAR_FOG,
            HAZARD_LIGHTS, TURN_SIGNAL_LEFT, TURN_SIGNAL_RIGHT,
            TURN_SIGNAL_FLASH, DRL,
            // 空调与环境
            AC_STATUS, AC_TEMP, FAN_LEVEL, AC_CIRC, INSIDE_TEMP, EXTERIOR_TEMP,
            // 轮胎压力
            TIRE_PRESS_FL, TIRE_PRESS_FR, TIRE_PRESS_RL, TIRE_PRESS_RR,
            // 安全带
            SEATBELT_FL, SEATBELT_FR, SEATBELT_RL, SEATBELT_RR,
            // ADAS辅助驾驶
            ADAS_ACC_MODE, ADAS_ACC_GRAY_STATE, ADAS_LCC_NCA_STATUS,
            ADAS_NOA_GRAY_STATE, ADAS_MNOA_STATUS, ADAS_EXEC_SPEED,
            ADAS_TJA_STATE, LANE_KEEP_STATE, ADAS_APA_PARKING_STATUS
    ));

    // ===================== 读取与解码 =====================

    /** 按 Field 描述读取并解码，返回 SensorValue */
    public static SensorValue read(Field f) {
        return readField(f);
    }

    private static SensorValue readField(Field f) {
        Integer raw = Vehicle.read(f.device, f.fid, f.transact);
        if (raw == null) return SensorValue.failed();

        boolean isEnum = (f.decoder == Decoder.INT_ENUM);
        String defaultUnit = f.unitSuffix.length() > 0 ? f.unitSuffix : f.decoder.getUnit();

        if (isEnum) {
            if (raw == -10011 || raw == -10013 || raw == 65535 || raw == 1048575) {
                return new SensorValue(raw, null, true, f.enumMap, defaultUnit);
            }
            return new SensorValue(raw, (double) raw, true, f.enumMap, defaultUnit);
        }

        Double decoded = f.decoder.decode(raw, f.scale);
        if (decoded == null) {
            return new SensorValue(raw, null, false, f.enumMap, defaultUnit);
        }
        return new SensorValue(raw, decoded, false, f.enumMap, defaultUnit);
    }

    // ===================== 单字段读取方法 =====================

    // --- 动力与速度 ---
    public static SensorValue speed() { return readField(SPEED); }
    public static SensorValue gear() { return readField(GEAR); }
    public static SensorValue power() { return readField(POWER); }
    public static SensorValue accelPedalDepth() { return readField(ACCEL_PEDAL_DEPTH); }
    public static SensorValue brakePedalDepth() { return readField(BRAKE_PEDAL_DEPTH); }
    public static SensorValue driveMode() { return readField(DRIVE_MODE); }
    public static SensorValue driveModeIndicator() { return readField(DRIVE_MODE_INDICATOR); }
    public static SensorValue workMode() { return readField(WORK_MODE); }
    public static SensorValue energyState() { return readField(ENERGY_STATE); }
    public static SensorValue powerState() { return readField(POWER_STATE); }
    public static SensorValue accCruiseState() { return readField(ACC_CRUISE_STATE); }
    public static SensorValue accCruisingSpeed() { return readField(ACC_CRUISING_SPEED); }
    public static SensorValue accSpeed() { return readField(ACC_SPEED); }
    public static SensorValue engineRpm() { return readField(ENGINE_RPM); }
    public static SensorValue engineRpm2() { return readField(ENGINE_RPM2); }
    public static SensorValue frontMotorRpm() { return readField(FRONT_MOTOR_RPM); }
    public static SensorValue rearMotorRpm() { return readField(REAR_MOTOR_RPM); }
    public static SensorValue rearMotorRpm2() { return readField(REAR_MOTOR_RPM2); }

    // --- 能源与续航 ---
    public static SensorValue fuelLevel() { return readField(FUEL_LEVEL); }
    public static SensorValue soc() { return readField(SOC); }
    public static SensorValue voltage12v() { return readField(VOLTAGE_12V); }
    public static SensorValue maxCellVoltage() { return readField(MAX_CELL_VOLTAGE); }
    public static SensorValue minCellVoltage() { return readField(MIN_CELL_VOLTAGE); }
    public static SensorValue maxBatTemp() { return readField(MAX_BAT_TEMP); }
    public static SensorValue minBatTemp() { return readField(MIN_BAT_TEMP); }
    public static SensorValue totalElecConsumption() { return readField(TOTAL_ELEC_CONSUMPTION); }
    public static SensorValue elecConPer100km() { return readField(ELEC_CON_PER_100KM); }
    public static SensorValue fuelConPer100km() { return readField(FUEL_CON_PER_100KM); }
    public static SensorValue instantFuelCon() { return readField(INSTANT_FUEL_CON); }
    public static SensorValue instantFuelConUnit() { return readField(INSTANT_FUEL_CON_UNIT); }
    public static SensorValue elecDrivingRangeKm() { return readField(ELEC_DRIVING_RANGE_KM); }
    public static SensorValue fuelDrivingRangeKm() { return readField(FUEL_DRIVING_RANGE_KM); }
    public static SensorValue chargeGunState() { return readField(CHARGE_GUN_STATE); }
    public static SensorValue chargingCapCover() { return readField(CHARGING_CAP_COVER); }
    public static SensorValue chargingCapLegacy() { return readField(CHARGING_CAP_LEGACY); }
    public static SensorValue chargingStatus() { return readField(CHARGING_STATUS); }

    // --- 里程信息 ---
    public static SensorValue mileage() { return readField(MILEAGE); }
    public static SensorValue tripMileage1Km() { return readField(TRIP_MILEAGE_1_KM); }
    public static SensorValue tripMileage2Km() { return readField(TRIP_MILEAGE_2_KM); }
    public static SensorValue totalEvMileageKm() { return readField(TOTAL_EV_MILEAGE_KM); }
    public static SensorValue totalHevMileageKm() { return readField(TOTAL_HEV_MILEAGE_KM); }
    public static SensorValue currentTripMileageKmInt() { return readField(CURRENT_TRIP_MILEAGE_KM_INT); }
    public static SensorValue currentTripMileageKmFloat() { return readField(CURRENT_TRIP_MILEAGE_KM_FLOAT); }
    public static SensorValue tripMileage1Float() { return readField(TRIP_MILEAGE_1_FLOAT); }
    public static SensorValue currentTripElecKwh() { return readField(CURRENT_TRIP_ELEC_KWH); }
    public static SensorValue currentTripFuelL() { return readField(CURRENT_TRIP_FUEL_L); }
    public static SensorValue currentTripDrivingTimeMin() { return readField(CURRENT_TRIP_DRIVING_TIME_MIN); }
    public static SensorValue currentTripDrivingTimeHours() { return readField(CURRENT_TRIP_DRIVING_TIME_HOURS); }
    public static SensorValue currentTripTravelTimeHours() { return readField(CURRENT_TRIP_TRAVEL_TIME_HOURS); }
    public static SensorValue currentTripTravelTimeHourPart() { return readField(CURRENT_TRIP_TRAVEL_TIME_HOUR_PART); }
    public static SensorValue currentTripTravelTimeMinFloat() { return readField(CURRENT_TRIP_TRAVEL_TIME_MIN_FLOAT); }
    public static SensorValue currentTripTravelTimeMinPart() { return readField(CURRENT_TRIP_TRAVEL_TIME_MIN_PART); }

    // --- 仪表与小计 ---
    public static SensorValue instrumentJourneyMileageKm() { return readField(INSTRUMENT_JOURNEY_MILEAGE_KM); }
    public static SensorValue instrumentJourneyTimeHours() { return readField(INSTRUMENT_JOURNEY_TIME_HOURS); }

    // --- 车身与门窗 ---
    public static SensorValue hood() { return readField(HOOD); }
    public static SensorValue trunk() { return readField(TRUNK); }
    public static SensorValue fuelCap() { return readField(FUEL_CAP); }
    public static SensorValue doorFL() { return readField(DOOR_FL); }
    public static SensorValue doorFR() { return readField(DOOR_FR); }
    public static SensorValue doorRL() { return readField(DOOR_RL); }
    public static SensorValue doorRR() { return readField(DOOR_RR); }
    public static SensorValue windowFL() { return readField(WINDOW_FL); }
    public static SensorValue windowFR() { return readField(WINDOW_FR); }
    public static SensorValue windowRL() { return readField(WINDOW_RL); }
    public static SensorValue windowRR() { return readField(WINDOW_RR); }
    public static SensorValue lockFL() { return readField(LOCK_FL); }
    public static SensorValue lockFR() { return readField(LOCK_FR); }
    public static SensorValue lockRL() { return readField(LOCK_RL); }
    public static SensorValue lockRR() { return readField(LOCK_RR); }

    // --- 灯光与信号 ---
    public static SensorValue lightSide() { return readField(LIGHT_SIDE); }
    public static SensorValue lightLow() { return readField(LIGHT_LOW); }
    public static SensorValue lightHigh() { return readField(LIGHT_HIGH); }
    public static SensorValue lightFrontFog() { return readField(LIGHT_FRONT_FOG); }
    public static SensorValue lightRearFog() { return readField(LIGHT_REAR_FOG); }
    public static SensorValue hazardLights() { return readField(HAZARD_LIGHTS); }
    public static SensorValue turnSignalLeft() { return readField(TURN_SIGNAL_LEFT); }
    public static SensorValue turnSignalRight() { return readField(TURN_SIGNAL_RIGHT); }
    public static SensorValue turnSignalFlash() { return readField(TURN_SIGNAL_FLASH); }
    public static SensorValue drl() { return readField(DRL); }

    // --- 空调与环境 ---
    public static SensorValue acStatus() { return readField(AC_STATUS); }
    public static SensorValue acTemp() { return readField(AC_TEMP); }
    public static SensorValue fanLevel() { return readField(FAN_LEVEL); }
    public static SensorValue acCirc() { return readField(AC_CIRC); }
    public static SensorValue insideTemp() { return readField(INSIDE_TEMP); }
    public static SensorValue exteriorTemp() { return readField(EXTERIOR_TEMP); }

    // --- 轮胎压力 ---
    public static SensorValue tirePressFL() { return readField(TIRE_PRESS_FL); }
    public static SensorValue tirePressFR() { return readField(TIRE_PRESS_FR); }
    public static SensorValue tirePressRL() { return readField(TIRE_PRESS_RL); }
    public static SensorValue tirePressRR() { return readField(TIRE_PRESS_RR); }

    // --- 安全带 ---
    public static SensorValue seatbeltFL() { return readField(SEATBELT_FL); }
    public static SensorValue seatbeltFR() { return readField(SEATBELT_FR); }
    public static SensorValue seatbeltRL() { return readField(SEATBELT_RL); }
    public static SensorValue seatbeltRR() { return readField(SEATBELT_RR); }

    // --- ADAS辅助驾驶 ---
    public static SensorValue adasAccMode() { return readField(ADAS_ACC_MODE); }
    public static SensorValue adasAccGrayState() { return readField(ADAS_ACC_GRAY_STATE); }
    public static SensorValue adasLccNcaStatus() { return readField(ADAS_LCC_NCA_STATUS); }
    public static SensorValue adasNoaGrayState() { return readField(ADAS_NOA_GRAY_STATE); }
    public static SensorValue adasMnoaStatus() { return readField(ADAS_MNOA_STATUS); }
    public static SensorValue adasExecSpeed() { return readField(ADAS_EXEC_SPEED); }
    public static SensorValue adasTjaState() { return readField(ADAS_TJA_STATE); }
    public static SensorValue laneKeepState() { return readField(LANE_KEEP_STATE); }
    public static SensorValue adasApaParkingStatus() { return readField(ADAS_APA_PARKING_STATUS); }
}
