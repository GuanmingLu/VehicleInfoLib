package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** shortcut_gearbox — 能力分组容器。 */
public final class shortcut_gearbox {
    private shortcut_gearbox() {}

    /** 自动挡原始档位（shortcut_gearbox.auto_mode）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class auto_mode {
        private auto_mode() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("GEARBOX_AUTO_MODE_TYPE", "Gearbox");
                int r0 = Runtime.readFid(1011, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"GEARBOX_AUTO_MODE_TYPE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_gearbox.auto_mode with given params");
            }
        }

    }

    /** 电子手刹原始状态（shortcut_gearbox.epb）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class epb {
        private epb() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("GEARBOX_EPB_STATE", "Gearbox");
                int r0 = Runtime.readFid(1011, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"GEARBOX_EPB_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_gearbox.epb with given params");
            }
        }

    }

    /** 旧规则日行灯开关（shortcut_gearbox.legacy_daylight）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class legacy_daylight {
        private legacy_daylight() {}

        public static void write(boolean value) {
            if (true) {
                int f0 = Runtime.fid("LIGHT_DAY_RUNNING_LIGHT_AUTO_STATE_SET", "Light");
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 2; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1004, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_gearbox.legacy_daylight with given params");
            }
        }

    }

    /** 手动挡原始档位（shortcut_gearbox.manual_mode）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class manual_mode {
        private manual_mode() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("GEARBOX_MANUAL_MODE_LEVEL", "Gearbox");
                int r0 = Runtime.readFid(1011, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"GEARBOX_MANUAL_MODE_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_gearbox.manual_mode with given params");
            }
        }

    }

    /** 驻车制动开关原始状态（shortcut_gearbox.park_switch）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class park_switch {
        private park_switch() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("GEARBOX_PARK_BRAKE_SWITCH", "Gearbox");
                int r0 = Runtime.readFid(1011, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"GEARBOX_PARK_BRAKE_SWITCH"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_gearbox.park_switch with given params");
            }
        }

    }

    /** 变速箱类型（shortcut_gearbox.type）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class type {
        private type() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("GEARBOX_TYPE", "Gearbox");
                int r0 = Runtime.readFid(1011, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"GEARBOX_TYPE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_gearbox.type with given params");
            }
        }

    }

}
