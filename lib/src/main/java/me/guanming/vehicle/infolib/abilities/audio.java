package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** audio — 能力分组容器。 */
public final class audio {
    private audio() {}

    /** 外放结束通知（audio.external_broadcast_end）— 由目录自动生成，请勿手改。 */
    public static final class external_broadcast_end {
        private external_broadcast_end() {}

        public static void write() {
            if (true) {
                int v0 = 1;
                Runtime.writeFid(1002, -1442839933, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.external_broadcast_end with given params");
            }
        }

    }

    /** 车外播报通道（audio.external_broadcast_position）— 由目录自动生成，请勿手改。 */
    public static final class external_broadcast_position {
        private external_broadcast_position() {}

        public static void write(int route, boolean value) {
            Runtime.checkEnum("route", route, 1, 2);
            if (Runtime.inInt(route, 1)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, -1442839934, v0);
            }
            else if (Runtime.inInt(route, 2)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1002, -1442839934, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.external_broadcast_position with given params");
            }
        }

    }

    /** 头枕音效（audio.headrest_effect）— 由目录自动生成，请勿手改。 */
    public static final class headrest_effect {
        private headrest_effect() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1002, -894435307, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OFF" : (r0 == 2 ? "SURROUND" : (r0 == 3 ? "DRIVER" : (r0 == 4 ? "PRIVATE" : null)))), new int[]{r0}, new String[]{"AUDIO_HEADREST_SOUND_EFFECT_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.headrest_effect with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3, 4);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1002, -1676672971, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.headrest_effect with given params");
            }
        }

    }

    /** 车外音效通道（audio.lock_sound_playback_flag）— 由目录自动生成，请勿手改。 */
    public static final class lock_sound_playback_flag {
        private lock_sound_playback_flag() {}

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, -1442839775, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.lock_sound_playback_flag with given params");
            }
        }

    }

    /** 车辆提示音量（audio.prompt_volume）— 由目录自动生成，请勿手改。 */
    public static final class prompt_volume {
        private prompt_volume() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, -1728052473, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "LOW" : (r0 == 2 ? "MEDIUM" : (r0 == 3 ? "HIGH" : null))), new int[]{r0}, new String[]{"AUDIO_PROMPT_VOLUME_LEVEL_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.prompt_volume with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, -1442839911, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.prompt_volume with given params");
            }
        }

    }

    /** 模拟声浪（audio.simulated_engine_sound）— 由目录自动生成，请勿手改。 */
    public static final class simulated_engine_sound {
        private simulated_engine_sound() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1002, 1339031610, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 0.0; d0_ok = true; }
                if (r0 == 1) { d0 = 1.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AUDIO_ESS_SETTING_STATUS"});
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.simulated_engine_sound with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == false)) { v0 = 0; v0_ok = true; }
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1002, 454033419, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.simulated_engine_sound with given params");
            }
        }

    }

    /** 声场平衡（audio.sound_balance）— 由目录自动生成，请勿手改。 */
    public static final class sound_balance {
        private sound_balance() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1002, 1108344848, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int r1 = Runtime.readFid(1002, 1108344852, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1}, new String[]{"AUDIO_X_SOUND_FIELD", "AUDIO_Y_SOUND_FIELD"});
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.sound_balance with given params");
            }
        }

        public static void write(int x, int y) {
            Runtime.checkRange("x", x, 0, 14);
            Runtime.checkRange("y", y, 0, 14);
            if (true) {
                int v0 = x;
                Runtime.writeFid(1002, 1309036560, v0);
                int v1 = y;
                Runtime.writeFid(1002, 1309036576, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.sound_balance with given params");
            }
        }

    }

    /** 声场聚焦（audio.sound_focus）— 由目录自动生成，请勿手改。 */
    public static final class sound_focus {
        private sound_focus() {}

        public static SensorValue read(int audioSystem) {
            Runtime.checkEnum("audioSystem", audioSystem, 1, 2);
            if (Runtime.inInt(audioSystem, 1)) {
                int r0 = Runtime.readFid(1002, 1339031565, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "ALL" : (r0 == 2 ? "DRIVER" : (r0 == 3 ? "PASSENGER" : (r0 == 4 ? "REAR" : (r0 == 5 ? "SURROUND" : (r0 == 6 ? "CUSTOM" : null)))))), new int[]{r0}, new String[]{"AUDIO_LEFT_RIGHT_FIELD_SET_STATE_qy"});
            }
            else if (Runtime.inInt(audioSystem, 2)) {
                int r0 = Runtime.readFid(1002, 1339031565, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 4 ? "REAR" : (r0 == 6 ? "ALL" : (r0 == 7 ? "FRONT" : null))), new int[]{r0}, new String[]{"AUDIO_SOUND_FIELD_FOCUS_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.sound_focus with given params");
            }
        }

        public static void write(int audioSystem, int value) {
            Runtime.checkEnum("audioSystem", audioSystem, 1, 2);
            Runtime.checkEnum("value", value, 1, 2, 3, 4, 5, 6, 6, 7);
            if (Runtime.inInt(audioSystem, 1)) {
                int v0 = value;
                Runtime.writeFid(1002, 470810653, v0);
            }
            else if (Runtime.inInt(audioSystem, 2)) {
                int v0 = value;
                Runtime.writeFid(1002, 470810653, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.sound_focus with given params");
            }
        }

    }

    /** 声音特色（audio.sound_profile）— 由目录自动生成，请勿手改。 */
    public static final class sound_profile {
        private sound_profile() {}

        public static SensorValue read(int audioSystem) {
            Runtime.checkEnum("audioSystem", audioSystem, 1, 2);
            if (Runtime.inInt(audioSystem, 1)) {
                int r0 = Runtime.readFid(1002, -866123768, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "AUTHENTIC" : (r0 == 2 ? "DYNAMIC" : (r0 == 3 ? "SOFT" : (r0 == 4 ? "SPEECH" : (r0 == 5 ? "CUSTOM" : null))))), new int[]{r0}, new String[]{"AUDIO_DYNAUDIO_SOUND_FEATURES"});
            }
            else if (Runtime.inInt(audioSystem, 2)) {
                int r0 = Runtime.readFid(1002, -866123768, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "CLASSIC" : (r0 == 2 ? "SOFT" : (r0 == 3 ? "DYNAMIC" : (r0 == 4 ? "ROCK" : (r0 == 5 ? "VOCAL" : (r0 == 6 ? "CUSTOM" : null)))))), new int[]{r0}, new String[]{"AUDIO_DYNAUDIO_SOUND_FEATURES"});
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.sound_profile with given params");
            }
        }

        public static void write(int audioSystem, int value) {
            Runtime.checkEnum("audioSystem", audioSystem, 1, 2);
            Runtime.checkEnum("value", value, 1, 2, 3, 4, 5, 1, 3, 5, 6);
            if (Runtime.inInt(audioSystem, 1)) {
                int v0 = value;
                Runtime.writeFid(1002, -1693450216, v0);
            }
            else if (Runtime.inInt(audioSystem, 2)) {
                int v0 = value;
                Runtime.writeFid(1002, -1693450216, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for audio.sound_profile with given params");
            }
        }

    }

}
