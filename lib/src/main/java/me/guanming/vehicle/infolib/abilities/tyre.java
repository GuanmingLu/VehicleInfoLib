package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** tyre — 能力分组容器。 */
public final class tyre {
    private tyre() {}

    /** 轮胎压力（tyre.pressure）— 由目录自动生成，请勿手改。 */
    public static final class pressure {
        private pressure() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 13, 14, 15, 16);
            if (Runtime.inInt(area, 13)) {
                int r0 = Runtime.readFid(1016, -1728052956, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"TYRE_PRESSURE_VALUE_LEFT_FRONT"});
            }
            else if (Runtime.inInt(area, 14)) {
                int r0 = Runtime.readFid(1016, -1728052952, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"TYRE_PRESSURE_VALUE_RIGHT_FRONT"});
            }
            else if (Runtime.inInt(area, 15)) {
                int r0 = Runtime.readFid(1016, -1728052948, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"TYRE_PRESSURE_VALUE_LEFT_REAR"});
            }
            else if (Runtime.inInt(area, 16)) {
                int r0 = Runtime.readFid(1016, -1728052944, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"TYRE_PRESSURE_VALUE_RIGHT_REAR"});
            }
            else {
                throw new IllegalArgumentException("No matching case for tyre.pressure with given params");
            }
        }

    }

}
