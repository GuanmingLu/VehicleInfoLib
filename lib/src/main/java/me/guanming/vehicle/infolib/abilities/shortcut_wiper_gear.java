package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** shortcut_wiper_gear — 能力分组容器。 */
public final class shortcut_wiper_gear {
    private shortcut_wiper_gear() {}

    /** 前雨刮原始档位（shortcut_wiper_gear.front_gear）— 由目录自动生成，请勿手改。 */
    public static final class front_gear {
        private front_gear() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1046, 321912848, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"WIPER_FRONT_WIPER_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_wiper_gear.front_gear with given params");
            }
        }

    }

}
