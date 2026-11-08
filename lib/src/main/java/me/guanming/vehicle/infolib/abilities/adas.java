package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** adas — 能力分组容器。 */
public final class adas {
    private adas() {}

    /** 自适应巡航状态（adas.acc_cruise_state）— 由目录自动生成，请勿手改。 */
    public static final class acc_cruise_state {
        private acc_cruise_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 852492340, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "NOT_CRUISING_0" : (r0 == 1 ? "NOT_CRUISING_1" : (r0 == 2 ? "NOT_CRUISING_2" : (r0 == 3 ? "CRUISING_3" : (r0 == 4 ? "CRUISING_4" : (r0 == 5 ? "NOT_CRUISING_5" : (r0 == 6 ? "CRUISING_6" : (r0 == 7 ? "CRUISING_7" : (r0 == 8 ? "NOT_CRUISING_8" : (r0 == 9 ? "NOT_CRUISING_9" : null)))))))))), new int[]{r0}, new String[]{"ADAS_ACC_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.acc_cruise_state with given params");
            }
        }

    }

    /** 自动紧急制动（adas.aeb_state）— 由目录自动生成，请勿手改。 */
    public static final class aeb_state {
        private aeb_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 852492332, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ADAS_AEB_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.aeb_state with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1038, 944767020, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.aeb_state with given params");
            }
        }

    }

    /** 自动驻车（adas.auto_hold_state）— 由目录自动生成，请勿手改。 */
    public static final class auto_hold_state {
        private auto_hold_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 304087110, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ON" : (r0 == 2 ? "HOLDING" : null))), new int[]{r0}, new String[]{"ADAS_AVH_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.auto_hold_state with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1038, 944766986, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.auto_hold_state with given params");
            }
        }

    }

    /** 盲区预警（adas.blind_spot_warning）— 由目录自动生成，请勿手改。 */
    public static final class blind_spot_warning {
        private blind_spot_warning() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 1098907664, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else if (true) {
                int r0 = Runtime.readFid(1038, 1098907666, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.blind_spot_warning with given params");
            }
        }

    }

    /** 制动助力模式（adas.brake_foot_sense）— 由目录自动生成，请勿手改。 */
    public static final class brake_foot_sense {
        private brake_foot_sense() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 305135629, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                if (r0 == 0) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 1 ? "SPORT_OR_STANDARD" : (r0 == 2 ? "COMFORT" : null)), new int[]{r0}, new String[]{"ADAS_CMD_BRAKE_FOOT_SENSE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.brake_foot_sense with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1038, 944766988, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.brake_foot_sense with given params");
            }
        }

    }

    /** 开门预警（adas.door_open_warning）— 由目录自动生成，请勿手改。 */
    public static final class door_open_warning {
        private door_open_warning() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 1098907680, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int r1 = Runtime.readFid(1038, 1098907668, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1}, new String[]{"ADAS_DOW_WARN_LEFT", ""});
            }
            else if (true) {
                int r0 = Runtime.readFid(1038, 1098907682, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int r1 = Runtime.readFid(1038, 1098907669, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1}, new String[]{"ADAS_DOW_WARN_RIGHT", ""});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.door_open_warning with given params");
            }
        }

    }

    /** ESP 车身稳定（adas.electronic_stability_control）— 由目录自动生成，请勿手改。 */
    public static final class electronic_stability_control {
        private electronic_stability_control() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 305135676, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 0) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ADAS_ESP_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.electronic_stability_control with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if ((value == true)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1038, 944766984, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.electronic_stability_control with given params");
            }
        }

    }

    /** 前向碰撞预警（adas.forward_collision_warning）— 由目录自动生成，请勿手改。 */
    public static final class forward_collision_warning {
        private forward_collision_warning() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 327155720, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ADAS_FCW_LEVEL_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.forward_collision_warning with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3, 4);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1038, 1324560420, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.forward_collision_warning with given params");
            }
        }

    }

    /** 前方穿行预警（adas.front_cross_traffic_alert）— 由目录自动生成，请勿手改。 */
    public static final class front_cross_traffic_alert {
        private front_cross_traffic_alert() {}

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1038, 1324560400, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.front_cross_traffic_alert with given params");
            }
        }

    }

    /** 高速领航状态（adas.highway_navigation_state）— 由目录自动生成，请勿手改。 */
    public static final class highway_navigation_state {
        private highway_navigation_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 70044882, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.highway_navigation_state with given params");
            }
        }

    }

    /** 陡坡缓降（adas.hill_descent_control）— 由目录自动生成，请勿手改。 */
    public static final class hill_descent_control {
        private hill_descent_control() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 305135665, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ON" : (r0 == 2 ? "ON_BLINKING" : (r0 == 3 ? "OFFLINE" : null)))), new int[]{r0}, new String[]{"ADAS_CMD_HDC_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.hill_descent_control with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1038, 944766998, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.hill_descent_control with given params");
            }
        }

    }

    /** iTAC 智能扭矩控制（adas.intelligent_torque_control）— 由目录自动生成，请勿手改。 */
    public static final class intelligent_torque_control {
        private intelligent_torque_control() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 656408615, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "ON" : (r0 == 1 ? "OFF" : null)), new int[]{r0}, new String[]{"SET_ITAC_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.intelligent_torque_control with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1324376094, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.intelligent_torque_control with given params");
            }
        }

    }

    /** 车道偏离模式（adas.lane_departure_mode）— 由目录自动生成，请勿手改。 */
    public static final class lane_departure_mode {
        private lane_departure_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 828375054, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "DEPARTURE_WARNING" : (r0 == 2 ? "LANE_KEEPING" : (r0 == 3 ? "ALL" : (r0 == 4 ? "ON" : null))))), new int[]{r0}, new String[]{"ADAS_LKS_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.lane_departure_mode with given params");
            }
        }

        public static void write(int equipment, int value) {
            Runtime.checkEnum("equipment", equipment, 1, 2);
            Runtime.checkEnum("value", value, 0, 1, 2);
            if (Runtime.inInt(equipment, 1)) {
                int v0 = value;
                Runtime.writeFid(1038, 944767016, v0);
            }
            else if (Runtime.inInt(equipment, 2)) {
                int v0 = value;
                Runtime.writeFid(1038, 944767016, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.lane_departure_mode with given params");
            }
        }

    }

    /** 交通拥堵辅助（adas.traffic_jam_assist_state）— 由目录自动生成，请勿手改。 */
    public static final class traffic_jam_assist_state {
        private traffic_jam_assist_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 828375084, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "INACTIVE" : (r0 == 2 ? "ACTIVE_1" : (r0 == 3 ? "ACTIVE_2" : (r0 == 4 ? "FAULT" : null))))), new int[]{r0}, new String[]{"ADAS_TJA_ICA_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.traffic_jam_assist_state with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1038, 944766996, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.traffic_jam_assist_state with given params");
            }
        }

    }

    /** 城市领航状态（adas.urban_navigation_state）— 由目录自动生成，请勿手改。 */
    public static final class urban_navigation_state {
        private urban_navigation_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1038, 700448800, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else {
                throw new IllegalArgumentException("No matching case for adas.urban_navigation_state with given params");
            }
        }

    }

}
