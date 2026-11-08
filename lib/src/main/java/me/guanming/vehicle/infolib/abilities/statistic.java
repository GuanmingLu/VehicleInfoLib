package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** statistic — 能力分组容器。 */
public final class statistic {
    private statistic() {}

    /** 动力电池容量（statistic.battery_capacity）— 由目录自动生成，请勿手改。 */
    public static final class battery_capacity {
        private battery_capacity() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1001, 1146095632, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 * (0.1);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"BODYWORK_BATTERY_CAPACITY"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.battery_capacity with given params");
            }
        }

    }

    /** 电池健康参考值（statistic.battery_health）— 由目录自动生成，请勿手改。 */
    public static final class battery_health {
        private battery_health() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1145045032, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"STATISTIC_BATTERY_HEALTHY_INDEX"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.battery_health with given params");
            }
        }

    }

    /** 动力电池电量（statistic.battery_soc）— 由目录自动生成，请勿手改。 */
    public static final class battery_soc {
        private battery_soc() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1246777400, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"STATISTIC_ELEC_PERCENTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.battery_soc with given params");
            }
        }

    }

    /** 电芯温度（statistic.battery_temperature）— 由目录自动生成，请勿手改。 */
    public static final class battery_temperature {
        private battery_temperature() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 50, 51, 52);
            if (Runtime.inInt(area, 50)) {
                int r0 = Runtime.readFid(1014, 1148190752, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-40);
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"STATISTIC_HIGHEST_BATTERY_TEMP"});
            }
            else if (Runtime.inInt(area, 51)) {
                int r0 = Runtime.readFid(1014, 1148190776, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-40);
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"STATISTIC_AVERAGE_BATTERY_TEMP"});
            }
            else if (Runtime.inInt(area, 52)) {
                int r0 = Runtime.readFid(1014, 1148190736, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 + (-40);
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"STATISTIC_LOWEST_BATTERY_TEMP"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.battery_temperature with given params");
            }
        }

    }

    /** 电芯电压（statistic.battery_voltage）— 由目录自动生成，请勿手改。 */
    public static final class battery_voltage {
        private battery_voltage() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 50, 52);
            if (Runtime.inInt(area, 50)) {
                int r0 = Runtime.readFid(1014, 1147142192, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 * (0.001);
                return Runtime.value(r0, d0, "volt", null, new int[]{r0}, new String[]{"STATISTIC_HIGHEST_BATTERY_VOLTAGE"});
            }
            else if (Runtime.inInt(area, 52)) {
                int r0 = Runtime.readFid(1014, 1147142160, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 * (0.001);
                return Runtime.value(r0, d0, "volt", null, new int[]{r0}, new String[]{"STATISTIC_LOWEST_BATTERY_VOLTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.battery_voltage with given params");
            }
        }

    }

    /** 冷却液温度（statistic.coolant_temperature）— 由目录自动生成，请勿手改。 */
    public static final class coolant_temperature {
        private coolant_temperature() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1033199672, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"STATISTIC_WATER_TEMPERATURE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.coolant_temperature with given params");
            }
        }

    }

    /** 近 200 米平均电耗（statistic.electric_consumption_last_200m）— 由目录自动生成，请勿手改。 */
    public static final class electric_consumption_last_200m {
        private electric_consumption_last_200m() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1032871952, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0 * (0.1);
                return Runtime.value(r0, d0, "kilowattHoursPer100Kilometers", null, new int[]{r0}, new String[]{"STATISTICS_AVERAGE_INSTANT_EV_LAST_200M"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.electric_consumption_last_200m with given params");
            }
        }

    }

    /** 平均电耗（statistic.electric_consumption_per_100km）— 由目录自动生成，请勿手改。 */
    public static final class electric_consumption_per_100km {
        private electric_consumption_per_100km() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1033195580, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"STATISTIC_TOTAL_ELEC_CON_PHM"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.electric_consumption_per_100km with given params");
            }
        }

    }

    /** 纯电剩余续航（statistic.electric_range）— 由目录自动生成，请勿手改。 */
    public static final class electric_range {
        private electric_range() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1246765118, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "kilometer", null, new int[]{r0}, new String[]{"STATISTIC_ELEC_DRIVING_RANGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.electric_range with given params");
            }
        }

    }

    /** 近 200 米平均油耗（statistic.fuel_consumption_last_200m）— 由目录自动生成，请勿手改。 */
    public static final class fuel_consumption_last_200m {
        private fuel_consumption_last_200m() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1032871964, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "litersPer100Kilometers", null, new int[]{r0}, new String[]{"STATISTICS_AVERAGE_INSTANT_FUEL_LAST_200M"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.fuel_consumption_last_200m with given params");
            }
        }

    }

    /** 剩余油量（statistic.fuel_percent）— 由目录自动生成，请勿手改。 */
    public static final class fuel_percent {
        private fuel_percent() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1246785600, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"STATISTIC_FUEL_PERCENTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.fuel_percent with given params");
            }
        }

    }

    /** 燃油剩余续航（statistic.fuel_range）— 由目录自动生成，请勿手改。 */
    public static final class fuel_range {
        private fuel_range() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1246773304, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "kilometer", null, new int[]{r0}, new String[]{"STATISTIC_FUEL_DRIVING_RANGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.fuel_range with given params");
            }
        }

    }

    /** 低压电池电压（statistic.low_voltage_battery_voltage）— 由目录自动生成，请勿手改。 */
    public static final class low_voltage_battery_voltage {
        private low_voltage_battery_voltage() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1032, 1196425280, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"OTA_BATTERY_POWER_VOLTAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.low_voltage_battery_voltage with given params");
            }
        }

    }

    /** 累计电耗（statistic.total_electric_consumption）— 由目录自动生成，请勿手改。 */
    public static final class total_electric_consumption {
        private total_electric_consumption() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1033199632, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"STATISTIC_TOTAL_ELEC_CONSUMPTION"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.total_electric_consumption with given params");
            }
        }

    }

    /** 累计油耗（statistic.total_fuel_consumption）— 由目录自动生成，请勿手改。 */
    public static final class total_fuel_consumption {
        private total_fuel_consumption() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1033211920, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"STATISTIC_TOTAL_FUEL_CONSUMPTION"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.total_fuel_consumption with given params");
            }
        }

    }

    /** 总里程（statistic.total_mileage）— 由目录自动生成，请勿手改。 */
    public static final class total_mileage {
        private total_mileage() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1014, 1033543696, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"STATISTIC_TOTAL_MILEAGE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for statistic.total_mileage with given params");
            }
        }

    }

}
