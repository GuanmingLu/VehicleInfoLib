package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** setting — 能力分组容器。 */
public final class setting {
    private setting() {}

    /** 分区氛围灯亮度（setting.ambient_light_area_brightness）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class ambient_light_area_brightness {
        private ambient_light_area_brightness() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2, 3);
            if (Runtime.inInt(area, 1)) {
                int f0 = Runtime.fid("SET_IAL_FRONT_BRIGHTNESS", "Setting");
                int r0 = Runtime.readFid(1023, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-1);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_IAL_FRONT_BRIGHTNESS"});
            }
            else if (Runtime.inInt(area, 2)) {
                int f0 = Runtime.fid("SET_IAL_BACK_BRIGHTNESS", "Setting");
                int r0 = Runtime.readFid(1023, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-1);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_IAL_BACK_BRIGHTNESS"});
            }
            else if (Runtime.inInt(area, 3)) {
                int f0 = Runtime.fid("SET_IAL_ALL_BRIGHTNESS", "Setting");
                int r0 = Runtime.readFid(1023, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-1);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_IAL_ALL_BRIGHTNESS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.ambient_light_area_brightness with given params");
            }
        }

        public static void write(int area, int value) {
            Runtime.checkEnum("area", area, 1, 2, 3);
            Runtime.checkRange("value", value, 0, 5);
            if (Runtime.inInt(area, 1)) {
                int f0 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_COLOR_SET", "Setting");
                int v0 = 0;
                Runtime.writeFid(1023, f0, v0);
                int f1 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_BRIGHTNESS_SET", "Setting");
                int v1 = (int) (value - (-1));
                Runtime.writeFid(1023, f1, v1);
                int f2 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_AREA_SET", "Setting");
                int v2 = 1;
                Runtime.writeFid(1023, f2, v2);
                int f3 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_SOURCE_SET", "Setting");
                int v3 = 0;
                Runtime.writeFid(1023, f3, v3);
            }
            else if (Runtime.inInt(area, 2)) {
                int f0 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_COLOR_SET", "Setting");
                int v0 = 0;
                Runtime.writeFid(1023, f0, v0);
                int f1 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_BRIGHTNESS_SET", "Setting");
                int v1 = (int) (value - (-1));
                Runtime.writeFid(1023, f1, v1);
                int f2 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_AREA_SET", "Setting");
                int v2 = 2;
                Runtime.writeFid(1023, f2, v2);
                int f3 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_SOURCE_SET", "Setting");
                int v3 = 0;
                Runtime.writeFid(1023, f3, v3);
            }
            else if (Runtime.inInt(area, 3)) {
                int f0 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_COLOR_SET", "Setting");
                int v0 = 0;
                Runtime.writeFid(1023, f0, v0);
                int f1 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_BRIGHTNESS_SET", "Setting");
                int v1 = (int) (value - (-1));
                Runtime.writeFid(1023, f1, v1);
                int f2 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_AREA_SET", "Setting");
                int v2 = 3;
                Runtime.writeFid(1023, f2, v2);
                int f3 = Runtime.fid("SET_INTERIOR_ATMOSPHERE_LAMP_SOURCE_SET", "Setting");
                int v3 = 0;
                Runtime.writeFid(1023, f3, v3);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.ambient_light_area_brightness with given params");
            }
        }

    }

    /** 全车氛围灯亮度（setting.ambient_light_brightness）— 由目录自动生成，请勿手改。 */
    public static final class ambient_light_brightness {
        private ambient_light_brightness() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1121976328, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-1);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_IAL_FRONT_BRIGHTNESS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.ambient_light_brightness with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 0, 5);
            if (true) {
                int v0 = (int) (value - (-1));
                Runtime.writeFid(1000, 1069547536, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.ambient_light_brightness with given params");
            }
        }

    }

    /** 氛围灯总开关（setting.ambient_light_power）— 由目录自动生成，请勿手改。 */
    public static final class ambient_light_power {
        private ambient_light_power() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1060110406, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "ON" : null)), new int[]{r0}, new String[]{"LIGHT_ATMOSPHERE_MAIN_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.ambient_light_power with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276153924, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.ambient_light_power with given params");
            }
        }

    }

    /** 智能防晕车（setting.anti_motion）— 由目录自动生成，请勿手改。 */
    public static final class anti_motion {
        private anti_motion() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 320864462, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "ON" : null)), new int[]{r0}, new String[]{"ADAS_ANTIMOTION_CONTROL_SWITCH_SIGNAL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.anti_motion with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 850616366, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.anti_motion with given params");
            }
        }

    }

    /** 露营调平（setting.camping_leveling）— 由目录自动生成，请勿手改。 */
    public static final class camping_leveling {
        private camping_leveling() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 629145690, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ON" : (r0 == 2 ? "OFF" : null)), new int[]{r0}, new String[]{"SETTING_CAMPING_LEVELING_FUNCTION_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.camping_leveling with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1324617766, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.camping_leveling with given params");
            }
        }

    }

    /** 舒适控制强度（setting.comfort_mode）— 由目录自动生成，请勿手改。 */
    public static final class comfort_mode {
        private comfort_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 938475534, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "STRONG" : (r0 == 2 ? "MEDIUM" : (r0 == 3 ? "WEAK" : null))), new int[]{r0}, new String[]{"BODYWORK_CDE_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.comfort_mode with given params");
            }
        }

        public static void write(int maximumLevel, int value) {
            Runtime.checkEnum("maximumLevel", maximumLevel, 2, 3);
            Runtime.checkEnum("value", value, 1, 2, 3);
            if (Runtime.inInt(maximumLevel, 2)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324617780, v0);
            }
            else if (Runtime.inInt(maximumLevel, 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324617780, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.comfort_mode with given params");
            }
        }

    }

    /** 巡航提示（setting.cruise_prompt）— 由目录自动生成，请勿手改。 */
    public static final class cruise_prompt {
        private cruise_prompt() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1023, 852492318, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "NO_PROMPT" : (r0 == 1 ? "PROMPT" : null)), new int[]{r0}, new String[]{"SETTING_AUTO_CRUISE_PROMPT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.cruise_prompt with given params");
            }
        }

    }

    /** ETC 开关（setting.etc_power）— 由目录自动生成，请勿手改。 */
    public static final class etc_power {
        private etc_power() {}

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 850460688, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.etc_power with given params");
            }
        }

    }

    /** 前照灯高度（setting.headlamp_height）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class headlamp_height {
        private headlamp_height() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1023, 985661459, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else if (Runtime.inInt(area, 2)) {
                int r0 = Runtime.readFid(1023, 986710035, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.headlamp_height with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 1, 11);
            if (true) {
                int f0 = Runtime.fid("SET_CMD_HEADLAMP_HEIGHT_LEVEL_SET", "Setting");
                int v0 = value;
                Runtime.writeFid(1023, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.headlamp_height with given params");
            }
        }

    }

    /** HUD 亮度（setting.hud_brightness）— 由目录自动生成，请勿手改。 */
    public static final class hud_brightness {
        private hud_brightness() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 951058448, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_BRIGHTNESS_GEAR_FEEDBACK"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.hud_brightness with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 1, 11);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 1276174360, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.hud_brightness with given params");
            }
        }

    }

    /** HUD 开关（setting.hud_power）— 由目录自动生成，请勿手改。 */
    public static final class hud_power {
        private hud_power() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 951058460, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ON" : (r0 == 2 ? "OFF" : null)), new int[]{r0}, new String[]{"SET_HUD_SWITCH_STATUS_FEEDBACK"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.hud_power with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276174373, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.hud_power with given params");
            }
        }

    }

    /** 电子后视镜自动亮度（setting.mirror_auto_brightness）— 由目录自动生成，请勿手改。 */
    public static final class mirror_auto_brightness {
        private mirror_auto_brightness() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1041236000, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "AUTO" : (r0 == 2 ? "MANUAL" : null)), new int[]{r0}, new String[]{"REAR_VIEW_MIRROR_EXTERNAL_BACKLIGHT_AUTO_ADJUST"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_auto_brightness with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 427827260, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_auto_brightness with given params");
            }
        }

    }

    /** 后视镜自动折叠（setting.mirror_auto_fold）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class mirror_auto_fold {
        private mirror_auto_fold() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH", "Setting");
                int r0 = Runtime.readFid(1023, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_auto_fold with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int f0 = Runtime.fid("SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH_SET", "Setting");
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1023, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_auto_fold with given params");
            }
        }

    }

    /** 电子后视镜亮度（setting.mirror_brightness）— 由目录自动生成，请勿手改。 */
    public static final class mirror_brightness {
        private mirror_brightness() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1041235987, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"REAR_VIEW_MIRROR_EXTERNAL_CMS_LEFT_DISPLAY_SCREEN_ADJUST_LIGHT_GEAR"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_brightness with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 13, 22);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 427827227, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_brightness with given params");
            }
        }

    }

    /** 后视镜折叠动作（setting.mirror_fold_command）— 由目录自动生成，请勿手改。 */
    public static final class mirror_fold_command {
        private mirror_fold_command() {}

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1001, 1324556317, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_fold_command with given params");
            }
        }

    }

    /** 后视镜随动（setting.mirror_follow_up）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class mirror_follow_up {
        private mirror_follow_up() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH", "Setting");
                int r0 = Runtime.readFid(1023, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_follow_up with given params");
            }
        }

        public static void write(int rawCommand) {
            Runtime.checkRange("rawCommand", rawCommand, 0, 1);
            if (true) {
                int f0 = Runtime.fid("SET_CAR_EXTREARMIR_FOLLOWUP_SWITCH_SET", "Setting");
                int v0 = rawCommand;
                Runtime.writeFid(1023, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_follow_up with given params");
            }
        }

    }

    /** 倒车后视镜翻转（setting.mirror_reverse_tilt）— 由目录自动生成，请勿手改。 */
    public static final class mirror_reverse_tilt {
        private mirror_reverse_tilt() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1228931096, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ON" : null)), new int[]{r0}, new String[]{"SET_CAR_REAR_VIEW"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_reverse_tilt with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1312817180, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.mirror_reverse_tilt with given params");
            }
        }

    }

    /** 后排娱乐屏（setting.rear_entertainment_screen）— 由目录自动生成，请勿手改。 */
    public static final class rear_entertainment_screen {
        private rear_entertainment_screen() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1000, 814743574, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OPEN_0" : (r0 == 1 ? "OPEN_1" : (r0 == 2 ? "CLOSED" : null))), new int[]{r0}, new String[]{"MOTOR_OVERHEAD_SCREEN_LOCATION_STATUS_SET"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.rear_entertainment_screen with given params");
            }
        }

        public static void write(int area, boolean enabled) {
            Runtime.checkEnum("area", area, 1, 2, 3, 4);
            if (Runtime.inInt(area, 1)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 5; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1285554288, v0);
            }
            else if (Runtime.inInt(area, 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 457179158, v0);
            }
            else if (Runtime.inInt(area, 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 457179172, v0);
            }
            else if (Runtime.inInt(area, 4)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 457179158, v0);
                int v1 = 0;
                boolean v1_ok = false;
                if ((enabled == true)) { v1 = 1; v1_ok = true; }
                if ((enabled == false)) { v1 = 2; v1_ok = true; }
                if (!v1_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 457179172, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.rear_entertainment_screen with given params");
            }
        }

    }

    /** 路面预瞄（setting.road_preview）— 由目录自动生成，请勿手改。 */
    public static final class road_preview {
        private road_preview() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 629145674, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ON" : null)), new int[]{r0}, new String[]{"ADAS_PREVIEW_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.road_preview with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1324576806, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.road_preview with given params");
            }
        }

    }

    /** 气囊翻滚抑制（setting.rollover_airbag_suppression）— 由目录自动生成，请勿手改。 */
    public static final class rollover_airbag_suppression {
        private rollover_airbag_suppression() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 146800695, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ON" : (r0 == 2 ? "OFF" : null)), new int[]{r0}, new String[]{"RESCUE_AIRBAG_ROLL_SUPPRESSION_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.rollover_airbag_suppression with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1324564518, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.rollover_airbag_suppression with given params");
            }
        }

    }

    /** 沙地 ABS（setting.sand_abs）— 由目录自动生成，请勿手改。 */
    public static final class sand_abs {
        private sand_abs() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 572523572, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "ON" : null)), new int[]{r0}, new String[]{"SETTING_SAND_ABS_ENABLE_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.sand_abs with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1324564530, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.sand_abs with given params");
            }
        }

    }

    /** 座椅加热（setting.seat_heating）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class seat_heating {
        private seat_heating() {}

        public static SensorValue read(String area) {
            Runtime.requireProfile("setting.seat_heating");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1335885835, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_DRIVER_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1335885843, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_PASSENGER_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1214251019, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_LEFT_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1214251027, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_RIGHT_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1335885835, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_DRIVER_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1335885843, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_PASSENGER_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1214251019, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_LEFT_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1214251027, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_RIGHT_SEAT_HEATING_STATE"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 702545948, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MAIN_DRIVE_SEAT_HEATING_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 711983132, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_PASSENGER_SEAT_HEATING_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 655360044, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_LEFT_SEAT_HEATING_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 655360052, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_RIGHT_SEAT_HEATING_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_heating with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.requireProfile("setting.seat_heating");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            Runtime.checkAnyRange("value", value, 0, 2, 0, 3, 1, 5);
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122068, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122076, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122084, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122092, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122068, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122076, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122084, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122092, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252180, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252188, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252196, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252204, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_heating with given params");
            }
        }

    }

    /** 关闭五档座椅加热（setting.seat_heating_off）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class seat_heating_off {
        private seat_heating_off() {}

        public static void write(String area) {
            Runtime.requireProfile("setting.seat_heating_off");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248084, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248092, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248100, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248108, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_heating_off with given params");
            }
        }

    }

    /** 座椅按摩强度（setting.seat_massage_level）— 由目录自动生成，请勿手改。 */
    public static final class seat_massage_level {
        private seat_massage_level() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver")) {
                int r0 = Runtime.readFid(1000, 1335885876, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_FRONT_LEFT_SEAT_MASSAGE_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int r0 = Runtime.readFid(1000, 1335885884, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_FRONT_RIGHT_SEAT_MASSAGE_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearLeft")) {
                int r0 = Runtime.readFid(1000, 1335885860, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_LEFT_SEAT_MASSAGE_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearRight")) {
                int r0 = Runtime.readFid(1000, 1335885868, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_RIGHT_SEAT_MASSAGE_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_massage_level with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            Runtime.checkRange("value", value, 1, 3);
            if (Runtime.inStr(area, "driver")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276190736, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276190739, v0);
            }
            else if (Runtime.inStr(area, "rearLeft")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276190746, v0);
            }
            else if (Runtime.inStr(area, "rearRight")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276190749, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_massage_level with given params");
            }
        }

    }

    /** 座椅按摩模式（setting.seat_massage_mode）— 由目录自动生成，请勿手改。 */
    public static final class seat_massage_mode {
        private seat_massage_mode() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver")) {
                int r0 = Runtime.readFid(1000, 1335885872, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                if (r0 == 5) { d0 = 4.0; d0_ok = true; }
                if (r0 == 6) { d0 = 5.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 0 ? "off" : (r0 == 1 ? "wave" : (r0 == 2 ? "pulse" : (r0 == 3 ? "stretch" : (r0 == 4 ? "relax" : (r0 == 5 ? "soothe" : null)))))), new int[]{r0}, new String[]{"SET_FRONT_LEFT_SEAT_MASSAGE_MODE"});
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int r0 = Runtime.readFid(1000, 1335885880, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                if (r0 == 5) { d0 = 4.0; d0_ok = true; }
                if (r0 == 6) { d0 = 5.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 0 ? "off" : (r0 == 1 ? "wave" : (r0 == 2 ? "pulse" : (r0 == 3 ? "stretch" : (r0 == 4 ? "relax" : (r0 == 5 ? "soothe" : null)))))), new int[]{r0}, new String[]{"SET_FRONT_RIGHT_SEAT_MASSAGE_MODE"});
            }
            else if (Runtime.inStr(area, "rearLeft")) {
                int r0 = Runtime.readFid(1000, 1335885856, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                if (r0 == 5) { d0 = 4.0; d0_ok = true; }
                if (r0 == 6) { d0 = 5.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 0 ? "off" : (r0 == 1 ? "wave" : (r0 == 2 ? "pulse" : (r0 == 3 ? "stretch" : (r0 == 4 ? "relax" : (r0 == 5 ? "soothe" : null)))))), new int[]{r0}, new String[]{"SET_REAR_LEFT_SEAT_MASSAGE_MODE"});
            }
            else if (Runtime.inStr(area, "rearRight")) {
                int r0 = Runtime.readFid(1000, 1335885864, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                if (r0 == 5) { d0 = 4.0; d0_ok = true; }
                if (r0 == 6) { d0 = 5.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 0 ? "off" : (r0 == 1 ? "wave" : (r0 == 2 ? "pulse" : (r0 == 3 ? "stretch" : (r0 == 4 ? "relax" : (r0 == 5 ? "soothe" : null)))))), new int[]{r0}, new String[]{"SET_REAR_RIGHT_SEAT_MASSAGE_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_massage_mode with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            Runtime.checkEnum("value", value, 0, 1, 2, 3, 4, 5);
            if (Runtime.inStr(area, "driver")) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if ((value == 4)) { v0 = 5; v0_ok = true; }
                if ((value == 5)) { v0 = 6; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276190752, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if ((value == 4)) { v0 = 5; v0_ok = true; }
                if ((value == 5)) { v0 = 6; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276190756, v0);
            }
            else if (Runtime.inStr(area, "rearLeft")) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if ((value == 4)) { v0 = 5; v0_ok = true; }
                if ((value == 5)) { v0 = 6; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276190760, v0);
            }
            else if (Runtime.inStr(area, "rearRight")) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if ((value == 4)) { v0 = 5; v0_ok = true; }
                if ((value == 5)) { v0 = 6; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276190764, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_massage_mode with given params");
            }
        }

    }

    /** 保存座椅记忆（setting.seat_memory_save）— 由目录自动生成，请勿手改。 */
    public static final class seat_memory_save {
        private seat_memory_save() {}

        public static void write(String area, int value) {
            Runtime.checkSelector("area", area, "frontPassenger", "rearLeft", "rearRight");
            Runtime.checkRange("value", value, 1, 2);
            if (Runtime.inStr(area, "frontPassenger")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276186680, v0);
            }
            else if (Runtime.inStr(area, "rearLeft")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276186682, v0);
            }
            else if (Runtime.inStr(area, "rearRight")) {
                int v0 = value;
                Runtime.writeFid(1000, 1276186684, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_memory_save with given params");
            }
        }

    }

    /** 座椅预设位置（setting.seat_position）— 由目录自动生成，请勿手改。 */
    public static final class seat_position {
        private seat_position() {}

        public static void write(int position, String area) {
            Runtime.checkEnum("position", position, 1, 2, 3, 4);
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight", "thirdRowLeft", "thirdRowRight");
            if (Runtime.inStr(area, "driver") && Runtime.inInt(position, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324650558, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inInt(position, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679184, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inInt(position, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679200, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inInt(position, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679216, v0);
            }
            else if (Runtime.inStr(area, "thirdRowLeft") && Runtime.inInt(position, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679224, v0);
            }
            else if (Runtime.inStr(area, "thirdRowRight") && Runtime.inInt(position, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679232, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.inInt(position, 2)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324650560, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inInt(position, 2)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679186, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inInt(position, 2)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679202, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inInt(position, 2)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679218, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.inInt(position, 3)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324650562, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inInt(position, 3)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679188, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inInt(position, 3)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679204, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inInt(position, 3)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679220, v0);
            }
            else if (Runtime.inStr(area, "thirdRowLeft") && Runtime.inInt(position, 4)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679222, v0);
            }
            else if (Runtime.inStr(area, "thirdRowRight") && Runtime.inInt(position, 4)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324679230, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_position with given params");
            }
        }

    }

    /** 座椅通风（setting.seat_ventilation）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class seat_ventilation {
        private seat_ventilation() {}

        public static SensorValue read(String area) {
            Runtime.requireProfile("setting.seat_ventilation");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1335885832, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_DRIVER_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1335885840, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_PASSENGER_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1214251016, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_LEFT_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 2)) {
                int r0 = Runtime.readFid(1000, 1214251024, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_RIGHT_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1335885832, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_DRIVER_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1335885840, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_PASSENGER_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1214251016, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_LEFT_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1214251024, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                if (r0 == 4) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_REAR_RIGHT_SEAT_VENTILATING_STATE"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 702545944, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MAIN_DRIVE_SEAT_VENTILATING_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 711983128, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_PASSENGER_SEAT_VENTILATING_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 655360040, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_LEFT_SEAT_VENTILATING_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 5)) {
                int r0 = Runtime.readFid(1000, 655360048, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_RIGHT_SEAT_VENTILATING_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_ventilation with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.requireProfile("setting.seat_ventilation");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            Runtime.checkAnyRange("value", value, 0, 2, 0, 3, 1, 5);
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122064, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122072, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122088, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122096, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122064, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122072, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122088, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 3)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 0)) { v0 = 1; v0_ok = true; }
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 4; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1125122096, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252176, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252184, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252192, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276252200, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_ventilation with given params");
            }
        }

    }

    /** 关闭五档座椅通风（setting.seat_ventilation_off）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class seat_ventilation_off {
        private seat_ventilation_off() {}

        public static void write(String area) {
            Runtime.requireProfile("setting.seat_ventilation_off");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248080, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248088, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248096, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("seatHeating", 5)) {
                int v0 = 2;
                Runtime.writeFid(1000, 1276248104, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.seat_ventilation_off with given params");
            }
        }

    }

    /** SOC 保电目标（setting.soc_target）— 由目录自动生成，请勿手改。 */
    public static final class soc_target {
        private soc_target() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 873463848, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"SET_DR_SOC_TARGET"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.soc_target with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 20, 70);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 1311768632, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.soc_target with given params");
            }
        }

    }

    /** 转向助力模式（setting.steering_assist_mode）— 由目录自动生成，请勿手改。 */
    public static final class steering_assist_mode {
        private steering_assist_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 616562704, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "COMFORT" : (r0 == 2 ? "SPORT" : null)), new int[]{r0}, new String[]{"SET_DR_ST_ASSIS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.steering_assist_mode with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 1311768610, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.steering_assist_mode with given params");
            }
        }

    }

    /** 方向盘加热开关（setting.steering_wheel_heating）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class steering_wheel_heating {
        private steering_wheel_heating() {}

        public static SensorValue read() {
            Runtime.requireProfile("setting.steering_wheel_heating");
            if (Runtime.profileAllows("steeringWheelHeating", 1, 3, 5)) {
                int r0 = Runtime.readFid(1000, 1116733454, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_STEERING_WHEEL_HEAT_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.steering_wheel_heating with given params");
            }
        }

        public static void write(boolean value) {
            Runtime.requireProfile("setting.steering_wheel_heating");
            if (Runtime.profileAllows("steeringWheelHeating", 1, 3, 5)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 944767029, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.steering_wheel_heating with given params");
            }
        }

    }

    /** 方向盘加热档位（setting.steering_wheel_heating_level）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class steering_wheel_heating_level {
        private steering_wheel_heating_level() {}

        public static SensorValue read() {
            Runtime.requireProfile("setting.steering_wheel_heating_level");
            if (Runtime.profileAllows("steeringWheelHeating", 3)) {
                int r0 = Runtime.readFid(1000, 1116733465, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_AUTO_STEER_WHEEL_HEATING_GEAR"});
            }
            else if (Runtime.profileAllows("steeringWheelHeating", 5)) {
                int r0 = Runtime.readFid(1000, 1116733465, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SET_AUTO_STEER_WHEEL_HEATING_GEAR"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.steering_wheel_heating_level with given params");
            }
        }

        public static void write(int value) {
            Runtime.requireProfile("setting.steering_wheel_heating_level");
            Runtime.checkAnyRange("value", value, 1, 3, 1, 5);
            if (Runtime.profileAllows("steeringWheelHeating", 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276256274, v0);
            }
            else if (Runtime.profileAllows("steeringWheelHeating", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1276256274, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.steering_wheel_heating_level with given params");
            }
        }

    }

    /** 悬架超高或超低模式（setting.suspension_extreme_height）— 由目录自动生成，请勿手改。 */
    public static final class suspension_extreme_height {
        private suspension_extreme_height() {}

        public static void write(int mode, int action) {
            Runtime.checkEnum("mode", mode, 1, 2);
            Runtime.checkEnum("action", action, 2, 3);
            if (Runtime.inInt(mode, 1)) {
                int v0 = action;
                Runtime.writeFid(1000, 1324617788, v0);
            }
            else if (Runtime.inInt(mode, 2)) {
                int v0 = action;
                Runtime.writeFid(1000, 1324617790, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.suspension_extreme_height with given params");
            }
        }

    }

    /** 悬架高度调节（setting.suspension_height_command）— 由目录自动生成，请勿手改。 */
    public static final class suspension_height_command {
        private suspension_height_command() {}

        public static void write(int nativeRoute, int value) {
            Runtime.checkEnum("nativeRoute", nativeRoute, 1000, 1004);
            Runtime.checkEnum("value", value, 1, 2);
            if (Runtime.inInt(nativeRoute, 1000)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324617772, v0);
            }
            else if (Runtime.inInt(nativeRoute, 1004)) {
                int v0 = value;
                Runtime.writeFid(1004, 1324617772, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.suspension_height_command with given params");
            }
        }

    }

    /** 悬架高度锁定（setting.suspension_height_lock）— 由目录自动生成，请勿手改。 */
    public static final class suspension_height_lock {
        private suspension_height_lock() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 629145680, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"SETTING_SUSPENSION_HEIGHT_MODE_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.suspension_height_lock with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 2; v0_ok = true; }
                if ((value == false)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1324617760, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.suspension_height_lock with given params");
            }
        }

    }

    /** 云辇模式（setting.suspension_mode）— 由目录自动生成，请勿手改。 */
    public static final class suspension_mode {
        private suspension_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 938475528, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "COMFORT" : (r0 == 2 ? "SPORT" : null)), new int[]{r0}, new String[]{"SET_DYNAMIC_SUSPENSION_ACTUAL_OPERATING_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.suspension_mode with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 944767034, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.suspension_mode with given params");
            }
        }

    }

    /** 矢量控制模式（setting.vector_control）— 由目录自动生成，请勿手改。 */
    public static final class vector_control {
        private vector_control() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 533725224, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "comfort" : (r0 == 2 ? "sport" : null)), new int[]{r0}, new String[]{"SETTING_CVC_WORKING_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.vector_control with given params");
            }
        }

        public static void write(int mode) {
            Runtime.checkEnum("mode", mode, 1, 2);
            if (true) {
                int v0 = mode;
                Runtime.writeFid(1000, 1324617782, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for setting.vector_control with given params");
            }
        }

    }

}
