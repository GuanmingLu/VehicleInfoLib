package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** energy — 能力分组容器。 */
public final class energy {
    private energy() {}

    /** 长续航模式（energy.extended_range）— 由目录自动生成，请勿手改。 */
    public static final class extended_range {
        private extended_range() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 450889768, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "ON" : null)), new int[]{r0}, new String[]{"SETTING_MANDATORY_EV_MODE_SWITCH_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.extended_range with given params");
            }
        }

        public static void write(boolean enabled) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((enabled == true)) { v0 = 2; v0_ok = true; }
                if ((enabled == false)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + enabled);
                Runtime.writeFid(1000, 753971268, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.extended_range with given params");
            }
        }

    }

    /** 能量模式（energy.mode）— 由目录自动生成，请勿手改。 */
    public static final class mode {
        private mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1006, 874512420, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "STOPPED" : (r0 == 1 ? "EV" : (r0 == 2 ? "FORCED_EV" : (r0 == 3 ? "HEV" : (r0 == 4 ? "FUEL" : (r0 == 5 ? "HOLD" : null)))))), new int[]{r0}, new String[]{"ENERGY_MODE_INSTRUMENT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.mode with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3);
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 1; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1006, 734003254, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.mode with given params");
            }
        }

    }

    /** 驾驶模式（energy.operation_mode）— 由目录自动生成，请勿手改。 */
    public static final class operation_mode {
        private operation_mode() {}

        public static SensorValue read(int system) {
            Runtime.checkEnum("system", system, 1, 2);
            if (Runtime.inInt(system, 1)) {
                int r0 = Runtime.readFid(1006, 555745294, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ECO" : (r0 == 2 ? "SPORT" : (r0 == 3 ? "NORMAL" : (r0 == 4 ? "SNOW" : (r0 == 5 ? "MUD" : (r0 == 6 ? "SAND" : null)))))), new int[]{r0}, new String[]{"ENERGY_OPERATION_MODE"});
            }
            else if (Runtime.inInt(system, 2)) {
                int r0 = Runtime.readFid(1000, 255852712, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SETTING_TARGET_DRIVING_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.operation_mode with given params");
            }
        }

        public static void write(int system, int value) {
            Runtime.checkEnum("system", system, 1, 2);
            Runtime.checkEnum("value", value, 1, 2, 3, 4, 5, 6, 7, 8, 19, 20, 23);
            if (Runtime.inInt(system, 1)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 1; v0_ok = true; }
                if ((value == 4)) { v0 = 4; v0_ok = true; }
                if ((value == 5)) { v0 = 5; v0_ok = true; }
                if ((value == 6)) { v0 = 6; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1006, 734003229, v0);
            }
            else if (Runtime.inInt(system, 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if ((value == 3)) { v0 = 1; v0_ok = true; }
                if ((value == 4)) { v0 = 4; v0_ok = true; }
                if ((value == 5)) { v0 = 6; v0_ok = true; }
                if ((value == 6)) { v0 = 5; v0_ok = true; }
                if ((value == 7)) { v0 = 7; v0_ok = true; }
                if ((value == 8)) { v0 = 8; v0_ok = true; }
                if ((value == 19)) { v0 = 19; v0_ok = true; }
                if ((value == 20)) { v0 = 20; v0_ok = true; }
                if ((value == 23)) { v0 = 23; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1276260400, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.operation_mode with given params");
            }
        }

    }

    /** 能量回收强度（energy.regeneration）— 由目录自动生成，请勿手改。 */
    public static final class regeneration {
        private regeneration() {}

        public static SensorValue read(int market) {
            Runtime.checkEnum("market", market, 1, 2);
            if (Runtime.inInt(market, 1)) {
                int r0 = Runtime.readFid(1006, 873463842, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                if (r0 == 2) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 1 ? "STANDARD" : (r0 == 2 ? "STRONG" : null)), new int[]{r0}, new String[]{"SET_DR_ENERGY_FB"});
            }
            else if (Runtime.inInt(market, 2)) {
                int r0 = Runtime.readFid(1006, 873463842, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 2) { d0 = 1.0; d0_ok = true; }
                if (r0 == 3) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 1 ? "STANDARD" : (r0 == 2 ? "STRONG" : null)), new int[]{r0}, new String[]{"SET_DR_ENERGY_FB"});
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.regeneration with given params");
            }
        }

        public static void write(int market, int value) {
            Runtime.checkEnum("market", market, 1, 2);
            Runtime.checkEnum("value", value, 1, 2);
            if (Runtime.inInt(market, 1)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 1; v0_ok = true; }
                if ((value == 2)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1006, 1311768613, v0);
            }
            else if (Runtime.inInt(market, 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 2; v0_ok = true; }
                if ((value == 2)) { v0 = 3; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1006, 1311768613, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for energy.regeneration with given params");
            }
        }

    }

}
