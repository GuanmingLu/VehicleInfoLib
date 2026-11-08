package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** charging — 能力分组容器。 */
public final class charging {
    private charging() {}

    /** 电池充放电状态（charging.battery_state）— 由目录自动生成，请勿手改。 */
    public static final class battery_state {
        private battery_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 876609560, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "PREPARING" : (r0 == 1 ? "CHARGING" : (r0 == 2 ? "CHARGE_COMPLETE" : (r0 == 3 ? "DISCHARGING" : (r0 == 4 ? "TERMINATED" : (r0 == 5 ? "C10_FAULT" : (r0 == 6 ? "GUN_FAULT" : (r0 == 7 ? "CHARGER_FAULT" : (r0 == 8 ? "AC_FAULT" : (r0 == 9 ? "SCHEDULED" : (r0 == 10 ? "CBU_DISCHARGING" : (r0 == 11 ? "TIMEOUT" : (r0 == 12 ? "DISCHARGE_COMPLETE" : (r0 == 13 ? "PAUSED" : null)))))))))))))), new int[]{r0}, new String[]{"CHARGING_BATTERRY_DEVICE_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.battery_state with given params");
            }
        }

    }

    /** 动力电池电压（charging.battery_voltage）— 由目录自动生成，请勿手改。 */
    public static final class battery_voltage {
        private battery_voltage() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 1145045000, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "volt", null, new int[]{r0}, new String[]{"CHARGING_CHARGE_BATTERY_VOLT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.battery_voltage with given params");
            }
        }

    }

    /** 充电机工作状态（charging.charger_work_state）— 由目录自动生成，请勿手改。 */
    public static final class charger_work_state {
        private charger_work_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 666894346, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "PREPARING" : (r0 == 2 ? "STARTED" : (r0 == 3 ? "FINISHED" : (r0 == 4 ? "TERMINATED" : null)))), new int[]{r0}, new String[]{"CHARGING_CHARGER_WORK_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.charger_work_state with given params");
            }
        }

    }

    /** 充放电电流（charging.current）— 由目录自动生成，请勿手改。 */
    public static final class current {
        private current() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 1145045016, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "ampere", null, new int[]{r0}, new String[]{"CHARGING_CHARGE_CURRENT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.current with given params");
            }
        }

    }

    /** 对外放电电流（charging.external_discharge_current）— 由目录自动生成，请勿手改。 */
    public static final class external_discharge_current {
        private external_discharge_current() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 1248985116, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, "ampere", null, new int[]{r0}, new String[]{"CHARGING_DISCHARGE_VEHICLE_OUTPUT_CURRENT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.external_discharge_current with given params");
            }
        }

    }

    /** 充电枪连接状态（charging.gun_state）— 由目录自动生成，请勿手改。 */
    public static final class gun_state {
        private gun_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 876609586, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "DISCONNECTED" : (r0 == 2 ? "AC" : (r0 == 3 ? "DC" : (r0 == 4 ? "AC_AND_DC" : (r0 == 5 ? "DISCHARGING" : null))))), new int[]{r0}, new String[]{"CHARGING_GUN_CONNECT_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.gun_state with given params");
            }
        }

    }

    /** 充电功率（charging.power）— 由目录自动生成，请勿手改。 */
    public static final class power {
        private power() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 842006552, 7);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = Float.intBitsToFloat(r0);
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"CHARGING_CHARGE_POWER_DD"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.power with given params");
            }
        }

    }

    /** 保电模式（charging.soc_save_mode）— 由目录自动生成，请勿手改。 */
    public static final class soc_save_mode {
        private soc_save_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1009, 1186988086, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "SMART" : (r0 == 2 ? "FORCED" : null)), new int[]{r0}, new String[]{"CHARGING_SOC_SAVE_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.soc_save_mode with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1009, 782237704, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for charging.soc_save_mode with given params");
            }
        }

    }

}
