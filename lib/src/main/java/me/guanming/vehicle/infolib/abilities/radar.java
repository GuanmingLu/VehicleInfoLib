package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** radar — 能力分组容器。 */
public final class radar {
    private radar() {}

    /** 雷达障碍物距离（radar.distance）— 由目录自动生成，请勿手改。 */
    public static final class distance {
        private distance() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 30, 31, 32, 33, 34, 35, 36, 37);
            if (Runtime.inInt(area, 30)) {
                int r0 = Runtime.readFid(1025, -1728053151, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_LEFT_FRONT"});
            }
            else if (Runtime.inInt(area, 31)) {
                int r0 = Runtime.readFid(1025, -1728053148, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_RIGHT_FRONT"});
            }
            else if (Runtime.inInt(area, 32)) {
                int r0 = Runtime.readFid(1025, -1728053146, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_LEFT_REAR"});
            }
            else if (Runtime.inInt(area, 33)) {
                int r0 = Runtime.readFid(1025, -1728053145, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_RIGHT_REAR"});
            }
            else if (Runtime.inInt(area, 34)) {
                int r0 = Runtime.readFid(1025, -1728053147, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_LEFT"});
            }
            else if (Runtime.inInt(area, 35)) {
                int r0 = Runtime.readFid(1025, -1728053150, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_FRONT_LEFT_MID"});
            }
            else if (Runtime.inInt(area, 36)) {
                int r0 = Runtime.readFid(1025, -1728053149, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{"RADAR_OBSTACLE_DISTANCE_FRONT_RIGHT_MID"});
            }
            else if (Runtime.inInt(area, 37)) {
                int r0 = Runtime.readFid(1025, -1728053143, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, null, new int[]{r0}, new String[]{""});
            }
            else {
                throw new IllegalArgumentException("No matching case for radar.distance with given params");
            }
        }

    }

}
