package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** panorama — 能力分组容器。 */
public final class panorama {
    private panorama() {}

    /** 自动泊车状态（panorama.apa_state）— 由目录自动生成，请勿手改。 */
    public static final class apa_state {
        private apa_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1031, 322961416, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "PASSIVE" : (r0 == 1 ? "SEARCHING" : (r0 == 2 ? "PARKING" : (r0 == 3 ? "SUSPENDED" : (r0 == 4 ? "TERMINATED" : (r0 == 5 ? "COMPLETED" : (r0 == 6 ? "FAILURE" : (r0 == 7 ? "STANDBY" : null)))))))), new int[]{r0}, new String[]{"PANORAMA_APA_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for panorama.apa_state with given params");
            }
        }

    }

    /** 全景显示模式（panorama.display_mode）— 由目录自动生成，请勿手改。 */
    public static final class display_mode {
        private display_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1031, 1329598484, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "PANORAMA" : (r0 == 1 ? "FULL_SCREEN" : (r0 == 3 ? "WIDGET" : (r0 == 4 ? "RF_REVERSE" : (r0 == 5 ? "REVERSE" : (r0 == 6 ? "PANORAMA_3D" : null)))))), new int[]{r0}, new String[]{"PANORAMA_WORK_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for panorama.display_mode with given params");
            }
        }

    }

    /** 全景显示设置（panorama.display_parameters）— 由目录自动生成，请勿手改。 */
    public static final class display_parameters {
        private display_parameters() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1031, 1329598480, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int r1 = Runtime.readFid(1031, 1329598484, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                int r2 = Runtime.readFid(1031, 1329598492, 5);
                if (Runtime.isSentinel(r2)) return SensorValue.failed();
                double d2 = r2;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "INVALID" : (r0 == 1 ? "OFF" : (r0 == 2 ? "FRONT" : (r0 == 3 ? "REAR" : (r0 == 4 ? "LEFT" : (r0 == 5 ? "RIGHT" : (r0 == 6 ? "COMPOSE" : (r0 == 7 ? "MATCHING" : (r0 == 8 ? "FRONT_LEFT" : (r0 == 9 ? "FRONT_RIGHT" : (r0 == 10 ? "REAR_LEFT" : (r0 == 11 ? "REAR_RIGHT" : (r0 == 12 ? "FRONT_WIDE" : (r0 == 13 ? "REAR_WIDE" : (r0 == 14 ? "WIDTH_LIMIT" : null))))))))))))))), new int[]{r0, r1, r2}, new String[]{"PANORAMA_OUTPUT_STATE", "PANORAMA_WORK_MODE", "PANORAMA_ROTATION"});
            }
            else {
                throw new IllegalArgumentException("No matching case for panorama.display_parameters with given params");
            }
        }

        public static void write(int output, int mode, int rotation) {
            Runtime.checkEnum("output", output, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
            Runtime.checkEnum("mode", mode, 0, 1, 3, 4, 5, 6);
            Runtime.checkEnum("rotation", rotation, 1, 2);
            if (true) {
                int v0 = output;
                Runtime.writeFid(1031, 1306529808, v0);
                int v1 = mode;
                Runtime.writeFid(1031, 1306529812, v1);
                int v2 = rotation;
                Runtime.writeFid(1031, 1306529826, v2);
            }
            else {
                throw new IllegalArgumentException("No matching case for panorama.display_parameters with given params");
            }
        }

    }

    /** 全景输出视角（panorama.output）— 由目录自动生成，请勿手改。 */
    public static final class output {
        private output() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1031, 1329598480, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "INVALID" : (r0 == 1 ? "OFF" : (r0 == 2 ? "FRONT" : (r0 == 3 ? "REAR" : (r0 == 4 ? "LEFT" : (r0 == 5 ? "RIGHT" : (r0 == 6 ? "COMPOSE" : (r0 == 7 ? "MATCHING" : (r0 == 8 ? "FRONT_LEFT" : (r0 == 9 ? "FRONT_RIGHT" : (r0 == 10 ? "REAR_LEFT" : (r0 == 11 ? "REAR_RIGHT" : (r0 == 12 ? "FRONT_WIDE" : (r0 == 13 ? "REAR_WIDE" : (r0 == 14 ? "WIDTH_LIMIT" : null))))))))))))))), new int[]{r0}, new String[]{"PANORAMA_OUTPUT_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for panorama.output with given params");
            }
        }

    }

    /** 全景显示方向（panorama.rotation）— 由目录自动生成，请勿手改。 */
    public static final class rotation {
        private rotation() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1031, 1329598492, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "HORIZONTAL" : (r0 == 2 ? "VERTICAL" : null)), new int[]{r0}, new String[]{"PANORAMA_ROTATION"});
            }
            else {
                throw new IllegalArgumentException("No matching case for panorama.rotation with given params");
            }
        }

    }

}
