package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** vehicle — 能力分组容器。 */
public final class vehicle {
    private vehicle() {}

    /** 加速踏板深度（vehicle.accelerator_depth）— 由目录自动生成，请勿手改。 */
    public static final class accelerator_depth {
        private accelerator_depth() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1013, 874512392, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"SPEED_ACCELERATOR_S"});
            }
            else {
                throw new IllegalArgumentException("No matching case for vehicle.accelerator_depth with given params");
            }
        }

    }

    /** 制动踏板深度（vehicle.brake_pedal_depth）— 由目录自动生成，请勿手改。 */
    public static final class brake_pedal_depth {
        private brake_pedal_depth() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1013, 874512400, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"SPEED_BRAKE_S"});
            }
            else {
                throw new IllegalArgumentException("No matching case for vehicle.brake_pedal_depth with given params");
            }
        }

    }

    /** 纵向加速度（vehicle.longitudinal_acceleration）— 由目录自动生成，请勿手改。 */
    public static final class longitudinal_acceleration {
        private longitudinal_acceleration() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1013, 1033220112, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "metersPerSecondSquared", null, new int[]{r0}, new String[]{"SPEED_ACCELERATE_VALUE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for vehicle.longitudinal_acceleration with given params");
            }
        }

    }

    /** 当前车速（vehicle.speed）— 由目录自动生成，请勿手改。 */
    public static final class speed {
        private speed() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1013, 303038472, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SPEED_AUTO_SPEED"});
            }
            else {
                throw new IllegalArgumentException("No matching case for vehicle.speed with given params");
            }
        }

    }

}
