package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** gearbox — 能力分组容器。 */
public final class gearbox {
    private gearbox() {}

    /** 当前挡位（gearbox.gear）— 由目录自动生成，请勿手改。 */
    public static final class gear {
        private gear() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1011, 555745336, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "P" : (r0 == 2 ? "R" : (r0 == 3 ? "N" : (r0 == 4 ? "D" : (r0 == 5 ? "M" : (r0 == 6 ? "S" : null)))))), new int[]{r0}, new String[]{"GEARBOX_AUTO_MODE_TYPE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for gearbox.gear with given params");
            }
        }

    }

}
