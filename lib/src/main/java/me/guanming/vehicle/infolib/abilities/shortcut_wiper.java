package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** shortcut_wiper — 能力分组容器。 */
public final class shortcut_wiper {
    private shortcut_wiper() {}

    /** 前雨刮速度（shortcut_wiper.front_speed）— 由目录自动生成，请勿手改。 */
    public static final class front_speed {
        private front_speed() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1023, 1196425250, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SETTING_FRONT_RAIN_WIPER_SPEED"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_wiper.front_speed with given params");
            }
        }

    }

}
