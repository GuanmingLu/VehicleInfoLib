package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** bodywork — 能力分组容器。 */
public final class bodywork {
    private bodywork() {}

    /** 充电口盖（bodywork.charging_flap）— 由目录自动生成，请勿手改。 */
    public static final class charging_flap {
        private charging_flap() {}

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1276178470, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.charging_flap with given params");
            }
        }

    }

    /** door — 能力分组容器。 */
    public static final class door {
        private door() {}

        /** 车门与盖板开闭状态（bodywork.door.open）— 由目录自动生成，请勿手改。 */
        public static final class open {
            private open() {}

            public static SensorValue read(int area) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4, 9, 10, 11);
                if (Runtime.inInt(area, 1)) {
                    int r0 = Runtime.readFid(1001, 315621384, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_LEFT_HAND_FRONT_DOOR"});
                }
                else if (Runtime.inInt(area, 2)) {
                    int r0 = Runtime.readFid(1001, 315621385, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_RIGHT_HAND_FRONT_DOOR"});
                }
                else if (Runtime.inInt(area, 3)) {
                    int r0 = Runtime.readFid(1001, 315621386, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_LEFT_HAND_REAR_DOOR"});
                }
                else if (Runtime.inInt(area, 4)) {
                    int r0 = Runtime.readFid(1001, 315621387, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_RIGHT_HAND_REAR_DOOR"});
                }
                else if (Runtime.inInt(area, 9)) {
                    int r0 = Runtime.readFid(1001, 315621389, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_HOOD"});
                }
                else if (Runtime.inInt(area, 10)) {
                    int r0 = Runtime.readFid(1001, 315621388, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_LUGGAGE_DOOR"});
                }
                else if (Runtime.inInt(area, 11)) {
                    int r0 = Runtime.readFid(1001, 1336934422, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = 0;
                    boolean d0_ok = false;
                    if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                    if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_FUEL_TANK_CAP"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.door.open with given params");
                }
            }

        }

    }

    /** electric_door — 能力分组容器。 */
    public static final class electric_door {
        private electric_door() {}

        /** 电动车门运动状态（bodywork.electric_door.actuator_state）— 由目录自动生成，请勿手改。 */
        public static final class actuator_state {
            private actuator_state() {}

            public static SensorValue read(int area) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4);
                if (Runtime.inInt(area, 1)) {
                    int r0 = Runtime.readFid(1001, 957350169, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "MOVING_1" : (r0 == 2 ? "MOVING_2" : (r0 == 3 ? "PAUSED" : (r0 == 5 ? "OPEN" : null)))), new int[]{r0}, new String[]{"BODYWORK_LF_ELECTRIC_DOOR_ACTUATOR_STATUS"});
                }
                else if (Runtime.inInt(area, 2)) {
                    int r0 = Runtime.readFid(1001, 957350172, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "MOVING_1" : (r0 == 2 ? "MOVING_2" : (r0 == 3 ? "PAUSED" : (r0 == 5 ? "OPEN" : null)))), new int[]{r0}, new String[]{"BODYWORK_RF_ELECTRIC_DOOR_ACTUATOR_STATUS"});
                }
                else if (Runtime.inInt(area, 3)) {
                    int r0 = Runtime.readFid(1001, 957350176, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "MOVING_1" : (r0 == 2 ? "MOVING_2" : (r0 == 3 ? "PAUSED" : (r0 == 5 ? "OPEN" : null)))), new int[]{r0}, new String[]{"BODYWORK_LR_ELECTRIC_DOOR_ACTUATOR_STATUS"});
                }
                else if (Runtime.inInt(area, 4)) {
                    int r0 = Runtime.readFid(1001, 957350179, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "MOVING_1" : (r0 == 2 ? "MOVING_2" : (r0 == 3 ? "PAUSED" : (r0 == 5 ? "OPEN" : null)))), new int[]{r0}, new String[]{"BODYWORK_RR_ELECTRIC_DOOR_ACTUATOR_STATUS"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.electric_door.actuator_state with given params");
                }
            }

        }

        /** 电动车门（bodywork.electric_door.position）— 由目录自动生成，请勿手改。 */
        public static final class position {
            private position() {}

            public static SensorValue read(int area) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4);
                if (Runtime.inInt(area, 1)) {
                    int r0 = Runtime.readFid(1001, 957350000, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "NOT_CLOSED_1" : (r0 == 2 ? "CLOSED" : (r0 == 3 ? "NOT_CLOSED_3" : null))), new int[]{r0}, new String[]{"BODYWORK_LF_ELECTRIC_DOOR_CURRENT_POSITION"});
                }
                else if (Runtime.inInt(area, 2)) {
                    int r0 = Runtime.readFid(1001, 957350002, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "NOT_CLOSED_1" : (r0 == 2 ? "CLOSED" : (r0 == 3 ? "NOT_CLOSED_3" : null))), new int[]{r0}, new String[]{"BODYWORK_RF_ELECTRIC_DOOR_CURRENT_POSITION"});
                }
                else if (Runtime.inInt(area, 3)) {
                    int r0 = Runtime.readFid(1001, 957350004, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "NOT_CLOSED_1" : (r0 == 2 ? "CLOSED" : (r0 == 3 ? "NOT_CLOSED_3" : null))), new int[]{r0}, new String[]{"BODYWORK_LR_ELECTRIC_DOOR_CURRENT_POSITION"});
                }
                else if (Runtime.inInt(area, 4)) {
                    int r0 = Runtime.readFid(1001, 957350006, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "NOT_CLOSED_1" : (r0 == 2 ? "CLOSED" : (r0 == 3 ? "NOT_CLOSED_3" : null))), new int[]{r0}, new String[]{"BODYWORK_RR_ELECTRIC_DOOR_CURRENT_POSITION"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.electric_door.position with given params");
                }
            }

            public static void write(int area, int value) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4);
                Runtime.checkEnum("value", value, 1, 2, 3);
                if (Runtime.inInt(area, 1)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1276178480, v0);
                }
                else if (Runtime.inInt(area, 2)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1276178483, v0);
                }
                else if (Runtime.inInt(area, 3)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1276178488, v0);
                }
                else if (Runtime.inInt(area, 4)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1276178491, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.electric_door.position with given params");
                }
            }

        }

    }

    /** 电动踏板（bodywork.electric_step）— 由目录自动生成，请勿手改。 */
    public static final class electric_step {
        private electric_step() {}

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 3; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1001, 1276194884, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.electric_step with given params");
            }
        }

    }

    /** 电动前舱盖（bodywork.front_hood）— 由目录自动生成，请勿手改。 */
    public static final class front_hood {
        private front_hood() {}

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1276182560, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.front_hood with given params");
            }
        }

    }

    /** 离地间隙（bodywork.ground_clearance）— 由目录自动生成，请勿手改。 */
    public static final class ground_clearance {
        private ground_clearance() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1001, 277872946, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "millimeter", null, new int[]{r0}, new String[]{"BODYWORK_GROUND_CLEARANCE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.ground_clearance with given params");
            }
        }

    }

    /** 手动后备箱解锁（bodywork.manual_trunk_release）— 由目录自动生成，请勿手改。 */
    public static final class manual_trunk_release {
        private manual_trunk_release() {}

        public static void write() {
            if (true) {
                int v0 = 1;
                Runtime.writeFid(1001, 1276141592, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.manual_trunk_release with given params");
            }
        }

    }

    /** 整车电源状态（bodywork.power_state）— 由目录自动生成，请勿手改。 */
    public static final class power_state {
        private power_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1001, 315621418, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ACC" : (r0 == 2 ? "ON" : (r0 == 3 ? "OK" : (r0 == 4 ? "FAKE_OK" : null))))), new int[]{r0}, new String[]{"BODYWORK_POWER_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.power_state with given params");
            }
        }

    }

    /** roof — 能力分组容器。 */
    public static final class roof {
        private roof() {}

        /** 天窗动作（bodywork.roof.command）— 由目录自动生成，请勿手改。 */
        public static final class command {
            private command() {}

            public static void write(int value) {
                Runtime.checkEnum("value", value, 0, 253, 254);
                if (true) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1330642976, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.roof.command with given params");
                }
            }

        }

        /** 天窗开度（bodywork.roof.position）— 由目录自动生成，请勿手改。 */
        public static final class position {
            private position() {}

            public static SensorValue read() {
                if (true) {
                    int r0 = Runtime.readFid(1001, 1101004808, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_MOON_ROOF_OPEN_PERCENT"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.roof.position with given params");
                }
            }

            public static void write(int value) {
                Runtime.checkRange("value", value, 12, 100);
                if (true) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1330642976, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.roof.position with given params");
                }
            }

        }

    }

    /** 电动侧滑门（bodywork.sliding_door）— 由目录自动生成，请勿手改。 */
    public static final class sliding_door {
        private sliding_door() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 3, 4);
            if (Runtime.inInt(area, 3)) {
                int r0 = Runtime.readFid(1001, 1259339792, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "CLOSED" : (r0 == 2 ? "OPEN" : null)), new int[]{r0}, new String[]{""});
            }
            else if (Runtime.inInt(area, 4)) {
                int r0 = Runtime.readFid(1001, 1260388368, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "CLOSED" : (r0 == 2 ? "OPEN" : null)), new int[]{r0}, new String[]{""});
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.sliding_door with given params");
            }
        }

        public static void write(int area, int value) {
            Runtime.checkEnum("area", area, 3, 4);
            Runtime.checkEnum("value", value, 1, 2);
            if (Runtime.inInt(area, 3)) {
                int v0 = value;
                Runtime.writeFid(1001, 1312817200, v0);
            }
            else if (Runtime.inInt(area, 4)) {
                int v0 = value;
                Runtime.writeFid(1001, 1312817202, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.sliding_door with given params");
            }
        }

    }

    /** 电动尾翼（bodywork.spoiler）— 由目录自动生成，请勿手改。 */
    public static final class spoiler {
        private spoiler() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1200619531, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 5 ? "ON" : (r0 == 6 ? "OFF" : null)), new int[]{r0}, new String[]{"SETTING_ELECTRIC_TAIL_CURRENT_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.spoiler with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1276182580, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.spoiler with given params");
            }
        }

    }

    /** sunshade — 能力分组容器。 */
    public static final class sunshade {
        private sunshade() {}

        /** 车顶遮阳帘开度（bodywork.sunshade.position）— 由目录自动生成，请勿手改。 */
        public static final class position {
            private position() {}

            public static SensorValue read(int area) {
                Runtime.checkEnum("area", area, 1, 2);
                if (Runtime.inInt(area, 1)) {
                    int r0 = Runtime.readFid(1001, 1101004816, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_SUNSHADE_PANEL_PERCENT"});
                }
                else if (Runtime.inInt(area, 2)) {
                    int r0 = Runtime.readFid(1001, 1206910984, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_REAR_SUNSHADE_PANEL_PERCENT"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.sunshade.position with given params");
                }
            }

            public static void write(int area, int value) {
                Runtime.checkEnum("area", area, 1, 2);
                Runtime.checkRange("value", value, 0, 100);
                if (Runtime.inInt(area, 1)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1330642984, v0);
                }
                else if (Runtime.inInt(area, 2)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1276178472, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.sunshade.position with given params");
                }
            }

        }

        /** 停止前遮阳帘（bodywork.sunshade.stop）— 由目录自动生成，请勿手改。 */
        public static final class stop {
            private stop() {}

            public static void write(int value) {
                Runtime.checkEnum("value", value, 254);
                if (true) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1330642984, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.sunshade.stop with given params");
                }
            }

        }

    }

    /** trunk — 能力分组容器。 */
    public static final class trunk {
        private trunk() {}

        /** 尾门动作（bodywork.trunk.command）— 由目录自动生成，请勿手改。 */
        public static final class command {
            private command() {}

            public static void write(int value) {
                Runtime.checkEnum("value", value, 1, 2, 3);
                if (true) {
                    int v0 = value;
                    Runtime.writeFid(1000, 1125122080, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.trunk.command with given params");
                }
            }

        }

        /** 尾门最大开启高度（bodywork.trunk.open_height）— 由目录自动生成，请勿手改。 */
        public static final class open_height {
            private open_height() {}

            public static SensorValue read() {
                if (true) {
                    int r0 = Runtime.readFid(1023, 1074790408, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_BACK_DOOR_OPEN_HEIGHT"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.trunk.open_height with given params");
                }
            }

            public static void write(int value) {
                Runtime.checkRange("value", value, 15, 100);
                if (true) {
                    int v0 = value;
                    Runtime.writeFid(1023, 1069547584, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.trunk.open_height with given params");
                }
            }

        }

        /** 尾门位置（bodywork.trunk.position）— 由目录自动生成，请勿手改。 */
        public static final class position {
            private position() {}

            public static SensorValue read() {
                if (true) {
                    int r0 = Runtime.readFid(1000, 1074790416, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, null, (r0 == 1 ? "OPEN" : (r0 == 2 ? "NOT_OPEN_2" : (r0 == 3 ? "NOT_OPEN_3" : null))), new int[]{r0}, new String[]{"BODYWORK_BACKDOOR_CURRENT_POSITION"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.trunk.position with given params");
                }
            }

        }

    }

    /** 车身高度（bodywork.vehicle_height）— 由目录自动生成，请勿手改。 */
    public static final class vehicle_height {
        private vehicle_height() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1001, 277872956, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "millimeter", null, new int[]{r0}, new String[]{"BODYWORK_VEHICLE_HEIGHT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.vehicle_height with given params");
            }
        }

    }

    /** window — 能力分组容器。 */
    public static final class window {
        private window() {}

        /** 车窗动作（bodywork.window.action）— 由目录自动生成，请勿手改。 */
        public static final class action {
            private action() {}

            public static void write(int area, int value) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4);
                Runtime.checkEnum("value", value, 1, 2, 3, 4, 5);
                if (Runtime.inInt(area, 1)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1125122104, v0);
                }
                else if (Runtime.inInt(area, 2)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1125122107, v0);
                }
                else if (Runtime.inInt(area, 3)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1125122112, v0);
                }
                else if (Runtime.inInt(area, 4)) {
                    int v0 = value;
                    Runtime.writeFid(1001, 1125122115, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.window.action with given params");
                }
            }

        }

        /** 车窗组合动作（bodywork.window.command）— 由目录自动生成，请勿手改。 */
        public static final class command {
            private command() {}

            public static void write(int value) {
                Runtime.checkEnum("value", value, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 11, 12);
                if (Runtime.inInt(value, 0)) {
                    int v0 = 2;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 2;
                    Runtime.writeFid(1001, 1125122107, v1);
                    int v2 = 2;
                    Runtime.writeFid(1001, 1125122112, v2);
                    int v3 = 2;
                    Runtime.writeFid(1001, 1125122115, v3);
                }
                else if (Runtime.inInt(value, 1)) {
                    int v0 = 1;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 1;
                    Runtime.writeFid(1001, 1125122107, v1);
                    int v2 = 1;
                    Runtime.writeFid(1001, 1125122112, v2);
                    int v3 = 1;
                    Runtime.writeFid(1001, 1125122115, v3);
                }
                else if (Runtime.inInt(value, 2)) {
                    int v0 = 4;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 4;
                    Runtime.writeFid(1001, 1125122107, v1);
                    int v2 = 4;
                    Runtime.writeFid(1001, 1125122112, v2);
                    int v3 = 4;
                    Runtime.writeFid(1001, 1125122115, v3);
                }
                else if (Runtime.inInt(value, 3)) {
                    int v0 = 1;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 1;
                    Runtime.writeFid(1001, 1125122107, v1);
                }
                else if (Runtime.inInt(value, 4)) {
                    int v0 = 2;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 2;
                    Runtime.writeFid(1001, 1125122107, v1);
                }
                else if (Runtime.inInt(value, 5)) {
                    int v0 = 4;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 4;
                    Runtime.writeFid(1001, 1125122107, v1);
                }
                else if (Runtime.inInt(value, 6)) {
                    int v0 = 5;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 5;
                    Runtime.writeFid(1001, 1125122107, v1);
                }
                else if (Runtime.inInt(value, 7)) {
                    int v0 = 1;
                    Runtime.writeFid(1001, 1125122112, v0);
                    int v1 = 1;
                    Runtime.writeFid(1001, 1125122115, v1);
                }
                else if (Runtime.inInt(value, 8)) {
                    int v0 = 2;
                    Runtime.writeFid(1001, 1125122112, v0);
                    int v1 = 2;
                    Runtime.writeFid(1001, 1125122115, v1);
                }
                else if (Runtime.inInt(value, 9)) {
                    int v0 = 4;
                    Runtime.writeFid(1001, 1125122112, v0);
                    int v1 = 4;
                    Runtime.writeFid(1001, 1125122115, v1);
                }
                else if (Runtime.inInt(value, 10)) {
                    int v0 = 5;
                    Runtime.writeFid(1001, 1125122112, v0);
                    int v1 = 5;
                    Runtime.writeFid(1001, 1125122115, v1);
                }
                else if (Runtime.inInt(value, 13)) {
                    int v0 = 3;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 3;
                    Runtime.writeFid(1001, 1125122107, v1);
                    int v2 = 3;
                    Runtime.writeFid(1001, 1125122112, v2);
                    int v3 = 3;
                    Runtime.writeFid(1001, 1125122115, v3);
                    int v4 = 254;
                    Runtime.writeFid(1001, 1330642976, v4);
                    int v5 = 254;
                    Runtime.writeFid(1001, 1330642984, v5);
                }
                else if (Runtime.inInt(value, 11)) {
                    int v0 = 2;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 2;
                    Runtime.writeFid(1001, 1125122107, v1);
                    int v2 = 2;
                    Runtime.writeFid(1001, 1125122112, v2);
                    int v3 = 2;
                    Runtime.writeFid(1001, 1125122115, v3);
                    int v4 = 254;
                    Runtime.writeFid(1001, 1330642976, v4);
                    int v5 = 0;
                    Runtime.writeFid(1001, 1330642976, v5);
                    int v6 = 0;
                    Runtime.writeFid(1001, 1330642984, v6);
                }
                else if (Runtime.inInt(value, 12)) {
                    int v0 = 5;
                    Runtime.writeFid(1001, 1125122104, v0);
                    int v1 = 5;
                    Runtime.writeFid(1001, 1125122107, v1);
                    int v2 = 5;
                    Runtime.writeFid(1001, 1125122112, v2);
                    int v3 = 5;
                    Runtime.writeFid(1001, 1125122115, v3);
                    int v4 = 254;
                    Runtime.writeFid(1001, 1330642976, v4);
                    int v5 = 253;
                    Runtime.writeFid(1001, 1330642976, v5);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.window.command with given params");
                }
            }

        }

        /** 车窗开度（bodywork.window.position）— 由目录自动生成，请勿手改。 */
        public static final class position {
            private position() {}

            public static SensorValue read(int area) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4);
                if (Runtime.inInt(area, 1)) {
                    int r0 = Runtime.readFid(1001, 947912720, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_WINDOW_LEFT_FRONT_PERCENT"});
                }
                else if (Runtime.inInt(area, 2)) {
                    int r0 = Runtime.readFid(1001, 947912728, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_WINDOW_RIGHT_FRONT_PERCENT"});
                }
                else if (Runtime.inInt(area, 3)) {
                    int r0 = Runtime.readFid(1001, 947912736, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_WINDOW_LEFT_REAR_PERCENT"});
                }
                else if (Runtime.inInt(area, 4)) {
                    int r0 = Runtime.readFid(1001, 947912744, 5);
                    if (Runtime.isSentinel(r0)) return SensorValue.failed();
                    double d0 = r0;
                    return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"BODYWORK_WINDOW_RIGHT_REAR_PERCENT"});
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.window.position with given params");
                }
            }

            public static void write(int area, int value) {
                Runtime.checkEnum("area", area, 1, 2, 3, 4);
                Runtime.checkRange("value", value, 0, 100);
                if (Runtime.inInt(area, 1)) {
                    int v0 = value;
                    Runtime.writeFid(1000, 1276219408, v0);
                }
                else if (Runtime.inInt(area, 2)) {
                    int v0 = value;
                    Runtime.writeFid(1000, 1276219424, v0);
                }
                else if (Runtime.inInt(area, 3)) {
                    int v0 = value;
                    Runtime.writeFid(1000, 1276219416, v0);
                }
                else if (Runtime.inInt(area, 4)) {
                    int v0 = value;
                    Runtime.writeFid(1000, 1276219432, v0);
                }
                else {
                    throw new IllegalArgumentException("No matching case for bodywork.window.position with given params");
                }
            }

        }

    }

    /** 后侧窗遮阳帘（bodywork.window_sunshade）— 由目录自动生成，请勿手改。 */
    public static final class window_sunshade {
        private window_sunshade() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 3, 4);
            if (Runtime.inInt(area, 3)) {
                int r0 = Runtime.readFid(1001, 1025507390, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SETTING_LR_WINDOW_SUNSHADE_POSITION"});
            }
            else if (Runtime.inInt(area, 4)) {
                int r0 = Runtime.readFid(1001, 899678526, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SETTING_RR_WINDOW_SUNSHADE_POSITION"});
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.window_sunshade with given params");
            }
        }

        public static void write(int area, int value) {
            Runtime.checkEnum("area", area, 3, 4);
            Runtime.checkEnum("value", value, 1, 2);
            if (Runtime.inInt(area, 3)) {
                int v0 = value;
                Runtime.writeFid(1001, 1276207172, v0);
            }
            else if (Runtime.inInt(area, 4)) {
                int v0 = value;
                Runtime.writeFid(1001, 1276207174, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for bodywork.window_sunshade with given params");
            }
        }

    }

}
