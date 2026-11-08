package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** air_quality — 能力分组容器。 */
public final class air_quality {
    private air_quality() {}

    /** PM2.5 浓度（air_quality.pm25）— 由目录自动生成，请勿手改。 */
    public static final class pm25 {
        private pm25() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1008, 1185939472, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "microgramsPerCubicMeter", null, new int[]{r0}, new String[]{"PM2P5_VALUE_IN"});
            }
            else if (Runtime.inInt(area, 2)) {
                int r0 = Runtime.readFid(1008, 1331691548, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "microgramsPerCubicMeter", null, new int[]{r0}, new String[]{"PM2P5_VALUE_OUT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for air_quality.pm25 with given params");
            }
        }

    }

    /** PM2.5 检测开关（air_quality.pm25_detection）— 由目录自动生成，请勿手改。 */
    public static final class pm25_detection {
        private pm25_detection() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1008, 1331691577, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 2) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"PM25_DUAL_CHANNEL_DETECT_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for air_quality.pm25_detection with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1008, 1330642970, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for air_quality.pm25_detection with given params");
            }
        }

    }

    /** 空气净化（air_quality.purification）— 由目录自动生成，请勿手改。 */
    public static final class purification {
        private purification() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1033895944, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "NOT_RUNNING" : (r0 == 1 ? "RUNNING" : (r0 == 2 ? "ENDED" : (r0 == 3 ? "TERMINATED" : null)))), new int[]{r0}, new String[]{"AC_QUICK_CLEAN_AIR"});
            }
            else {
                throw new IllegalArgumentException("No matching case for air_quality.purification with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1330642960, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for air_quality.purification with given params");
            }
        }

    }

}
