package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** light — 能力分组容器。 */
public final class light {
    private light() {}

    /** 自适应远光（light.auto_high_beam）— 由目录自动生成，请勿手改。 */
    public static final class auto_high_beam {
        private auto_high_beam() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 0, 1);
            if (Runtime.inInt(area, 0)) {
                int r0 = Runtime.readFid(1004, 985661468, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "INVALID" : (r0 == 1 ? "OFF" : (r0 == 2 ? "STANDBY" : (r0 == 3 ? "ACTIVATION" : (r0 == 4 ? "WORKING" : (r0 == 5 ? "FAULT" : null)))))), new int[]{r0}, new String[]{"LIGHT_LF_ADB_STATE"});
            }
            else if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1004, 986710044, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "INVALID" : (r0 == 1 ? "OFF" : (r0 == 2 ? "STANDBY" : (r0 == 3 ? "ACTIVATION" : (r0 == 4 ? "WORKING" : (r0 == 5 ? "FAULT" : null)))))), new int[]{r0}, new String[]{"LIGHT_RF_ADB_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.auto_high_beam with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 1125122110, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.auto_high_beam with given params");
            }
        }

    }

    /** 日间行车灯（light.daytime_light）— 由目录自动生成，请勿手改。 */
    public static final class daytime_light {
        private daytime_light() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1004, 985661476, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 2) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_DAY_RUNNING_LIGHT_AUTO_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.daytime_light with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 1125122118, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.daytime_light with given params");
            }
        }

    }

    /** 双闪（light.double_flash）— 由目录自动生成，请勿手改。 */
    public static final class double_flash {
        private double_flash() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1004, 950009900, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 0.0; d0_ok = true; }
                if (r0 == 3) { d0 = 0.0; d0_ok = true; }
                if (r0 == 4) { d0 = 0.0; d0_ok = true; }
                if (r0 == 5) { d0 = 0.0; d0_ok = true; }
                if (r0 == 6) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_TURN_SIGNAL_LIGHT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.double_flash with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 871366669, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.double_flash with given params");
            }
        }

    }

    /** 雾灯开关（怀挡）（light.fog_switch）— 由目录自动生成，请勿手改。 */
    public static final class fog_switch {
        private fog_switch() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1004, 1011875876, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 2) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"INSTRUMENT_FRONT_FOG_LAMPS_SWITCH_STATE"});
            }
            else if (Runtime.inInt(area, 2)) {
                int r0 = Runtime.readFid(1004, 950009872, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 2) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_REAR_FOG_LAMPS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.fog_switch with given params");
            }
        }

        public static void write(int area, boolean value) {
            Runtime.checkEnum("area", area, 1, 2);
            if (Runtime.inInt(area, 1)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 1276153908, v0);
            }
            else if (Runtime.inInt(area, 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 1276153904, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.fog_switch with given params");
            }
        }

    }

    /** 车灯控制模式（light.headlight_mode）— 由目录自动生成，请勿手改。 */
    public static final class headlight_mode {
        private headlight_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1004, 1011875880, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "AUTO" : (r0 == 3 ? "SIDE_LIGHT" : (r0 == 4 ? "LOW_BEAM" : null)))), new int[]{r0}, new String[]{"INSTRUMENT_HEADLIGHT_CONTROL_FEEDBACK"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.headlight_mode with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3, 4);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1004, 1276153912, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.headlight_mode with given params");
            }
        }

    }

    /** 智能远近光（light.high_beam_assist）— 由目录自动生成，请勿手改。 */
    public static final class high_beam_assist {
        private high_beam_assist() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1004, 828375048, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "INACTIVE" : (r0 == 2 ? "STANDBY" : (r0 == 3 ? "ACTIVE" : (r0 == 4 ? "FAULT" : (r0 == 5 ? "CAMERA_BLOCKED" : null)))))), new int[]{r0}, new String[]{"ADAS_HMA_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.high_beam_assist with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1004, 944767008, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.high_beam_assist with given params");
            }
        }

    }

    /** 车内灯（light.inside_light）— 由目录自动生成，请勿手改。 */
    public static final class inside_light {
        private inside_light() {}

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1330643002, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.inside_light with given params");
            }
        }

    }

    /** 开门自动亮灯（light.inside_light_on_door）— 由目录自动生成，请勿手改。 */
    public static final class inside_light_on_door {
        private inside_light_on_door() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1121976363, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 2) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_INSIDE_LIGHT_DOOR_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.inside_light_on_door with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1330643000, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.inside_light_on_door with given params");
            }
        }

    }

    /** 灯具状态（light.light_state）— 由目录自动生成，请勿手改。 */
    public static final class light_state {
        private light_state() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2, 3, 6, 7, 8);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1004, 321912841, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_SIDE_LIGHT"});
            }
            else if (Runtime.inInt(area, 2)) {
                int r0 = Runtime.readFid(1004, 321912842, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_LOW_BEAM_LIGHT"});
            }
            else if (Runtime.inInt(area, 3)) {
                int r0 = Runtime.readFid(1004, 321912843, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_HIGH_BEAM_LIGHT"});
            }
            else if (Runtime.inInt(area, 6)) {
                int r0 = Runtime.readFid(1004, 321912846, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_FRONT_FOG_LIGHT"});
            }
            else if (Runtime.inInt(area, 7)) {
                int r0 = Runtime.readFid(1004, 321912847, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_REAR_FOG_LIGHT"});
            }
            else if (Runtime.inInt(area, 8)) {
                int r0 = Runtime.readFid(1004, 315621432, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_FOOT_LIGHT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.light_state with given params");
            }
        }

        public static void write(int area, boolean value) {
            Runtime.checkEnum("area", area, 6, 7);
            if (Runtime.inInt(area, 6)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 871366675, v0);
            }
            else if (Runtime.inInt(area, 7)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 871366667, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.light_state with given params");
            }
        }

    }

    /** 分侧转向灯（light.turn_signal）— 由目录自动生成，请勿手改。 */
    public static final class turn_signal {
        private turn_signal() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1004, 321912884, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_LEFT_TURN_SIGNAL_LIGHT_SWITCH_STATE"});
            }
            else if (Runtime.inInt(area, 2)) {
                int r0 = Runtime.readFid(1004, 321912886, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"LIGHT_RIGHT_TURN_SIGNAL_LIGHT_SWITCH_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.turn_signal with given params");
            }
        }

        public static void write(int area, boolean value) {
            Runtime.checkEnum("area", area, 1, 2);
            if (Runtime.inInt(area, 1)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 871366669, v0);
            }
            else if (Runtime.inInt(area, 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, 871366669, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.turn_signal with given params");
            }
        }

    }

    /** 转向信号模式（light.turn_signal_state）— 由目录自动生成，请勿手改。 */
    public static final class turn_signal_state {
        private turn_signal_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1004, 950009900, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "LEFT_NORMAL" : (r0 == 3 ? "LEFT_FAST" : (r0 == 4 ? "RIGHT_NORMAL" : (r0 == 5 ? "RIGHT_FAST" : (r0 == 6 ? "HAZARD" : (r0 == 7 ? "EMERGENCY" : (r0 == 8 ? "REAR_END_WARNING" : (r0 == 9 ? "FAST_FLASH" : null))))))))), new int[]{r0}, new String[]{"LIGHT_TURN_SIGNAL_LIGHT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for light.turn_signal_state with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 0, 1, 2, 3);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1004, 871366669, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for light.turn_signal_state with given params");
            }
        }

    }

}
