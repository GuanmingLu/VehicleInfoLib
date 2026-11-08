package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** wiper — 能力分组容器。 */
public final class wiper {
    private wiper() {}

    /** 自动雨刮（wiper.automatic）— 由目录自动生成，请勿手改。 */
    public static final class automatic {
        private automatic() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1023, 321912862, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ON" : (r0 == 2 ? "OFF" : null)), new int[]{r0}, new String[]{"SET_AUTO_RAIN_WIPER_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.automatic with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1023, 503316540, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.automatic with given params");
            }
        }

    }

    /** 前雨刮档位（wiper.front_wiper_level）— 由目录自动生成，请勿手改。 */
    public static final class front_wiper_level {
        private front_wiper_level() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1046, 321912848, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"WIPER_FRONT_WIPER_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.front_wiper_level with given params");
            }
        }

    }

    /** 雨刮继电器状态（wiper.relay_state）— 由目录自动生成，请勿手改。 */
    public static final class relay_state {
        private relay_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1046, 1336934438, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ACTUATION" : (r0 == 2 ? "DISCONNECT" : null)), new int[]{r0}, new String[]{"WIPER_RELAY_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.relay_state with given params");
            }
        }

    }

    /** 雨刮复位状态（wiper.reset_state）— 由目录自动生成，请勿手改。 */
    public static final class reset_state {
        private reset_state() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "frontWindshield", "rearWindshield");
            if (Runtime.inStr(area, "frontWindshield")) {
                int r0 = Runtime.readFid(1046, 1196425224, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "NOT_RESET" : (r0 == 2 ? "RESET" : null)), new int[]{r0}, new String[]{"WIPER_AREA_FRONT_STATE"});
            }
            else if (Runtime.inStr(area, "rearWindshield")) {
                int r0 = Runtime.readFid(1046, 1196425226, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "NOT_RESET" : (r0 == 2 ? "RESET" : null)), new int[]{r0}, new String[]{"WIPER_AREA_REAR_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.reset_state with given params");
            }
        }

    }

    /** 雨刮灵敏度（wiper.sensitivity）— 由目录自动生成，请勿手改。 */
    public static final class sensitivity {
        private sensitivity() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1046, 321912864, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"WIPER_WINDSCREEN_WIPER_SENSITIVITY"});
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.sensitivity with given params");
            }
        }

    }

    /** 雨刮检修模式（wiper.service_mode）— 由目录自动生成，请勿手改。 */
    public static final class service_mode {
        private service_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1196425244, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ON" : (r0 == 2 ? "OFF" : null)), new int[]{r0}, new String[]{"SET_FRONT_WINDSCREEN_WIPER_OVERHAUL_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.service_mode with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 1; v0_ok = true; }
                if ((enabled == false)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 1330642972, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for wiper.service_mode with given params");
            }
        }

    }

}
