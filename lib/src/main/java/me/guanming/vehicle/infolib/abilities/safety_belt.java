package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** safety_belt — 能力分组容器。 */
public final class safety_belt {
    private safety_belt() {}

    /** 安全带状态（safety_belt.state）— 由目录自动生成，请勿手改。 */
    public static final class state {
        private state() {}

        public static SensorValue read(String area) {
            Runtime.checkSelector("area", area, "driver", "frontPassenger", "rearLeft", "rearRight", "rearCenter");
            if (Runtime.inStr(area, "driver")) {
                int r0 = Runtime.readFid(1042, 89129025, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "UNBUCKLED" : (r0 == 1 ? "BUCKLED" : (r0 == 2 ? "INVALID" : null))), new int[]{r0}, new String[]{"SAFETY_BELT_COMMAND_AREA_MAIN"});
            }
            else if (Runtime.inStr(area, "frontPassenger")) {
                int r0 = Runtime.readFid(1042, 315621439, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "UNBUCKLED" : (r0 == 1 ? "BUCKLED" : (r0 == 2 ? "INVALID" : null))), new int[]{r0}, new String[]{"SAFETY_BELT_COMMAND_AREA_DEPUTY"});
            }
            else if (Runtime.inStr(area, "rearLeft")) {
                int r0 = Runtime.readFid(1042, 1196425240, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "UNBUCKLED" : (r0 == 1 ? "BUCKLED" : (r0 == 2 ? "INVALID" : null))), new int[]{r0}, new String[]{"SAFETY_BELT_COMMAND_AREA_SECOND_ROW_SEAT_LEFT"});
            }
            else if (Runtime.inStr(area, "rearRight")) {
                int r0 = Runtime.readFid(1042, 1196425242, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "UNBUCKLED" : (r0 == 1 ? "BUCKLED" : (r0 == 2 ? "INVALID" : null))), new int[]{r0}, new String[]{"SAFETY_BELT_COMMAND_AREA_SECOND_ROW_SEAT_RIGHT"});
            }
            else if (Runtime.inStr(area, "rearCenter")) {
                int r0 = Runtime.readFid(1042, 1196425244, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 0 ? "UNBUCKLED" : (r0 == 1 ? "BUCKLED" : (r0 == 2 ? "INVALID" : null))), new int[]{r0}, new String[]{"SAFETY_BELT_COMMAND_AREA_SECOND_ROW_SEAT_MID"});
            }
            else {
                throw new IllegalArgumentException("No matching case for safety_belt.state with given params");
            }
        }

    }

    /** 副驾安全带提醒（safety_belt.warning）— 由目录自动生成，请勿手改。 */
    public static final class warning {
        private warning() {}

        public static SensorValue read() {
            if (true) {
                int r0 = Runtime.readFid(1042, 89129027, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "WARNING" : (r0 == 2 ? "NORMAL" : null)), new int[]{r0}, new String[]{"SAFETY_BELT_REMINDER"});
            }
            else {
                throw new IllegalArgumentException("No matching case for safety_belt.warning with given params");
            }
        }

    }

}
