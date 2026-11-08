package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.SensorValue;

/** door_lock — 能力分组容器。 */
public final class door_lock {
    private door_lock() {}

    /** 四门锁控制（door_lock.all_doors）— 由目录自动生成，请勿手改。 */
    public static final class all_doors {
        private all_doors() {}

        public static void write(int value) {
            Runtime.checkEnum("value", value, 1, 2);
            if (true) {
                int v0 = value;
                Runtime.writeFid(1001, 1276141590, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for door_lock.all_doors with given params");
            }
        }

    }

    /** 儿童锁（door_lock.child_lock_state）— 由目录自动生成，请勿手改。 */
    public static final class child_lock_state {
        private child_lock_state() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 3, 4);
            if (Runtime.inInt(area, 3)) {
                int r0 = Runtime.readFid(1041, 1228931106, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_CHILDLOCK_LEFT"});
            }
            else if (Runtime.inInt(area, 4)) {
                int r0 = Runtime.readFid(1041, 1228931108, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_CHILDLOCK_RIGHT"});
            }
            else {
                throw new IllegalArgumentException("No matching case for door_lock.child_lock_state with given params");
            }
        }

        public static void write(int area, int value) {
            Runtime.checkEnum("area", area, 3, 4);
            Runtime.checkEnum("value", value, 1, 2);
            if (Runtime.inInt(area, 3)) {
                int v0 = value;
                Runtime.writeFid(1001, 1276141584, v0);
            }
            else if (Runtime.inInt(area, 4)) {
                int v0 = value;
                Runtime.writeFid(1001, 1276141586, v0);
            }
            else {
                throw new IllegalArgumentException("No matching case for door_lock.child_lock_state with given params");
            }
        }

    }

    /** 门锁状态（door_lock.lock_state）— 由目录自动生成，请勿手改。 */
    public static final class lock_state {
        private lock_state() {}

        public static SensorValue read(int area) {
            Runtime.checkEnum("area", area, 1, 2, 3, 4, 10);
            if (Runtime.inInt(area, 1)) {
                int r0 = Runtime.readFid(1041, 960495668, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_LEFT_FRONT"});
            }
            else if (Runtime.inInt(area, 2)) {
                int r0 = Runtime.readFid(1041, 960495670, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_RIGHT_FRONT"});
            }
            else if (Runtime.inInt(area, 3)) {
                int r0 = Runtime.readFid(1041, 960495672, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_LEFT_REAR"});
            }
            else if (Runtime.inInt(area, 4)) {
                int r0 = Runtime.readFid(1041, 960495674, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_RIGHT_REAR"});
            }
            else if (Runtime.inInt(area, 10)) {
                int r0 = Runtime.readFid(1041, 960495676, 5);
                if (Runtime.isSentinel(r0)) return SensorValue.failed();
                double d0 = r0;
                return Runtime.value(r0, d0, null, (r0 == 1 ? "UNLOCKED" : (r0 == 2 ? "LOCKED" : null)), new int[]{r0}, new String[]{"DOOR_LOCK_COMMAND_AREA_BACK"});
            }
            else {
                throw new IllegalArgumentException("No matching case for door_lock.lock_state with given params");
            }
        }

    }

}
