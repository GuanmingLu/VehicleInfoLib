package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** instrument — 能力分组容器。 */
public final class instrument {
    private instrument() {}

    /** 仪表背光亮度（instrument.backlight_brightness）— 由目录自动生成，请勿手改。 */
    public static final class backlight_brightness {
        private backlight_brightness() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1007, 1274019896, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 1) { d0 = 0.0; d0_ok = true; }
                if (r0 == 2) { d0 = 10.0; d0_ok = true; }
                if (r0 == 3) { d0 = 20.0; d0_ok = true; }
                if (r0 == 4) { d0 = 30.0; d0_ok = true; }
                if (r0 == 5) { d0 = 40.0; d0_ok = true; }
                if (r0 == 6) { d0 = 50.0; d0_ok = true; }
                if (r0 == 7) { d0 = 60.0; d0_ok = true; }
                if (r0 == 8) { d0 = 70.0; d0_ok = true; }
                if (r0 == 9) { d0 = 80.0; d0_ok = true; }
                if (r0 == 10) { d0 = 90.0; d0_ok = true; }
                if (r0 == 11) { d0 = 100.0; d0_ok = true; }
                if (r0 == 12) { d0 = 0.0; d0_ok = true; }
                if (r0 == 13) { d0 = 10.0; d0_ok = true; }
                if (r0 == 14) { d0 = 20.0; d0_ok = true; }
                if (r0 == 15) { d0 = 30.0; d0_ok = true; }
                if (r0 == 16) { d0 = 40.0; d0_ok = true; }
                if (r0 == 17) { d0 = 50.0; d0_ok = true; }
                if (r0 == 18) { d0 = 60.0; d0_ok = true; }
                if (r0 == 19) { d0 = 70.0; d0_ok = true; }
                if (r0 == 20) { d0 = 80.0; d0_ok = true; }
                if (r0 == 21) { d0 = 90.0; d0_ok = true; }
                if (r0 == 22) { d0 = 100.0; d0_ok = true; }
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"INSTRUMENT_BACKLIGHT_BRIGHTNESS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for instrument.backlight_brightness with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 0, 100);
            if (true) {
                int v0 = (int) (value - (-10));
                Runtime.writeFid(1007, 1043333144, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for instrument.backlight_brightness with given params");
            }
        }

    }

    /** 仪表亮度百分比（instrument.brightness_percent）— 由目录自动生成，请勿手改。 */
    public static final class brightness_percent {
        private brightness_percent() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1007, 1179648012, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 * (0.1);
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"INSTRUMENT_BRIGHTNESS_PERCENTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for instrument.brightness_percent with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkRange("value", value, 0, 100);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1007, 507514927, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for instrument.brightness_percent with given params");
            }
        }

    }

}
