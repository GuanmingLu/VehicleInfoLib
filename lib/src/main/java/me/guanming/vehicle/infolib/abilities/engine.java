package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** engine — 能力分组容器。 */
public final class engine {
    private engine() {}

    /** 发动机发电功率（engine.charge_power）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class charge_power {
        private charge_power() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("ENGINE_CHARGE_POWER", "Engine");
                int r0 = Runtime.readFid(1012, f0, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "kilowatt", null, new int[]{r0}, new String[]{"ENGINE_CHARGE_POWER"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.charge_power with given params");
            }
        }

    }

    /** 发动机扭矩（engine.indicated_torque）— 由目录自动生成，请勿手改。 */
    public static final class indicated_torque {
        private indicated_torque() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 283115528, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ENGINE_INDICATED_TORQUE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.indicated_torque with given params");
            }
        }

    }

    /** 低压侧电流（engine.low_voltage_side_current）— 由目录自动生成，请勿手改。 */
    public static final class low_voltage_side_current {
        private low_voltage_side_current() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 919601192, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "ampere", null, new int[]{r0}, new String[]{"ENERGY_LOW_VOLTAGE_SIDE_CURRENT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.low_voltage_side_current with given params");
            }
        }

    }

    /** 低压侧电压（engine.low_voltage_side_voltage）— 由目录自动生成，请勿手改。 */
    public static final class low_voltage_side_voltage {
        private low_voltage_side_voltage() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 919601184, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "volt", null, new int[]{r0}, new String[]{"ENERGY_LOW_VOLTAGE_SIDE_VOLTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.low_voltage_side_voltage with given params");
            }
        }

    }

    /** 电机输入电压（engine.motor_input_voltage）— 由目录自动生成，请勿手改。 */
    public static final class motor_input_voltage {
        private motor_input_voltage() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 1141899304, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "volt", null, new int[]{r0}, new String[]{"ENGINE_MOTOR_INPUT_VOLTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.motor_input_voltage with given params");
            }
        }

    }

    /** 电机转速（engine.motor_rpm）— 由目录自动生成，请勿手改。 */
    public static final class motor_rpm {
        private motor_rpm() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 40, 41);
            if (Runtime.inInt(area, 40)) {
                int r0 = Runtime.readFid(1012, 1141899272, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "revolutionsPerMinute", null, new int[]{r0}, new String[]{"ENGINE_FRONT_MOTOR_SPEED"});
            }
            else if (Runtime.inInt(area, 41)) {
                int r0 = Runtime.readFid(1012, 621805576, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "revolutionsPerMinute", null, new int[]{r0}, new String[]{"ENGINE_REAR_MOTOR_SPEED"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.motor_rpm with given params");
            }
        }

    }

    /** 电机温度（engine.motor_temperature）— 由目录自动生成，请勿手改。 */
    public static final class motor_temperature {
        private motor_temperature() {}

        public static SensorValue read(int source) {
            Runtime.checkEnum("source", source, 1, 2, 3, 4);
            if (Runtime.inInt(source, 1)) {
                int r0 = Runtime.readFid(1039, 1154482192, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"GB_FRONT_MOTOR_TEMP"});
            }
            else if (Runtime.inInt(source, 2)) {
                int r0 = Runtime.readFid(1039, 1155530768, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"GB_TREAR_MOTOR_TEMP"});
            }
            else if (Runtime.inInt(source, 3)) {
                int r0 = Runtime.readFid(1039, 1154482184, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"GB_FRONT_MOTOR_IPM_TEMP"});
            }
            else if (Runtime.inInt(source, 4)) {
                int r0 = Runtime.readFid(1039, 1155530760, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"GB_REAR_MOTOR_IPM_TEMP"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.motor_temperature with given params");
            }
        }

    }

    /** 电机扭矩（engine.motor_torque）— 由目录自动生成，请勿手改。 */
    public static final class motor_torque {
        private motor_torque() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 40, 41);
            if (Runtime.inInt(area, 40)) {
                int r0 = Runtime.readFid(1012, 1141899288, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ENGINE_FRONT_MOTOR_TORQUE"});
            }
            else if (Runtime.inInt(area, 41)) {
                int r0 = Runtime.readFid(1012, 621805592, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ENGINE_REAR_MOTOR_TORQUE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.motor_torque with given params");
            }
        }

    }

    /** 机油液位（engine.oil_level）— 由目录自动生成，请勿手改。 */
    public static final class oil_level {
        private oil_level() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 89129016, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ENGINE_OIL_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.oil_level with given params");
            }
        }

    }

    /** 动力功率（engine.power）— 由目录自动生成，请勿手改。 */
    public static final class power {
        private power() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 339738656, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "kilowatt", null, new int[]{r0}, new String[]{"ENGINE_POWER"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.power with given params");
            }
        }

    }

    /** 发动机转速（engine.rpm）— 由目录自动生成，请勿手改。 */
    public static final class rpm {
        private rpm() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 282066952, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "revolutionsPerMinute", null, new int[]{r0}, new String[]{"ENGINE_SPEED_GB"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.rpm with given params");
            }
        }

    }

    /** 引擎模拟音（engine.voice_simulator）— 由目录自动生成，请勿手改。 */
    public static final class voice_simulator {
        private voice_simulator() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1012, 1223688202, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"ENGINE_VOICE_SIMULATOR_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.voice_simulator with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1012, 1043333152, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for engine.voice_simulator with given params");
            }
        }

    }

}
