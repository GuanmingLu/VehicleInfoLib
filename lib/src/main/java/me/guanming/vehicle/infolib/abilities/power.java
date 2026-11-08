package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** power — 能力分组容器。 */
public final class power {
    private power() {}

    /** 车机 ACC 电源（power.acc_status）— 由目录自动生成，请勿手改。 */
    public static final class acc_status {
        private acc_status() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1005, -1728053193, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ON" : null)), new int[]{r0}, new String[]{"POWER_ACC_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for power.acc_status with given params");
            }
        }

    }

}
