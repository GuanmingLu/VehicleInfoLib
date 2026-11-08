package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** sensor — 能力分组容器。 */
public final class sensor {
    private sensor() {}

    /** 车辆姿态角（sensor.attitude）— 由目录自动生成，请勿手改。 */
    public static final class attitude {
        private attitude() {}

        public static SensorValue read(int axis) {
            Runtime.checkEnum("axis", axis, 1, 2, 3);
            if (Runtime.inInt(axis, 1)) {
                int r0 = Runtime.readFid(1023, 754975024, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "degree", null, new int[]{r0}, new String[]{"SETTING_PITCH_ANGLE_CALCULATION"});
            }
            else if (Runtime.inInt(axis, 2)) {
                int r0 = Runtime.readFid(1023, 754975032, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "degree", null, new int[]{r0}, new String[]{"SETTING_ROLL_ANGLE_CALCULATION"});
            }
            else if (Runtime.inInt(axis, 3)) {
                int r0 = Runtime.readFid(1043, 573571116, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "degree", null, new int[]{r0}, new String[]{"SENSOR_AUTO_SLOPE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for sensor.attitude with given params");
            }
        }

    }

    /** 雨量（sensor.rain_amount）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class rain_amount {
        private rain_amount() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("SENSOR_RAIN_FALL_VALUE", "Sensor");
                int r0 = Runtime.readFid(1043, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"SENSOR_RAIN_FALL_VALUE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for sensor.rain_amount with given params");
            }
        }

    }

    /** 方向盘转角（sensor.steering_wheel_angle）— 由目录自动生成，请勿手改。 */
    public static final class steering_wheel_angle {
        private steering_wheel_angle() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1001, 300941320, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_STEERING_WHEEL_ANGEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for sensor.steering_wheel_angle with given params");
            }
        }

    }

    /** 方向盘转速（sensor.steering_wheel_speed）— 由目录自动生成，请勿手改。 */
    public static final class steering_wheel_speed {
        private steering_wheel_speed() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1001, 300941336, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_STEERING_WHEEL_SPEED"});
            }
            else {
                throw new IllegalArgumentException("No matching case for sensor.steering_wheel_speed with given params");
            }
        }

    }

}
