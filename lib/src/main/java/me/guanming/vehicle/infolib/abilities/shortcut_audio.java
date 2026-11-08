package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** shortcut_audio — 能力分组容器。 */
public final class shortcut_audio {
    private shortcut_audio() {}

    /** 主静音状态（shortcut_audio.master_mute）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class master_mute {
        private master_mute() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("AUDIO_MUTE_STATUS", "Audio");
                int r0 = Runtime.readFid(1002, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AUDIO_MUTE_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_audio.master_mute with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int f0 = Runtime.fid("AUDIO_MUTE_STATUS_SET", "Audio");
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1002, f0, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_audio.master_mute with given params");
            }
        }

    }

    /** 主音量（shortcut_audio.master_volume）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class master_volume {
        private master_volume() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("AUDIO_VOLUME", "Audio");
                int r0 = Runtime.readFid(1002, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "percent", null, new int[]{r0}, new String[]{"AUDIO_VOLUME"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_audio.master_volume with given params");
            }
        }

    }

    /** 导航静音状态（shortcut_audio.navigation_mute）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class navigation_mute {
        private navigation_mute() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("AUDIO_NAVI_MUTE_STATUS", "Audio");
                int r0 = Runtime.readFid(1002, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 2 ? "state2" : (r0 == 3 ? "state3" : null)), new int[]{r0}, new String[]{"AUDIO_NAVI_MUTE_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_audio.navigation_mute with given params");
            }
        }

    }

    /** 导航音量（shortcut_audio.navigation_volume）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class navigation_volume {
        private navigation_volume() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("AUDIO_NAVI_VOLUME", "Audio");
                int r0 = Runtime.readFid(1002, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AUDIO_NAVI_VOLUME"});
            }
            else {
                throw new IllegalArgumentException("No matching case for shortcut_audio.navigation_volume with given params");
            }
        }

    }

}
