package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** shortcut_charging — 能力分组容器。 */
public final class shortcut_charging {
    private shortcut_charging() {}

    /** 对外放电（shortcut_charging.discharge）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class discharge {
        private discharge() {}

        public static void write(boolean value) {
            if (true) {
                int f0 = Runtime.fid("CHARGING_DISCHARGE_TO_CAR_STATE_SET", "Charging");
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1009, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_charging.discharge with given params");
            }
        }

    }

    /** 无线充电（shortcut_charging.wireless）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class wireless {
        private wireless() {}

        public static void write(boolean value) {
            if (true) {
                int f0 = Runtime.fid("CHARGING_CHARGE_WIRELESS_CHARGING_SWITCH_SET", "Charging");
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1009, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_charging.wireless with given params");
            }
        }

    }

}
