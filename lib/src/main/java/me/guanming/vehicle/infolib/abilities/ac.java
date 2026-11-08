package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** ac — 能力分组容器。 */
public final class ac {
    private ac() {}

    /** 吹风模式（ac.airflow_mode）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class airflow_mode {
        private airflow_mode() {}

        public static SensorValue read(String area) {
            Runtime.requireProfile("ac.airflow_mode");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            if (Runtime.inStr(area, "driver")) {
                int r0 = Runtime.readFid(1000, 1269825936, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int r1 = Runtime.readFid(1000, 1269825940, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                int r2 = Runtime.readFid(1000, 1269825946, 5);
                if (Runtime.isSentinel(r2)) return SensorValue.failed();
                double d2 = r2;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1, r2}, new String[]{"AC_MAIN_DRIVE_BLOWS_MODE_SIGNAL_TO_PEOPLE", "AC_MAIN_DRIVE_SIDE_TO_AVOID_BLOWING_MODE_SIGNAL_TO_PEOPLE", "AC_MAIN_DRIVE_LEFT_AND_RIGHT_SWEEP_SIGNAL"});
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int r0 = Runtime.readFid(1000, 1269825938, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int r1 = Runtime.readFid(1000, 1269825942, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                int r2 = Runtime.readFid(1000, 1269825950, 5);
                if (Runtime.isSentinel(r2)) return SensorValue.failed();
                double d2 = r2;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1, r2}, new String[]{"AC_PASSENGER_BLOWS_MODE_SIGNAL_TO_PEOPLE", "AC_PASSENGER_SIDE_TO_AVOID_BLOWING_MODE_SIGNAL_TO_PEOPLE", "AC_PASSENGER_LEFT_AND_RIGHT_SWEEP_SIGNAL"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 4, 5)) {
                int f0 = Runtime.fid("AC_SECOND_ROW_LEFT_BLOW_TO_PERSON_MODE_SIGNAL", "Ac");
                int r0 = Runtime.readFid(1000, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_SECOND_ROW_LEFT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                int f2 = Runtime.fid("AC_SECOND_ROW_LEFT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL", "Ac");
                int r2 = Runtime.readFid(1000, f2, 5);
                if (Runtime.isSentinel(r2)) return SensorValue.failed();
                double d2 = r2;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1, r2}, new String[]{"AC_SECOND_ROW_LEFT_BLOW_TO_PERSON_MODE_SIGNAL", "AC_SECOND_ROW_LEFT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL", "AC_SECOND_ROW_LEFT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 4, 5)) {
                int f0 = Runtime.fid("AC_SECOND_ROW_RIGHT_BLOW_TO_PERSON_MODE_SIGNAL", "Ac");
                int r0 = Runtime.readFid(1000, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_SECOND_ROW_RIGHT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = r1;
                int f2 = Runtime.fid("AC_SECOND_ROW_RIGHT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL", "Ac");
                int r2 = Runtime.readFid(1000, f2, 5);
                if (Runtime.isSentinel(r2)) return SensorValue.failed();
                double d2 = r2;
                return Runtime.value(r0, d0, null, null, new int[]{r0, r1, r2}, new String[]{"AC_SECOND_ROW_RIGHT_BLOW_TO_PERSON_MODE_SIGNAL", "AC_SECOND_ROW_RIGHT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL", "AC_SECOND_ROW_RIGHT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.airflow_mode with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.requireProfile("ac.airflow_mode");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight");
            Runtime.checkEnum("value", value, 1, 2, 3);
            if (Runtime.inStr(area, "driver") && Runtime.inInt(value, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367888, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.inInt(value, 2)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367892, v0);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.inInt(value, 3)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367898, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inInt(value, 1)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367890, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inInt(value, 2)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367894, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inInt(value, 3)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367902, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inInt(value, 1) && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324380208, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inInt(value, 2) && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1326448688, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inInt(value, 3) && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324380218, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inInt(value, 1) && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324380210, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inInt(value, 2) && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324380214, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inInt(value, 3) && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324380222, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.airflow_mode with given params");
            }
        }

    }

    /** 车内温度（ac.cabin_temperature）— 由目录自动生成，请勿手改。 */
    public static final class cabin_temperature {
        private cabin_temperature() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1031798832, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"AC_TEMP_INSIDE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.cabin_temperature with given params");
            }
        }

    }

    /** 关闭副驾出风（ac.close_passenger_vent）— 由目录自动生成，请勿手改。 */
    public static final class close_passenger_vent {
        private close_passenger_vent() {}

        public static void write() {
            if (true) {
                int v0 = 1;
                Runtime.writeFid(1000, 1324367910, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.close_passenger_vent with given params");
            }
        }

    }

    /** AC 模式（ac.compressor）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class compressor {
        private compressor() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1077936137, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_COMPRESSOR_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.compressor with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219344, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.compressor with given params");
            }
        }

    }

    /** 自动模式（ac.control_mode）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class control_mode {
        private control_mode() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "frontPair", "rearCabin");
            if (Runtime.inStr(area, "frontPair")) {
                int r0 = Runtime.readFid(1000, 1077936146, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 1.0; d0_ok = true; }
                if (r0 == 1) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 1 ? "AUTO" : (r0 == 2 ? "MANUAL" : null)), new int[]{r0}, new String[]{"AC_CTRL_MODE"});
            }
            else if (Runtime.inStr(area, "rearCabin")) {
                int r0 = Runtime.readFid(1000, 1218445326, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 1.0; d0_ok = true; }
                if (r0 == 1) { d0 = 2.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 1 ? "AUTO" : (r0 == 2 ? "MANUAL" : null)), new int[]{r0}, new String[]{"AC_REAR_CTRL_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.control_mode with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.checkSelector("area", area, "frontPair", "rearCabin");
            Runtime.checkEnum("value", value, 1, 2);
            if (Runtime.inStr(area, "frontPair")) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 0; v0_ok = true; }
                if ((value == 2)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 501219352, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearCabin")) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 0; v0_ok = true; }
                if ((value == 2)) { v0 = 1; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1337982990, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.control_mode with given params");
            }
        }

    }

    /** 内外循环（ac.cycle_mode）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class cycle_mode {
        private cycle_mode() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1077936148, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                if (r0 == 0) { d0 = 1.0; d0_ok = true; }
                if (r0 == 1) { d0 = 2.0; d0_ok = true; }
                if (r0 == 2) { d0 = 3.0; d0_ok = true; }
                return Runtime.value(r0, d0, null, (r0 == 1 ? "OUTSIDE_AIR" : (r0 == 2 ? "RECIRCULATION" : (r0 == 3 ? "AUTO" : null))), new int[]{r0}, new String[]{"AC_CYCLE_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.cycle_mode with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3);
            if (true) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == 1)) { v0 = 0; v0_ok = true; }
                if ((value == 2)) { v0 = 1; v0_ok = true; }
                if ((value == 3)) { v0 = 2; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 501219355, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.cycle_mode with given params");
            }
        }

    }

    /** 除霜（ac.defrost）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class defrost {
        private defrost() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "frontWindshield", "rearWindshield");
            if (Runtime.inStr(area, "frontWindshield")) {
                int r0 = Runtime.readFid(1000, 1077936150, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_DEFROST_FRONT_STATE"});
            }
            else if (Runtime.inStr(area, "rearWindshield")) {
                int r0 = Runtime.readFid(1000, 1128267825, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_DEFROST_REAR_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.defrost with given params");
            }
        }

        public static void write(String area, boolean value) {
            Runtime.checkSelector("area", area, "frontWindshield", "rearWindshield");
            if (Runtime.inStr(area, "frontWindshield")) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219362, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearWindshield")) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219357, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.defrost with given params");
            }
        }

    }

    /** 最大制冷（ac.maximum_cooling）— 由目录自动生成，请勿手改。 */
    public static final class maximum_cooling {
        private maximum_cooling() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1314914324, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MAX_COOLING_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.maximum_cooling with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 1337983004, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.maximum_cooling with given params");
            }
        }

    }

    /** 后视镜加热（ac.mirror_heating）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class mirror_heating {
        private mirror_heating() {}

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219357, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 1;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.mirror_heating with given params");
            }
        }

    }

    /** 车外温度（ac.outside_temperature）— 由目录自动生成，请勿手改。 */
    public static final class outside_temperature {
        private outside_temperature() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1077936184, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0}, new String[]{"AC_TEMP_OUT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.outside_temperature with given params");
            }
        }

    }

    /** 香氛浓度（ac.perfume_concentration）— 由目录自动生成，请勿手改。 */
    public static final class perfume_concentration {
        private perfume_concentration() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1242562568, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "LOWEST" : (r0 == 2 ? "ON" : (r0 == 3 ? "HIGHEST" : (r0 == 4 ? "OFF" : null)))), new int[]{r0}, new String[]{"AC_PERFUME_CONCENTRATION_FEEDBACK"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.perfume_concentration with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3, 4);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 1324556328, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.perfume_concentration with given params");
            }
        }

    }

    /** 香氛种类（ac.perfume_type）— 由目录自动生成，请勿手改。 */
    public static final class perfume_type {
        private perfume_type() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1242562571, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "TYPE_1" : (r0 == 2 ? "TYPE_2" : (r0 == 3 ? "TYPE_3" : null))), new int[]{r0}, new String[]{"AC_CURRENT_PERFUME_TYPE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.perfume_type with given params");
            }
        }

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2, 3);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1000, 1324556331, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.perfume_type with given params");
            }
        }

    }

    /** 空调开关（ac.power）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class power {
        private power() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "frontPair", "rearCabin");
            if (Runtime.inStr(area, "frontPair")) {
                int r0 = Runtime.readFid(1000, 1077936144, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_POWER_STATE"});
            }
            else if (Runtime.inStr(area, "rearCabin")) {
                int r0 = Runtime.readFid(1000, 1218445324, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_POWER_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.power with given params");
            }
        }

        public static void write(String area, boolean value) {
            Runtime.checkSelector("area", area, "frontPair", "rearCabin");
            if (Runtime.inStr(area, "frontPair")) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219364, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearCabin")) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 1337982988, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.power with given params");
            }
        }

    }

    /** 后排空调锁（ac.rear_lock_state）— 由目录自动生成，请勿手改。 */
    public static final class rear_lock_state {
        private rear_lock_state() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1314914326, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_LOCK_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.rear_lock_state with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 1337983006, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.rear_lock_state with given params");
            }
        }

    }

    /** 后排最高风量档位（ac.rear_max_fan_level）— 由目录自动生成，请勿手改。 */
    public static final class rear_max_fan_level {
        private rear_max_fan_level() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1127219220, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "MAX_LEVEL_4" : (r0 == 2 ? "MAX_LEVEL_7" : null)), new int[]{r0}, new String[]{"AC_REAR_MAX_WIND_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.rear_max_fan_level with given params");
            }
        }

    }

    /** 分控模式（ac.separate_control）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class separate_control {
        private separate_control() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1077936164, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "OFF" : (r0 == 1 ? "ON" : (r0 == 3 ? "SINGLE_RANGE" : null))), new int[]{r0}, new String[]{"AC_TEMPCTRL_SEPARATE_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.separate_control with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219346, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.separate_control with given params");
            }
        }

    }

    /** 整车同步（ac.synchronization）— 由目录自动生成，请勿手改。 */
    public static final class synchronization {
        private synchronization() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1060110344, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_SYNCHRONOUS_KEY"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.synchronization with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 1324355604, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.synchronization with given params");
            }
        }

    }

    /** 空调温度（ac.temperature）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class temperature {
        private temperature() {}

        public static SensorValue read(String area) {
            Runtime.requireProfile("ac.temperature");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearCabin", "rearLeft", "rearRight", "thirdRow");
            if (Runtime.inStr(area, "driver")) {
                int r0 = Runtime.readFid(1000, 1077936168, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = 0;
                boolean d1_ok = false;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0, r1}, new String[]{"AC_TEMP_MAIN", "AC_TEMPERATURE_UNIT"});
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int r0 = Runtime.readFid(1000, 1077936176, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = 0;
                boolean d1_ok = false;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0, r1}, new String[]{"AC_TEMP_DEPUTY", "AC_TEMPERATURE_UNIT"});
            }
            else if (Runtime.inStr(area, "rearCabin") && Runtime.profileAllows("climateZones", 3)) {
                int r0 = Runtime.readFid(1000, 1218445328, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = 0;
                boolean d1_ok = false;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0, r1}, new String[]{"AC_TEMP_REAR", "AC_TEMPERATURE_UNIT"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 4, 5)) {
                int r0 = Runtime.readFid(1000, 1060110376, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = 0;
                boolean d1_ok = false;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0, r1}, new String[]{"AC_MIDDLE_LEFT_TEMP", "AC_TEMPERATURE_UNIT"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 4, 5)) {
                int r0 = Runtime.readFid(1000, 1060110384, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = 0;
                boolean d1_ok = false;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0, r1}, new String[]{"AC_MIDDLE_RIGHT_TEMP", "AC_TEMPERATURE_UNIT"});
            }
            else if (Runtime.inStr(area, "thirdRow") && Runtime.profileAllows("climateZones", 5)) {
                int r0 = Runtime.readFid(1000, 1218445328, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                int f1 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r1 = Runtime.readFid(1000, f1, 5);
                if (Runtime.isSentinel(r1)) return SensorValue.failed();
                double d1 = 0;
                boolean d1_ok = false;
                return Runtime.value(r0, d0, "celsius", null, new int[]{r0, r1}, new String[]{"AC_TEMP_REAR", "AC_TEMPERATURE_UNIT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.temperature with given params");
            }
        }

        public static void write(String area, int value, String unit) {
            Runtime.requireProfile("ac.temperature");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearCabin", "rearLeft", "rearRight", "thirdRow", "frontPair", "allCabin");
            Runtime.checkAnyRange("value", value, 17, 33, 64, 91);
            Runtime.checkSelector("unit", unit, "celsius", "fahrenheit");
            if (Runtime.inStr(area, "driver") && Runtime.inStr(unit, "celsius")) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.inStr(unit, "fahrenheit")) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inStr(unit, "celsius")) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219376, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.inStr(unit, "fahrenheit")) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219376, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "rearCabin") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 3)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219384, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "rearCabin") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 3)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219384, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 4, 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355624, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 4, 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355624, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 4, 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355640, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 4, 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355640, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "thirdRow") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 598736952, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "thirdRow") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 598736952, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "frontPair") && Runtime.inStr(unit, "celsius")) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int f3 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v3 = 0;
                Runtime.writeFid(1000, f3, v3);
            }
            else if (Runtime.inStr(area, "frontPair") && Runtime.inStr(unit, "fahrenheit")) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int f3 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v3 = 0;
                Runtime.writeFid(1000, f3, v3);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 2)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int f3 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v3 = 0;
                Runtime.writeFid(1000, f3, v3);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 2)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int f3 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v3 = 0;
                Runtime.writeFid(1000, f3, v3);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 3)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int v3 = value;
                Runtime.writeFid(1000, 501219384, v3);
                int f4 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v4 = 0;
                Runtime.writeFid(1000, f4, v4);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 3)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int v3 = value;
                Runtime.writeFid(1000, 501219384, v3);
                int f4 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v4 = 0;
                Runtime.writeFid(1000, f4, v4);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 4)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int v3 = value;
                Runtime.writeFid(1000, 1324355624, v3);
                int v4 = value;
                Runtime.writeFid(1000, 1324355640, v4);
                int f5 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v5 = 0;
                Runtime.writeFid(1000, f5, v5);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 4)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int v3 = value;
                Runtime.writeFid(1000, 1324355624, v3);
                int v4 = value;
                Runtime.writeFid(1000, 1324355640, v4);
                int f5 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v5 = 0;
                Runtime.writeFid(1000, f5, v5);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "celsius") && Runtime.profileAllows("climateZones", 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int v3 = value;
                Runtime.writeFid(1000, 1324355624, v3);
                int v4 = value;
                Runtime.writeFid(1000, 1324355640, v4);
                int v5 = value;
                Runtime.writeFid(1000, 598736952, v5);
                int f6 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v6 = 0;
                Runtime.writeFid(1000, f6, v6);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.inStr(unit, "fahrenheit") && Runtime.profileAllows("climateZones", 5)) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT_SET", "Ac");
                int v0 = 0;
                boolean v0_ok = false;
                if ("celsius".equals(unit)) { v0 = 1; v0_ok = true; }
                if ("fahrenheit".equals(unit)) { v0 = 0; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + unit);
                Runtime.writeFid(1000, f0, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219368, v1);
                int v2 = value;
                Runtime.writeFid(1000, 501219376, v2);
                int v3 = value;
                Runtime.writeFid(1000, 1324355624, v3);
                int v4 = value;
                Runtime.writeFid(1000, 1324355640, v4);
                int v5 = value;
                Runtime.writeFid(1000, 598736952, v5);
                int f6 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v6 = 0;
                Runtime.writeFid(1000, f6, v6);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.temperature with given params");
            }
        }

    }

    /** 温度单位（ac.temperature_unit）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class temperature_unit {
        private temperature_unit() {}

        public static SensorValue read() {
            if (true) {
                int f0 = Runtime.fid("AC_TEMPERATURE_UNIT", "Ac");
                int r0 = Runtime.readFid(1000, f0, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = 0;
                boolean d0_ok = false;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_TEMPERATURE_UNIT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.temperature_unit with given params");
            }
        }

    }

    /** 驻车通风（ac.ventilation）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class ventilation {
        private ventilation() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1000, 1314914314, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_VENTILATION_STATE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.ventilation with given params");
            }
        }

        public static void write(boolean value) {
            if (true) {
                int v0 = (value ? 1 : 0);
                Runtime.writeFid(1000, 501219394, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.ventilation with given params");
            }
        }

    }

    /** 出风模式（ac.wind_mode）— 由目录自动生成，请勿手改。
     *
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class wind_mode {
        private wind_mode() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "frontPair", "rearCabin");
            if (Runtime.inStr(area, "frontPair")) {
                int r0 = Runtime.readFid(1000, 1077936152, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "FACE" : (r0 == 2 ? "FACE_FOOT" : (r0 == 3 ? "FOOT" : (r0 == 4 ? "FOOT_DEFROST" : (r0 == 5 ? "DEFROST" : (r0 == 6 ? "FACE_FOOT_DEFROST" : (r0 == 7 ? "FACE_DEFROST" : null))))))), new int[]{r0}, new String[]{"AC_WIND_MODE"});
            }
            else if (Runtime.inStr(area, "rearCabin")) {
                int r0 = Runtime.readFid(1000, 1218445336, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "FACE" : (r0 == 2 ? "FACE_FOOT" : (r0 == 3 ? "FOOT" : (r0 == 4 ? "FOOT_DEFROST" : (r0 == 5 ? "DEFROST" : (r0 == 6 ? "FACE_FOOT_DEFROST" : (r0 == 7 ? "FACE_DEFROST" : null))))))), new int[]{r0}, new String[]{"AC_REAR_WIND_MODE"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.wind_mode with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.checkSelector("area", area, "frontPair", "rearCabin");
            Runtime.checkEnum("value", value, 1, 2, 3, 4, 5, 6, 7);
            if (Runtime.inStr(area, "frontPair")) {
                int v0 = value;
                Runtime.writeFid(1000, 501219336, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearCabin")) {
                int v0 = value;
                Runtime.writeFid(1000, 1337983000, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.wind_mode with given params");
            }
        }

    }

    /** 风量（ac.zone_fan_level）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     * 注：部分 feature 无数字 id，由 Runtime 初始化时从 framework 反射解析
     */
    public static final class zone_fan_level {
        private zone_fan_level() {}

        public static SensorValue read(String area) {
            Runtime.requireProfile("ac.zone_fan_level");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearCabin", "rearLeft", "rearRight", "thirdRow");
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 0)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 2)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 2)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 3)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 3)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearCabin") && Runtime.profileAllows("climateZones", 3)) {
                int r0 = Runtime.readFid(1000, 1218445320, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 4)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 4)) {
                int r0 = Runtime.readFid(1000, 1060110352, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_PASSENGER_WINDLEVEL"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 4)) {
                int r0 = Runtime.readFid(1000, 1060110368, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MIDDLE_LEFT_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 4)) {
                int r0 = Runtime.readFid(1000, 1060110372, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MIDDLE_RIGHT_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 5)) {
                int r0 = Runtime.readFid(1000, 1077936156, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 5)) {
                int r0 = Runtime.readFid(1000, 1060110352, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_PASSENGER_WINDLEVEL"});
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 5)) {
                int r0 = Runtime.readFid(1000, 1060110368, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MIDDLE_LEFT_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 5)) {
                int r0 = Runtime.readFid(1000, 1060110372, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_MIDDLE_RIGHT_WIND_LEVEL"});
            }
            else if (Runtime.inStr(area, "thirdRow") && Runtime.profileAllows("climateZones", 5)) {
                int r0 = Runtime.readFid(1000, 1218445320, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"AC_REAR_WIND_LEVEL"});
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.zone_fan_level with given params");
            }
        }

        public static void write(String area, int value) {
            Runtime.requireProfile("ac.zone_fan_level");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "frontPair", "allCabin", "rearCabin", "rearLeft", "rearRight", "thirdRow");
            Runtime.checkRange("value", value, 1, 7);
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 2)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 2)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPair") && Runtime.profileAllows("climateZones", 2)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219340, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.profileAllows("climateZones", 2)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219340, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearCabin") && Runtime.profileAllows("climateZones", 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 1337982984, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPair") && Runtime.profileAllows("climateZones", 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219340, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.profileAllows("climateZones", 3)) {
                int v0 = value;
                Runtime.writeFid(1000, 501219340, v0);
                int v1 = value;
                Runtime.writeFid(1000, 501219340, v1);
                int v2 = value;
                Runtime.writeFid(1000, 1337982984, v2);
                int f3 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v3 = 0;
                Runtime.writeFid(1000, f3, v3);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 4)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355600, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 4)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355612, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 4)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355632, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 4)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355636, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPair") && Runtime.profileAllows("climateZones", 4)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355600, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355612, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.profileAllows("climateZones", 4)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355600, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355612, v1);
                int v2 = value;
                Runtime.writeFid(1000, 1324355632, v2);
                int v3 = value;
                Runtime.writeFid(1000, 1324355636, v3);
                int f4 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v4 = 0;
                Runtime.writeFid(1000, f4, v4);
            }
            else if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355600, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355612, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355632, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355636, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "thirdRow") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1337982984, v0);
                int f1 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v1 = 0;
                Runtime.writeFid(1000, f1, v1);
            }
            else if (Runtime.inStr(area, "frontPair") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355600, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355612, v1);
                int f2 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v2 = 0;
                Runtime.writeFid(1000, f2, v2);
            }
            else if (Runtime.inStr(area, "allCabin") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = value;
                Runtime.writeFid(1000, 1324355600, v0);
                int v1 = value;
                Runtime.writeFid(1000, 1324355612, v1);
                int v2 = value;
                Runtime.writeFid(1000, 1324355632, v2);
                int v3 = value;
                Runtime.writeFid(1000, 1324355636, v3);
                int v4 = value;
                Runtime.writeFid(1000, 1337982984, v4);
                int f5 = Runtime.fid("AC_CTRL_SOURCE_SET", "Ac");
                int v5 = 0;
                Runtime.writeFid(1000, f5, v5);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.zone_fan_level with given params");
            }
        }

    }

    /** 温区开关（ac.zone_power）— 由目录自动生成，请勿手改。
     *
     * 注：含车型 profiles 分支，调用前须先 Runtime.setVehicleProfile(climateZones, seatHeating, steeringWheelHeating)
     */
    public static final class zone_power {
        private zone_power() {}

        public static void write(String area, boolean value) {
            Runtime.requireProfile("ac.zone_power");
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight", "thirdRow");
            if (Runtime.inStr(area, "driver") && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 8; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1324355600, v0);
            }
            else if (Runtime.inStr(area, "frontPassenger") && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 8; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1324355612, v0);
            }
            else if (Runtime.inStr(area, "rearLeft") && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 8; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1324355632, v0);
            }
            else if (Runtime.inStr(area, "rearRight") && Runtime.profileAllows("climateZones", 4, 5)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 8; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1324355636, v0);
            }
            else if (Runtime.inStr(area, "thirdRow") && Runtime.profileAllows("climateZones", 5)) {
                int v0 = 0;
                boolean v0_ok = false;
                if ((value == true)) { v0 = 1; v0_ok = true; }
                if ((value == false)) { v0 = 8; v0_ok = true; }
                if (!v0_ok) throw new IllegalArgumentException("Invalid enum value: " + value);
                Runtime.writeFid(1000, 1337982984, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for ac.zone_power with given params");
            }
        }

    }

}
