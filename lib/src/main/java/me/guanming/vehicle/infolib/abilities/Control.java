package me.guanming.vehicle.infolib.abilities;

import me.guanming.vehicle.infolib.Vehicle;

/**
 * 由 APK 车辆能力目录（assets/byd/devices，revision 2026.10.27.1）生成的全部控制能力。
 *
 * <p>该命名空间不修改、不复制原 {@code me.guanming.vehicle.infolib.Control}，
 * 每个方法直接通过 {@link Vehicle#write(int, int, int)} 写入，并返回原 Control 风格的 boolean。
 * 变体通过调用链区分，例如 {@code Control.bodywork.window.position.driver(50)}。
 */
public final class Control {
    private Control() {}

    private static boolean write(int dev, int fid, int value) {
        return Vehicle.write(dev, fid, value);
    }

    private static boolean writeAll(int[] devs, int[] fids, int[] values) {
        boolean ok = true;
        for (int i = 0; i < fids.length; i++) ok &= Vehicle.write(devs[i], fids[i], values[i]);
        return ok;
    }

    public static final class ac {
        private ac() {}

        /**
         * ac.close_passenger_vent — 关闭副驾出风.
         * <p>关闭副驾一侧的出风口。
         * <p>写入 Ac.AC_PASSENGER_TUYERE_CLOSING_SIGNAL_SET (dev=1000, fid=1324367910)
         */
        public static boolean closePassengerVent() {
            return write(1000, 1324367910, 1);
        }

        /**
         * ac.mirror_heating — 后视镜加热.
         * <p>打开或关闭后视镜加热。
         * @param value 0/1
         * <p>写入 Ac.AC_DEFROST_REAR_STATE_SET (dev=1000, fid=501219357)
         */
        public static boolean mirrorHeating(int value) {
            return write(1000, 501219357, value);
        }

        /**
         * ac.mirror_heating — 后视镜加热.
         * <p>打开或关闭后视镜加热。
         * @param value 0/1
         * <p>写入 Ac.AC_DEFROST_REAR_STATE_SET (dev=1000, fid=501219357)
         */
        public static boolean mirrorHeating(boolean value) {
            return mirrorHeating(value ? 1 : 0);
        }

        /**
         * ac.perfume_concentration — 香氛浓度.
         * <p>设置香氛浓度，或打开、关闭香氛。
         * @param value 1=LOWEST, 2=ON, 3=HIGHEST, 4=OFF
         * <p>写入 Ac.AC_PERFUME_WORKING_STATE_SET (dev=1000, fid=1324556328)
         */
        public static boolean perfumeConcentration(int value) {
            return write(1000, 1324556328, value);
        }

        /**
         * ac.perfume_type — 香氛种类.
         * <p>选择使用 1 号、2 号或 3 号香氛。
         * @param value 1=TYPE_1, 2=TYPE_2, 3=TYPE_3
         * <p>写入 Ac.AC_CURRENT_PERFUME_TYPE_SET (dev=1000, fid=1324556331)
         */
        public static boolean perfumeType(int value) {
            return write(1000, 1324556331, value);
        }

        /**
         * ac.compressor — AC 模式.
         * <p>打开或关闭空调的 AC 制冷功能，不会关闭空调本身。
         * @param value 0/1
         * <p>写入 Ac.AC_COMPRESSOR_MODE_SET (dev=1000, fid=501219344)
         */
        public static boolean compressor(int value) {
            return write(1000, 501219344, value);
        }

        /**
         * ac.compressor — AC 模式.
         * <p>打开或关闭空调的 AC 制冷功能，不会关闭空调本身。
         * @param value 0/1
         * <p>写入 Ac.AC_COMPRESSOR_MODE_SET (dev=1000, fid=501219344)
         */
        public static boolean compressor(boolean value) {
            return compressor(value ? 1 : 0);
        }

        /**
         * ac.cycle_mode — 内外循环.
         * <p>切换空调内循环、外循环或自动循环。
         * @param value 1=OUTSIDE_AIR, 2=RECIRCULATION, 3=AUTO
         * <p>写入 Ac.AC_CYCLE_MODE_SET (dev=1000, fid=501219355)
         */
        public static boolean cycleMode(int value) {
            return write(1000, 501219355, value);
        }

        /**
         * ac.maximum_cooling — 最大制冷.
         * <p>打开或关闭最大制冷，空调以最强制冷运行。
         * @param value 0/1
         * <p>写入 Ac.AC_MAX_COOLING_STATE_SET (dev=1000, fid=1337983004)
         */
        public static boolean maximumCooling(int value) {
            return write(1000, 1337983004, value);
        }

        /**
         * ac.maximum_cooling — 最大制冷.
         * <p>打开或关闭最大制冷，空调以最强制冷运行。
         * @param value 0/1
         * <p>写入 Ac.AC_MAX_COOLING_STATE_SET (dev=1000, fid=1337983004)
         */
        public static boolean maximumCooling(boolean value) {
            return maximumCooling(value ? 1 : 0);
        }

        /**
         * ac.rear_lock_state — 后排空调锁.
         * <p>锁定或解锁后排空调面板，锁定后后排无法操作空调。
         * @param value 0/1
         * <p>写入 Ac.AC_REAR_LOCK_STATE_SET (dev=1000, fid=1337983006)
         */
        public static boolean rearLockState(int value) {
            return write(1000, 1337983006, value);
        }

        /**
         * ac.rear_lock_state — 后排空调锁.
         * <p>锁定或解锁后排空调面板，锁定后后排无法操作空调。
         * @param value 0/1
         * <p>写入 Ac.AC_REAR_LOCK_STATE_SET (dev=1000, fid=1337983006)
         */
        public static boolean rearLockState(boolean value) {
            return rearLockState(value ? 1 : 0);
        }

        /**
         * ac.separate_control — 分控模式.
         * <p>打开或关闭温度分控，开启后各区域可以设置不同温度。
         * @param value 0/1
         * <p>写入 Ac.AC_TEMPCTRL_SEPARATE_STATE_SET (dev=1000, fid=501219346)
         */
        public static boolean separateControl(int value) {
            return write(1000, 501219346, value);
        }

        /**
         * ac.separate_control — 分控模式.
         * <p>打开或关闭温度分控，开启后各区域可以设置不同温度。
         * @param value 0/1
         * <p>写入 Ac.AC_TEMPCTRL_SEPARATE_STATE_SET (dev=1000, fid=501219346)
         */
        public static boolean separateControl(boolean value) {
            return separateControl(value ? 1 : 0);
        }

        /**
         * ac.synchronization — 整车同步.
         * <p>打开或关闭整车温度同步。
         * @param value 0/1
         * <p>写入 Ac.AC_SYNCHRONOUS_KEY_SET (dev=1000, fid=1324355604)
         */
        public static boolean synchronization(int value) {
            return write(1000, 1324355604, value);
        }

        /**
         * ac.synchronization — 整车同步.
         * <p>打开或关闭整车温度同步。
         * @param value 0/1
         * <p>写入 Ac.AC_SYNCHRONOUS_KEY_SET (dev=1000, fid=1324355604)
         */
        public static boolean synchronization(boolean value) {
            return synchronization(value ? 1 : 0);
        }

        /**
         * ac.ventilation — 驻车通风.
         * <p>打开或关闭驻车通风，停车时为车内通风换气。
         * @param value 0/1
         * <p>写入 Ac.AC_VENTILATION_STATE_SET (dev=1000, fid=501219394)
         */
        public static boolean ventilation(int value) {
            return write(1000, 501219394, value);
        }

        /**
         * ac.ventilation — 驻车通风.
         * <p>打开或关闭驻车通风，停车时为车内通风换气。
         * @param value 0/1
         * <p>写入 Ac.AC_VENTILATION_STATE_SET (dev=1000, fid=501219394)
         */
        public static boolean ventilation(boolean value) {
            return ventilation(value ? 1 : 0);
        }

        public static final class airflowMode {
            private airflowMode() {}

            /**
             * ac.airflow_mode — 吹风模式.
             * <p>选择出风口对着人吹、避开人吹，或左右扫风。
             * @param value 1=DIRECT, 2=AVOID, 3=SWEEP
             * <p>写入 Ac.AC_MAIN_DRIVE_BLOWS_MODE_SIGNAL_TO_PEOPLE_SET (dev=1000, fid=1324367888)；Ac.AC_MAIN_DRIVE_SIDE_TO_AVOID_BLOWING_MODE_SIGNAL_TO_PEOPLE_SET (dev=1000, fid=1324367892)；Ac.AC_MAIN_DRIVE_LEFT_AND_RIGHT_SWEEP_SIGNAL_SET (dev=1000, fid=1324367898)
             */
            public static boolean driver(int value) {
                if (value == 1) {
                    return write(1000, 1324367888, 1);
                }
                if (value == 2) {
                    return write(1000, 1324367892, 1);
                }
                if (value == 3) {
                    return write(1000, 1324367898, 1);
                }
                return false;
            }

            /**
             * ac.airflow_mode — 吹风模式.
             * <p>选择出风口对着人吹、避开人吹，或左右扫风。
             * @param value 1=DIRECT, 2=AVOID, 3=SWEEP
             * <p>写入 Ac.AC_PASSENGER_BLOWS_MODE_SIGNAL_TO_PEOPLE_SET (dev=1000, fid=1324367890)；Ac.AC_PASSENGER_SIDE_TO_AVOID_BLOWING_MODE_SIGNAL_TO_PEOPLE_SET (dev=1000, fid=1324367894)；Ac.AC_PASSENGER_LEFT_AND_RIGHT_SWEEP_SIGNAL_SET (dev=1000, fid=1324367902)
             */
            public static boolean frontPassenger(int value) {
                if (value == 1) {
                    return write(1000, 1324367890, 1);
                }
                if (value == 2) {
                    return write(1000, 1324367894, 1);
                }
                if (value == 3) {
                    return write(1000, 1324367902, 1);
                }
                return false;
            }

            /**
             * ac.airflow_mode — 吹风模式.
             * <p>选择出风口对着人吹、避开人吹，或左右扫风。
             * @param value 1=DIRECT, 2=AVOID, 3=SWEEP
             * <p>写入 Ac.AC_SECOND_ROW_LEFT_BLOW_TO_PERSON_MODE_SIGNAL_SET (dev=1000, fid=1324380208)；Ac.AC_SECOND_ROW_LEFT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL_SET (dev=1000, fid=1326448688)；Ac.AC_SECOND_ROW_LEFT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL_SET (dev=1000, fid=1324380218)
             */
            public static boolean rearLeft(int value) {
                if (value == 1) {
                    return write(1000, 1324380208, 1);
                }
                if (value == 2) {
                    return write(1000, 1326448688, 1);
                }
                if (value == 3) {
                    return write(1000, 1324380218, 1);
                }
                return false;
            }

            /**
             * ac.airflow_mode — 吹风模式.
             * <p>选择出风口对着人吹、避开人吹，或左右扫风。
             * @param value 1=DIRECT, 2=AVOID, 3=SWEEP
             * <p>写入 Ac.AC_SECOND_ROW_RIGHT_BLOW_TO_PERSON_MODE_SIGNAL_SET (dev=1000, fid=1324380210)；Ac.AC_SECOND_ROW_RIGHT_AVOID_BLOW_TO_PERSON_MODE_SIGNAL_SET (dev=1000, fid=1324380214)；Ac.AC_SECOND_ROW_RIGHT_SIDE_LEFT_RIGHT_SWEEP_SIGNAL_SET (dev=1000, fid=1324380222)
             */
            public static boolean rearRight(int value) {
                if (value == 1) {
                    return write(1000, 1324380210, 1);
                }
                if (value == 2) {
                    return write(1000, 1324380214, 1);
                }
                if (value == 3) {
                    return write(1000, 1324380222, 1);
                }
                return false;
            }

        }

        public static final class defrost {
            private defrost() {}

            /**
             * ac.defrost — 除霜.
             * <p>打开或关闭前挡风玻璃除霜或后窗电除霜。
             * @param value 0/1
             * <p>写入 Ac.AC_DEFROST_FRONT_STATE_SET (dev=1000, fid=501219362)
             */
            public static boolean frontWindshield(int value) {
                return write(1000, 501219362, value);
            }

            /**
             * ac.defrost — 除霜.
             * <p>打开或关闭前挡风玻璃除霜或后窗电除霜。
             * @param value 0/1
             * <p>写入 Ac.AC_DEFROST_FRONT_STATE_SET (dev=1000, fid=501219362)
             */
            public static boolean frontWindshield(boolean value) {
                return frontWindshield(value ? 1 : 0);
            }

            /**
             * ac.defrost — 除霜.
             * <p>打开或关闭前挡风玻璃除霜或后窗电除霜。
             * @param value 0/1
             * <p>写入 Ac.AC_DEFROST_REAR_STATE_SET (dev=1000, fid=501219357)
             */
            public static boolean rearWindshield(int value) {
                return write(1000, 501219357, value);
            }

            /**
             * ac.defrost — 除霜.
             * <p>打开或关闭前挡风玻璃除霜或后窗电除霜。
             * @param value 0/1
             * <p>写入 Ac.AC_DEFROST_REAR_STATE_SET (dev=1000, fid=501219357)
             */
            public static boolean rearWindshield(boolean value) {
                return rearWindshield(value ? 1 : 0);
            }

        }

        public static final class windMode {
            private windMode() {}

            /**
             * ac.wind_mode — 出风模式.
             * <p>选择空调出风位置：吹面、吹脚，或与除霜组合。
             * @param value 1=FACE, 2=FACE_FOOT, 3=FOOT, 4=FOOT_DEFROST, 5=DEFROST, 6=FACE_FOOT_DEFROST, 7=FACE_DEFROST
             * <p>写入 Ac.AC_WIND_MODE_SET (dev=1000, fid=501219336)
             */
            public static boolean frontPair(int value) {
                return write(1000, 501219336, value);
            }

            /**
             * ac.wind_mode — 出风模式.
             * <p>选择空调出风位置：吹面、吹脚，或与除霜组合。
             * @param value 1=FACE, 2=FACE_FOOT, 3=FOOT
             * <p>写入 Ac.AC_REAR_WIND_MODE_SET (dev=1000, fid=1337983000)
             */
            public static boolean rearCabin(int value) {
                return write(1000, 1337983000, value);
            }

        }

        public static final class zoneFanLevel {
            private zoneFanLevel() {}

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_WIND_LEVEL_SET (dev=1000, fid=501219340)；Ac.AC_MAIN_DRIVE_AIR_CONDITION_WINDLEVEL_SET (dev=1000, fid=1324355600)
             */
            public static boolean driver(int value) {
                return write(1000, 501219340, value);
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_WIND_LEVEL_SET (dev=1000, fid=501219340)；Ac.AC_PASSENGER_WINDLEVEL_SET (dev=1000, fid=1324355612)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 501219340, value);
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_WIND_LEVEL_SET (dev=1000, fid=501219340)；Ac.AC_MAIN_DRIVE_AIR_CONDITION_WINDLEVEL_SET (dev=1000, fid=1324355600)；Ac.AC_PASSENGER_WINDLEVEL_SET (dev=1000, fid=1324355612)
             */
            public static boolean frontPair(int value) {
                return writeAll(new int[]{1000, 1000}, new int[]{501219340, 501219340}, new int[]{value, value});
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_WIND_LEVEL_SET (dev=1000, fid=501219340)；Ac.AC_REAR_WIND_LEVEL_SET (dev=1000, fid=1337982984)；Ac.AC_MAIN_DRIVE_AIR_CONDITION_WINDLEVEL_SET (dev=1000, fid=1324355600) …
             */
            public static boolean allCabin(int value) {
                return writeAll(new int[]{1000, 1000}, new int[]{501219340, 501219340}, new int[]{value, value});
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_REAR_WIND_LEVEL_SET (dev=1000, fid=1337982984)
             */
            public static boolean rearCabin(int value) {
                return write(1000, 1337982984, value);
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_MIDDLE_LEFT_WIND_LEVEL_SET (dev=1000, fid=1324355632)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1324355632, value);
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_MIDDLE_RIGHT_WIND_LEVEL_SET (dev=1000, fid=1324355636)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1324355636, value);
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_REAR_WIND_LEVEL_SET (dev=1000, fid=1337982984)
             */
            public static boolean thirdRow(int value) {
                return write(1000, 1337982984, value);
            }

        }

        public static final class controlMode {
            private controlMode() {}

            /**
             * ac.control_mode — 自动模式.
             * <p>打开或关闭空调自动模式，由空调自动调节风量和出风。
             * @param value 1=AUTO, 2=MANUAL
             * <p>写入 Ac.AC_CTRL_MODE_SET (dev=1000, fid=501219352)
             */
            public static boolean frontPair(int value) {
                return write(1000, 501219352, value);
            }

            /**
             * ac.control_mode — 自动模式.
             * <p>打开或关闭空调自动模式，由空调自动调节风量和出风。
             * @param value 1=AUTO, 2=MANUAL
             * <p>写入 Ac.AC_REAR_CTRL_MODE_SET (dev=1000, fid=1337982990)
             */
            public static boolean rearCabin(int value) {
                return write(1000, 1337982990, value);
            }

        }

        public static final class power {
            private power() {}

            /**
             * ac.power — 空调开关.
             * <p>打开或关闭空调。
             * @param value 0/1
             * <p>写入 Ac.AC_POWER_STATE_SET (dev=1000, fid=501219364)
             */
            public static boolean frontPair(int value) {
                return write(1000, 501219364, value);
            }

            /**
             * ac.power — 空调开关.
             * <p>打开或关闭空调。
             * @param value 0/1
             * <p>写入 Ac.AC_POWER_STATE_SET (dev=1000, fid=501219364)
             */
            public static boolean frontPair(boolean value) {
                return frontPair(value ? 1 : 0);
            }

            /**
             * ac.power — 空调开关.
             * <p>打开或关闭空调。
             * @param value 0/1
             * <p>写入 Ac.AC_REAR_POWER_STATE_SET (dev=1000, fid=1337982988)
             */
            public static boolean rearCabin(int value) {
                return write(1000, 1337982988, value);
            }

            /**
             * ac.power — 空调开关.
             * <p>打开或关闭空调。
             * @param value 0/1
             * <p>写入 Ac.AC_REAR_POWER_STATE_SET (dev=1000, fid=1337982988)
             */
            public static boolean rearCabin(boolean value) {
                return rearCabin(value ? 1 : 0);
            }

        }

        public static final class zonePower {
            private zonePower() {}

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_MAIN_DRIVE_AIR_CONDITION_WINDLEVEL_SET (dev=1000, fid=1324355600)
             */
            public static boolean driver(int value) {
                return write(1000, 1324355600, value);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_MAIN_DRIVE_AIR_CONDITION_WINDLEVEL_SET (dev=1000, fid=1324355600)
             */
            public static boolean driver(boolean value) {
                return driver(value ? 1 : 0);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_PASSENGER_WINDLEVEL_SET (dev=1000, fid=1324355612)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 1324355612, value);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_PASSENGER_WINDLEVEL_SET (dev=1000, fid=1324355612)
             */
            public static boolean frontPassenger(boolean value) {
                return frontPassenger(value ? 1 : 0);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_MIDDLE_LEFT_WIND_LEVEL_SET (dev=1000, fid=1324355632)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1324355632, value);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_MIDDLE_LEFT_WIND_LEVEL_SET (dev=1000, fid=1324355632)
             */
            public static boolean rearLeft(boolean value) {
                return rearLeft(value ? 1 : 0);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_MIDDLE_RIGHT_WIND_LEVEL_SET (dev=1000, fid=1324355636)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1324355636, value);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_MIDDLE_RIGHT_WIND_LEVEL_SET (dev=1000, fid=1324355636)
             */
            public static boolean rearRight(boolean value) {
                return rearRight(value ? 1 : 0);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_REAR_WIND_LEVEL_SET (dev=1000, fid=1337982984)
             */
            public static boolean thirdRow(int value) {
                return write(1000, 1337982984, value);
            }

            /**
             * ac.zone_power — 温区开关.
             * <p>单独打开或关闭某个温区的空调。
             * @param value 0/1
             * <p>写入 Ac.AC_REAR_WIND_LEVEL_SET (dev=1000, fid=1337982984)
             */
            public static boolean thirdRow(boolean value) {
                return thirdRow(value ? 1 : 0);
            }

        }

        public static final class temperature {
            private temperature() {}

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_TEMP_MAIN_SET (dev=1000, fid=501219368)
             */
            public static boolean driver(int value) {
                return write(1000, 501219368, value);
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_TEMP_DEPUTY_SET (dev=1000, fid=501219376)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 501219376, value);
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_TEMP_REAR_SET (dev=1000, fid=501219384)
             */
            public static boolean rearCabin(int value) {
                return write(1000, 501219384, value);
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_MIDDLE_LEFT_TEMP_SET (dev=1000, fid=1324355624)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1324355624, value);
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_MIDDLE_RIGHT_TEMP_SET (dev=1000, fid=1324355640)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1324355640, value);
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_TEMP_REAR_SET (dev=1000, fid=598736952)
             */
            public static boolean thirdRow(int value) {
                return write(1000, 598736952, value);
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_TEMP_MAIN_SET (dev=1000, fid=501219368)；Ac.AC_TEMP_DEPUTY_SET (dev=1000, fid=501219376)
             */
            public static boolean frontPair(int value) {
                return writeAll(new int[]{1000, 1000}, new int[]{501219368, 501219376}, new int[]{value, value});
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * @param value 原始整数
             * <p>写入 Ac.AC_TEMP_MAIN_SET (dev=1000, fid=501219368)；Ac.AC_TEMP_DEPUTY_SET (dev=1000, fid=501219376)；Ac.AC_TEMP_REAR_SET (dev=1000, fid=501219384) …
             */
            public static boolean allCabin(int value) {
                return writeAll(new int[]{1000, 1000}, new int[]{501219368, 501219376}, new int[]{value, value});
            }

        }

    }

    public static final class adas {
        private adas() {}

        /**
         * adas.aeb_state — 自动紧急制动.
         * <p>打开或关闭自动紧急制动，并查看其状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_AEB_STATE_SET (dev=1038, fid=944767020)
         */
        public static boolean aebState(int value) {
            return write(1038, 944767020, value);
        }

        /**
         * adas.aeb_state — 自动紧急制动.
         * <p>打开或关闭自动紧急制动，并查看其状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_AEB_STATE_SET (dev=1038, fid=944767020)
         */
        public static boolean aebState(boolean value) {
            return aebState(value ? 1 : 0);
        }

        /**
         * adas.auto_hold_state — 自动驻车.
         * <p>打开或关闭自动驻车，并查看驻车工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_AVH_STATE_SET (dev=1038, fid=944766986)
         */
        public static boolean autoHoldState(int value) {
            return write(1038, 944766986, value);
        }

        /**
         * adas.auto_hold_state — 自动驻车.
         * <p>打开或关闭自动驻车，并查看驻车工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_AVH_STATE_SET (dev=1038, fid=944766986)
         */
        public static boolean autoHoldState(boolean value) {
            return autoHoldState(value ? 1 : 0);
        }

        /**
         * adas.brake_foot_sense — 制动助力模式.
         * <p>选择舒适或运动模式，调整制动踏板的助力感受。
         * @param value 1=SPORT_OR_STANDARD, 2=COMFORT
         * <p>写入 Adas.ADAS_CMD_BRAKE_FOOT_SENSE_SET (dev=1038, fid=944766988)
         */
        public static boolean brakeFootSense(int value) {
            return write(1038, 944766988, value);
        }

        /**
         * adas.electronic_stability_control — ESP 车身稳定.
         * <p>打开或关闭车身稳定控制功能。
         * @param value 0/1
         * <p>写入 Adas.ADAS_ESP_STATE_SET (dev=1038, fid=944766984)
         */
        public static boolean electronicStabilityControl(int value) {
            return write(1038, 944766984, value);
        }

        /**
         * adas.electronic_stability_control — ESP 车身稳定.
         * <p>打开或关闭车身稳定控制功能。
         * @param value 0/1
         * <p>写入 Adas.ADAS_ESP_STATE_SET (dev=1038, fid=944766984)
         */
        public static boolean electronicStabilityControl(boolean value) {
            return electronicStabilityControl(value ? 1 : 0);
        }

        /**
         * adas.forward_collision_warning — 前向碰撞预警.
         * <p>选择前向碰撞预警的提示时机，或关闭预警。
         * @param value 1=OFF, 2=LATE, 3=MEDIUM, 4=EARLY
         * <p>写入 Adas.ADAS_FCW_LEVEL_SET (dev=1038, fid=1324560420)
         */
        public static boolean forwardCollisionWarning(int value) {
            return write(1038, 1324560420, value);
        }

        /**
         * adas.front_cross_traffic_alert — 前方穿行预警.
         * <p>打开或关闭前方穿行预警。
         * @param value 0/1
         * <p>写入 Adas.ADAS_FCTA_SWITCH_SET (dev=1038, fid=1324560400)
         */
        public static boolean frontCrossTrafficAlert(int value) {
            return write(1038, 1324560400, value);
        }

        /**
         * adas.front_cross_traffic_alert — 前方穿行预警.
         * <p>打开或关闭前方穿行预警。
         * @param value 0/1
         * <p>写入 Adas.ADAS_FCTA_SWITCH_SET (dev=1038, fid=1324560400)
         */
        public static boolean frontCrossTrafficAlert(boolean value) {
            return frontCrossTrafficAlert(value ? 1 : 0);
        }

        /**
         * adas.hill_descent_control — 陡坡缓降.
         * <p>打开或关闭陡坡缓降，并查看其工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_CMD_HDC_SWITCH_SET (dev=1038, fid=944766998)
         */
        public static boolean hillDescentControl(int value) {
            return write(1038, 944766998, value);
        }

        /**
         * adas.hill_descent_control — 陡坡缓降.
         * <p>打开或关闭陡坡缓降，并查看其工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_CMD_HDC_SWITCH_SET (dev=1038, fid=944766998)
         */
        public static boolean hillDescentControl(boolean value) {
            return hillDescentControl(value ? 1 : 0);
        }

        /**
         * adas.traffic_jam_assist_state — 交通拥堵辅助.
         * <p>打开或关闭交通拥堵辅助，并查看其工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_TJA_ICA_SWITCH_SET (dev=1038, fid=944766996)
         */
        public static boolean trafficJamAssistState(int value) {
            return write(1038, 944766996, value);
        }

        /**
         * adas.traffic_jam_assist_state — 交通拥堵辅助.
         * <p>打开或关闭交通拥堵辅助，并查看其工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_TJA_ICA_SWITCH_SET (dev=1038, fid=944766996)
         */
        public static boolean trafficJamAssistState(boolean value) {
            return trafficJamAssistState(value ? 1 : 0);
        }

        /**
         * adas.intelligent_torque_control — iTAC 智能扭矩控制.
         * <p>启用或停用车辆的 iTAC 智能扭矩控制。
         * @param enabled 0/1
         * <p>写入 Setting.SET_ITAC_STATE_SET (dev=1000, fid=1324376094)
         */
        public static boolean intelligentTorqueControl(int enabled) {
            return write(1000, 1324376094, enabled);
        }

        /**
         * adas.intelligent_torque_control — iTAC 智能扭矩控制.
         * <p>启用或停用车辆的 iTAC 智能扭矩控制。
         * @param enabled 0/1
         * <p>写入 Setting.SET_ITAC_STATE_SET (dev=1000, fid=1324376094)
         */
        public static boolean intelligentTorqueControl(boolean enabled) {
            return intelligentTorqueControl(enabled ? 1 : 0);
        }

        public static final class laneDepartureMode {
            private laneDepartureMode() {}

            /**
             * adas.lane_departure_mode — 车道偏离模式.
             * <p>选择车道偏离预警或车道保持，或关闭该功能。
             * @param value 0=OFF, 1=DEPARTURE_WARNING
             * <p>写入 Adas.ADAS_LKS_MODE_SET (dev=1038, fid=944767016)
             */
            public static boolean warningOnly(int value) {
                return write(1038, 944767016, value);
            }

            /**
             * adas.lane_departure_mode — 车道偏离模式.
             * <p>选择车道偏离预警或车道保持，或关闭该功能。
             * @param value 0=OFF, 1=DEPARTURE_WARNING, 2=LANE_KEEPING
             * <p>写入 Adas.ADAS_LKS_MODE_SET (dev=1038, fid=944767016)
             */
            public static boolean warningAndKeeping(int value) {
                return write(1038, 944767016, value);
            }

        }

    }

    public static final class airQuality {
        private airQuality() {}

        /**
         * air_quality.pm25_detection — PM2.5 检测开关.
         * <p>打开或关闭 PM2.5 检测。
         * @param value 0/1
         * <p>写入 PM25_DUAL_CHANNEL_DETECT_STATE_SET (dev=1008, fid=1330642970)
         */
        public static boolean pm25Detection(int value) {
            return write(1008, 1330642970, value);
        }

        /**
         * air_quality.pm25_detection — PM2.5 检测开关.
         * <p>打开或关闭 PM2.5 检测。
         * @param value 0/1
         * <p>写入 PM25_DUAL_CHANNEL_DETECT_STATE_SET (dev=1008, fid=1330642970)
         */
        public static boolean pm25Detection(boolean value) {
            return pm25Detection(value ? 1 : 0);
        }

        /**
         * air_quality.purification — 空气净化.
         * <p>启动或停止车内空气净化，并查看净化进度状态。
         * @param value 0/1
         * <p>写入 Ac.AC_QUICK_CLEAN_AIR_SET (dev=1000, fid=1330642960)
         */
        public static boolean purification(int value) {
            return write(1000, 1330642960, value);
        }

        /**
         * air_quality.purification — 空气净化.
         * <p>启动或停止车内空气净化，并查看净化进度状态。
         * @param value 0/1
         * <p>写入 Ac.AC_QUICK_CLEAN_AIR_SET (dev=1000, fid=1330642960)
         */
        public static boolean purification(boolean value) {
            return purification(value ? 1 : 0);
        }

    }

    public static final class audio {
        private audio() {}

        /**
         * audio.external_broadcast_end — 外放结束通知.
         * <p>在车外播报结束后通知音频系统。
         * <p>写入 Audio.ADAS_UE_BROADCAST_ENDING_FLAG_SET (dev=1002, fid=-1442839933)
         */
        public static boolean externalBroadcastEnd() {
            return write(1002, -1442839933, 1);
        }

        /**
         * audio.headrest_effect — 头枕音效.
         * <p>选择头枕音响的环绕、驾享或独享音效，或关闭音效。
         * @param value 1=OFF, 2=SURROUND, 3=DRIVER, 4=PRIVATE
         * <p>写入 Audio.AUDIO_HEADREST_SOUND_EFFECT_STATUS_SET (dev=1002, fid=-1676672971)
         */
        public static boolean headrestEffect(int value) {
            return write(1002, -1676672971, value);
        }

        /**
         * audio.lock_sound_playback_flag — 车外音效通道.
         * <p>打开或关闭车外音效使用的音频通道。
         * @param value 0/1
         * <p>写入 Audio.AUDIO_LOCK_CAR_SOUND_EFFECT_PLAYBACK_STATUS_SET (dev=1000, fid=-1442839775)
         */
        public static boolean lockSoundPlaybackFlag(int value) {
            return write(1000, -1442839775, value);
        }

        /**
         * audio.lock_sound_playback_flag — 车外音效通道.
         * <p>打开或关闭车外音效使用的音频通道。
         * @param value 0/1
         * <p>写入 Audio.AUDIO_LOCK_CAR_SOUND_EFFECT_PLAYBACK_STATUS_SET (dev=1000, fid=-1442839775)
         */
        public static boolean lockSoundPlaybackFlag(boolean value) {
            return lockSoundPlaybackFlag(value ? 1 : 0);
        }

        /**
         * audio.prompt_volume — 车辆提示音量.
         * <p>选择车辆提示音的低、中或高音量。
         * @param value 1=LOW, 2=MEDIUM, 3=HIGH
         * <p>写入 Audio.AUDIO_PROMPT_VOLUME_LEVEL_SET (dev=1000, fid=-1442839911)
         */
        public static boolean promptVolume(int value) {
            return write(1000, -1442839911, value);
        }

        /**
         * audio.simulated_engine_sound — 模拟声浪.
         * <p>打开或关闭模拟引擎声浪。
         * @param value 0/1
         * <p>写入 Audio.AUDIO_ESS_SETTING_1B1_STATUS_SET (dev=1002, fid=454033419)
         */
        public static boolean simulatedEngineSound(int value) {
            return write(1002, 454033419, value);
        }

        /**
         * audio.simulated_engine_sound — 模拟声浪.
         * <p>打开或关闭模拟引擎声浪。
         * @param value 0/1
         * <p>写入 Audio.AUDIO_ESS_SETTING_1B1_STATUS_SET (dev=1002, fid=454033419)
         */
        public static boolean simulatedEngineSound(boolean value) {
            return simulatedEngineSound(value ? 1 : 0);
        }

        /**
         * audio.sound_balance — 声场平衡.
         * <p>调节声音在车内左右、前后方向的分布。
         * @param x 原始整数
         * @param y 原始整数
         * <p>写入 Audio.AUDIO_X_SOUND_FIELD_SET (dev=1002, fid=1309036560)；Audio.AUDIO_Y_SOUND_FIELD_SET (dev=1002, fid=1309036576)
         */
        public static boolean soundBalance(int x, int y) {
            return writeAll(new int[]{1002, 1002}, new int[]{1309036560, 1309036576}, new int[]{x, y});
        }

        public static final class externalBroadcastPosition {
            private externalBroadcastPosition() {}

            /**
             * audio.external_broadcast_position — 车外播报通道.
             * <p>打开或关闭车外播报使用的音频通道。
             * @param value 0/1
             * <p>写入 Audio.ADAS_UE_BROADCAST_POSITION_SET (dev=1000, fid=-1442839934)
             */
            public static boolean common(int value) {
                return write(1000, -1442839934, value);
            }

            /**
             * audio.external_broadcast_position — 车外播报通道.
             * <p>打开或关闭车外播报使用的音频通道。
             * @param value 0/1
             * <p>写入 Audio.ADAS_UE_BROADCAST_POSITION_SET (dev=1000, fid=-1442839934)
             */
            public static boolean common(boolean value) {
                return common(value ? 1 : 0);
            }

            /**
             * audio.external_broadcast_position — 车外播报通道.
             * <p>打开或关闭车外播报使用的音频通道。
             * @param value 0/1
             * <p>写入 Audio.ADAS_UE_BROADCAST_POSITION_SET (dev=1002, fid=-1442839934)
             */
            public static boolean controller23(int value) {
                return write(1002, -1442839934, value);
            }

            /**
             * audio.external_broadcast_position — 车外播报通道.
             * <p>打开或关闭车外播报使用的音频通道。
             * @param value 0/1
             * <p>写入 Audio.ADAS_UE_BROADCAST_POSITION_SET (dev=1002, fid=-1442839934)
             */
            public static boolean controller23(boolean value) {
                return controller23(value ? 1 : 0);
            }

        }

        public static final class soundFocus {
            private soundFocus() {}

            /**
             * audio.sound_focus — 声场聚焦.
             * <p>选择声音聚焦的座位区域或声场模式。
             * @param value 1=ALL, 2=DRIVER, 3=PASSENGER, 4=REAR, 5=SURROUND, 6=CUSTOM
             * <p>写入 Audio.AUDIO_LEFT_RIGHT_FIELD_SET_SET_qy (dev=1002, fid=470810653)
             */
            public static boolean dynaudio(int value) {
                return write(1002, 470810653, value);
            }

            /**
             * audio.sound_focus — 声场聚焦.
             * <p>选择声音聚焦的座位区域或声场模式。
             * @param value 4=REAR, 6=ALL, 7=FRONT
             * <p>写入 Audio.AUDIO_LEFT_RIGHT_FIELD_SET_SET (dev=1002, fid=470810653)
             */
            public static boolean devialet(int value) {
                return write(1002, 470810653, value);
            }

        }

        public static final class soundProfile {
            private soundProfile() {}

            /**
             * audio.sound_profile — 声音特色.
             * <p>选择音响的音色风格，例如原声、动感或人声。
             * @param value 1=AUTHENTIC, 2=DYNAMIC, 3=SOFT, 4=SPEECH, 5=CUSTOM
             * <p>写入 Audio.AUDIO_DYNAUDIO_SOUND_FEATURES_SET (dev=1002, fid=-1693450216)
             */
            public static boolean dynaudio(int value) {
                return write(1002, -1693450216, value);
            }

            /**
             * audio.sound_profile — 声音特色.
             * <p>选择音响的音色风格，例如原声、动感或人声。
             * @param value 1=CLASSIC, 3=DYNAMIC, 5=VOCAL, 6=CUSTOM
             * <p>写入 Audio.AUDIO_DYNAUDIO_SOUND_FEATURES_SET (dev=1002, fid=-1693450216)
             */
            public static boolean devialet(int value) {
                return write(1002, -1693450216, value);
            }

        }

    }

    public static final class bodywork {
        private bodywork() {}

        /**
         * bodywork.spoiler — 电动尾翼.
         * <p>打开或收起电动尾翼。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_ELECTRIC_TAIL_SOFT_SWITCH_ORDER_SET (dev=1000, fid=1276182580)
         */
        public static boolean spoiler(int enabled) {
            return write(1000, 1276182580, enabled);
        }

        /**
         * bodywork.spoiler — 电动尾翼.
         * <p>打开或收起电动尾翼。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_ELECTRIC_TAIL_SOFT_SWITCH_ORDER_SET (dev=1000, fid=1276182580)
         */
        public static boolean spoiler(boolean enabled) {
            return spoiler(enabled ? 1 : 0);
        }

        /**
         * bodywork.charging_flap — 充电口盖.
         * <p>打开或关闭右侧充电口盖。
         * @param enabled 0/1
         * <p>写入 Setting.CHARGING_RIGHT_FLAP_COVER_STATE_SET (dev=1000, fid=1276178470)
         */
        public static boolean chargingFlap(int enabled) {
            return write(1000, 1276178470, enabled);
        }

        /**
         * bodywork.charging_flap — 充电口盖.
         * <p>打开或关闭右侧充电口盖。
         * @param enabled 0/1
         * <p>写入 Setting.CHARGING_RIGHT_FLAP_COVER_STATE_SET (dev=1000, fid=1276178470)
         */
        public static boolean chargingFlap(boolean enabled) {
            return chargingFlap(enabled ? 1 : 0);
        }

        /**
         * bodywork.electric_step — 电动踏板.
         * <p>打开或关闭电动踏板功能。
         * @param value 0/1
         * <p>写入 Bodywork.BODYWORK_ELECTRIC_PEDAL_MODE_STATUS_SET (dev=1001, fid=1276194884)
         */
        public static boolean electricStep(int value) {
            return write(1001, 1276194884, value);
        }

        /**
         * bodywork.electric_step — 电动踏板.
         * <p>打开或关闭电动踏板功能。
         * @param value 0/1
         * <p>写入 Bodywork.BODYWORK_ELECTRIC_PEDAL_MODE_STATUS_SET (dev=1001, fid=1276194884)
         */
        public static boolean electricStep(boolean value) {
            return electricStep(value ? 1 : 0);
        }

        /**
         * bodywork.front_hood — 电动前舱盖.
         * <p>打开或关闭电动前舱盖。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_ELECTRIC_FORECABIN_SWITCH_SET (dev=1000, fid=1276182560)
         */
        public static boolean frontHood(int enabled) {
            return write(1000, 1276182560, enabled);
        }

        /**
         * bodywork.front_hood — 电动前舱盖.
         * <p>打开或关闭电动前舱盖。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_ELECTRIC_FORECABIN_SWITCH_SET (dev=1000, fid=1276182560)
         */
        public static boolean frontHood(boolean enabled) {
            return frontHood(enabled ? 1 : 0);
        }

        /**
         * bodywork.manual_trunk_release — 手动后备箱解锁.
         * <p>释放手动后备箱锁扣，随后可手动打开后备箱。
         * <p>写入 Bodywork.BODYWORK_LUGGAGE_SETTING_SET (dev=1001, fid=1276141592)
         */
        public static boolean manualTrunkRelease() {
            return write(1001, 1276141592, 1);
        }

        public static final class electricDoor {
            private electricDoor() {}

            public static final class position {
                private position() {}

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP
                 * <p>写入 Bodywork.BODYWORK_LF_ELECTRIC_DOOR_SET (dev=1001, fid=1276178480)
                 */
                public static boolean driver(int value) {
                    return write(1001, 1276178480, value);
                }

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP
                 * <p>写入 Bodywork.BODYWORK_RF_ELECTRIC_DOOR_SET (dev=1001, fid=1276178483)
                 */
                public static boolean frontPassenger(int value) {
                    return write(1001, 1276178483, value);
                }

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP
                 * <p>写入 Bodywork.BODYWORK_LR_ELECTRIC_DOOR_SET (dev=1001, fid=1276178488)
                 */
                public static boolean rearLeft(int value) {
                    return write(1001, 1276178488, value);
                }

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP
                 * <p>写入 Bodywork.BODYWORK_RR_ELECTRIC_DOOR_SET (dev=1001, fid=1276178491)
                 */
                public static boolean rearRight(int value) {
                    return write(1001, 1276178491, value);
                }

            }

        }

        public static final class slidingDoor {
            private slidingDoor() {}

            /**
             * bodywork.sliding_door — 电动侧滑门.
             * <p>打开或关闭左后、右后的电动侧滑门，并查看门的位置状态。
             * @param value 1=OPEN, 2=CLOSE
             * <p>写入 Bodywork.BODYWORK_L_ELEC_SLIDE_STATE_SET33 (dev=1001, fid=1312817200)
             */
            public static boolean rearLeft(int value) {
                return write(1001, 1312817200, value);
            }

            /**
             * bodywork.sliding_door — 电动侧滑门.
             * <p>打开或关闭左后、右后的电动侧滑门，并查看门的位置状态。
             * @param value 1=OPEN, 2=CLOSE
             * <p>写入 Bodywork.BODYWORK_R_ELEC_SLIDE_STATE_SET33 (dev=1001, fid=1312817202)
             */
            public static boolean rearRight(int value) {
                return write(1001, 1312817202, value);
            }

        }

        public static final class roof {
            private roof() {}

            /**
             * bodywork.roof.command — 天窗动作.
             * <p>关闭天窗、将天窗设为透气位置，或停止天窗移动。
             * @param value 0=CLOSE, 253=VENTILATE, 254=STOP
             * <p>写入 Bodywork.BODYWORK_MOON_ROOF_OPEN_PERCENT_SET (dev=1001, fid=1330642976)
             */
            public static boolean command(int value) {
                return write(1001, 1330642976, value);
            }

            /**
             * bodywork.roof.position — 天窗开度.
             * <p>查看或设置天窗的打开百分比。
             * @param value 原始整数
             * <p>写入 Bodywork.BODYWORK_MOON_ROOF_OPEN_PERCENT_SET (dev=1001, fid=1330642976)
             */
            public static boolean position(int value) {
                return write(1001, 1330642976, value);
            }

        }

        public static final class sunshade {
            private sunshade() {}

            /**
             * bodywork.sunshade.stop — 停止前遮阳帘.
             * <p>停止前排车顶遮阳帘移动。
             * @param value 254=STOP
             * <p>写入 Bodywork.BODYWORK_SUNSHADE_PANEL_PERCENT_SET (dev=1001, fid=1330642984)
             */
            public static boolean stop(int value) {
                if (value == 254) {
                    return write(1001, 1330642984, value);
                }
                return false;
            }

            public static final class position {
                private position() {}

                /**
                 * bodywork.sunshade.position — 车顶遮阳帘开度.
                 * <p>查看或设置前排、后排车顶遮阳帘的打开百分比。
                 * @param value 原始整数
                 * <p>写入 Bodywork.BODYWORK_SUNSHADE_PANEL_PERCENT_SET (dev=1001, fid=1330642984)
                 */
                public static boolean front(int value) {
                    return write(1001, 1330642984, value);
                }

                /**
                 * bodywork.sunshade.position — 车顶遮阳帘开度.
                 * <p>查看或设置前排、后排车顶遮阳帘的打开百分比。
                 * @param value 原始整数
                 * <p>写入 Bodywork.BODYWORK_REAR_SUNSHADE_PANEL_PERCENT_SET (dev=1001, fid=1276178472)
                 */
                public static boolean rear(int value) {
                    return write(1001, 1276178472, value);
                }

            }

        }

        public static final class trunk {
            private trunk() {}

            /**
             * bodywork.trunk.command — 尾门动作.
             * <p>打开、关闭或停止电动尾门。
             * @param value 1=OPEN, 2=STOP, 3=CLOSE
             * <p>写入 Setting.SET_VOICE_CTRL_BACK_DOOR_SET (dev=1000, fid=1125122080)
             */
            public static boolean command(int value) {
                return write(1000, 1125122080, value);
            }

            /**
             * bodywork.trunk.open_height — 尾门最大开启高度.
             * <p>查看或调整电动尾门开启时的高度上限。
             * @param value 原始整数
             * <p>写入 Setting.SET_BACK_DOOR_OPEN_HEIGHT_SET (dev=1023, fid=1069547584)
             */
            public static boolean openHeight(int value) {
                return write(1023, 1069547584, value);
            }

        }

        public static final class window {
            private window() {}

            /**
             * bodywork.window.command — 车窗组合动作.
             * <p>同时操作前排、后排或全部侧窗，也可联动天窗和前遮阳帘。
             * @param value 0=CLOSE_ALL_WINDOWS, 1=OPEN_ALL_WINDOWS, 2=HALF_OPEN_ALL_WINDOWS, 3=OPEN_FRONT_WINDOWS, 4=CLOSE_FRONT_WINDOWS, 5=HALF_OPEN_FRONT_WINDOWS, 6=VENTILATE_FRONT_WINDOWS, 7=OPEN_REAR_WINDOWS, 8=CLOSE_REAR_WINDOWS, 9=HALF_OPEN_REAR_WINDOWS, 10=VENTILATE_REAR_WINDOWS, 13=STOP_ALL_OPENINGS, 11=CLOSE_ALL_OPENINGS, 12=VENTILATE_ALL_OPENINGS
             * <p>写入 Bodywork.BODYWORK_LF_WINDOW_CTRL_SET (dev=1001, fid=1125122104)；Bodywork.BODYWORK_RF_WINDOW_CTRL_SET (dev=1001, fid=1125122107)；Bodywork.BODYWORK_LR_WINDOW_CTRL_SET (dev=1001, fid=1125122112) …
             */
            public static boolean command(int value) {
                if (value == 0) {
                    return writeAll(new int[]{1001, 1001, 1001, 1001}, new int[]{1125122104, 1125122107, 1125122112, 1125122115}, new int[]{2, 2, 2, 2});
                }
                if (value == 1) {
                    return writeAll(new int[]{1001, 1001, 1001, 1001}, new int[]{1125122104, 1125122107, 1125122112, 1125122115}, new int[]{1, 1, 1, 1});
                }
                if (value == 2) {
                    return writeAll(new int[]{1001, 1001, 1001, 1001}, new int[]{1125122104, 1125122107, 1125122112, 1125122115}, new int[]{4, 4, 4, 4});
                }
                if (value == 3) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122104, 1125122107}, new int[]{1, 1});
                }
                if (value == 4) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122104, 1125122107}, new int[]{2, 2});
                }
                if (value == 5) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122104, 1125122107}, new int[]{4, 4});
                }
                if (value == 6) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122104, 1125122107}, new int[]{5, 5});
                }
                if (value == 7) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122112, 1125122115}, new int[]{1, 1});
                }
                if (value == 8) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122112, 1125122115}, new int[]{2, 2});
                }
                if (value == 9) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122112, 1125122115}, new int[]{4, 4});
                }
                if (value == 10) {
                    return writeAll(new int[]{1001, 1001}, new int[]{1125122112, 1125122115}, new int[]{5, 5});
                }
                if (value == 13) {
                    return writeAll(new int[]{1001, 1001, 1001, 1001, 1001, 1001}, new int[]{1125122104, 1125122107, 1125122112, 1125122115, 1330642976, 1330642984}, new int[]{3, 3, 3, 3, 254, 254});
                }
                if (value == 11) {
                    return writeAll(new int[]{1001, 1001, 1001, 1001, 1001, 1001, 1001}, new int[]{1125122104, 1125122107, 1125122112, 1125122115, 1330642976, 1330642976, 1330642984}, new int[]{2, 2, 2, 2, 254, 0, 0});
                }
                if (value == 12) {
                    return writeAll(new int[]{1001, 1001, 1001, 1001, 1001, 1001}, new int[]{1125122104, 1125122107, 1125122112, 1125122115, 1330642976, 1330642976}, new int[]{5, 5, 5, 5, 254, 253});
                }
                return false;
            }

            public static final class action {
                private action() {}

                /**
                 * bodywork.window.action — 车窗动作.
                 * <p>让指定侧窗全开、半开、透气、关闭或停止移动。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP, 4=HALF_OPEN, 5=VENTILATE
                 * <p>写入 Bodywork.BODYWORK_LF_WINDOW_CTRL_SET (dev=1001, fid=1125122104)
                 */
                public static boolean driver(int value) {
                    return write(1001, 1125122104, value);
                }

                /**
                 * bodywork.window.action — 车窗动作.
                 * <p>让指定侧窗全开、半开、透气、关闭或停止移动。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP, 4=HALF_OPEN, 5=VENTILATE
                 * <p>写入 Bodywork.BODYWORK_RF_WINDOW_CTRL_SET (dev=1001, fid=1125122107)
                 */
                public static boolean frontPassenger(int value) {
                    return write(1001, 1125122107, value);
                }

                /**
                 * bodywork.window.action — 车窗动作.
                 * <p>让指定侧窗全开、半开、透气、关闭或停止移动。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP, 4=HALF_OPEN, 5=VENTILATE
                 * <p>写入 Bodywork.BODYWORK_LR_WINDOW_CTRL_SET (dev=1001, fid=1125122112)
                 */
                public static boolean rearLeft(int value) {
                    return write(1001, 1125122112, value);
                }

                /**
                 * bodywork.window.action — 车窗动作.
                 * <p>让指定侧窗全开、半开、透气、关闭或停止移动。
                 * @param value 1=OPEN, 2=CLOSE, 3=STOP, 4=HALF_OPEN, 5=VENTILATE
                 * <p>写入 Bodywork.BODYWORK_RR_WINDOW_CTRL_SET (dev=1001, fid=1125122115)
                 */
                public static boolean rearRight(int value) {
                    return write(1001, 1125122115, value);
                }

            }

            public static final class position {
                private position() {}

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * @param value 原始整数
                 * <p>写入 Bodywork.BODYWORK_LF_WINDOW_TARGET_POSITION_SET (dev=1000, fid=1276219408)
                 */
                public static boolean driver(int value) {
                    return write(1000, 1276219408, value);
                }

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * @param value 原始整数
                 * <p>写入 Bodywork.BODYWORK_RF_WINDOW_TARGET_POSITION_SET (dev=1000, fid=1276219424)
                 */
                public static boolean frontPassenger(int value) {
                    return write(1000, 1276219424, value);
                }

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * @param value 原始整数
                 * <p>写入 Bodywork.BODYWORK_LR_WINDOW_TARGET_POSITION_SET (dev=1000, fid=1276219416)
                 */
                public static boolean rearLeft(int value) {
                    return write(1000, 1276219416, value);
                }

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * @param value 原始整数
                 * <p>写入 Bodywork.BODYWORK_RR_WINDOW_TARGET_POSITION_SET (dev=1000, fid=1276219432)
                 */
                public static boolean rearRight(int value) {
                    return write(1000, 1276219432, value);
                }

            }

        }

        public static final class windowSunshade {
            private windowSunshade() {}

            /**
             * bodywork.window_sunshade — 后侧窗遮阳帘.
             * <p>展开或收起左后、右后侧窗的遮阳帘，并查看其位置。
             * @param value 1=EXTEND, 2=RETRACT
             * <p>写入 Bodywork.BODYWORK_LR_WINDOW_SUNSHADE_CONTROL_SET (dev=1001, fid=1276207172)
             */
            public static boolean rearLeft(int value) {
                return write(1001, 1276207172, value);
            }

            /**
             * bodywork.window_sunshade — 后侧窗遮阳帘.
             * <p>展开或收起左后、右后侧窗的遮阳帘，并查看其位置。
             * @param value 1=EXTEND, 2=RETRACT
             * <p>写入 Bodywork.BODYWORK_RR_WINDOW_SUNSHADE_CONTROL_SET (dev=1001, fid=1276207174)
             */
            public static boolean rearRight(int value) {
                return write(1001, 1276207174, value);
            }

        }

    }

    public static final class charging {
        private charging() {}

        /**
         * charging.soc_save_mode — 保电模式.
         * <p>选择智能保电或强制保电。
         * @param value 1=SMART, 2=FORCED
         * <p>写入 Charging.CHARGING_SOC_SAVE_SWITCH_SET (dev=1009, fid=782237704)
         */
        public static boolean socSaveMode(int value) {
            return write(1009, 782237704, value);
        }

    }

    public static final class doorLock {
        private doorLock() {}

        /**
         * door_lock.all_doors — 四门锁控制.
         * <p>同时锁定或解锁四个车门。
         * @param value 1=UNLOCKED, 2=LOCKED
         * <p>写入 Bodywork.BODYWORK_FOUR_DOOR_SET (dev=1001, fid=1276141590)
         */
        public static boolean allDoors(int value) {
            return write(1001, 1276141590, value);
        }

        public static final class childLockState {
            private childLockState() {}

            /**
             * door_lock.child_lock_state — 儿童锁.
             * <p>打开或解除左后、右后车门的儿童锁，并查看锁定状态。
             * @param value 1=UNLOCKED, 2=LOCKED
             * <p>写入 DoorLock.DOOR_LOCK_COMMAND_AREA_CHILDLOCK_LEFT_SET (dev=1001, fid=1276141584)
             */
            public static boolean rearLeft(int value) {
                return write(1001, 1276141584, value);
            }

            /**
             * door_lock.child_lock_state — 儿童锁.
             * <p>打开或解除左后、右后车门的儿童锁，并查看锁定状态。
             * @param value 1=UNLOCKED, 2=LOCKED
             * <p>写入 DoorLock.DOOR_LOCK_COMMAND_AREA_CHILDLOCK_RIGHT_SET (dev=1001, fid=1276141586)
             */
            public static boolean rearRight(int value) {
                return write(1001, 1276141586, value);
            }

        }

    }

    public static final class energy {
        private energy() {}

        /**
         * energy.mode — 能量模式.
         * <p>选择纯电、强制纯电或混动模式，并查看当前能量模式。
         * @param value 1=EV, 2=FORCED_EV, 3=HEV
         * <p>写入 Energy.ENERGY_MODE_SET (dev=1006, fid=734003254)
         */
        public static boolean mode(int value) {
            return write(1006, 734003254, value);
        }

        /**
         * energy.extended_range — 长续航模式.
         * <p>开启或关闭长续航模式。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_MANDATORY_EV_MODE_SWITCH_COMMAND_SET (dev=1000, fid=753971268)
         */
        public static boolean extendedRange(int enabled) {
            return write(1000, 753971268, enabled);
        }

        /**
         * energy.extended_range — 长续航模式.
         * <p>开启或关闭长续航模式。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_MANDATORY_EV_MODE_SWITCH_COMMAND_SET (dev=1000, fid=753971268)
         */
        public static boolean extendedRange(boolean enabled) {
            return extendedRange(enabled ? 1 : 0);
        }

        public static final class operationMode {
            private operationMode() {}

            /**
             * energy.operation_mode — 驾驶模式.
             * <p>按车辆实际驾驶模式控制系统，选择普通、经济、运动、地形或竞速模式。
             * @param value 1=ECO, 2=SPORT, 3=NORMAL, 4=SNOW, 5=MUD, 6=SAND
             * <p>写入 Energy.ENERGY_OPERATION_MODE_SET (dev=1006, fid=734003229)
             */
            public static boolean energy(int value) {
                return write(1006, 734003229, value);
            }

            /**
             * energy.operation_mode — 驾驶模式.
             * <p>按车辆实际驾驶模式控制系统，选择普通、经济、运动、地形或竞速模式。
             * @param value 1=ECO, 2=SPORT, 3=NORMAL, 4=SNOW, 5=MUD, 6=SAND, 7=MOUNTAIN, 8=ROCK, 19=CUSTOM, 20=RACING, 23=EXTREME
             * <p>写入 Setting.SETTING_PRESELECTED_DRIVING_MODE_SET (dev=1000, fid=1276260400)
             */
            public static boolean extended(int value) {
                return write(1000, 1276260400, value);
            }

        }

        public static final class regeneration {
            private regeneration() {}

            /**
             * energy.regeneration — 能量回收强度.
             * <p>将能量回收设为标准或较强。
             * @param value 1=STANDARD, 2=STRONG
             * <p>写入 Setting.SET_DR_ENERGY_FB_SET (dev=1006, fid=1311768613)
             */
            public static boolean domestic(int value) {
                return write(1006, 1311768613, value);
            }

            /**
             * energy.regeneration — 能量回收强度.
             * <p>将能量回收设为标准或较强。
             * @param value 1=STANDARD, 2=STRONG
             * <p>写入 Setting.SET_DR_ENERGY_FB_SET (dev=1006, fid=1311768613)
             */
            public static boolean overseas(int value) {
                return write(1006, 1311768613, value);
            }

        }

    }

    public static final class engine {
        private engine() {}

        /**
         * engine.voice_simulator — 引擎模拟音.
         * <p>打开或关闭引擎模拟音。
         * @param value 0/1
         * <p>写入 Engine.ENGINE_VOICE_SIMULATOR_STATE_SET (dev=1012, fid=1043333152)
         */
        public static boolean voiceSimulator(int value) {
            return write(1012, 1043333152, value);
        }

        /**
         * engine.voice_simulator — 引擎模拟音.
         * <p>打开或关闭引擎模拟音。
         * @param value 0/1
         * <p>写入 Engine.ENGINE_VOICE_SIMULATOR_STATE_SET (dev=1012, fid=1043333152)
         */
        public static boolean voiceSimulator(boolean value) {
            return voiceSimulator(value ? 1 : 0);
        }

    }

    public static final class instrument {
        private instrument() {}

        /**
         * instrument.backlight_brightness — 仪表背光亮度.
         * <p>按原车设置界面的百分比调节仪表背光亮度，适用于 DiLink 3、4 车机（Android 12 以下）。
         * @param value 原始整数
         * <p>写入 Instrument.INSTRUMENT_BACKLIGHT_BRIGHTNESS_SET (dev=1007, fid=1043333144)
         */
        public static boolean backlightBrightness(int value) {
            return write(1007, 1043333144, value);
        }

        /**
         * instrument.brightness_percent — 仪表亮度百分比.
         * <p>按百分比调节仪表亮度，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用仪表背光亮度。
         * @param value 原始整数
         * <p>写入 Instrument.INSTRUMENT_PANEL_BACKLIGHT_ADJUSTMENT_LEVEL_SET (dev=1007, fid=507514927)
         */
        public static boolean brightnessPercent(int value) {
            return write(1007, 507514927, value);
        }

    }

    public static final class light {
        private light() {}

        /**
         * light.auto_high_beam — 自适应远光.
         * <p>打开或关闭自适应远光功能，并分别查看左右远光灯的工作状态。
         * @param value 0/1
         * <p>写入 Light.LIGHT_ADB_STATE_SET (dev=1004, fid=1125122110)
         */
        public static boolean autoHighBeam(int value) {
            return write(1004, 1125122110, value);
        }

        /**
         * light.auto_high_beam — 自适应远光.
         * <p>打开或关闭自适应远光功能，并分别查看左右远光灯的工作状态。
         * @param value 0/1
         * <p>写入 Light.LIGHT_ADB_STATE_SET (dev=1004, fid=1125122110)
         */
        public static boolean autoHighBeam(boolean value) {
            return autoHighBeam(value ? 1 : 0);
        }

        /**
         * light.daytime_light — 日间行车灯.
         * <p>打开或关闭日间行车灯。
         * @param value 0/1
         * <p>写入 Light.LIGHT_DAY_RUNNING_LIGHT_AUTO_STATE_SET (dev=1004, fid=1125122118)
         */
        public static boolean daytimeLight(int value) {
            return write(1004, 1125122118, value);
        }

        /**
         * light.daytime_light — 日间行车灯.
         * <p>打开或关闭日间行车灯。
         * @param value 0/1
         * <p>写入 Light.LIGHT_DAY_RUNNING_LIGHT_AUTO_STATE_SET (dev=1004, fid=1125122118)
         */
        public static boolean daytimeLight(boolean value) {
            return daytimeLight(value ? 1 : 0);
        }

        /**
         * light.double_flash — 双闪.
         * <p>打开或关闭双闪警示灯。
         * @param value 0/1
         * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
         */
        public static boolean doubleFlash(int value) {
            return write(1004, 871366669, value);
        }

        /**
         * light.double_flash — 双闪.
         * <p>打开或关闭双闪警示灯。
         * @param value 0/1
         * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
         */
        public static boolean doubleFlash(boolean value) {
            return doubleFlash(value ? 1 : 0);
        }

        /**
         * light.headlight_mode — 车灯控制模式.
         * <p>选择车灯关闭、自动、小灯或近光灯模式。
         * @param value 1=OFF, 2=AUTO, 3=SIDE_LIGHT, 4=LOW_BEAM
         * <p>写入 Instrument.INSTRUMENT_HEADLIGHT_CONTROL_SET (dev=1004, fid=1276153912)
         */
        public static boolean headlightMode(int value) {
            return write(1004, 1276153912, value);
        }

        /**
         * light.high_beam_assist — 智能远近光.
         * <p>打开或关闭智能远近光切换，并查看工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_HMA_STATE_SET (dev=1004, fid=944767008)
         */
        public static boolean highBeamAssist(int value) {
            return write(1004, 944767008, value);
        }

        /**
         * light.high_beam_assist — 智能远近光.
         * <p>打开或关闭智能远近光切换，并查看工作状态。
         * @param value 0/1
         * <p>写入 Adas.ADAS_HMA_STATE_SET (dev=1004, fid=944767008)
         */
        public static boolean highBeamAssist(boolean value) {
            return highBeamAssist(value ? 1 : 0);
        }

        /**
         * light.inside_light — 车内灯.
         * <p>打开或关闭车内照明灯。
         * @param value 0/1
         * <p>写入 Setting.SET_INSIDE_LIGHT_STATE_SET (dev=1000, fid=1330643002)
         */
        public static boolean insideLight(int value) {
            return write(1000, 1330643002, value);
        }

        /**
         * light.inside_light — 车内灯.
         * <p>打开或关闭车内照明灯。
         * @param value 0/1
         * <p>写入 Setting.SET_INSIDE_LIGHT_STATE_SET (dev=1000, fid=1330643002)
         */
        public static boolean insideLight(boolean value) {
            return insideLight(value ? 1 : 0);
        }

        /**
         * light.inside_light_on_door — 开门自动亮灯.
         * <p>设置打开车门时是否自动点亮车内灯。
         * @param value 0/1
         * <p>写入 Setting.SET_INSIDE_LIGHT_DOOR_STATE_SET (dev=1000, fid=1330643000)
         */
        public static boolean insideLightOnDoor(int value) {
            return write(1000, 1330643000, value);
        }

        /**
         * light.inside_light_on_door — 开门自动亮灯.
         * <p>设置打开车门时是否自动点亮车内灯。
         * @param value 0/1
         * <p>写入 Setting.SET_INSIDE_LIGHT_DOOR_STATE_SET (dev=1000, fid=1330643000)
         */
        public static boolean insideLightOnDoor(boolean value) {
            return insideLightOnDoor(value ? 1 : 0);
        }

        /**
         * light.turn_signal_state — 转向信号模式.
         * <p>选择左转向、右转向、双闪或关闭，并查看整车转向信号状态。
         * @param value 0=OFF, 1=HAZARD, 2=LEFT, 3=RIGHT
         * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
         */
        public static boolean turnSignalState(int value) {
            return write(1004, 871366669, value);
        }

        public static final class fogSwitch {
            private fogSwitch() {}

            /**
             * light.fog_switch — 雾灯开关（怀挡）.
             * <p>打开或关闭怀挡车型的前雾灯或后雾灯。
             * @param value 0/1
             * <p>写入 Instrument.INSTRUMENT_FRONT_FOG_LAMPS_SWITCH_SET (dev=1004, fid=1276153908)
             */
            public static boolean front(int value) {
                return write(1004, 1276153908, value);
            }

            /**
             * light.fog_switch — 雾灯开关（怀挡）.
             * <p>打开或关闭怀挡车型的前雾灯或后雾灯。
             * @param value 0/1
             * <p>写入 Instrument.INSTRUMENT_FRONT_FOG_LAMPS_SWITCH_SET (dev=1004, fid=1276153908)
             */
            public static boolean front(boolean value) {
                return front(value ? 1 : 0);
            }

            /**
             * light.fog_switch — 雾灯开关（怀挡）.
             * <p>打开或关闭怀挡车型的前雾灯或后雾灯。
             * @param value 0/1
             * <p>写入 Instrument.INSTRUMENT_REAR_FOG_LAMPS_SWITCH_SET (dev=1004, fid=1276153904)
             */
            public static boolean rear(int value) {
                return write(1004, 1276153904, value);
            }

            /**
             * light.fog_switch — 雾灯开关（怀挡）.
             * <p>打开或关闭怀挡车型的前雾灯或后雾灯。
             * @param value 0/1
             * <p>写入 Instrument.INSTRUMENT_REAR_FOG_LAMPS_SWITCH_SET (dev=1004, fid=1276153904)
             */
            public static boolean rear(boolean value) {
                return rear(value ? 1 : 0);
            }

        }

        public static final class lightState {
            private lightState() {}

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * @param value 0/1
             * <p>写入 Light.LIGHT_FRONT_FOG_LIGHT_SET (dev=1004, fid=871366675)
             */
            public static boolean frontFog(int value) {
                return write(1004, 871366675, value);
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * @param value 0/1
             * <p>写入 Light.LIGHT_FRONT_FOG_LIGHT_SET (dev=1004, fid=871366675)
             */
            public static boolean frontFog(boolean value) {
                return frontFog(value ? 1 : 0);
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * @param value 0/1
             * <p>写入 Light.LIGHT_REAR_FOG_LIGHT_SET (dev=1004, fid=871366667)
             */
            public static boolean rearFog(int value) {
                return write(1004, 871366667, value);
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * @param value 0/1
             * <p>写入 Light.LIGHT_REAR_FOG_LIGHT_SET (dev=1004, fid=871366667)
             */
            public static boolean rearFog(boolean value) {
                return rearFog(value ? 1 : 0);
            }

        }

        public static final class turnSignal {
            private turnSignal() {}

            /**
             * light.turn_signal — 分侧转向灯.
             * <p>打开指定一侧的转向灯，或查看该侧的转向信号状态。
             * @param value 0/1
             * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
             */
            public static boolean left(int value) {
                return write(1004, 871366669, value);
            }

            /**
             * light.turn_signal — 分侧转向灯.
             * <p>打开指定一侧的转向灯，或查看该侧的转向信号状态。
             * @param value 0/1
             * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
             */
            public static boolean left(boolean value) {
                return left(value ? 1 : 0);
            }

            /**
             * light.turn_signal — 分侧转向灯.
             * <p>打开指定一侧的转向灯，或查看该侧的转向信号状态。
             * @param value 0/1
             * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
             */
            public static boolean right(int value) {
                return write(1004, 871366669, value);
            }

            /**
             * light.turn_signal — 分侧转向灯.
             * <p>打开指定一侧的转向灯，或查看该侧的转向信号状态。
             * @param value 0/1
             * <p>写入 Light.LIGHT_TURN_SIGNAL_LIGHT_SET (dev=1004, fid=871366669)
             */
            public static boolean right(boolean value) {
                return right(value ? 1 : 0);
            }

        }

    }

    public static final class panorama {
        private panorama() {}

        /**
         * panorama.display_parameters — 全景显示设置.
         * <p>一起设置全景影像的视角、显示模式和横竖方向，或读取这三项设置。
         * @param mode 0=PANORAMA, 1=FULL_SCREEN, 3=WIDGET, 4=RF_REVERSE, 5=REVERSE, 6=PANORAMA_3D
         * @param output 0=INVALID, 1=OFF, 2=FRONT, 3=REAR, 4=LEFT, 5=RIGHT, 6=COMPOSE, 7=MATCHING, 8=FRONT_LEFT, 9=FRONT_RIGHT, 10=REAR_LEFT, 11=REAR_RIGHT, 12=FRONT_WIDE, 13=REAR_WIDE, 14=WIDTH_LIMIT
         * @param rotation 1=HORIZONTAL, 2=VERTICAL
         * <p>写入 Panorama.PANORAMA_OUTPUT_STATE_SET (dev=1031, fid=1306529808)；Panorama.PANORAMA_WORK_MODE_SET (dev=1031, fid=1306529812)；Panorama.PANORAMA_ROTATION_SET (dev=1031, fid=1306529826)
         */
        public static boolean displayParameters(int mode, int output, int rotation) {
            return writeAll(new int[]{1031, 1031, 1031}, new int[]{1306529808, 1306529812, 1306529826}, new int[]{output, mode, rotation});
        }

    }

    public static final class setting {
        private setting() {}

        /**
         * setting.anti_motion — 智能防晕车.
         * <p>打开或关闭智能防晕车功能。
         * @param value 0/1
         * <p>写入 Setting.BODYWORK_COMFORT_ANTI_DAZZLE_SWITCH_SIGNAL_SET (dev=1000, fid=850616366)
         */
        public static boolean antiMotion(int value) {
            return write(1000, 850616366, value);
        }

        /**
         * setting.anti_motion — 智能防晕车.
         * <p>打开或关闭智能防晕车功能。
         * @param value 0/1
         * <p>写入 Setting.BODYWORK_COMFORT_ANTI_DAZZLE_SWITCH_SIGNAL_SET (dev=1000, fid=850616366)
         */
        public static boolean antiMotion(boolean value) {
            return antiMotion(value ? 1 : 0);
        }

        /**
         * setting.etc_power — ETC 开关.
         * <p>打开或关闭车辆 ETC 功能。
         * @param value 0/1
         * <p>写入 Setting.BODYWORK_ETC_SWITCH_CONTROL_SET (dev=1000, fid=850460688)
         */
        public static boolean etcPower(int value) {
            return write(1000, 850460688, value);
        }

        /**
         * setting.etc_power — ETC 开关.
         * <p>打开或关闭车辆 ETC 功能。
         * @param value 0/1
         * <p>写入 Setting.BODYWORK_ETC_SWITCH_CONTROL_SET (dev=1000, fid=850460688)
         */
        public static boolean etcPower(boolean value) {
            return etcPower(value ? 1 : 0);
        }

        /**
         * setting.hud_brightness — HUD 亮度.
         * <p>调节抬头显示的亮度档位。
         * @param value 原始整数
         * <p>写入 Setting.SET_BRIGHTNESS_GEAR_SET (dev=1000, fid=1276174360)
         */
        public static boolean hudBrightness(int value) {
            return write(1000, 1276174360, value);
        }

        /**
         * setting.hud_power — HUD 开关.
         * <p>打开或关闭抬头显示。
         * @param value 0/1
         * <p>写入 Setting.SET_HUD_SWITCH_SET (dev=1000, fid=1276174373)
         */
        public static boolean hudPower(int value) {
            return write(1000, 1276174373, value);
        }

        /**
         * setting.hud_power — HUD 开关.
         * <p>打开或关闭抬头显示。
         * @param value 0/1
         * <p>写入 Setting.SET_HUD_SWITCH_SET (dev=1000, fid=1276174373)
         */
        public static boolean hudPower(boolean value) {
            return hudPower(value ? 1 : 0);
        }

        /**
         * setting.road_preview — 路面预瞄.
         * <p>打开或关闭路面预瞄功能。
         * @param value 0/1
         * <p>写入 Setting.ADAS_PREVIEW_SWITCH_SET (dev=1000, fid=1324576806)
         */
        public static boolean roadPreview(int value) {
            return write(1000, 1324576806, value);
        }

        /**
         * setting.road_preview — 路面预瞄.
         * <p>打开或关闭路面预瞄功能。
         * @param value 0/1
         * <p>写入 Setting.ADAS_PREVIEW_SWITCH_SET (dev=1000, fid=1324576806)
         */
        public static boolean roadPreview(boolean value) {
            return roadPreview(value ? 1 : 0);
        }

        /**
         * setting.soc_target — SOC 保电目标.
         * <p>设置希望车辆保留的电量百分比。
         * @param value 原始整数
         * <p>写入 Setting.SET_DR_SOC_TARGET_SET (dev=1000, fid=1311768632)
         */
        public static boolean socTarget(int value) {
            return write(1000, 1311768632, value);
        }

        /**
         * setting.steering_assist_mode — 转向助力模式.
         * <p>选择舒适或运动转向助力，调整方向盘的操控感受。
         * @param value 1=COMFORT, 2=SPORT
         * <p>写入 Setting.SET_DR_ST_ASSIS_SET (dev=1000, fid=1311768610)
         */
        public static boolean steeringAssistMode(int value) {
            return write(1000, 1311768610, value);
        }

        /**
         * setting.steering_wheel_heating — 方向盘加热开关.
         * <p>打开或关闭方向盘加热。
         * @param value 0/1
         * <p>写入 Setting.SET_STEERING_WHEEL_HEAT_STATE_SET (dev=1000, fid=944767029)
         */
        public static boolean steeringWheelHeating(int value) {
            return write(1000, 944767029, value);
        }

        /**
         * setting.steering_wheel_heating — 方向盘加热开关.
         * <p>打开或关闭方向盘加热。
         * @param value 0/1
         * <p>写入 Setting.SET_STEERING_WHEEL_HEAT_STATE_SET (dev=1000, fid=944767029)
         */
        public static boolean steeringWheelHeating(boolean value) {
            return steeringWheelHeating(value ? 1 : 0);
        }

        /**
         * setting.steering_wheel_heating_level — 方向盘加热档位.
         * <p>调节方向盘加热强度，适用于三档或五档车型。
         * @param value 原始整数
         * <p>写入 Setting.SET_AUTO_STEER_WHEEL_HEATING_GEAR_SET (dev=1000, fid=1276256274)
         */
        public static boolean steeringWheelHeatingLevel(int value) {
            return write(1000, 1276256274, value);
        }

        /**
         * setting.ambient_light_brightness — 全车氛围灯亮度.
         * <p>一起调节全车氛围灯亮度，也可将亮度设为零关闭。DiLink 3、4 车机（Android 12 以下）没有氛围灯总开关，打开或关闭氛围灯也使用本能力：关闭设为 0，打开可设为最高亮度 5。
         * @param value 原始整数
         * <p>写入 Setting.SET_INTERIOR_ATMOSPHERE_LAMP_BRIGHTNESS_SET (dev=1000, fid=1069547536)
         */
        public static boolean ambientLightBrightness(int value) {
            return write(1000, 1069547536, value);
        }

        /**
         * setting.ambient_light_power — 氛围灯总开关.
         * <p>打开或关闭全车氛围灯，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用全车氛围灯亮度。
         * @param value 0/1
         * <p>写入 Setting.LIGHT_ATMOSPHERE_MAIN_SWITCH_SET (dev=1000, fid=1276153924)
         */
        public static boolean ambientLightPower(int value) {
            return write(1000, 1276153924, value);
        }

        /**
         * setting.ambient_light_power — 氛围灯总开关.
         * <p>打开或关闭全车氛围灯，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用全车氛围灯亮度。
         * @param value 0/1
         * <p>写入 Setting.LIGHT_ATMOSPHERE_MAIN_SWITCH_SET (dev=1000, fid=1276153924)
         */
        public static boolean ambientLightPower(boolean value) {
            return ambientLightPower(value ? 1 : 0);
        }

        /**
         * setting.mirror_auto_brightness — 电子后视镜自动亮度.
         * <p>选择电子后视镜自动调节亮度或手动调节亮度。
         * @param value 0/1
         * <p>写入 Setting.REAR_VIEW_MIRROR_EXTERNAL_BACKLIGHT_AUTO_ADJUST_SET (dev=1000, fid=427827260)
         */
        public static boolean mirrorAutoBrightness(int value) {
            return write(1000, 427827260, value);
        }

        /**
         * setting.mirror_auto_brightness — 电子后视镜自动亮度.
         * <p>选择电子后视镜自动调节亮度或手动调节亮度。
         * @param value 0/1
         * <p>写入 Setting.REAR_VIEW_MIRROR_EXTERNAL_BACKLIGHT_AUTO_ADJUST_SET (dev=1000, fid=427827260)
         */
        public static boolean mirrorAutoBrightness(boolean value) {
            return mirrorAutoBrightness(value ? 1 : 0);
        }

        /**
         * setting.mirror_brightness — 电子后视镜亮度.
         * <p>调节左侧电子后视镜显示屏亮度。
         * @param value 原始整数
         * <p>写入 Setting.REAR_VIEW_MIRROR_EXTERNAL_CMS_LEFT_DISPLAY_SCREEN_ADJUST_LIGHT_GEAR_SET (dev=1000, fid=427827227)
         */
        public static boolean mirrorBrightness(int value) {
            return write(1000, 427827227, value);
        }

        /**
         * setting.mirror_fold_command — 后视镜折叠动作.
         * <p>立即折叠或展开车外后视镜，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持立即折叠，只能设置后视镜自动折叠。
         * @param value 1=FOLD, 2=UNFOLD
         * <p>写入 Setting.BODYWORK_REAR_VIEW_MIRROR_AND_CHASSIS_LIFT_SET (dev=1001, fid=1324556317)
         */
        public static boolean mirrorFoldCommand(int value) {
            return write(1001, 1324556317, value);
        }

        /**
         * setting.mirror_reverse_tilt — 倒车后视镜翻转.
         * <p>设置倒车时是否将后视镜翻转至记忆角度。
         * @param value 0/1
         * <p>写入 Setting.SET_CAR_REAR_VIEW_SET (dev=1000, fid=1312817180)
         */
        public static boolean mirrorReverseTilt(int value) {
            return write(1000, 1312817180, value);
        }

        /**
         * setting.mirror_reverse_tilt — 倒车后视镜翻转.
         * <p>设置倒车时是否将后视镜翻转至记忆角度。
         * @param value 0/1
         * <p>写入 Setting.SET_CAR_REAR_VIEW_SET (dev=1000, fid=1312817180)
         */
        public static boolean mirrorReverseTilt(boolean value) {
            return mirrorReverseTilt(value ? 1 : 0);
        }

        /**
         * setting.suspension_height_lock — 悬架高度锁定.
         * <p>锁定或解除悬架高度锁定。
         * @param value 0/1
         * <p>写入 Setting.SETTING_HEIGHT_CONTROL_OFF_SWITCH_SET (dev=1000, fid=1324617760)
         */
        public static boolean suspensionHeightLock(int value) {
            return write(1000, 1324617760, value);
        }

        /**
         * setting.suspension_height_lock — 悬架高度锁定.
         * <p>锁定或解除悬架高度锁定。
         * @param value 0/1
         * <p>写入 Setting.SETTING_HEIGHT_CONTROL_OFF_SWITCH_SET (dev=1000, fid=1324617760)
         */
        public static boolean suspensionHeightLock(boolean value) {
            return suspensionHeightLock(value ? 1 : 0);
        }

        /**
         * setting.suspension_mode — 云辇模式.
         * <p>选择云辇悬架的舒适或运动模式。
         * @param value 1=COMFORT, 2=SPORT
         * <p>写入 Setting.SET_COMFORT_MODE_CONTROL_SWITCH_SIGNAL_SET (dev=1000, fid=944767034)
         */
        public static boolean suspensionMode(int value) {
            return write(1000, 944767034, value);
        }

        /**
         * setting.suspension_extreme_height — 悬架超高或超低模式.
         * <p>确认或激活悬架超高、超低模式。
         * @param action 2=CONFIRM, 3=ACTIVATE
         * @param mode 1=ultraHigh, 2=ultraLow
         * <p>写入 Setting.BODYWORK_DISUS_ULTRA_HIGH_SWITCH_STATUS_SET (dev=1000, fid=1324617788)；Setting.BODYWORK_DISUS_ULTRA_LOW_SWITCH_STATUS_SET (dev=1000, fid=1324617790)
         */
        public static boolean suspensionExtremeHeight(int action, int mode) {
            if (mode == 1) {
                return write(1000, 1324617788, action);
            }
            if (mode == 2) {
                return write(1000, 1324617790, action);
            }
            return false;
        }

        /**
         * setting.vector_control — 矢量控制模式.
         * <p>选择 CVC 矢量控制的舒适或运动模式。
         * @param mode 1=comfort, 2=sport
         * <p>写入 Setting.SETTING_CVC_WORKING_MODE_SET (dev=1000, fid=1324617782)
         */
        public static boolean vectorControl(int mode) {
            return write(1000, 1324617782, mode);
        }

        /**
         * setting.rollover_airbag_suppression — 气囊翻滚抑制.
         * <p>开启或关闭气囊翻滚抑制。
         * @param enabled 0/1
         * <p>写入 Setting.RESCUE_AIRBAG_ROLL_SUPPRESSION_STATUS_SET (dev=1000, fid=1324564518)
         */
        public static boolean rolloverAirbagSuppression(int enabled) {
            return write(1000, 1324564518, enabled);
        }

        /**
         * setting.rollover_airbag_suppression — 气囊翻滚抑制.
         * <p>开启或关闭气囊翻滚抑制。
         * @param enabled 0/1
         * <p>写入 Setting.RESCUE_AIRBAG_ROLL_SUPPRESSION_STATUS_SET (dev=1000, fid=1324564518)
         */
        public static boolean rolloverAirbagSuppression(boolean enabled) {
            return rolloverAirbagSuppression(enabled ? 1 : 0);
        }

        /**
         * setting.sand_abs — 沙地 ABS.
         * <p>开启或关闭沙地 ABS。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_SAND_ABS_ENABLE_SIGNAL_SET (dev=1000, fid=1324564530)
         */
        public static boolean sandAbs(int enabled) {
            return write(1000, 1324564530, enabled);
        }

        /**
         * setting.sand_abs — 沙地 ABS.
         * <p>开启或关闭沙地 ABS。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_SAND_ABS_ENABLE_SIGNAL_SET (dev=1000, fid=1324564530)
         */
        public static boolean sandAbs(boolean enabled) {
            return sandAbs(enabled ? 1 : 0);
        }

        /**
         * setting.camping_leveling — 露营调平.
         * <p>开启或关闭露营调平。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_CAMPING_LEVELING_FUNCTION_SWITCH_SET (dev=1000, fid=1324617766)
         */
        public static boolean campingLeveling(int enabled) {
            return write(1000, 1324617766, enabled);
        }

        /**
         * setting.camping_leveling — 露营调平.
         * <p>开启或关闭露营调平。
         * @param enabled 0/1
         * <p>写入 Setting.SETTING_CAMPING_LEVELING_FUNCTION_SWITCH_SET (dev=1000, fid=1324617766)
         */
        public static boolean campingLeveling(boolean enabled) {
            return campingLeveling(enabled ? 1 : 0);
        }

        public static final class comfortMode {
            private comfortMode() {}

            /**
             * setting.comfort_mode — 舒适控制强度.
             * <p>选择舒适控制的较强、适中或偏弱档位。
             * @param value 1=STRONG, 2=MEDIUM
             * <p>写入 Setting.BODYWORK_CDE_SWITCH_SET (dev=1000, fid=1324617780)
             */
            public static boolean maxLevel2(int value) {
                return write(1000, 1324617780, value);
            }

            /**
             * setting.comfort_mode — 舒适控制强度.
             * <p>选择舒适控制的较强、适中或偏弱档位。
             * @param value 1=STRONG, 2=MEDIUM, 3=WEAK
             * <p>写入 Setting.BODYWORK_CDE_SWITCH_SET (dev=1000, fid=1324617780)
             */
            public static boolean maxLevel3(int value) {
                return write(1000, 1324617780, value);
            }

        }

        public static final class rearEntertainmentScreen {
            private rearEntertainmentScreen() {}

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.MOTOR_IVI_TO_REAR_LARGE_SCREEN_SET (dev=1000, fid=1285554288)
             */
            public static boolean ceiling(int enabled) {
                return write(1000, 1285554288, enabled);
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.MOTOR_IVI_TO_REAR_LARGE_SCREEN_SET (dev=1000, fid=1285554288)
             */
            public static boolean ceiling(boolean enabled) {
                return ceiling(enabled ? 1 : 0);
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.RSE_IVI_PAD_TO_LRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179158)
             */
            public static boolean leftD9(int enabled) {
                return write(1000, 457179158, enabled);
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.RSE_IVI_PAD_TO_LRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179158)
             */
            public static boolean leftD9(boolean enabled) {
                return leftD9(enabled ? 1 : 0);
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.RSE_IVI_PAD_TO_RRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179172)
             */
            public static boolean rightD9(int enabled) {
                return write(1000, 457179172, enabled);
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.RSE_IVI_PAD_TO_RRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179172)
             */
            public static boolean rightD9(boolean enabled) {
                return rightD9(enabled ? 1 : 0);
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.RSE_IVI_PAD_TO_LRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179158)；Setting.RSE_IVI_PAD_TO_RRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179172)
             */
            public static boolean bothD9(int enabled) {
                return writeAll(new int[]{1000, 1000}, new int[]{457179158, 457179172}, new int[]{enabled, enabled});
            }

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * @param enabled 0/1
             * <p>写入 Setting.RSE_IVI_PAD_TO_LRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179158)；Setting.RSE_IVI_PAD_TO_RRSE_KILL_BRIGHT_SCREEN_SET_GET (dev=1000, fid=457179172)
             */
            public static boolean bothD9(boolean enabled) {
                return bothD9(enabled ? 1 : 0);
            }

        }

        public static final class seatHeating {
            private seatHeating() {}

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_DRIVER_SEAT_HEATING_STATE_SET (dev=1000, fid=1125122068)；Ac.AC_MAIN_DRIVE_SEAT_HEATING_LEVEL_SET (dev=1000, fid=1276252180)
             */
            public static boolean driver(int value) {
                return write(1000, 1125122068, value);
            }

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_PASSENGER_SEAT_HEATING_STATE_SET (dev=1000, fid=1125122076)；Ac.AC_PASSENGER_SEAT_HEATING_LEVEL_SET (dev=1000, fid=1276252188)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 1125122076, value);
            }

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_REAR_LEFT_SEAT_HEATING_STATE_SET (dev=1000, fid=1125122084)；Ac.AC_REAR_LEFT_SEAT_HEATING_LEVEL_SET (dev=1000, fid=1276252196)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1125122084, value);
            }

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_REAR_RIGHT_SEAT_HEATING_STATE_SET (dev=1000, fid=1125122092)；Ac.AC_REAR_RIGHT_SEAT_HEATING_LEVEL_SET (dev=1000, fid=1276252204)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1125122092, value);
            }

        }

        public static final class seatHeatingOff {
            private seatHeatingOff() {}

            /**
             * setting.seat_heating_off — 关闭五档座椅加热.
             * <p>关闭指定座椅的加热，适用于五档车型。
             * <p>写入 Ac.AC_MAIN_DRIVE_SEAT_HEATING_STATUS_SET (dev=1000, fid=1276248084)
             */
            public static boolean driver() {
                return write(1000, 1276248084, 2);
            }

            /**
             * setting.seat_heating_off — 关闭五档座椅加热.
             * <p>关闭指定座椅的加热，适用于五档车型。
             * <p>写入 Ac.AC_PASSENGER_SEAT_HEATING_STATUS_SET (dev=1000, fid=1276248092)
             */
            public static boolean frontPassenger() {
                return write(1000, 1276248092, 2);
            }

            /**
             * setting.seat_heating_off — 关闭五档座椅加热.
             * <p>关闭指定座椅的加热，适用于五档车型。
             * <p>写入 Ac.AC_REAR_LEFT_SEAT_HEATING_STATUS_SET (dev=1000, fid=1276248100)
             */
            public static boolean rearLeft() {
                return write(1000, 1276248100, 2);
            }

            /**
             * setting.seat_heating_off — 关闭五档座椅加热.
             * <p>关闭指定座椅的加热，适用于五档车型。
             * <p>写入 Ac.AC_REAR_RIGHT_SEAT_HEATING_STATUS_SET (dev=1000, fid=1276248108)
             */
            public static boolean rearRight() {
                return write(1000, 1276248108, 2);
            }

        }

        public static final class seatVentilation {
            private seatVentilation() {}

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_DRIVER_SEAT_VENTILATING_STATE_SET (dev=1000, fid=1125122064)；Ac.AC_MAIN_DRIVE_SEAT_VENTILATING_LEVEL_SET (dev=1000, fid=1276252176)
             */
            public static boolean driver(int value) {
                return write(1000, 1125122064, value);
            }

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_PASSENGER_SEAT_VENTILATING_STATE_SET (dev=1000, fid=1125122072)；Ac.AC_PASSENGER_SEAT_VENTILATING_LEVEL_SET (dev=1000, fid=1276252184)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 1125122072, value);
            }

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_REAR_LEFT_SEAT_VENTILATING_STATE_SET (dev=1000, fid=1125122088)；Ac.AC_REAR_LEFT_SEAT_VENTILATING_LEVEL_SET (dev=1000, fid=1276252192)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1125122088, value);
            }

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * @param value 原始整数
             * <p>写入 Setting.SET_REAR_RIGHT_SEAT_VENTILATING_STATE_SET (dev=1000, fid=1125122096)；Ac.AC_REAR_RIGHT_SEAT_VENTILATING_LEVEL_SET (dev=1000, fid=1276252200)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1125122096, value);
            }

        }

        public static final class seatVentilationOff {
            private seatVentilationOff() {}

            /**
             * setting.seat_ventilation_off — 关闭五档座椅通风.
             * <p>关闭指定座椅的通风，适用于五档车型。
             * <p>写入 Ac.AC_MAIN_DRIVE_SEAT_VENTILATING_STATUS_SET (dev=1000, fid=1276248080)
             */
            public static boolean driver() {
                return write(1000, 1276248080, 2);
            }

            /**
             * setting.seat_ventilation_off — 关闭五档座椅通风.
             * <p>关闭指定座椅的通风，适用于五档车型。
             * <p>写入 Ac.AC_PASSENGER_SEAT_VENTILATING_STATUS_SET (dev=1000, fid=1276248088)
             */
            public static boolean frontPassenger() {
                return write(1000, 1276248088, 2);
            }

            /**
             * setting.seat_ventilation_off — 关闭五档座椅通风.
             * <p>关闭指定座椅的通风，适用于五档车型。
             * <p>写入 Ac.AC_REAR_LEFT_SEAT_VENTILATING_STATUS_SET (dev=1000, fid=1276248096)
             */
            public static boolean rearLeft() {
                return write(1000, 1276248096, 2);
            }

            /**
             * setting.seat_ventilation_off — 关闭五档座椅通风.
             * <p>关闭指定座椅的通风，适用于五档车型。
             * <p>写入 Ac.AC_REAR_RIGHT_SEAT_VENTILATING_STATUS_SET (dev=1000, fid=1276248104)
             */
            public static boolean rearRight() {
                return write(1000, 1276248104, 2);
            }

        }

        public static final class seatMassageLevel {
            private seatMassageLevel() {}

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * @param value 原始整数
             * <p>写入 Setting.SET_MAIN_DRIVING_SEAT_MASSAGE_WORK_INTENSUTY_SET (dev=1000, fid=1276190736)
             */
            public static boolean driver(int value) {
                return write(1000, 1276190736, value);
            }

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * @param value 原始整数
             * <p>写入 Setting.SET_CO_DRIVING_SEAT_MASSAGE_WORK_INTENSUTY_SET (dev=1000, fid=1276190739)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 1276190739, value);
            }

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * @param value 原始整数
             * <p>写入 Setting.SET_WORKING_INTENSITY_OF_LEFT_REAR_SEAT_MASSAGE_SET (dev=1000, fid=1276190746)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1276190746, value);
            }

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * @param value 原始整数
             * <p>写入 Setting.SET_WORKING_INTENSITY_OF_RIGHT_REAR_SEAT_MASSAGE_SET (dev=1000, fid=1276190749)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1276190749, value);
            }

        }

        public static final class seatMassageMode {
            private seatMassageMode() {}

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * @param value 0=off, 1=wave, 2=pulse, 3=stretch, 4=relax, 5=soothe
             * <p>写入 Setting.SET_MAIN_DRIVER_SEAT_MASSAGE_WORKING_MODE_SET (dev=1000, fid=1276190752)
             */
            public static boolean driver(int value) {
                return write(1000, 1276190752, value);
            }

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * @param value 0=off, 1=wave, 2=pulse, 3=stretch, 4=relax, 5=soothe
             * <p>写入 Setting.SET_CO_DRIVER_SEAT_MASSAGE_WORKING_MODE_SET (dev=1000, fid=1276190756)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 1276190756, value);
            }

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * @param value 0=off, 1=wave, 2=pulse, 3=stretch, 4=relax, 5=soothe
             * <p>写入 Setting.SET_LEFT_REAR_SEAT_MASSAGE_WORKING_MODE_SET (dev=1000, fid=1276190760)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1276190760, value);
            }

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * @param value 0=off, 1=wave, 2=pulse, 3=stretch, 4=relax, 5=soothe
             * <p>写入 Setting.SET_RIGHT_REAR_SEAT_MASSAGE_WORKING_MODE_SET (dev=1000, fid=1276190764)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1276190764, value);
            }

        }

        public static final class seatMemorySave {
            private seatMemorySave() {}

            /**
             * setting.seat_memory_save — 保存座椅记忆.
             * <p>将当前座椅位置保存到指定记忆位，供以后恢复。
             * @param value 原始整数
             * <p>写入 Setting.SET_RF_MEMORY_LOCATION_SET (dev=1000, fid=1276186680)
             */
            public static boolean frontPassenger(int value) {
                return write(1000, 1276186680, value);
            }

            /**
             * setting.seat_memory_save — 保存座椅记忆.
             * <p>将当前座椅位置保存到指定记忆位，供以后恢复。
             * @param value 原始整数
             * <p>写入 Setting.SET_LR_MEMORY_LOCATION_SET (dev=1000, fid=1276186682)
             */
            public static boolean rearLeft(int value) {
                return write(1000, 1276186682, value);
            }

            /**
             * setting.seat_memory_save — 保存座椅记忆.
             * <p>将当前座椅位置保存到指定记忆位，供以后恢复。
             * @param value 原始整数
             * <p>写入 Setting.SET_RR_MEMORY_LOCATION_SET (dev=1000, fid=1276186684)
             */
            public static boolean rearRight(int value) {
                return write(1000, 1276186684, value);
            }

        }

        public static final class seatPosition {
            private seatPosition() {}

            /**
             * setting.seat_position — 座椅预设位置.
             * <p>将指定座椅移动到常规、舒躺、平躺或折叠位置。
             * @param position 1=standard, 2=relax, 3=flat
             * <p>写入 Setting.SETTING_DRIVER_STANDARD_POSITION_SET (dev=1000, fid=1324650558)；Setting.SETTING_DRIVER_RELAX_POSITION_SET (dev=1000, fid=1324650560)；Setting.SETTING_DRIVER_FLAT_POSITION_SET (dev=1000, fid=1324650562)
             */
            public static boolean driver(int position) {
                if (position == 1) {
                    return write(1000, 1324650558, 1);
                }
                if (position == 2) {
                    return write(1000, 1324650560, 1);
                }
                if (position == 3) {
                    return write(1000, 1324650562, 1);
                }
                return false;
            }

            /**
             * setting.seat_position — 座椅预设位置.
             * <p>将指定座椅移动到常规、舒躺、平躺或折叠位置。
             * @param position 1=standard, 2=relax, 3=flat
             * <p>写入 Setting.SETTING_PASSENGER_STANDARD_POSITION_SET (dev=1000, fid=1324679184)；Setting.SETTING_PASSENGER_RELAX_POSITION_SET (dev=1000, fid=1324679186)；Setting.SETTING_PASSENGER_FLAT_POSITION_SET (dev=1000, fid=1324679188)
             */
            public static boolean frontPassenger(int position) {
                if (position == 1) {
                    return write(1000, 1324679184, 1);
                }
                if (position == 2) {
                    return write(1000, 1324679186, 1);
                }
                if (position == 3) {
                    return write(1000, 1324679188, 1);
                }
                return false;
            }

            /**
             * setting.seat_position — 座椅预设位置.
             * <p>将指定座椅移动到常规、舒躺、平躺或折叠位置。
             * @param position 1=standard, 2=relax, 3=flat
             * <p>写入 Setting.SETTING_LEFT_REAR_STANDARD_POSITION_SET (dev=1000, fid=1324679200)；Setting.SETTING_LEFT_REAR_RELAX_POSITION_SET (dev=1000, fid=1324679202)；Setting.SETTING_LEFT_REAR_FLAT_POSITION_SET (dev=1000, fid=1324679204)
             */
            public static boolean rearLeft(int position) {
                if (position == 1) {
                    return write(1000, 1324679200, 1);
                }
                if (position == 2) {
                    return write(1000, 1324679202, 1);
                }
                if (position == 3) {
                    return write(1000, 1324679204, 1);
                }
                return false;
            }

            /**
             * setting.seat_position — 座椅预设位置.
             * <p>将指定座椅移动到常规、舒躺、平躺或折叠位置。
             * @param position 1=standard, 2=relax, 3=flat
             * <p>写入 Setting.SETTING_RIGHT_REAR_STANDARD_POSITION_SET (dev=1000, fid=1324679216)；Setting.SETTING_RIGHT_REAR_RELAX_POSITION_SET (dev=1000, fid=1324679218)；Setting.SETTING_RIGHT_REAR_FLAT_POSITION_SET (dev=1000, fid=1324679220)
             */
            public static boolean rearRight(int position) {
                if (position == 1) {
                    return write(1000, 1324679216, 1);
                }
                if (position == 2) {
                    return write(1000, 1324679218, 1);
                }
                if (position == 3) {
                    return write(1000, 1324679220, 1);
                }
                return false;
            }

            /**
             * setting.seat_position — 座椅预设位置.
             * <p>将指定座椅移动到常规、舒躺、平躺或折叠位置。
             * @param position 1=standard, 4=fold
             * <p>写入 Setting.SETTING_THIRD_ROW_LEFT_STANDARD_POSITION_SET (dev=1000, fid=1324679224)；Setting.SETTING_THIRD_ROW_LEFT_ONE_TOUCH_FOLD_MODE_SET (dev=1000, fid=1324679222)
             */
            public static boolean thirdRowLeft(int position) {
                if (position == 1) {
                    return write(1000, 1324679224, 1);
                }
                if (position == 4) {
                    return write(1000, 1324679222, 1);
                }
                return false;
            }

            /**
             * setting.seat_position — 座椅预设位置.
             * <p>将指定座椅移动到常规、舒躺、平躺或折叠位置。
             * @param position 1=standard, 4=fold
             * <p>写入 Setting.SETTING_THIRD_ROW_RIGHT_STANDARD_POSITION_SET (dev=1000, fid=1324679232)；Setting.SETTING_THIRD_ROW_RIGHT_ONE_TOUCH_FOLD_MODE_SET (dev=1000, fid=1324679230)
             */
            public static boolean thirdRowRight(int position) {
                if (position == 1) {
                    return write(1000, 1324679232, 1);
                }
                if (position == 4) {
                    return write(1000, 1324679230, 1);
                }
                return false;
            }

        }

        public static final class suspensionHeightCommand {
            private suspensionHeightCommand() {}

            /**
             * setting.suspension_height_command — 悬架高度调节.
             * <p>将悬架调高或调低。
             * @param value 1=RAISE, 2=LOWER
             * <p>写入 Setting.SETTING_VOICE_CONTROL_SUSPENSION_HEIGHT_ADJUSTMENT_SET (dev=1000, fid=1324617772)
             */
            public static boolean generalControl(int value) {
                return write(1000, 1324617772, value);
            }

            /**
             * setting.suspension_height_command — 悬架高度调节.
             * <p>将悬架调高或调低。
             * @param value 1=RAISE, 2=LOWER
             * <p>写入 Setting.SETTING_VOICE_CONTROL_SUSPENSION_HEIGHT_ADJUSTMENT_SET (dev=1004, fid=1324617772)
             */
            public static boolean chassisControl(int value) {
                return write(1004, 1324617772, value);
            }

        }

    }

    public static final class wiper {
        private wiper() {}

        /**
         * wiper.automatic — 自动雨刮.
         * <p>开启或关闭自动感应雨刮。
         * @param enabled 0/1
         * <p>写入 Setting.SET_AUTO_RAIN_WIPER_SWITCH_SET (dev=1023, fid=503316540)
         */
        public static boolean automatic(int enabled) {
            return write(1023, 503316540, enabled);
        }

        /**
         * wiper.automatic — 自动雨刮.
         * <p>开启或关闭自动感应雨刮。
         * @param enabled 0/1
         * <p>写入 Setting.SET_AUTO_RAIN_WIPER_SWITCH_SET (dev=1023, fid=503316540)
         */
        public static boolean automatic(boolean enabled) {
            return automatic(enabled ? 1 : 0);
        }

        /**
         * wiper.service_mode — 雨刮检修模式.
         * <p>开启或退出雨刮检修模式，便于更换雨刮片。
         * @param enabled 0/1
         * <p>写入 Setting.SET_FRONT_WINDSCREEN_WIPER_OVERHAUL_STATE_SET (dev=1000, fid=1330642972)
         */
        public static boolean serviceMode(int enabled) {
            return write(1000, 1330642972, enabled);
        }

        /**
         * wiper.service_mode — 雨刮检修模式.
         * <p>开启或退出雨刮检修模式，便于更换雨刮片。
         * @param enabled 0/1
         * <p>写入 Setting.SET_FRONT_WINDSCREEN_WIPER_OVERHAUL_STATE_SET (dev=1000, fid=1330642972)
         */
        public static boolean serviceMode(boolean enabled) {
            return serviceMode(enabled ? 1 : 0);
        }

    }

}
