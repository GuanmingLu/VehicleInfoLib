package me.guanming.vehicle.infolib;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 车辆控制动作写入入口。为全部 138 个车辆控制动作提供类型化的写入方法。
 *
 * <p>每个方法调用 {@link Vehicle#write(int, int, int)} 执行写入，返回 boolean 表示是否成功。
 *
 * <p>使用示例：
 * <pre>
 *   // 无参方法（唯一写入值）
 *   boolean ok = Control.acOn();
 *
 *   // 带参方法（值范围）
 *   boolean ok = Control.acTempMain(24);
 * </pre>
 *
 * <p>{@link #ALL} 暴露全部动作元数据，便于 UI 枚举展示。
 */
public final class Control {

    private Control() {}

    // ===================== 动作元数据 =====================

    /** 描述一个车辆控制动作，用于 UI 枚举与写入。 */
    public static final class Action {
        public final String name;          // action name (e.g., "window_driver_open")
        public final int device;
        public final int fid;
        public final int valueMin;
        public final int valueMax;
        public final String displayName;   // Chinese display name
        public final String category;
        public final String valueHint;     // description of value meaning
        public final String source;        // protocol source

        public Action(String name, int device, int fid, int valueMin, int valueMax,
                      String displayName, String category, String valueHint, String source) {
            this.name = name;
            this.device = device;
            this.fid = fid;
            this.valueMin = valueMin;
            this.valueMax = valueMax;
            this.displayName = displayName;
            this.category = category;
            this.valueHint = valueHint;
            this.source = source == null ? "" : source;
        }

        public String getMetaText() {
            return "dev=" + device + " fid=" + fid
                    + (valueMin == 0 && valueMax == 0 ? "" : " [" + valueMin + "~" + valueMax + "]")
                    + (source.isEmpty() ? "" : " " + source);
        }

        @Override
        public String toString() {
            return "Action{" + name + ": " + displayName + " (" + category + ")}";
        }
    }

    // ===================== 动作常量 =====================

    // --- 空调与除霜 (CLIMATE) ---
    private static final Action AC_ON = new Action("ac_on", 1000, 501219364, 1, 1, "空调-开", "空调与除霜", "1=打开空调", "TM0.d");
    private static final Action AC_OFF = new Action("ac_off", 1000, 501219364, 0, 0, "空调-关", "空调与除霜", "0=关闭空调", "TM0.d");
    private static final Action AC_POWER = new Action("ac_power", 1000, 501219364, 0, 0, "空调-电源", "空调与除霜", "0=电源开关", "competitor");
    private static final Action AC_TEMP_MAIN = new Action("ac_temp_main", 1000, 501219368, 16, 30, "主驾温度", "空调与除霜", "16~30: 温度（℃）", "TM0.d");
    private static final Action AC_TEMP_DEPUTY = new Action("ac_temp_deputy", 1000, 501219376, 16, 30, "副驾温度", "空调与除霜", "16~30: 温度（℃）", "competitor");
    private static final Action SET_DRIVER_TEMP = new Action("set_driver_temp", 1000, 501219368, 16, 30, "主驾温度(别名)", "空调与除霜", "16~30: 温度（℃）", "competitor");
    private static final Action SET_PASSENGER_TEMP = new Action("set_passenger_temp", 1000, 501219376, 16, 30, "副驾温度(别名)", "空调与除霜", "16~30: 温度（℃）", "competitor");
    private static final Action AC_AUTO = new Action("ac_auto", 1000, 501219352, 0, 0, "自动空调", "空调与除霜", "0=自动模式", "TM0.e");
    private static final Action AC_CTRL_MODE = new Action("ac_ctrl_mode", 1000, 501219352, 0, 0, "空调控制模式", "空调与除霜", "0=控制模式", "competitor");
    private static final Action AC_CYCLE_INNER = new Action("ac_cycle_inner", 1000, 501219355, 1, 1, "内循环", "空调与除霜", "1=内循环", "TM0.d");
    private static final Action AC_CYCLE_OUTER = new Action("ac_cycle_outer", 1000, 501219355, 0, 0, "外循环", "空调与除霜", "0=外循环", "TM0.d");
    private static final Action AC_CYCLE_MODE = new Action("ac_cycle_mode", 1000, 501219355, 0, 0, "循环模式", "空调与除霜", "0=循环模式", "competitor");
    private static final Action AC_DUAL = new Action("ac_dual", 1000, 501219346, 0, 0, "双区", "空调与除霜", "0=双区开关", "competitor");
    private static final Action AC_WIND_LEVEL = new Action("ac_wind_level", 1000, 501219340, 1, 7, "风量等级", "空调与除霜", "1~7: 风量档位", "TM0.e");
    private static final Action AC_MAIN_DRIVE_WIND_LEVEL = new Action("ac_main_drive_wind_level", 1000, 1324355600, 1, 7, "主驾风量", "空调与除霜", "1~7: 主驾风量档位", "TM0.e");
    private static final Action AC_PASSENGER_WIND_LEVEL = new Action("ac_passenger_wind_level", 1000, 1324355612, 1, 7, "副驾风量", "空调与除霜", "1~7: 副驾风量档位", "TM0.e");
    private static final Action AC_WIND_MODE = new Action("ac_wind_mode", 1000, 501219336, 0, 0, "风量模式", "空调与除霜", "0=风量模式", "competitor");
    private static final Action AC_FLOW_ONLY_ON = new Action("ac_flow_only_on", 1000, 501219394, 1, 1, "仅通风-开", "空调与除霜", "1=仅通风开", "competitor");
    private static final Action AC_FLOW_ONLY_OFF = new Action("ac_flow_only_off", 1000, 501219394, 0, 0, "仅通风-关", "空调与除霜", "0=仅通风关", "competitor");
    private static final Action DEFROST_FRONT_ON = new Action("defrost_front_on", 1000, 501219362, 1, 1, "前挡除霜-开", "空调与除霜", "1=前挡除霜", "competitor");
    private static final Action DEFROST_FRONT_OFF = new Action("defrost_front_off", 1000, 501219362, 0, 0, "前挡除霜-关", "空调与除霜", "0=关闭前挡除霜", "competitor");
    private static final Action DEFROST_REAR_ON = new Action("defrost_rear_on", 1000, 501219357, 1, 1, "后视镜加热-开", "空调与除霜", "1=后视镜加热", "TM0.d");
    private static final Action DEFROST_REAR_OFF = new Action("defrost_rear_off", 1000, 501219357, 0, 0, "后视镜加热-关", "空调与除霜", "0=关闭后视镜加热", "TM0.d");
    private static final Action AC_DEFROST_FRONT = new Action("ac_defrost_front", 1000, 501219362, 0, 0, "前挡除霜(别名)", "空调与除霜", "0=前挡除霜", "competitor");
    private static final Action AC_DEFROST_REAR = new Action("ac_defrost_rear", 1000, 501219357, 0, 0, "后挡除霜(别名)", "空调与除霜", "0=后挡除霜", "competitor");
    private static final Action STEERING_HEAT_ON = new Action("steering_heat_on", 1000, 944767029, 2, 2, "方向盘加热-开", "空调与除霜", "2=打开", "competitor");
    private static final Action STEERING_HEAT_OFF = new Action("steering_heat_off", 1000, 944767029, 1, 1, "方向盘加热-关", "空调与除霜", "1=关闭", "competitor");
    private static final Action STEERING_HEAT_LVL2 = new Action("steering_heat_lvl2", 1000, 944767029, 3, 3, "方向盘加热-2档", "空调与除霜", "3=2档", "competitor");
    private static final Action WHEEL_HEAT_ON = new Action("wheel_heat_on", 1000, 944767029, 2, 2, "方向盘加热-开(别名)", "空调与除霜", "2=打开", "competitor");
    private static final Action WHEEL_HEAT_OFF = new Action("wheel_heat_off", 1000, 944767029, 1, 1, "方向盘加热-关(别名)", "空调与除霜", "1=关闭", "competitor");
    private static final Action STEERING_WHEEL_HEAT = new Action("steering_wheel_heat", 1000, 944767029, 0, 0, "方向盘加热(基础)", "空调与除霜", "0=基础值", "competitor");

    // --- 车窗 (WINDOWS) ---
    private static final Action WINDOW_DRIVER_OPEN = new Action("window_driver_open", 1001, 1125122104, 1, 1, "主驾窗-开", "车窗", "1=一步打开", "TM0.d");
    private static final Action WINDOW_DRIVER_CLOSE = new Action("window_driver_close", 1001, 1125122104, 2, 2, "主驾窗-关", "车窗", "2=关闭", "TM0.d");
    private static final Action WINDOW_DRIVER_HALF = new Action("window_driver_half", 1001, 1125122104, 4, 4, "主驾窗-半开", "车窗", "4=半开", "competitor");
    private static final Action WINDOW_DRIVER_STOP = new Action("window_driver_stop", 1001, 1125122104, 3, 3, "主驾窗-停止", "车窗", "3=停止", "competitor");
    private static final Action WINDOW_DRIVER_VENT = new Action("window_driver_vent", 1001, 1125122104, 5, 5, "主驾窗-通风", "车窗", "5=通风", "competitor");
    private static final Action WINDOW_DRIVER_POS = new Action("window_driver_pos", 1001, 1276219408, 0, 100, "主驾窗位置", "车窗", "0~100: 位置百分比（0=关, 100=全开）", "TM0.d");
    private static final Action WINDOW_PASSENGER_OPEN = new Action("window_passenger_open", 1001, 1125122107, 1, 1, "副驾窗-开", "车窗", "1=一步打开", "TM0.d");
    private static final Action WINDOW_PASSENGER_CLOSE = new Action("window_passenger_close", 1001, 1125122107, 2, 2, "副驾窗-关", "车窗", "2=关闭", "TM0.d");
    private static final Action WINDOW_PASSENGER_HALF = new Action("window_passenger_half", 1001, 1125122107, 4, 4, "副驾窗-半开", "车窗", "4=半开", "competitor");
    private static final Action WINDOW_PASSENGER_STOP = new Action("window_passenger_stop", 1001, 1125122107, 3, 3, "副驾窗-停止", "车窗", "3=停止", "competitor");
    private static final Action WINDOW_PASSENGER_VENT = new Action("window_passenger_vent", 1001, 1125122107, 5, 5, "副驾窗-通风", "车窗", "5=通风", "competitor");
    private static final Action WINDOW_PASSENGER_POS = new Action("window_passenger_pos", 1001, 1276219424, 0, 100, "副驾窗位置", "车窗", "0~100: 位置百分比", "TM0.d");
    private static final Action WINDOW_REAR_LEFT_OPEN = new Action("window_rear_left_open", 1001, 1125122112, 1, 1, "后左窗-开", "车窗", "1=一步打开", "competitor");
    private static final Action WINDOW_REAR_LEFT_CLOSE = new Action("window_rear_left_close", 1001, 1125122112, 2, 2, "后左窗-关", "车窗", "2=关闭", "competitor");
    private static final Action WINDOW_REAR_LEFT_HALF = new Action("window_rear_left_half", 1001, 1125122112, 4, 4, "后左窗-半开", "车窗", "4=半开", "competitor");
    private static final Action WINDOW_REAR_LEFT_STOP = new Action("window_rear_left_stop", 1001, 1125122112, 3, 3, "后左窗-停止", "车窗", "3=停止", "competitor");
    private static final Action WINDOW_REAR_LEFT_VENT = new Action("window_rear_left_vent", 1001, 1125122112, 5, 5, "后左窗-通风", "车窗", "5=通风", "competitor");
    private static final Action WINDOW_REAR_LEFT_POS = new Action("window_rear_left_pos", 1001, 1276219416, 0, 100, "后左窗位置", "车窗", "0~100: 位置百分比", "TM0.d");
    private static final Action WINDOW_REAR_RIGHT_OPEN = new Action("window_rear_right_open", 1001, 1125122115, 1, 1, "后右窗-开", "车窗", "1=一步打开", "competitor");
    private static final Action WINDOW_REAR_RIGHT_CLOSE = new Action("window_rear_right_close", 1001, 1125122115, 2, 2, "后右窗-关", "车窗", "2=关闭", "competitor");
    private static final Action WINDOW_REAR_RIGHT_HALF = new Action("window_rear_right_half", 1001, 1125122115, 4, 4, "后右窗-半开", "车窗", "4=半开", "competitor");
    private static final Action WINDOW_REAR_RIGHT_STOP = new Action("window_rear_right_stop", 1001, 1125122115, 3, 3, "后右窗-停止", "车窗", "3=停止", "competitor");
    private static final Action WINDOW_REAR_RIGHT_VENT = new Action("window_rear_right_vent", 1001, 1125122115, 5, 5, "后右窗-通风", "车窗", "5=通风", "competitor");
    private static final Action WINDOW_REAR_RIGHT_POS = new Action("window_rear_right_pos", 1001, 1276219432, 0, 100, "后右窗位置", "车窗", "0~100: 位置百分比", "TM0.d");
    private static final Action WINDOWS_OPEN = new Action("windows_open", 1001, 1125122104, 1, 1, "全车窗-开", "车窗", "1=全车打开", "competitor");
    private static final Action WINDOWS_CLOSE = new Action("windows_close", 1001, 1125122104, 2, 2, "全车窗-关", "车窗", "2=全车关闭", "competitor");
    private static final Action WINDOWS_HALF = new Action("windows_half", 1001, 1125122104, 4, 4, "全车窗-半开", "车窗", "4=半开", "competitor");
    private static final Action WINDOWS_VENT = new Action("windows_vent", 1001, 1125122104, 5, 5, "全车窗-通风", "车窗", "5=通风", "competitor");
    private static final Action OPEN_WINDOWS = new Action("open_windows", 1001, 1125122104, 1, 1, "全车窗-开(旧)", "车窗", "1=打开", "competitor");
    private static final Action CLOSE_WINDOWS = new Action("close_windows", 1001, 1125122104, 2, 2, "全车窗-关(旧)", "车窗", "2=关闭", "competitor");

    // --- 天窗 (SUNROOF) ---
    private static final Action SUNROOF_OPEN = new Action("sunroof_open", 1001, 1125122056, 1, 1, "天窗-全开", "天窗", "1=全开", "TM0.d");
    private static final Action SUNROOF_CLOSE = new Action("sunroof_close", 1001, 1125122056, 2, 2, "天窗-关闭", "天窗", "2=关闭", "TM0.d");
    private static final Action SUNROOF_TILT = new Action("sunroof_tilt", 1001, 1125122056, 3, 3, "天窗-翘起", "天窗", "3=翘起", "TM0.d");
    private static final Action SUNROOF_STOP = new Action("sunroof_stop", 1001, 1125122056, 4, 4, "天窗-停止", "天窗", "4=停止", "TM0.d");
    private static final Action SUNROOF_UPDIP = new Action("sunroof_updip", 1001, 1125122056, 5, 5, "天窗-上掀", "天窗", "5=上掀", "TM0.d");
    private static final Action SUNROOF_COMFORT = new Action("sunroof_comfort", 1001, 1125122056, 6, 6, "天窗-舒适位", "天窗", "6=舒适位置", "TM0.d");
    private static final Action OPEN_SUNROOF = new Action("open_sunroof", 1001, 1125122056, 1, 1, "天窗-开(别名)", "天窗", "1=打开", "competitor");
    private static final Action CLOSE_SUNROOF = new Action("close_sunroof", 1001, 1125122056, 2, 2, "天窗-关(别名)", "天窗", "2=关闭", "competitor");
    private static final Action TILT_SUNROOF = new Action("tilt_sunroof", 1001, 1125122056, 5, 5, "天窗-翘起(别名)", "天窗", "5=上掀", "competitor");
    private static final Action COMFORT_SUNROOF = new Action("comfort_sunroof", 1001, 1125122056, 6, 6, "天窗-舒适(别名)", "天窗", "6=舒适位置", "competitor");

    // --- 遮阳帘 (SUNSHADE) ---
    private static final Action SUNSHADE_OPEN = new Action("sunshade_open", 1001, 1125122060, 1, 1, "遮阳帘-开", "遮阳帘", "1=打开", "TM0.d");
    private static final Action SUNSHADE_CLOSE = new Action("sunshade_close", 1001, 1125122060, 2, 2, "遮阳帘-关", "遮阳帘", "2=关闭", "TM0.d");
    private static final Action OPEN_SUNSHADE = new Action("open_sunshade", 1001, 1125122060, 1, 1, "遮阳帘-开(别名)", "遮阳帘", "1=打开", "competitor");
    private static final Action CLOSE_SUNSHADE = new Action("close_sunshade", 1001, 1125122060, 2, 2, "遮阳帘-关(别名)", "遮阳帘", "2=关闭", "competitor");
    private static final Action SUNSHADE_SET_POSITION = new Action("sunshade_set_position", 1001, 1330642984, 0, 100, "遮阳帘位置", "遮阳帘", "0~100: 位置百分比", "competitor");
    private static final Action SET_SUNSHADE_POSITION = new Action("set_sunshade_position", 1001, 1330642984, 0, 100, "遮阳帘位置(别名)", "遮阳帘", "0~100: 位置百分比", "competitor");

    // --- 门锁 (LOCKS) ---
    private static final Action DOORS_UNLOCK = new Action("doors_unlock", 1001, 1276141590, 1, 1, "车门-解锁", "门锁", "1=解锁", "TM0.d");
    private static final Action DOORS_LOCK = new Action("doors_lock", 1001, 1276141590, 2, 2, "车门-上锁", "门锁", "2=上锁", "TM0.d");
    private static final Action UNLOCK_DOORS = new Action("unlock_doors", 1001, 1276141590, 1, 1, "车门-解锁(别名)", "门锁", "1=解锁", "competitor");
    private static final Action LOCK_DOORS = new Action("lock_doors", 1001, 1276141590, 2, 2, "车门-上锁(别名)", "门锁", "2=上锁", "competitor");

    // --- 后备箱 (TRUNK) ---
    private static final Action OPEN_TRUNK = new Action("open_trunk", 1001, 1125122080, 1, 1, "后备箱-开", "后备箱", "1=打开", "competitor");
    private static final Action CLOSE_TRUNK = new Action("close_trunk", 1001, 1125122080, 3, 3, "后备箱-关", "后备箱", "3=关闭", "competitor");
    private static final Action TRUNK_OPEN = new Action("trunk_open", 1001, 1125122080, 1, 1, "后备箱-开(别名)", "后备箱", "1=打开", "competitor");
    private static final Action TRUNK_CLOSE = new Action("trunk_close", 1001, 1125122080, 3, 3, "后备箱-关(别名)", "后备箱", "3=关闭", "competitor");

    // --- 灯光 (LIGHTS) ---
    private static final Action INTERIOR_LIGHT_ON = new Action("interior_light_on", 1023, 1330643002, 2, 2, "车内灯-开", "灯光", "2=打开", "TM0.d (blacklisted)");
    private static final Action INTERIOR_LIGHT_OFF = new Action("interior_light_off", 1023, 1330643002, 1, 1, "车内灯-关", "灯光", "1=关闭", "TM0.d (blacklisted)");
    private static final Action AMBIENT_LIGHT_ON = new Action("ambient_light_on", 1023, 1069547536, 5, 5, "氛围灯-开", "灯光", "5=打开", "TM0.d (blacklisted)");
    private static final Action AMBIENT_LIGHT_OFF = new Action("ambient_light_off", 1023, 1069547536, 1, 1, "氛围灯-关", "灯光", "1=关闭", "TM0.d (blacklisted)");
    private static final Action DRL_ON = new Action("drl_on", 1004, 1125122118, 1, 1, "日行灯-开", "灯光", "1=打开", "TM0.d (blacklisted)");
    private static final Action DRL_OFF = new Action("drl_off", 1004, 1125122118, 2, 2, "日行灯-关", "灯光", "2=关闭", "TM0.d (blacklisted)");
    private static final Action DRL_AUTO = new Action("drl_auto", 1004, 1125122118, 0, 0, "日行灯-自动", "灯光", "0=自动", "competitor");

    // --- 座椅 (SEATS) ---
    private static final Action DRIVER_SEAT_HEAT_ON = new Action("driver_seat_heat_on", 1001, 1125122068, 2, 2, "主驾加热-开", "座椅", "2=打开", "competitor");
    private static final Action DRIVER_SEAT_HEAT_OFF = new Action("driver_seat_heat_off", 1001, 1125122068, 1, 1, "主驾加热-关", "座椅", "1=关闭", "competitor");
    private static final Action DRIVER_SEAT_HEAT = new Action("driver_seat_heat", 1001, 1125122068, 0, 0, "主驾加热(基础)", "座椅", "0=基础值", "competitor");
    private static final Action DRIVER_SEAT_HEAT_LVL2 = new Action("driver_seat_heat_lvl2", 1001, 1125122068, 3, 3, "主驾加热-2档", "座椅", "3=2档", "competitor");
    private static final Action DRIVER_SEAT_HEAT_LVL3 = new Action("driver_seat_heat_lvl3", 1001, 1125122068, 4, 4, "主驾加热-3档", "座椅", "4=3档", "competitor");
    private static final Action DRIVER_SEAT_HEAT_LVL4 = new Action("driver_seat_heat_lvl4", 1001, 1125122068, 5, 5, "主驾加热-4档", "座椅", "5=4档", "competitor");
    private static final Action DRIVER_SEAT_HEAT_LVL5 = new Action("driver_seat_heat_lvl5", 1001, 1125122068, 6, 6, "主驾加热-5档", "座椅", "6=5档", "competitor");
    private static final Action DRIVER_SEAT_VENT_ON = new Action("driver_seat_vent_on", 1001, 1125122064, 2, 2, "主驾通风-开", "座椅", "2=打开", "competitor");
    private static final Action DRIVER_SEAT_VENT_OFF = new Action("driver_seat_vent_off", 1001, 1125122064, 1, 1, "主驾通风-关", "座椅", "1=关闭", "competitor");
    private static final Action DRIVER_SEAT_VENT = new Action("driver_seat_vent", 1001, 1125122064, 0, 0, "主驾通风(基础)", "座椅", "0=基础值", "competitor");
    private static final Action DRIVER_SEAT_VENT_LVL2 = new Action("driver_seat_vent_lvl2", 1001, 1125122064, 3, 3, "主驾通风-2档", "座椅", "3=2档", "competitor");
    private static final Action DRIVER_SEAT_VENT_LVL3 = new Action("driver_seat_vent_lvl3", 1001, 1125122064, 4, 4, "主驾通风-3档", "座椅", "4=3档", "competitor");
    private static final Action DRIVER_SEAT_VENT_LVL4 = new Action("driver_seat_vent_lvl4", 1001, 1125122064, 5, 5, "主驾通风-4档", "座椅", "5=4档", "competitor");
    private static final Action DRIVER_SEAT_VENT_LVL5 = new Action("driver_seat_vent_lvl5", 1001, 1125122064, 6, 6, "主驾通风-5档", "座椅", "6=5档", "competitor");
    private static final Action PASSENGER_SEAT_HEAT_ON = new Action("passenger_seat_heat_on", 1001, 1125122076, 2, 2, "副驾加热-开", "座椅", "2=打开", "competitor");
    private static final Action PASSENGER_SEAT_HEAT_OFF = new Action("passenger_seat_heat_off", 1001, 1125122076, 1, 1, "副驾加热-关", "座椅", "1=关闭", "competitor");
    private static final Action PASSENGER_SEAT_HEAT = new Action("passenger_seat_heat", 1001, 1125122076, 0, 0, "副驾加热(基础)", "座椅", "0=基础值", "competitor");
    private static final Action PASSENGER_SEAT_HEAT_LVL2 = new Action("passenger_seat_heat_lvl2", 1001, 1125122076, 3, 3, "副驾加热-2档", "座椅", "3=2档", "competitor");
    private static final Action PASSENGER_SEAT_HEAT_LVL3 = new Action("passenger_seat_heat_lvl3", 1001, 1125122076, 4, 4, "副驾加热-3档", "座椅", "4=3档", "competitor");
    private static final Action PASSENGER_SEAT_HEAT_LVL4 = new Action("passenger_seat_heat_lvl4", 1001, 1125122076, 5, 5, "副驾加热-4档", "座椅", "5=4档", "competitor");
    private static final Action PASSENGER_SEAT_HEAT_LVL5 = new Action("passenger_seat_heat_lvl5", 1001, 1125122076, 6, 6, "副驾加热-5档", "座椅", "6=5档", "competitor");
    private static final Action PASSENGER_SEAT_VENT_ON = new Action("passenger_seat_vent_on", 1001, 1125122072, 2, 2, "副驾通风-开", "座椅", "2=打开", "competitor");
    private static final Action PASSENGER_SEAT_VENT_OFF = new Action("passenger_seat_vent_off", 1001, 1125122072, 1, 1, "副驾通风-关", "座椅", "1=关闭", "competitor");
    private static final Action PASSENGER_SEAT_VENT = new Action("passenger_seat_vent", 1001, 1125122072, 0, 0, "副驾通风(基础)", "座椅", "0=基础值", "competitor");
    private static final Action PASSENGER_SEAT_VENT_LVL2 = new Action("passenger_seat_vent_lvl2", 1001, 1125122072, 3, 3, "副驾通风-2档", "座椅", "3=2档", "competitor");
    private static final Action PASSENGER_SEAT_VENT_LVL3 = new Action("passenger_seat_vent_lvl3", 1001, 1125122072, 4, 4, "副驾通风-3档", "座椅", "4=3档", "competitor");
    private static final Action PASSENGER_SEAT_VENT_LVL4 = new Action("passenger_seat_vent_lvl4", 1001, 1125122072, 5, 5, "副驾通风-4档", "座椅", "5=4档", "competitor");
    private static final Action PASSENGER_SEAT_VENT_LVL5 = new Action("passenger_seat_vent_lvl5", 1001, 1125122072, 6, 6, "副驾通风-5档", "座椅", "6=5档", "competitor");

    // --- 按摩 (MASSAGE) ---
    private static final Action DRIVER_MASSAGE_LVL1 = new Action("driver_massage_lvl1", 1023, 1276190736, 1, 1, "主驾按摩-1档", "按摩", "1=1档强度", "competitor");
    private static final Action DRIVER_MASSAGE_LVL2 = new Action("driver_massage_lvl2", 1023, 1276190736, 2, 2, "主驾按摩-2档", "按摩", "2=2档强度", "competitor");
    private static final Action DRIVER_MASSAGE_LVL3 = new Action("driver_massage_lvl3", 1023, 1276190736, 3, 3, "主驾按摩-3档", "按摩", "3=3档强度", "competitor");
    private static final Action DRIVER_MASSAGE_OFF = new Action("driver_massage_off", 1023, 1276190752, 1, 1, "主驾按摩-关", "按摩", "1=关闭", "competitor");
    private static final Action DRIVER_MASSAGE_PULSE = new Action("driver_massage_pulse", 1023, 1276190752, 2, 2, "主驾按摩-脉冲", "按摩", "2=脉冲模式", "competitor");
    private static final Action DRIVER_MASSAGE_SHOULDER = new Action("driver_massage_shoulder", 1023, 1276190752, 3, 3, "主驾按摩-肩部", "按摩", "3=肩部模式", "competitor");
    private static final Action DRIVER_MASSAGE_STRETCH = new Action("driver_massage_stretch", 1023, 1276190752, 4, 4, "主驾按摩-拉伸", "按摩", "4=拉伸模式", "competitor");
    private static final Action DRIVER_MASSAGE_WAIST = new Action("driver_massage_waist", 1023, 1276190752, 5, 5, "主驾按摩-腰部", "按摩", "5=腰部模式", "competitor");
    private static final Action DRIVER_MASSAGE_WAVE = new Action("driver_massage_wave", 1023, 1276190752, 6, 6, "主驾按摩-波浪", "按摩", "6=波浪模式", "competitor");
    private static final Action PASSENGER_MASSAGE_LVL1 = new Action("passenger_massage_lvl1", 1023, 1276190739, 1, 1, "副驾按摩-1档", "按摩", "1=1档强度", "competitor");
    private static final Action PASSENGER_MASSAGE_LVL2 = new Action("passenger_massage_lvl2", 1023, 1276190739, 2, 2, "副驾按摩-2档", "按摩", "2=2档强度", "competitor");
    private static final Action PASSENGER_MASSAGE_LVL3 = new Action("passenger_massage_lvl3", 1023, 1276190739, 3, 3, "副驾按摩-3档", "按摩", "3=3档强度", "competitor");
    private static final Action PASSENGER_MASSAGE_OFF = new Action("passenger_massage_off", 1023, 1276190756, 1, 1, "副驾按摩-关", "按摩", "1=关闭", "competitor");
    private static final Action PASSENGER_MASSAGE_PULSE = new Action("passenger_massage_pulse", 1023, 1276190756, 2, 2, "副驾按摩-脉冲", "按摩", "2=脉冲模式", "competitor");
    private static final Action PASSENGER_MASSAGE_SHOULDER = new Action("passenger_massage_shoulder", 1023, 1276190756, 3, 3, "副驾按摩-肩部", "按摩", "3=肩部模式", "competitor");
    private static final Action PASSENGER_MASSAGE_STRETCH = new Action("passenger_massage_stretch", 1023, 1276190756, 4, 4, "副驾按摩-拉伸", "按摩", "4=拉伸模式", "competitor");
    private static final Action PASSENGER_MASSAGE_WAIST = new Action("passenger_massage_waist", 1023, 1276190756, 5, 5, "副驾按摩-腰部", "按摩", "5=腰部模式", "competitor");
    private static final Action PASSENGER_MASSAGE_WAVE = new Action("passenger_massage_wave", 1023, 1276190756, 6, 6, "副驾按摩-波浪", "按摩", "6=波浪模式", "competitor");

    // ===================== 全部动作 =====================

    /** 全部 138 个动作，按定义顺序排列。 */
    public static final List<Action> ALL = Collections.unmodifiableList(Arrays.asList(
            // 空调与除霜
            AC_ON, AC_OFF, AC_POWER, AC_TEMP_MAIN, AC_TEMP_DEPUTY,
            SET_DRIVER_TEMP, SET_PASSENGER_TEMP, AC_AUTO, AC_CTRL_MODE,
            AC_CYCLE_INNER, AC_CYCLE_OUTER, AC_CYCLE_MODE, AC_DUAL,
            AC_WIND_LEVEL, AC_MAIN_DRIVE_WIND_LEVEL, AC_PASSENGER_WIND_LEVEL,
            AC_WIND_MODE, AC_FLOW_ONLY_ON, AC_FLOW_ONLY_OFF,
            DEFROST_FRONT_ON, DEFROST_FRONT_OFF, DEFROST_REAR_ON, DEFROST_REAR_OFF,
            AC_DEFROST_FRONT, AC_DEFROST_REAR,
            STEERING_HEAT_ON, STEERING_HEAT_OFF, STEERING_HEAT_LVL2,
            WHEEL_HEAT_ON, WHEEL_HEAT_OFF, STEERING_WHEEL_HEAT,
            // 车窗
            WINDOW_DRIVER_OPEN, WINDOW_DRIVER_CLOSE, WINDOW_DRIVER_HALF,
            WINDOW_DRIVER_STOP, WINDOW_DRIVER_VENT, WINDOW_DRIVER_POS,
            WINDOW_PASSENGER_OPEN, WINDOW_PASSENGER_CLOSE, WINDOW_PASSENGER_HALF,
            WINDOW_PASSENGER_STOP, WINDOW_PASSENGER_VENT, WINDOW_PASSENGER_POS,
            WINDOW_REAR_LEFT_OPEN, WINDOW_REAR_LEFT_CLOSE, WINDOW_REAR_LEFT_HALF,
            WINDOW_REAR_LEFT_STOP, WINDOW_REAR_LEFT_VENT, WINDOW_REAR_LEFT_POS,
            WINDOW_REAR_RIGHT_OPEN, WINDOW_REAR_RIGHT_CLOSE, WINDOW_REAR_RIGHT_HALF,
            WINDOW_REAR_RIGHT_STOP, WINDOW_REAR_RIGHT_VENT, WINDOW_REAR_RIGHT_POS,
            WINDOWS_OPEN, WINDOWS_CLOSE, WINDOWS_HALF, WINDOWS_VENT,
            OPEN_WINDOWS, CLOSE_WINDOWS,
            // 天窗
            SUNROOF_OPEN, SUNROOF_CLOSE, SUNROOF_TILT, SUNROOF_STOP,
            SUNROOF_UPDIP, SUNROOF_COMFORT,
            OPEN_SUNROOF, CLOSE_SUNROOF, TILT_SUNROOF, COMFORT_SUNROOF,
            // 遮阳帘
            SUNSHADE_OPEN, SUNSHADE_CLOSE, OPEN_SUNSHADE, CLOSE_SUNSHADE,
            SUNSHADE_SET_POSITION, SET_SUNSHADE_POSITION,
            // 门锁
            DOORS_UNLOCK, DOORS_LOCK, UNLOCK_DOORS, LOCK_DOORS,
            // 后备箱
            OPEN_TRUNK, CLOSE_TRUNK, TRUNK_OPEN, TRUNK_CLOSE,
            // 灯光
            INTERIOR_LIGHT_ON, INTERIOR_LIGHT_OFF,
            AMBIENT_LIGHT_ON, AMBIENT_LIGHT_OFF,
            DRL_ON, DRL_OFF, DRL_AUTO,
            // 座椅
            DRIVER_SEAT_HEAT_ON, DRIVER_SEAT_HEAT_OFF, DRIVER_SEAT_HEAT,
            DRIVER_SEAT_HEAT_LVL2, DRIVER_SEAT_HEAT_LVL3, DRIVER_SEAT_HEAT_LVL4,
            DRIVER_SEAT_HEAT_LVL5,
            DRIVER_SEAT_VENT_ON, DRIVER_SEAT_VENT_OFF, DRIVER_SEAT_VENT,
            DRIVER_SEAT_VENT_LVL2, DRIVER_SEAT_VENT_LVL3, DRIVER_SEAT_VENT_LVL4,
            DRIVER_SEAT_VENT_LVL5,
            PASSENGER_SEAT_HEAT_ON, PASSENGER_SEAT_HEAT_OFF, PASSENGER_SEAT_HEAT,
            PASSENGER_SEAT_HEAT_LVL2, PASSENGER_SEAT_HEAT_LVL3, PASSENGER_SEAT_HEAT_LVL4,
            PASSENGER_SEAT_HEAT_LVL5,
            PASSENGER_SEAT_VENT_ON, PASSENGER_SEAT_VENT_OFF, PASSENGER_SEAT_VENT,
            PASSENGER_SEAT_VENT_LVL2, PASSENGER_SEAT_VENT_LVL3, PASSENGER_SEAT_VENT_LVL4,
            PASSENGER_SEAT_VENT_LVL5,
            // 按摩
            DRIVER_MASSAGE_LVL1, DRIVER_MASSAGE_LVL2, DRIVER_MASSAGE_LVL3,
            DRIVER_MASSAGE_OFF, DRIVER_MASSAGE_PULSE, DRIVER_MASSAGE_SHOULDER,
            DRIVER_MASSAGE_STRETCH, DRIVER_MASSAGE_WAIST, DRIVER_MASSAGE_WAVE,
            PASSENGER_MASSAGE_LVL1, PASSENGER_MASSAGE_LVL2, PASSENGER_MASSAGE_LVL3,
            PASSENGER_MASSAGE_OFF, PASSENGER_MASSAGE_PULSE, PASSENGER_MASSAGE_SHOULDER,
            PASSENGER_MASSAGE_STRETCH, PASSENGER_MASSAGE_WAIST, PASSENGER_MASSAGE_WAVE
    ));

    // ===================== 单动作写入方法 =====================

    // --- 空调与除霜 ---
    public static boolean acOn() { return Vehicle.write(1000, 501219364, 1); }
    public static boolean acOff() { return Vehicle.write(1000, 501219364, 0); }
    public static boolean acPower() { return Vehicle.write(1000, 501219364, 0); }
    public static boolean acTempMain(int value) { return Vehicle.write(1000, 501219368, value); }
    public static boolean acTempDeputy(int value) { return Vehicle.write(1000, 501219376, value); }
    public static boolean setDriverTemp(int value) { return Vehicle.write(1000, 501219368, value); }
    public static boolean setPassengerTemp(int value) { return Vehicle.write(1000, 501219376, value); }
    public static boolean acAuto() { return Vehicle.write(1000, 501219352, 0); }
    public static boolean acCtrlMode() { return Vehicle.write(1000, 501219352, 0); }
    public static boolean acCycleInner() { return Vehicle.write(1000, 501219355, 1); }
    public static boolean acCycleOuter() { return Vehicle.write(1000, 501219355, 0); }
    public static boolean acCycleMode() { return Vehicle.write(1000, 501219355, 0); }
    public static boolean acDual() { return Vehicle.write(1000, 501219346, 0); }
    public static boolean acWindLevel(int value) { return Vehicle.write(1000, 501219340, value); }
    public static boolean acMainDriveWindLevel(int value) { return Vehicle.write(1000, 1324355600, value); }
    public static boolean acPassengerWindLevel(int value) { return Vehicle.write(1000, 1324355612, value); }
    public static boolean acWindMode() { return Vehicle.write(1000, 501219336, 0); }
    public static boolean acFlowOnlyOn() { return Vehicle.write(1000, 501219394, 1); }
    public static boolean acFlowOnlyOff() { return Vehicle.write(1000, 501219394, 0); }
    public static boolean defrostFrontOn() { return Vehicle.write(1000, 501219362, 1); }
    public static boolean defrostFrontOff() { return Vehicle.write(1000, 501219362, 0); }
    public static boolean defrostRearOn() { return Vehicle.write(1000, 501219357, 1); }
    public static boolean defrostRearOff() { return Vehicle.write(1000, 501219357, 0); }
    public static boolean acDefrostFront() { return Vehicle.write(1000, 501219362, 0); }
    public static boolean acDefrostRear() { return Vehicle.write(1000, 501219357, 0); }
    public static boolean steeringHeatOn() { return Vehicle.write(1000, 944767029, 2); }
    public static boolean steeringHeatOff() { return Vehicle.write(1000, 944767029, 1); }
    public static boolean steeringHeatLvl2() { return Vehicle.write(1000, 944767029, 3); }
    public static boolean wheelHeatOn() { return Vehicle.write(1000, 944767029, 2); }
    public static boolean wheelHeatOff() { return Vehicle.write(1000, 944767029, 1); }
    public static boolean steeringWheelHeat() { return Vehicle.write(1000, 944767029, 0); }

    // --- 车窗 ---
    public static boolean windowDriverOpen() { return Vehicle.write(1001, 1125122104, 1); }
    public static boolean windowDriverClose() { return Vehicle.write(1001, 1125122104, 2); }
    public static boolean windowDriverHalf() { return Vehicle.write(1001, 1125122104, 4); }
    public static boolean windowDriverStop() { return Vehicle.write(1001, 1125122104, 3); }
    public static boolean windowDriverVent() { return Vehicle.write(1001, 1125122104, 5); }
    public static boolean windowDriverPos(int value) { return Vehicle.write(1001, 1276219408, value); }
    public static boolean windowPassengerOpen() { return Vehicle.write(1001, 1125122107, 1); }
    public static boolean windowPassengerClose() { return Vehicle.write(1001, 1125122107, 2); }
    public static boolean windowPassengerHalf() { return Vehicle.write(1001, 1125122107, 4); }
    public static boolean windowPassengerStop() { return Vehicle.write(1001, 1125122107, 3); }
    public static boolean windowPassengerVent() { return Vehicle.write(1001, 1125122107, 5); }
    public static boolean windowPassengerPos(int value) { return Vehicle.write(1001, 1276219424, value); }
    public static boolean windowRearLeftOpen() { return Vehicle.write(1001, 1125122112, 1); }
    public static boolean windowRearLeftClose() { return Vehicle.write(1001, 1125122112, 2); }
    public static boolean windowRearLeftHalf() { return Vehicle.write(1001, 1125122112, 4); }
    public static boolean windowRearLeftStop() { return Vehicle.write(1001, 1125122112, 3); }
    public static boolean windowRearLeftVent() { return Vehicle.write(1001, 1125122112, 5); }
    public static boolean windowRearLeftPos(int value) { return Vehicle.write(1001, 1276219416, value); }
    public static boolean windowRearRightOpen() { return Vehicle.write(1001, 1125122115, 1); }
    public static boolean windowRearRightClose() { return Vehicle.write(1001, 1125122115, 2); }
    public static boolean windowRearRightHalf() { return Vehicle.write(1001, 1125122115, 4); }
    public static boolean windowRearRightStop() { return Vehicle.write(1001, 1125122115, 3); }
    public static boolean windowRearRightVent() { return Vehicle.write(1001, 1125122115, 5); }
    public static boolean windowRearRightPos(int value) { return Vehicle.write(1001, 1276219432, value); }
    public static boolean windowsOpen() { return Vehicle.write(1001, 1125122104, 1); }
    public static boolean windowsClose() { return Vehicle.write(1001, 1125122104, 2); }
    public static boolean windowsHalf() { return Vehicle.write(1001, 1125122104, 4); }
    public static boolean windowsVent() { return Vehicle.write(1001, 1125122104, 5); }
    public static boolean openWindows() { return Vehicle.write(1001, 1125122104, 1); }
    public static boolean closeWindows() { return Vehicle.write(1001, 1125122104, 2); }

    // --- 天窗 ---
    public static boolean sunroofOpen() { return Vehicle.write(1001, 1125122056, 1); }
    public static boolean sunroofClose() { return Vehicle.write(1001, 1125122056, 2); }
    public static boolean sunroofTilt() { return Vehicle.write(1001, 1125122056, 3); }
    public static boolean sunroofStop() { return Vehicle.write(1001, 1125122056, 4); }
    public static boolean sunroofUpdip() { return Vehicle.write(1001, 1125122056, 5); }
    public static boolean sunroofComfort() { return Vehicle.write(1001, 1125122056, 6); }
    public static boolean openSunroof() { return Vehicle.write(1001, 1125122056, 1); }
    public static boolean closeSunroof() { return Vehicle.write(1001, 1125122056, 2); }
    public static boolean tiltSunroof() { return Vehicle.write(1001, 1125122056, 5); }
    public static boolean comfortSunroof() { return Vehicle.write(1001, 1125122056, 6); }

    // --- 遮阳帘 ---
    public static boolean sunshadeOpen() { return Vehicle.write(1001, 1125122060, 1); }
    public static boolean sunshadeClose() { return Vehicle.write(1001, 1125122060, 2); }
    public static boolean openSunshade() { return Vehicle.write(1001, 1125122060, 1); }
    public static boolean closeSunshade() { return Vehicle.write(1001, 1125122060, 2); }
    public static boolean sunshadeSetPosition(int value) { return Vehicle.write(1001, 1330642984, value); }
    public static boolean setSunshadePosition(int value) { return Vehicle.write(1001, 1330642984, value); }

    // --- 门锁 ---
    public static boolean doorsUnlock() { return Vehicle.write(1001, 1276141590, 1); }
    public static boolean doorsLock() { return Vehicle.write(1001, 1276141590, 2); }
    public static boolean unlockDoors() { return Vehicle.write(1001, 1276141590, 1); }
    public static boolean lockDoors() { return Vehicle.write(1001, 1276141590, 2); }

    // --- 后备箱 ---
    public static boolean openTrunk() { return Vehicle.write(1001, 1125122080, 1); }
    public static boolean closeTrunk() { return Vehicle.write(1001, 1125122080, 3); }
    public static boolean trunkOpen() { return Vehicle.write(1001, 1125122080, 1); }
    public static boolean trunkClose() { return Vehicle.write(1001, 1125122080, 3); }

    // --- 灯光 ---
    public static boolean interiorLightOn() { return Vehicle.write(1023, 1330643002, 2); }
    public static boolean interiorLightOff() { return Vehicle.write(1023, 1330643002, 1); }
    public static boolean ambientLightOn() { return Vehicle.write(1023, 1069547536, 5); }
    public static boolean ambientLightOff() { return Vehicle.write(1023, 1069547536, 1); }
    public static boolean drlOn() { return Vehicle.write(1004, 1125122118, 1); }
    public static boolean drlOff() { return Vehicle.write(1004, 1125122118, 2); }
    public static boolean drlAuto() { return Vehicle.write(1004, 1125122118, 0); }

    // --- 座椅 ---
    public static boolean driverSeatHeatOn() { return Vehicle.write(1001, 1125122068, 2); }
    public static boolean driverSeatHeatOff() { return Vehicle.write(1001, 1125122068, 1); }
    public static boolean driverSeatHeat() { return Vehicle.write(1001, 1125122068, 0); }
    public static boolean driverSeatHeatLvl2() { return Vehicle.write(1001, 1125122068, 3); }
    public static boolean driverSeatHeatLvl3() { return Vehicle.write(1001, 1125122068, 4); }
    public static boolean driverSeatHeatLvl4() { return Vehicle.write(1001, 1125122068, 5); }
    public static boolean driverSeatHeatLvl5() { return Vehicle.write(1001, 1125122068, 6); }
    public static boolean driverSeatVentOn() { return Vehicle.write(1001, 1125122064, 2); }
    public static boolean driverSeatVentOff() { return Vehicle.write(1001, 1125122064, 1); }
    public static boolean driverSeatVent() { return Vehicle.write(1001, 1125122064, 0); }
    public static boolean driverSeatVentLvl2() { return Vehicle.write(1001, 1125122064, 3); }
    public static boolean driverSeatVentLvl3() { return Vehicle.write(1001, 1125122064, 4); }
    public static boolean driverSeatVentLvl4() { return Vehicle.write(1001, 1125122064, 5); }
    public static boolean driverSeatVentLvl5() { return Vehicle.write(1001, 1125122064, 6); }
    public static boolean passengerSeatHeatOn() { return Vehicle.write(1001, 1125122076, 2); }
    public static boolean passengerSeatHeatOff() { return Vehicle.write(1001, 1125122076, 1); }
    public static boolean passengerSeatHeat() { return Vehicle.write(1001, 1125122076, 0); }
    public static boolean passengerSeatHeatLvl2() { return Vehicle.write(1001, 1125122076, 3); }
    public static boolean passengerSeatHeatLvl3() { return Vehicle.write(1001, 1125122076, 4); }
    public static boolean passengerSeatHeatLvl4() { return Vehicle.write(1001, 1125122076, 5); }
    public static boolean passengerSeatHeatLvl5() { return Vehicle.write(1001, 1125122076, 6); }
    public static boolean passengerSeatVentOn() { return Vehicle.write(1001, 1125122072, 2); }
    public static boolean passengerSeatVentOff() { return Vehicle.write(1001, 1125122072, 1); }
    public static boolean passengerSeatVent() { return Vehicle.write(1001, 1125122072, 0); }
    public static boolean passengerSeatVentLvl2() { return Vehicle.write(1001, 1125122072, 3); }
    public static boolean passengerSeatVentLvl3() { return Vehicle.write(1001, 1125122072, 4); }
    public static boolean passengerSeatVentLvl4() { return Vehicle.write(1001, 1125122072, 5); }
    public static boolean passengerSeatVentLvl5() { return Vehicle.write(1001, 1125122072, 6); }

    // --- 按摩 ---
    public static boolean driverMassageLvl1() { return Vehicle.write(1023, 1276190736, 1); }
    public static boolean driverMassageLvl2() { return Vehicle.write(1023, 1276190736, 2); }
    public static boolean driverMassageLvl3() { return Vehicle.write(1023, 1276190736, 3); }
    public static boolean driverMassageOff() { return Vehicle.write(1023, 1276190752, 1); }
    public static boolean driverMassagePulse() { return Vehicle.write(1023, 1276190752, 2); }
    public static boolean driverMassageShoulder() { return Vehicle.write(1023, 1276190752, 3); }
    public static boolean driverMassageStretch() { return Vehicle.write(1023, 1276190752, 4); }
    public static boolean driverMassageWaist() { return Vehicle.write(1023, 1276190752, 5); }
    public static boolean driverMassageWave() { return Vehicle.write(1023, 1276190752, 6); }
    public static boolean passengerMassageLvl1() { return Vehicle.write(1023, 1276190739, 1); }
    public static boolean passengerMassageLvl2() { return Vehicle.write(1023, 1276190739, 2); }
    public static boolean passengerMassageLvl3() { return Vehicle.write(1023, 1276190739, 3); }
    public static boolean passengerMassageOff() { return Vehicle.write(1023, 1276190756, 1); }
    public static boolean passengerMassagePulse() { return Vehicle.write(1023, 1276190756, 2); }
    public static boolean passengerMassageShoulder() { return Vehicle.write(1023, 1276190756, 3); }
    public static boolean passengerMassageStretch() { return Vehicle.write(1023, 1276190756, 4); }
    public static boolean passengerMassageWaist() { return Vehicle.write(1023, 1276190756, 5); }
    public static boolean passengerMassageWave() { return Vehicle.write(1023, 1276190756, 6); }
}
