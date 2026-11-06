package me.guanming.vehicle.infolib.abilities;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import me.guanming.vehicle.infolib.SensorValue;
import me.guanming.vehicle.infolib.Vehicle;

/**
 * 由 APK 车辆能力目录（assets/byd/devices，revision 2026.10.27.1）生成的全部状态读取能力。
 *
 * <p>该命名空间不修改、不复制原 {@code me.guanming.vehicle.infolib.Sensor}，
 * 每个方法直接通过 {@link Vehicle#read(int, int, int)} 读取，并返回原 {@link SensorValue}。
 * 变体通过调用链区分，例如 {@code Sensor.bodywork.window.position.driver()}。
 */
public final class Sensor {
    private Sensor() {}

    private static Map<Integer, String> map(Object... kv) {
        Map<Integer, String> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) {
            m.put((Integer) kv[i], (String) kv[i + 1]);
        }
        return Collections.unmodifiableMap(m);
    }

    private static SensorValue read(int dev, int fid, int transact, boolean isFloat,
                                   Map<Integer, String> enums, double scale, double offset, String unit) {
        Integer raw = Vehicle.read(dev, fid, transact);
        if (raw == null) return SensorValue.of(null, null, false, null, unit);
        if (enums != null) return SensorValue.of(raw, (double) raw, true, enums, unit);
        if (raw == -10011 || raw == -10013 || raw == 65535 || raw == 1048575)
            return SensorValue.of(raw, null, false, null, unit);
        double v = isFloat ? (double) Float.intBitsToFloat(raw) : (double) raw;
        if (isFloat && (Double.isNaN(v) || Double.isInfinite(v)))
            return SensorValue.of(raw, null, false, null, unit);
        v = v * scale + offset;
        return SensorValue.of(raw, v, false, null, unit);
    }

    public static final class ac {
        private ac() {}

        /**
         * ac.rear_max_fan_level — 后排最高风量档位.
         * <p>后排空调支持的最高风量档位。
         * <p>读取 Ac.AC_REAR_MAX_WIND_LEVEL (dev=1000, fid=1127219220).
         */
        public static SensorValue rearMaxFanLevel() {
            return read(1000, 1127219220, 5, false, map(1, "MAX_LEVEL_4", 2, "MAX_LEVEL_7"), 1.0, 0.0, "");
        }

        /**
         * ac.perfume_concentration — 香氛浓度.
         * <p>设置香氛浓度，或打开、关闭香氛。
         * <p>读取 Ac.AC_PERFUME_CONCENTRATION_FEEDBACK (dev=1000, fid=1242562568).
         */
        public static SensorValue perfumeConcentration() {
            return read(1000, 1242562568, 5, false, map(1, "LOWEST", 2, "ON", 3, "HIGHEST", 4, "OFF"), 1.0, 0.0, "");
        }

        /**
         * ac.perfume_type — 香氛种类.
         * <p>选择使用 1 号、2 号或 3 号香氛。
         * <p>读取 Ac.AC_CURRENT_PERFUME_TYPE (dev=1000, fid=1242562571).
         */
        public static SensorValue perfumeType() {
            return read(1000, 1242562571, 5, false, map(1, "TYPE_1", 2, "TYPE_2", 3, "TYPE_3"), 1.0, 0.0, "");
        }

        /**
         * ac.compressor — AC 模式.
         * <p>打开或关闭空调的 AC 制冷功能，不会关闭空调本身。
         * <p>读取 Ac.AC_COMPRESSOR_MODE (dev=1000, fid=1077936137).
         */
        public static SensorValue compressor() {
            return read(1000, 1077936137, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * ac.cycle_mode — 内外循环.
         * <p>切换空调内循环、外循环或自动循环。
         * <p>读取 Ac.AC_CYCLE_MODE (dev=1000, fid=1077936148).
         */
        public static SensorValue cycleMode() {
            return read(1000, 1077936148, 5, false, map(0, "1", 1, "2", 2, "3"), 1.0, 0.0, "");
        }

        /**
         * ac.maximum_cooling — 最大制冷.
         * <p>打开或关闭最大制冷，空调以最强制冷运行。
         * <p>读取 Ac.AC_MAX_COOLING_STATE (dev=1000, fid=1314914324).
         */
        public static SensorValue maximumCooling() {
            return read(1000, 1314914324, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * ac.rear_lock_state — 后排空调锁.
         * <p>锁定或解锁后排空调面板，锁定后后排无法操作空调。
         * <p>读取 Ac.AC_REAR_LOCK_STATE (dev=1000, fid=1314914326).
         */
        public static SensorValue rearLockState() {
            return read(1000, 1314914326, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * ac.separate_control — 分控模式.
         * <p>打开或关闭温度分控，开启后各区域可以设置不同温度。
         * <p>读取 Ac.AC_TEMPCTRL_SEPARATE_STATE (dev=1000, fid=1077936164).
         */
        public static SensorValue separateControl() {
            return read(1000, 1077936164, 5, false, map(0, "OFF", 1, "ON", 3, "SINGLE_RANGE"), 1.0, 0.0, "");
        }

        /**
         * ac.synchronization — 整车同步.
         * <p>打开或关闭整车温度同步。
         * <p>读取 Ac.AC_SYNCHRONOUS_KEY (dev=1000, fid=1060110344).
         */
        public static SensorValue synchronization() {
            return read(1000, 1060110344, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * ac.ventilation — 驻车通风.
         * <p>打开或关闭驻车通风，停车时为车内通风换气。
         * <p>读取 Ac.AC_VENTILATION_STATE (dev=1000, fid=1314914314).
         */
        public static SensorValue ventilation() {
            return read(1000, 1314914314, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * ac.cabin_temperature — 车内温度.
         * <p>车内当前温度（摄氏度）。
         * <p>读取 Ac.AC_TEMP_INSIDE (dev=1000, fid=1031798832).
         */
        public static SensorValue cabinTemperature() {
            return read(1000, 1031798832, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * ac.outside_temperature — 车外温度.
         * <p>车外当前温度（摄氏度）。
         * <p>读取 Ac.AC_TEMP_OUT (dev=1000, fid=1077936184).
         */
        public static SensorValue outsideTemperature() {
            return read(1000, 1077936184, 5, false, null, 1.0, 0.0, "");
        }

        public static final class airflowMode {
            private airflowMode() {}

            public static final class driver {
                private driver() {}

                /**
                 * ac.airflow_mode — 吹风模式.
                 * <p>选择出风口对着人吹、避开人吹，或左右扫风。
                 * <p>变体 driver；字段 direct：Ac.AC_MAIN_DRIVE_BLOWS_MODE_SIGNAL_TO_PEOPLE (dev=1000, fid=1269825936).
                 */
                public static SensorValue direct() {
                    return read(1000, 1269825936, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * ac.airflow_mode — 吹风模式.
                 * <p>选择出风口对着人吹、避开人吹，或左右扫风。
                 * <p>变体 driver；字段 avoid：Ac.AC_MAIN_DRIVE_SIDE_TO_AVOID_BLOWING_MODE_SIGNAL_TO_PEOPLE (dev=1000, fid=1269825940).
                 */
                public static SensorValue avoid() {
                    return read(1000, 1269825940, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * ac.airflow_mode — 吹风模式.
                 * <p>选择出风口对着人吹、避开人吹，或左右扫风。
                 * <p>变体 driver；字段 sweep：Ac.AC_MAIN_DRIVE_LEFT_AND_RIGHT_SWEEP_SIGNAL (dev=1000, fid=1269825946).
                 */
                public static SensorValue sweep() {
                    return read(1000, 1269825946, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

            }

            public static final class frontPassenger {
                private frontPassenger() {}

                /**
                 * ac.airflow_mode — 吹风模式.
                 * <p>选择出风口对着人吹、避开人吹，或左右扫风。
                 * <p>变体 frontPassenger；字段 direct：Ac.AC_PASSENGER_BLOWS_MODE_SIGNAL_TO_PEOPLE (dev=1000, fid=1269825938).
                 */
                public static SensorValue direct() {
                    return read(1000, 1269825938, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * ac.airflow_mode — 吹风模式.
                 * <p>选择出风口对着人吹、避开人吹，或左右扫风。
                 * <p>变体 frontPassenger；字段 avoid：Ac.AC_PASSENGER_SIDE_TO_AVOID_BLOWING_MODE_SIGNAL_TO_PEOPLE (dev=1000, fid=1269825942).
                 */
                public static SensorValue avoid() {
                    return read(1000, 1269825942, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * ac.airflow_mode — 吹风模式.
                 * <p>选择出风口对着人吹、避开人吹，或左右扫风。
                 * <p>变体 frontPassenger；字段 sweep：Ac.AC_PASSENGER_LEFT_AND_RIGHT_SWEEP_SIGNAL (dev=1000, fid=1269825950).
                 */
                public static SensorValue sweep() {
                    return read(1000, 1269825950, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

            }

        }

        public static final class defrost {
            private defrost() {}

            /**
             * ac.defrost — 除霜.
             * <p>打开或关闭前挡风玻璃除霜或后窗电除霜。
             * <p>变体 frontWindshield；读取 Ac.AC_DEFROST_FRONT_STATE (dev=1000, fid=1077936150).
             */
            public static SensorValue frontWindshield() {
                return read(1000, 1077936150, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * ac.defrost — 除霜.
             * <p>打开或关闭前挡风玻璃除霜或后窗电除霜。
             * <p>变体 rearWindshield；读取 Ac.AC_DEFROST_REAR_STATE (dev=1000, fid=1128267825).
             */
            public static SensorValue rearWindshield() {
                return read(1000, 1128267825, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

        }

        public static final class windMode {
            private windMode() {}

            /**
             * ac.wind_mode — 出风模式.
             * <p>选择空调出风位置：吹面、吹脚，或与除霜组合。
             * <p>变体 frontPair；读取 Ac.AC_WIND_MODE (dev=1000, fid=1077936152).
             */
            public static SensorValue frontPair() {
                return read(1000, 1077936152, 5, false, map(1, "FACE", 2, "FACE_FOOT", 3, "FOOT", 4, "FOOT_DEFROST", 5, "DEFROST", 6, "FACE_FOOT_DEFROST", 7, "FACE_DEFROST"), 1.0, 0.0, "");
            }

            /**
             * ac.wind_mode — 出风模式.
             * <p>选择空调出风位置：吹面、吹脚，或与除霜组合。
             * <p>变体 rearCabin；读取 Ac.AC_REAR_WIND_MODE (dev=1000, fid=1218445336).
             */
            public static SensorValue rearCabin() {
                return read(1000, 1218445336, 5, false, map(1, "FACE", 2, "FACE_FOOT", 3, "FOOT", 4, "FOOT_DEFROST", 5, "DEFROST", 6, "FACE_FOOT_DEFROST", 7, "FACE_DEFROST"), 1.0, 0.0, "");
            }

        }

        public static final class zoneFanLevel {
            private zoneFanLevel() {}

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * <p>变体 driver；读取 Ac.AC_WIND_LEVEL (dev=1000, fid=1077936156).
             */
            public static SensorValue driver() {
                return read(1000, 1077936156, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * <p>变体 frontPassenger；读取 Ac.AC_PASSENGER_WINDLEVEL (dev=1000, fid=1060110352).
             */
            public static SensorValue frontPassenger() {
                return read(1000, 1060110352, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * <p>变体 rearCabin；读取 Ac.AC_REAR_WIND_LEVEL (dev=1000, fid=1218445320).
             */
            public static SensorValue rearCabin() {
                return read(1000, 1218445320, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * <p>变体 rearLeft；读取 Ac.AC_MIDDLE_LEFT_WIND_LEVEL (dev=1000, fid=1060110368).
             */
            public static SensorValue rearLeft() {
                return read(1000, 1060110368, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * <p>变体 rearRight；读取 Ac.AC_MIDDLE_RIGHT_WIND_LEVEL (dev=1000, fid=1060110372).
             */
            public static SensorValue rearRight() {
                return read(1000, 1060110372, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.zone_fan_level — 风量.
             * <p>调节空调风量档位，可按区域分别设置。
             * <p>变体 thirdRow；读取 Ac.AC_REAR_WIND_LEVEL (dev=1000, fid=1218445320).
             */
            public static SensorValue thirdRow() {
                return read(1000, 1218445320, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class controlMode {
            private controlMode() {}

            /**
             * ac.control_mode — 自动模式.
             * <p>打开或关闭空调自动模式，由空调自动调节风量和出风。
             * <p>变体 frontPair；读取 Ac.AC_CTRL_MODE (dev=1000, fid=1077936146).
             */
            public static SensorValue frontPair() {
                return read(1000, 1077936146, 5, false, map(0, "1", 1, "2"), 1.0, 0.0, "");
            }

            /**
             * ac.control_mode — 自动模式.
             * <p>打开或关闭空调自动模式，由空调自动调节风量和出风。
             * <p>变体 rearCabin；读取 Ac.AC_REAR_CTRL_MODE (dev=1000, fid=1218445326).
             */
            public static SensorValue rearCabin() {
                return read(1000, 1218445326, 5, false, map(0, "1", 1, "2"), 1.0, 0.0, "");
            }

        }

        public static final class power {
            private power() {}

            /**
             * ac.power — 空调开关.
             * <p>打开或关闭空调。
             * <p>变体 frontPair；读取 Ac.AC_POWER_STATE (dev=1000, fid=1077936144).
             */
            public static SensorValue frontPair() {
                return read(1000, 1077936144, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * ac.power — 空调开关.
             * <p>打开或关闭空调。
             * <p>变体 rearCabin；读取 Ac.AC_REAR_POWER_STATE (dev=1000, fid=1218445324).
             */
            public static SensorValue rearCabin() {
                return read(1000, 1218445324, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

        }

        public static final class temperature {
            private temperature() {}

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * <p>变体 driver；读取 Ac.AC_TEMP_MAIN (dev=1000, fid=1077936168).
             */
            public static SensorValue driver() {
                return read(1000, 1077936168, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * <p>变体 frontPassenger；读取 Ac.AC_TEMP_DEPUTY (dev=1000, fid=1077936176).
             */
            public static SensorValue frontPassenger() {
                return read(1000, 1077936176, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * <p>变体 rearCabin；读取 Ac.AC_TEMP_REAR (dev=1000, fid=1218445328).
             */
            public static SensorValue rearCabin() {
                return read(1000, 1218445328, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * <p>变体 rearLeft；读取 Ac.AC_MIDDLE_LEFT_TEMP (dev=1000, fid=1060110376).
             */
            public static SensorValue rearLeft() {
                return read(1000, 1060110376, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * <p>变体 rearRight；读取 Ac.AC_MIDDLE_RIGHT_TEMP (dev=1000, fid=1060110384).
             */
            public static SensorValue rearRight() {
                return read(1000, 1060110384, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * ac.temperature — 空调温度.
             * <p>设置或读取空调目标温度，可按区域分别设置。
             * <p>变体 thirdRow；读取 Ac.AC_TEMP_REAR (dev=1000, fid=1218445328).
             */
            public static SensorValue thirdRow() {
                return read(1000, 1218445328, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class adas {
        private adas() {}

        /**
         * adas.aeb_state — 自动紧急制动.
         * <p>打开或关闭自动紧急制动，并查看其状态。
         * <p>读取 Adas.ADAS_AEB_STATE (dev=1038, fid=852492332).
         */
        public static SensorValue aebState() {
            return read(1038, 852492332, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * adas.auto_hold_state — 自动驻车.
         * <p>打开或关闭自动驻车，并查看驻车工作状态。
         * <p>读取 Adas.ADAS_AVH_STATE (dev=1038, fid=304087110).
         */
        public static SensorValue autoHoldState() {
            return read(1038, 304087110, 5, false, map(0, "OFF", 1, "ON", 2, "HOLDING"), 1.0, 0.0, "");
        }

        /**
         * adas.brake_foot_sense — 制动助力模式.
         * <p>选择舒适或运动模式，调整制动踏板的助力感受。
         * <p>读取 Adas.ADAS_CMD_BRAKE_FOOT_SENSE (dev=1038, fid=305135629).
         */
        public static SensorValue brakeFootSense() {
            return read(1038, 305135629, 5, false, map(1, "1", 0, "2"), 1.0, 0.0, "");
        }

        /**
         * adas.electronic_stability_control — ESP 车身稳定.
         * <p>打开或关闭车身稳定控制功能。
         * <p>读取 Adas.ADAS_ESP_STATE (dev=1038, fid=305135676).
         */
        public static SensorValue electronicStabilityControl() {
            return read(1038, 305135676, 5, false, map(1, "false", 0, "true"), 1.0, 0.0, "");
        }

        /**
         * adas.forward_collision_warning — 前向碰撞预警.
         * <p>选择前向碰撞预警的提示时机，或关闭预警。
         * <p>读取 Adas.ADAS_FCW_LEVEL_STATUS (dev=1038, fid=327155720).
         */
        public static SensorValue forwardCollisionWarning() {
            return read(1038, 327155720, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * adas.hill_descent_control — 陡坡缓降.
         * <p>打开或关闭陡坡缓降，并查看其工作状态。
         * <p>读取 Adas.ADAS_CMD_HDC_STATE (dev=1038, fid=305135665).
         */
        public static SensorValue hillDescentControl() {
            return read(1038, 305135665, 5, false, map(0, "OFF", 1, "ON", 2, "ON_BLINKING", 3, "OFFLINE"), 1.0, 0.0, "");
        }

        /**
         * adas.lane_departure_mode — 车道偏离模式.
         * <p>选择车道偏离预警或车道保持，或关闭该功能。
         * <p>读取 Adas.ADAS_LKS_MODE (dev=1038, fid=828375054).
         */
        public static SensorValue laneDepartureMode() {
            return read(1038, 828375054, 5, false, map(0, "OFF", 1, "DEPARTURE_WARNING", 2, "LANE_KEEPING", 3, "ALL", 4, "ON"), 1.0, 0.0, "");
        }

        /**
         * adas.traffic_jam_assist_state — 交通拥堵辅助.
         * <p>打开或关闭交通拥堵辅助，并查看其工作状态。
         * <p>读取 Adas.ADAS_TJA_ICA_STATE (dev=1038, fid=828375084).
         */
        public static SensorValue trafficJamAssistState() {
            return read(1038, 828375084, 5, false, map(0, "OFF", 1, "INACTIVE", 2, "ACTIVE_1", 3, "ACTIVE_2", 4, "FAULT"), 1.0, 0.0, "");
        }

        /**
         * adas.intelligent_torque_control — iTAC 智能扭矩控制.
         * <p>启用或停用车辆的 iTAC 智能扭矩控制。
         * <p>读取 Setting.SET_ITAC_STATE (dev=1000, fid=656408615).
         */
        public static SensorValue intelligentTorqueControl() {
            return read(1000, 656408615, 5, false, map(0, "ON", 1, "OFF"), 1.0, 0.0, "");
        }

    }

    public static final class airQuality {
        private airQuality() {}

        /**
         * air_quality.pm25_detection — PM2.5 检测开关.
         * <p>打开或关闭 PM2.5 检测。
         * <p>读取 PM25_DUAL_CHANNEL_DETECT_STATE (dev=1008, fid=1331691577).
         */
        public static SensorValue pm25Detection() {
            return read(1008, 1331691577, 5, false, map(2, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * air_quality.purification — 空气净化.
         * <p>启动或停止车内空气净化，并查看净化进度状态。
         * <p>读取 Ac.AC_QUICK_CLEAN_AIR (dev=1000, fid=1033895944).
         */
        public static SensorValue purification() {
            return read(1000, 1033895944, 5, false, map(0, "NOT_RUNNING", 1, "RUNNING", 2, "ENDED", 3, "TERMINATED"), 1.0, 0.0, "");
        }

        public static final class pm25 {
            private pm25() {}

            /**
             * air_quality.pm25 — PM2.5 浓度.
             * <p>查看车内或车外 PM2.5 浓度，单位为微克每立方米。
             * <p>变体 inside；读取 Pm2p5.PM2P5_VALUE_IN (dev=1008, fid=1185939472).
             */
            public static SensorValue inside() {
                return read(1008, 1185939472, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * air_quality.pm25 — PM2.5 浓度.
             * <p>查看车内或车外 PM2.5 浓度，单位为微克每立方米。
             * <p>变体 outside；读取 Pm2p5.PM2P5_VALUE_OUT (dev=1008, fid=1331691548).
             */
            public static SensorValue outside() {
                return read(1008, 1331691548, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class audio {
        private audio() {}

        /**
         * audio.headrest_effect — 头枕音效.
         * <p>选择头枕音响的环绕、驾享或独享音效，或关闭音效。
         * <p>读取 Audio.AUDIO_HEADREST_SOUND_EFFECT_STATUS (dev=1002, fid=-894435307).
         */
        public static SensorValue headrestEffect() {
            return read(1002, -894435307, 5, false, map(1, "OFF", 2, "SURROUND", 3, "DRIVER", 4, "PRIVATE"), 1.0, 0.0, "");
        }

        /**
         * audio.prompt_volume — 车辆提示音量.
         * <p>选择车辆提示音的低、中或高音量。
         * <p>读取 Audio.AUDIO_PROMPT_VOLUME_LEVEL_STATUS (dev=1000, fid=-1728052473).
         */
        public static SensorValue promptVolume() {
            return read(1000, -1728052473, 5, false, map(1, "LOW", 2, "MEDIUM", 3, "HIGH"), 1.0, 0.0, "");
        }

        /**
         * audio.simulated_engine_sound — 模拟声浪.
         * <p>打开或关闭模拟引擎声浪。
         * <p>读取 Audio.AUDIO_ESS_SETTING_STATUS (dev=1002, fid=1339031610).
         */
        public static SensorValue simulatedEngineSound() {
            return read(1002, 1339031610, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        public static final class soundBalance {
            private soundBalance() {}

            /**
             * audio.sound_balance — 声场平衡.
             * <p>调节声音在车内左右、前后方向的分布。
             * <p>字段 x：Audio.AUDIO_X_SOUND_FIELD (dev=1002, fid=1108344848).
             */
            public static SensorValue x() {
                return read(1002, 1108344848, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * audio.sound_balance — 声场平衡.
             * <p>调节声音在车内左右、前后方向的分布。
             * <p>字段 y：Audio.AUDIO_Y_SOUND_FIELD (dev=1002, fid=1108344852).
             */
            public static SensorValue y() {
                return read(1002, 1108344852, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class soundFocus {
            private soundFocus() {}

            /**
             * audio.sound_focus — 声场聚焦.
             * <p>选择声音聚焦的座位区域或声场模式。
             * <p>变体 dynaudio；读取 Audio.AUDIO_LEFT_RIGHT_FIELD_SET_STATE_qy (dev=1002, fid=1339031565).
             */
            public static SensorValue dynaudio() {
                return read(1002, 1339031565, 5, false, map(1, "ALL", 2, "DRIVER", 3, "PASSENGER", 4, "REAR", 5, "SURROUND", 6, "CUSTOM"), 1.0, 0.0, "");
            }

            /**
             * audio.sound_focus — 声场聚焦.
             * <p>选择声音聚焦的座位区域或声场模式。
             * <p>变体 devialet；读取 Audio.AUDIO_SOUND_FIELD_FOCUS_STATE (dev=1002, fid=1339031565).
             */
            public static SensorValue devialet() {
                return read(1002, 1339031565, 5, false, map(4, "REAR", 6, "ALL", 7, "FRONT"), 1.0, 0.0, "");
            }

        }

        public static final class soundProfile {
            private soundProfile() {}

            /**
             * audio.sound_profile — 声音特色.
             * <p>选择音响的音色风格，例如原声、动感或人声。
             * <p>变体 dynaudio；读取 Audio.AUDIO_DYNAUDIO_SOUND_FEATURES (dev=1002, fid=-866123768).
             */
            public static SensorValue dynaudio() {
                return read(1002, -866123768, 5, false, map(1, "AUTHENTIC", 2, "DYNAMIC", 3, "SOFT", 4, "SPEECH", 5, "CUSTOM"), 1.0, 0.0, "");
            }

            /**
             * audio.sound_profile — 声音特色.
             * <p>选择音响的音色风格，例如原声、动感或人声。
             * <p>变体 devialet；读取 Audio.AUDIO_DYNAUDIO_SOUND_FEATURES (dev=1002, fid=-866123768).
             */
            public static SensorValue devialet() {
                return read(1002, -866123768, 5, false, map(1, "CLASSIC", 2, "SOFT", 3, "DYNAMIC", 4, "ROCK", 5, "VOCAL", 6, "CUSTOM"), 1.0, 0.0, "");
            }

        }

    }

    public static final class bodywork {
        private bodywork() {}

        /**
         * bodywork.ground_clearance — 离地间隙.
         * <p>查看车辆当前离地间隙，单位为毫米。
         * <p>读取 Bodywork.BODYWORK_GROUND_CLEARANCE (dev=1001, fid=277872946).
         */
        public static SensorValue groundClearance() {
            return read(1001, 277872946, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * bodywork.power_state — 整车电源状态.
         * <p>查看整车当前处于 OFF、ACC、ON 等哪个电源档位。
         * <p>读取 Bodywork.BODYWORK_POWER_LEVEL (dev=1001, fid=315621418).
         */
        public static SensorValue powerState() {
            return read(1001, 315621418, 5, false, map(0, "OFF", 1, "ACC", 2, "ON", 3, "OK", 4, "FAKE_OK"), 1.0, 0.0, "");
        }

        /**
         * bodywork.vehicle_height — 车身高度.
         * <p>查看车辆当前车身高度，单位为毫米。
         * <p>读取 Bodywork.BODYWORK_VEHICLE_HEIGHT (dev=1001, fid=277872956).
         */
        public static SensorValue vehicleHeight() {
            return read(1001, 277872956, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * bodywork.spoiler — 电动尾翼.
         * <p>打开或收起电动尾翼。
         * <p>读取 Setting.SETTING_ELECTRIC_TAIL_CURRENT_STATUS (dev=1000, fid=1200619531).
         */
        public static SensorValue spoiler() {
            return read(1000, 1200619531, 5, false, map(5, "ON", 6, "OFF"), 1.0, 0.0, "");
        }

        public static final class door {
            private door() {}

            public static final class open {
                private open() {}

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 driver；读取 Bodywork.BODYWORK_LEFT_HAND_FRONT_DOOR (dev=1001, fid=315621384).
                 */
                public static SensorValue driver() {
                    return read(1001, 315621384, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 frontPassenger；读取 Bodywork.BODYWORK_RIGHT_HAND_FRONT_DOOR (dev=1001, fid=315621385).
                 */
                public static SensorValue frontPassenger() {
                    return read(1001, 315621385, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 rearLeft；读取 Bodywork.BODYWORK_LEFT_HAND_REAR_DOOR (dev=1001, fid=315621386).
                 */
                public static SensorValue rearLeft() {
                    return read(1001, 315621386, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 rearRight；读取 Bodywork.BODYWORK_RIGHT_HAND_REAR_DOOR (dev=1001, fid=315621387).
                 */
                public static SensorValue rearRight() {
                    return read(1001, 315621387, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 hood；读取 Bodywork.BODYWORK_HOOD (dev=1001, fid=315621389).
                 */
                public static SensorValue hood() {
                    return read(1001, 315621389, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 trunk；读取 Bodywork.BODYWORK_LUGGAGE_DOOR (dev=1001, fid=315621388).
                 */
                public static SensorValue trunk() {
                    return read(1001, 315621388, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.door.open — 车门与盖板开闭状态.
                 * <p>查看指定车门、机舱盖、尾门或油箱盖是否打开。
                 * <p>变体 fuelDoor；读取 Bodywork.BODYWORK_FUEL_TANK_CAP (dev=1001, fid=1336934422).
                 */
                public static SensorValue fuelDoor() {
                    return read(1001, 1336934422, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
                }

            }

        }

        public static final class electricDoor {
            private electricDoor() {}

            public static final class actuatorState {
                private actuatorState() {}

                /**
                 * bodywork.electric_door.actuator_state — 电动车门运动状态.
                 * <p>查看指定电动车门的运动状态。
                 * <p>变体 driver；读取 Bodywork.BODYWORK_LF_ELECTRIC_DOOR_ACTUATOR_STATUS (dev=1001, fid=957350169).
                 */
                public static SensorValue driver() {
                    return read(1001, 957350169, 5, false, map(1, "MOVING_1", 2, "MOVING_2", 3, "PAUSED", 5, "OPEN"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.electric_door.actuator_state — 电动车门运动状态.
                 * <p>查看指定电动车门的运动状态。
                 * <p>变体 frontPassenger；读取 Bodywork.BODYWORK_RF_ELECTRIC_DOOR_ACTUATOR_STATUS (dev=1001, fid=957350172).
                 */
                public static SensorValue frontPassenger() {
                    return read(1001, 957350172, 5, false, map(1, "MOVING_1", 2, "MOVING_2", 3, "PAUSED", 5, "OPEN"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.electric_door.actuator_state — 电动车门运动状态.
                 * <p>查看指定电动车门的运动状态。
                 * <p>变体 rearLeft；读取 Bodywork.BODYWORK_LR_ELECTRIC_DOOR_ACTUATOR_STATUS (dev=1001, fid=957350176).
                 */
                public static SensorValue rearLeft() {
                    return read(1001, 957350176, 5, false, map(1, "MOVING_1", 2, "MOVING_2", 3, "PAUSED", 5, "OPEN"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.electric_door.actuator_state — 电动车门运动状态.
                 * <p>查看指定电动车门的运动状态。
                 * <p>变体 rearRight；读取 Bodywork.BODYWORK_RR_ELECTRIC_DOOR_ACTUATOR_STATUS (dev=1001, fid=957350179).
                 */
                public static SensorValue rearRight() {
                    return read(1001, 957350179, 5, false, map(1, "MOVING_1", 2, "MOVING_2", 3, "PAUSED", 5, "OPEN"), 1.0, 0.0, "");
                }

            }

            public static final class position {
                private position() {}

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * <p>变体 driver；读取 Bodywork.BODYWORK_LF_ELECTRIC_DOOR_CURRENT_POSITION (dev=1001, fid=957350000).
                 */
                public static SensorValue driver() {
                    return read(1001, 957350000, 5, false, map(1, "NOT_CLOSED_1", 2, "CLOSED", 3, "NOT_CLOSED_3"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * <p>变体 frontPassenger；读取 Bodywork.BODYWORK_RF_ELECTRIC_DOOR_CURRENT_POSITION (dev=1001, fid=957350002).
                 */
                public static SensorValue frontPassenger() {
                    return read(1001, 957350002, 5, false, map(1, "NOT_CLOSED_1", 2, "CLOSED", 3, "NOT_CLOSED_3"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * <p>变体 rearLeft；读取 Bodywork.BODYWORK_LR_ELECTRIC_DOOR_CURRENT_POSITION (dev=1001, fid=957350004).
                 */
                public static SensorValue rearLeft() {
                    return read(1001, 957350004, 5, false, map(1, "NOT_CLOSED_1", 2, "CLOSED", 3, "NOT_CLOSED_3"), 1.0, 0.0, "");
                }

                /**
                 * bodywork.electric_door.position — 电动车门.
                 * <p>打开、关闭或暂停指定电动车门，并查看门的位置状态。
                 * <p>变体 rearRight；读取 Bodywork.BODYWORK_RR_ELECTRIC_DOOR_CURRENT_POSITION (dev=1001, fid=957350006).
                 */
                public static SensorValue rearRight() {
                    return read(1001, 957350006, 5, false, map(1, "NOT_CLOSED_1", 2, "CLOSED", 3, "NOT_CLOSED_3"), 1.0, 0.0, "");
                }

            }

        }

        public static final class slidingDoor {
            private slidingDoor() {}

            /**
             * bodywork.sliding_door — 电动侧滑门.
             * <p>打开或关闭左后、右后的电动侧滑门，并查看门的位置状态。
             * <p>变体 rearLeft；读取 feature1259339792 (dev=1001, fid=1259339792).
             */
            public static SensorValue rearLeft() {
                return read(1001, 1259339792, 5, false, map(0, "CLOSED", 2, "OPEN"), 1.0, 0.0, "");
            }

            /**
             * bodywork.sliding_door — 电动侧滑门.
             * <p>打开或关闭左后、右后的电动侧滑门，并查看门的位置状态。
             * <p>变体 rearRight；读取 feature1260388368 (dev=1001, fid=1260388368).
             */
            public static SensorValue rearRight() {
                return read(1001, 1260388368, 5, false, map(0, "CLOSED", 2, "OPEN"), 1.0, 0.0, "");
            }

        }

        public static final class roof {
            private roof() {}

            /**
             * bodywork.roof.position — 天窗开度.
             * <p>查看或设置天窗的打开百分比。
             * <p>读取 Bodywork.BODYWORK_MOON_ROOF_OPEN_PERCENT (dev=1001, fid=1101004808).
             */
            public static SensorValue position() {
                return read(1001, 1101004808, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class sunshade {
            private sunshade() {}

            public static final class position {
                private position() {}

                /**
                 * bodywork.sunshade.position — 车顶遮阳帘开度.
                 * <p>查看或设置前排、后排车顶遮阳帘的打开百分比。
                 * <p>变体 front；读取 Bodywork.BODYWORK_SUNSHADE_PANEL_PERCENT (dev=1001, fid=1101004816).
                 */
                public static SensorValue front() {
                    return read(1001, 1101004816, 5, false, null, 1.0, 0.0, "");
                }

                /**
                 * bodywork.sunshade.position — 车顶遮阳帘开度.
                 * <p>查看或设置前排、后排车顶遮阳帘的打开百分比。
                 * <p>变体 rear；读取 Bodywork.BODYWORK_REAR_SUNSHADE_PANEL_PERCENT (dev=1001, fid=1206910984).
                 */
                public static SensorValue rear() {
                    return read(1001, 1206910984, 5, false, null, 1.0, 0.0, "");
                }

            }

        }

        public static final class trunk {
            private trunk() {}

            /**
             * bodywork.trunk.open_height — 尾门最大开启高度.
             * <p>查看或调整电动尾门开启时的高度上限。
             * <p>读取 Setting.SET_BACK_DOOR_OPEN_HEIGHT (dev=1023, fid=1074790408).
             */
            public static SensorValue openHeight() {
                return read(1023, 1074790408, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * bodywork.trunk.position — 尾门位置.
             * <p>查看尾门当前位置。
             * <p>读取 Bodywork.BODYWORK_BACKDOOR_CURRENT_POSITION (dev=1000, fid=1074790416).
             */
            public static SensorValue position() {
                return read(1000, 1074790416, 5, false, map(1, "OPEN", 2, "NOT_OPEN_2", 3, "NOT_OPEN_3"), 1.0, 0.0, "");
            }

        }

        public static final class window {
            private window() {}

            public static final class position {
                private position() {}

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * <p>变体 driver；读取 Bodywork.BODYWORK_WINDOW_LEFT_FRONT_PERCENT (dev=1001, fid=947912720).
                 */
                public static SensorValue driver() {
                    return read(1001, 947912720, 5, false, null, 1.0, 0.0, "");
                }

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * <p>变体 frontPassenger；读取 Bodywork.BODYWORK_WINDOW_RIGHT_FRONT_PERCENT (dev=1001, fid=947912728).
                 */
                public static SensorValue frontPassenger() {
                    return read(1001, 947912728, 5, false, null, 1.0, 0.0, "");
                }

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * <p>变体 rearLeft；读取 Bodywork.BODYWORK_WINDOW_LEFT_REAR_PERCENT (dev=1001, fid=947912736).
                 */
                public static SensorValue rearLeft() {
                    return read(1001, 947912736, 5, false, null, 1.0, 0.0, "");
                }

                /**
                 * bodywork.window.position — 车窗开度.
                 * <p>查看或设置指定侧窗的打开百分比；设置目标开度适用于 DiLink 5 及以上车机（Android 12 及以上），查看开度不受此限制。
                 * <p>变体 rearRight；读取 Bodywork.BODYWORK_WINDOW_RIGHT_REAR_PERCENT (dev=1001, fid=947912744).
                 */
                public static SensorValue rearRight() {
                    return read(1001, 947912744, 5, false, null, 1.0, 0.0, "");
                }

            }

        }

        public static final class windowSunshade {
            private windowSunshade() {}

            /**
             * bodywork.window_sunshade — 后侧窗遮阳帘.
             * <p>展开或收起左后、右后侧窗的遮阳帘，并查看其位置。
             * <p>变体 rearLeft；读取 Setting.SETTING_LR_WINDOW_SUNSHADE_POSITION (dev=1001, fid=1025507390).
             */
            public static SensorValue rearLeft() {
                return read(1001, 1025507390, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * bodywork.window_sunshade — 后侧窗遮阳帘.
             * <p>展开或收起左后、右后侧窗的遮阳帘，并查看其位置。
             * <p>变体 rearRight；读取 Setting.SETTING_RR_WINDOW_SUNSHADE_POSITION (dev=1001, fid=899678526).
             */
            public static SensorValue rearRight() {
                return read(1001, 899678526, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class charging {
        private charging() {}

        /**
         * charging.gun_state — 充电枪连接状态.
         * <p>查看充电枪是否连接，以及交流、直流或对外放电的连接类型。
         * <p>读取 Charging.CHARGING_GUN_CONNECT_STATE (dev=1009, fid=876609586).
         */
        public static SensorValue gunState() {
            return read(1009, 876609586, 5, false, map(1, "DISCONNECTED", 2, "AC", 3, "DC", 4, "AC_AND_DC", 5, "DISCHARGING"), 1.0, 0.0, "");
        }

        /**
         * charging.charger_work_state — 充电机工作状态.
         * <p>查看充电机处于准备、开始、结束还是终止阶段。
         * <p>读取 Charging.CHARGING_CHARGER_WORK_STATE (dev=1009, fid=666894346).
         */
        public static SensorValue chargerWorkState() {
            return read(1009, 666894346, 5, false, map(1, "PREPARING", 2, "STARTED", 3, "FINISHED", 4, "TERMINATED"), 1.0, 0.0, "");
        }

        /**
         * charging.soc_save_mode — 保电模式.
         * <p>选择智能保电或强制保电。
         * <p>读取 Charging.CHARGING_SOC_SAVE_SWITCH (dev=1009, fid=1186988086).
         */
        public static SensorValue socSaveMode() {
            return read(1009, 1186988086, 5, false, map(1, "SMART", 2, "FORCED"), 1.0, 0.0, "");
        }

        /**
         * charging.battery_voltage — 动力电池电压.
         * <p>查看动力电池当前电压，单位为伏特。
         * <p>读取 Charging.CHARGING_CHARGE_BATTERY_VOLT (dev=1009, fid=1145045000).
         */
        public static SensorValue batteryVoltage() {
            return read(1009, 1145045000, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * charging.current — 充放电电流.
         * <p>查看动力电池的充放电电流，单位为安培。
         * <p>读取 Charging.CHARGING_CHARGE_CURRENT (dev=1009, fid=1145045016).
         */
        public static SensorValue current() {
            return read(1009, 1145045016, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * charging.external_discharge_current — 对外放电电流.
         * <p>查看车辆对外放电时的电流，单位为安培。
         * <p>读取 Charging.CHARGING_DISCHARGE_VEHICLE_OUTPUT_CURRENT (dev=1009, fid=1248985116).
         */
        public static SensorValue externalDischargeCurrent() {
            return read(1009, 1248985116, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * charging.power — 充电功率.
         * <p>查看车辆报告的充电功率读数。
         * <p>读取 Charging.CHARGING_CHARGE_POWER_DD (dev=1009, fid=842006552).
         */
        public static SensorValue power() {
            return read(1009, 842006552, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * charging.battery_state — 电池充放电状态.
         * <p>查看动力电池当前处于充电、放电、暂停或其他工作状态。
         * <p>读取 Charging.CHARGING_BATTERRY_DEVICE_STATE (dev=1009, fid=876609560).
         */
        public static SensorValue batteryState() {
            return read(1009, 876609560, 5, false, map(0, "PREPARING", 1, "CHARGING", 2, "CHARGE_COMPLETE", 3, "DISCHARGING", 4, "TERMINATED", 5, "C10_FAULT", 6, "GUN_FAULT", 7, "CHARGER_FAULT", 8, "AC_FAULT", 9, "SCHEDULED", 10, "CBU_DISCHARGING", 11, "TIMEOUT", 12, "DISCHARGE_COMPLETE", 13, "PAUSED"), 1.0, 0.0, "");
        }

    }

    public static final class doorLock {
        private doorLock() {}

        public static final class childLockState {
            private childLockState() {}

            /**
             * door_lock.child_lock_state — 儿童锁.
             * <p>打开或解除左后、右后车门的儿童锁，并查看锁定状态。
             * <p>变体 rearLeft；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_CHILDLOCK_LEFT (dev=1041, fid=1228931106).
             */
            public static SensorValue rearLeft() {
                return read(1041, 1228931106, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

            /**
             * door_lock.child_lock_state — 儿童锁.
             * <p>打开或解除左后、右后车门的儿童锁，并查看锁定状态。
             * <p>变体 rearRight；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_CHILDLOCK_RIGHT (dev=1041, fid=1228931108).
             */
            public static SensorValue rearRight() {
                return read(1041, 1228931108, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

        }

        public static final class lockState {
            private lockState() {}

            /**
             * door_lock.lock_state — 门锁状态.
             * <p>查看指定车门或尾门是否锁定。
             * <p>变体 driver；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_LEFT_FRONT (dev=1041, fid=960495668).
             */
            public static SensorValue driver() {
                return read(1041, 960495668, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

            /**
             * door_lock.lock_state — 门锁状态.
             * <p>查看指定车门或尾门是否锁定。
             * <p>变体 frontPassenger；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_RIGHT_FRONT (dev=1041, fid=960495670).
             */
            public static SensorValue frontPassenger() {
                return read(1041, 960495670, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

            /**
             * door_lock.lock_state — 门锁状态.
             * <p>查看指定车门或尾门是否锁定。
             * <p>变体 rearLeft；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_LEFT_REAR (dev=1041, fid=960495672).
             */
            public static SensorValue rearLeft() {
                return read(1041, 960495672, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

            /**
             * door_lock.lock_state — 门锁状态.
             * <p>查看指定车门或尾门是否锁定。
             * <p>变体 rearRight；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_RIGHT_REAR (dev=1041, fid=960495674).
             */
            public static SensorValue rearRight() {
                return read(1041, 960495674, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

            /**
             * door_lock.lock_state — 门锁状态.
             * <p>查看指定车门或尾门是否锁定。
             * <p>变体 trunk；读取 DoorLock.DOOR_LOCK_COMMAND_AREA_BACK (dev=1041, fid=960495676).
             */
            public static SensorValue trunk() {
                return read(1041, 960495676, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
            }

        }

    }

    public static final class energy {
        private energy() {}

        /**
         * energy.mode — 能量模式.
         * <p>选择纯电、强制纯电或混动模式，并查看当前能量模式。
         * <p>读取 Energy.ENERGY_MODE_INSTRUMENT (dev=1006, fid=874512420).
         */
        public static SensorValue mode() {
            return read(1006, 874512420, 5, false, map(0, "STOPPED", 1, "EV", 2, "FORCED_EV", 3, "HEV", 4, "FUEL", 5, "HOLD"), 1.0, 0.0, "");
        }

        /**
         * energy.extended_range — 长续航模式.
         * <p>开启或关闭长续航模式。
         * <p>读取 Setting.SETTING_MANDATORY_EV_MODE_SWITCH_STATUS (dev=1000, fid=450889768).
         */
        public static SensorValue extendedRange() {
            return read(1000, 450889768, 5, false, map(1, "OFF", 2, "ON"), 1.0, 0.0, "");
        }

        public static final class operationMode {
            private operationMode() {}

            /**
             * energy.operation_mode — 驾驶模式.
             * <p>按车辆实际驾驶模式控制系统，选择普通、经济、运动、地形或竞速模式。
             * <p>变体 energy；读取 Energy.ENERGY_OPERATION_MODE (dev=1006, fid=555745294).
             */
            public static SensorValue energy() {
                return read(1006, 555745294, 5, false, map(1, "ECO", 2, "SPORT", 3, "NORMAL", 4, "SNOW", 5, "MUD", 6, "SAND"), 1.0, 0.0, "");
            }

            /**
             * energy.operation_mode — 驾驶模式.
             * <p>按车辆实际驾驶模式控制系统，选择普通、经济、运动、地形或竞速模式。
             * <p>变体 extended；读取 Setting.SETTING_TARGET_DRIVING_MODE (dev=1000, fid=255852712).
             */
            public static SensorValue extended() {
                return read(1000, 255852712, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class regeneration {
            private regeneration() {}

            /**
             * energy.regeneration — 能量回收强度.
             * <p>将能量回收设为标准或较强。
             * <p>变体 domestic；读取 Setting.SET_DR_ENERGY_FB (dev=1006, fid=873463842).
             */
            public static SensorValue domestic() {
                return read(1006, 873463842, 5, false, map(1, "1", 2, "2"), 1.0, 0.0, "");
            }

            /**
             * energy.regeneration — 能量回收强度.
             * <p>将能量回收设为标准或较强。
             * <p>变体 overseas；读取 Setting.SET_DR_ENERGY_FB (dev=1006, fid=873463842).
             */
            public static SensorValue overseas() {
                return read(1006, 873463842, 5, false, map(2, "1", 3, "2"), 1.0, 0.0, "");
            }

        }

    }

    public static final class engine {
        private engine() {}

        /**
         * engine.rpm — 发动机转速.
         * <p>查看发动机每分钟转数。
         * <p>读取 Engine.ENGINE_SPEED_GB (dev=1012, fid=282066952).
         */
        public static SensorValue rpm() {
            return read(1012, 282066952, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * engine.power — 动力功率.
         * <p>查看动力系统当前功率，单位为千瓦。
         * <p>读取 Engine.ENGINE_POWER (dev=1012, fid=339738656).
         */
        public static SensorValue power() {
            return read(1012, 339738656, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * engine.voice_simulator — 引擎模拟音.
         * <p>打开或关闭引擎模拟音。
         * <p>读取 Engine.ENGINE_VOICE_SIMULATOR_STATE (dev=1012, fid=1223688202).
         */
        public static SensorValue voiceSimulator() {
            return read(1012, 1223688202, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * engine.indicated_torque — 发动机扭矩.
         * <p>查看发动机的扭矩读数。
         * <p>读取 Engine.ENGINE_INDICATED_TORQUE (dev=1012, fid=283115528).
         */
        public static SensorValue indicatedTorque() {
            return read(1012, 283115528, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * engine.motor_input_voltage — 电机输入电压.
         * <p>查看电机输入端电压，单位为伏特。
         * <p>读取 Engine.ENGINE_MOTOR_INPUT_VOLTAGE (dev=1012, fid=1141899304).
         */
        public static SensorValue motorInputVoltage() {
            return read(1012, 1141899304, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * engine.oil_level — 机油液位.
         * <p>查看车辆报告的机油液位等级。
         * <p>读取 Engine.ENGINE_OIL_LEVEL (dev=1012, fid=89129016).
         */
        public static SensorValue oilLevel() {
            return read(1012, 89129016, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * engine.low_voltage_side_voltage — 低压侧电压.
         * <p>查看动力系统低压侧电压，单位为伏特。
         * <p>读取 Energy.ENERGY_LOW_VOLTAGE_SIDE_VOLTAGE (dev=1012, fid=919601184).
         */
        public static SensorValue lowVoltageSideVoltage() {
            return read(1012, 919601184, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * engine.low_voltage_side_current — 低压侧电流.
         * <p>查看动力系统低压侧电流，单位为安培。
         * <p>读取 Energy.ENERGY_LOW_VOLTAGE_SIDE_CURRENT (dev=1012, fid=919601192).
         */
        public static SensorValue lowVoltageSideCurrent() {
            return read(1012, 919601192, 5, false, null, 1.0, 0.0, "");
        }

        public static final class motorRpm {
            private motorRpm() {}

            /**
             * engine.motor_rpm — 电机转速.
             * <p>查看前电机或后电机的每分钟转数。
             * <p>变体 frontMotor；读取 Engine.ENGINE_FRONT_MOTOR_SPEED (dev=1012, fid=1141899272).
             */
            public static SensorValue frontMotor() {
                return read(1012, 1141899272, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * engine.motor_rpm — 电机转速.
             * <p>查看前电机或后电机的每分钟转数。
             * <p>变体 rearMotor；读取 Engine.ENGINE_REAR_MOTOR_SPEED (dev=1012, fid=621805576).
             */
            public static SensorValue rearMotor() {
                return read(1012, 621805576, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class motorTorque {
            private motorTorque() {}

            /**
             * engine.motor_torque — 电机扭矩.
             * <p>查看前电机或后电机的扭矩读数。
             * <p>变体 frontMotor；读取 Engine.ENGINE_FRONT_MOTOR_TORQUE (dev=1012, fid=1141899288).
             */
            public static SensorValue frontMotor() {
                return read(1012, 1141899288, 7, true, null, 1.0, 0.0, "");
            }

            /**
             * engine.motor_torque — 电机扭矩.
             * <p>查看前电机或后电机的扭矩读数。
             * <p>变体 rearMotor；读取 Engine.ENGINE_REAR_MOTOR_TORQUE (dev=1012, fid=621805592).
             */
            public static SensorValue rearMotor() {
                return read(1012, 621805592, 7, false, null, 1.0, 0.0, "");
            }

        }

        public static final class motorTemperature {
            private motorTemperature() {}

            /**
             * engine.motor_temperature — 电机温度.
             * <p>查看前后电机或电机功率模块的温度，单位为摄氏度。
             * <p>变体 frontMotor；读取 Gb.GB_FRONT_MOTOR_TEMP (dev=1039, fid=1154482192).
             */
            public static SensorValue frontMotor() {
                return read(1039, 1154482192, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * engine.motor_temperature — 电机温度.
             * <p>查看前后电机或电机功率模块的温度，单位为摄氏度。
             * <p>变体 rearMotor；读取 Gb.GB_TREAR_MOTOR_TEMP (dev=1039, fid=1155530768).
             */
            public static SensorValue rearMotor() {
                return read(1039, 1155530768, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * engine.motor_temperature — 电机温度.
             * <p>查看前后电机或电机功率模块的温度，单位为摄氏度。
             * <p>变体 frontModule；读取 Gb.GB_FRONT_MOTOR_IPM_TEMP (dev=1039, fid=1154482184).
             */
            public static SensorValue frontModule() {
                return read(1039, 1154482184, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * engine.motor_temperature — 电机温度.
             * <p>查看前后电机或电机功率模块的温度，单位为摄氏度。
             * <p>变体 rearModule；读取 Gb.GB_REAR_MOTOR_IPM_TEMP (dev=1039, fid=1155530760).
             */
            public static SensorValue rearModule() {
                return read(1039, 1155530760, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class gearbox {
        private gearbox() {}

        /**
         * gearbox.gear — 当前挡位.
         * <p>查看车辆当前挡位，例如 P 挡、R 挡或 D 挡。
         * <p>读取 Gearbox.GEARBOX_AUTO_MODE_TYPE (dev=1011, fid=555745336).
         */
        public static SensorValue gear() {
            return read(1011, 555745336, 5, false, map(1, "P", 2, "R", 3, "N", 4, "D", 5, "M", 6, "S"), 1.0, 0.0, "");
        }

    }

    public static final class instrument {
        private instrument() {}

        /**
         * instrument.backlight_brightness — 仪表背光亮度.
         * <p>按原车设置界面的百分比调节仪表背光亮度，适用于 DiLink 3、4 车机（Android 12 以下）。
         * <p>读取 Instrument.INSTRUMENT_BACKLIGHT_BRIGHTNESS (dev=1007, fid=1274019896).
         */
        public static SensorValue backlightBrightness() {
            return read(1007, 1274019896, 5, false, map(1, "0", 2, "10", 3, "20", 4, "30", 5, "40", 6, "50", 7, "60", 8, "70", 9, "80", 10, "90", 11, "100", 12, "0", 13, "10", 14, "20", 15, "30", 16, "40", 17, "50", 18, "60", 19, "70", 20, "80", 21, "90", 22, "100"), 1.0, 0.0, "");
        }

        /**
         * instrument.brightness_percent — 仪表亮度百分比.
         * <p>按百分比调节仪表亮度，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用仪表背光亮度。
         * <p>读取 Instrument.INSTRUMENT_BRIGHTNESS_PERCENTAGE (dev=1007, fid=1179648012).
         */
        public static SensorValue brightnessPercent() {
            return read(1007, 1179648012, 7, false, null, 0.1, 0.0, "");
        }

    }

    public static final class light {
        private light() {}

        /**
         * light.daytime_light — 日间行车灯.
         * <p>打开或关闭日间行车灯。
         * <p>读取 Light.LIGHT_DAY_RUNNING_LIGHT_AUTO_STATE (dev=1004, fid=985661476).
         */
        public static SensorValue daytimeLight() {
            return read(1004, 985661476, 5, false, map(2, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * light.double_flash — 双闪.
         * <p>打开或关闭双闪警示灯。
         * <p>读取 Light.LIGHT_TURN_SIGNAL_LIGHT (dev=1004, fid=950009900).
         */
        public static SensorValue doubleFlash() {
            return read(1004, 950009900, 5, false, map(1, "false", 2, "false", 3, "false", 4, "false", 5, "false", 6, "true"), 1.0, 0.0, "");
        }

        /**
         * light.headlight_mode — 车灯控制模式.
         * <p>选择车灯关闭、自动、小灯或近光灯模式。
         * <p>读取 Instrument.INSTRUMENT_HEADLIGHT_CONTROL_FEEDBACK (dev=1004, fid=1011875880).
         */
        public static SensorValue headlightMode() {
            return read(1004, 1011875880, 5, false, map(1, "OFF", 2, "AUTO", 3, "SIDE_LIGHT", 4, "LOW_BEAM"), 1.0, 0.0, "");
        }

        /**
         * light.high_beam_assist — 智能远近光.
         * <p>打开或关闭智能远近光切换，并查看工作状态。
         * <p>读取 Adas.ADAS_HMA_STATE (dev=1004, fid=828375048).
         */
        public static SensorValue highBeamAssist() {
            return read(1004, 828375048, 5, false, map(0, "OFF", 1, "INACTIVE", 2, "STANDBY", 3, "ACTIVE", 4, "FAULT", 5, "CAMERA_BLOCKED"), 1.0, 0.0, "");
        }

        /**
         * light.inside_light_on_door — 开门自动亮灯.
         * <p>设置打开车门时是否自动点亮车内灯。
         * <p>读取 Setting.SET_INSIDE_LIGHT_DOOR_STATE (dev=1000, fid=1121976363).
         */
        public static SensorValue insideLightOnDoor() {
            return read(1000, 1121976363, 5, false, map(2, "false", 1, "true"), 1.0, 0.0, "");
        }

        /**
         * light.turn_signal_state — 转向信号模式.
         * <p>选择左转向、右转向、双闪或关闭，并查看整车转向信号状态。
         * <p>读取 Light.LIGHT_TURN_SIGNAL_LIGHT (dev=1004, fid=950009900).
         */
        public static SensorValue turnSignalState() {
            return read(1004, 950009900, 5, false, map(1, "OFF", 2, "LEFT_NORMAL", 3, "LEFT_FAST", 4, "RIGHT_NORMAL", 5, "RIGHT_FAST", 6, "HAZARD", 7, "EMERGENCY", 8, "REAR_END_WARNING", 9, "FAST_FLASH"), 1.0, 0.0, "");
        }

        public static final class autoHighBeam {
            private autoHighBeam() {}

            /**
             * light.auto_high_beam — 自适应远光.
             * <p>打开或关闭自适应远光功能，并分别查看左右远光灯的工作状态。
             * <p>变体 left；读取 Light.LIGHT_LF_ADB_STATE (dev=1004, fid=985661468).
             */
            public static SensorValue left() {
                return read(1004, 985661468, 5, false, map(0, "INVALID", 1, "OFF", 2, "STANDBY", 3, "ACTIVATION", 4, "WORKING", 5, "FAULT"), 1.0, 0.0, "");
            }

            /**
             * light.auto_high_beam — 自适应远光.
             * <p>打开或关闭自适应远光功能，并分别查看左右远光灯的工作状态。
             * <p>变体 right；读取 Light.LIGHT_RF_ADB_STATE (dev=1004, fid=986710044).
             */
            public static SensorValue right() {
                return read(1004, 986710044, 5, false, map(0, "INVALID", 1, "OFF", 2, "STANDBY", 3, "ACTIVATION", 4, "WORKING", 5, "FAULT"), 1.0, 0.0, "");
            }

        }

        public static final class fogSwitch {
            private fogSwitch() {}

            /**
             * light.fog_switch — 雾灯开关（怀挡）.
             * <p>打开或关闭怀挡车型的前雾灯或后雾灯。
             * <p>变体 front；读取 Instrument.INSTRUMENT_FRONT_FOG_LAMPS_SWITCH_STATE (dev=1004, fid=1011875876).
             */
            public static SensorValue front() {
                return read(1004, 1011875876, 5, false, map(2, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * light.fog_switch — 雾灯开关（怀挡）.
             * <p>打开或关闭怀挡车型的前雾灯或后雾灯。
             * <p>变体 rear；读取 Light.LIGHT_REAR_FOG_LAMPS (dev=1004, fid=950009872).
             */
            public static SensorValue rear() {
                return read(1004, 950009872, 5, false, map(2, "false", 1, "true"), 1.0, 0.0, "");
            }

        }

        public static final class lightState {
            private lightState() {}

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * <p>变体 sideLight；读取 Light.LIGHT_SIDE_LIGHT (dev=1004, fid=321912841).
             */
            public static SensorValue sideLight() {
                return read(1004, 321912841, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * <p>变体 lowBeam；读取 Light.LIGHT_LOW_BEAM_LIGHT (dev=1004, fid=321912842).
             */
            public static SensorValue lowBeam() {
                return read(1004, 321912842, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * <p>变体 highBeam；读取 Light.LIGHT_HIGH_BEAM_LIGHT (dev=1004, fid=321912843).
             */
            public static SensorValue highBeam() {
                return read(1004, 321912843, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * <p>变体 frontFog；读取 Light.LIGHT_FRONT_FOG_LIGHT (dev=1004, fid=321912846).
             */
            public static SensorValue frontFog() {
                return read(1004, 321912846, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * <p>变体 rearFog；读取 Light.LIGHT_REAR_FOG_LIGHT (dev=1004, fid=321912847).
             */
            public static SensorValue rearFog() {
                return read(1004, 321912847, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

            /**
             * light.light_state — 灯具状态.
             * <p>查看小灯、近光灯、远光灯、雾灯或脚照灯的开关状态，并可控制传统平台的雾灯。
             * <p>变体 footLight；读取 Light.LIGHT_FOOT_LIGHT (dev=1004, fid=315621432).
             */
            public static SensorValue footLight() {
                return read(1004, 315621432, 5, false, map(0, "false", 1, "true"), 1.0, 0.0, "");
            }

        }

        public static final class turnSignal {
            private turnSignal() {}

            /**
             * light.turn_signal — 分侧转向灯.
             * <p>打开指定一侧的转向灯，或查看该侧的转向信号状态。
             * <p>变体 left；读取 Light.LIGHT_LEFT_TURN_SIGNAL_LIGHT_SWITCH_STATE (dev=1004, fid=321912884).
             */
            public static SensorValue left() {
                return read(1004, 321912884, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * light.turn_signal — 分侧转向灯.
             * <p>打开指定一侧的转向灯，或查看该侧的转向信号状态。
             * <p>变体 right；读取 Light.LIGHT_RIGHT_TURN_SIGNAL_LIGHT_SWITCH_STATE (dev=1004, fid=321912886).
             */
            public static SensorValue right() {
                return read(1004, 321912886, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class panorama {
        private panorama() {}

        /**
         * panorama.apa_state — 自动泊车状态.
         * <p>查看自动泊车处于搜索车位、泊车、暂停或结束等哪个阶段。
         * <p>读取 Panorama.PANORAMA_APA_STATE (dev=1031, fid=322961416).
         */
        public static SensorValue apaState() {
            return read(1031, 322961416, 5, false, map(0, "PASSIVE", 1, "SEARCHING", 2, "PARKING", 3, "SUSPENDED", 4, "TERMINATED", 5, "COMPLETED", 6, "FAILURE", 7, "STANDBY"), 1.0, 0.0, "");
        }

        /**
         * panorama.display_mode — 全景显示模式.
         * <p>查看全景影像当前使用全屏、小窗或其他显示模式。
         * <p>读取 Panorama.PANORAMA_WORK_MODE (dev=1031, fid=1329598484).
         */
        public static SensorValue displayMode() {
            return read(1031, 1329598484, 5, false, map(0, "PANORAMA", 1, "FULL_SCREEN", 3, "WIDGET", 4, "RF_REVERSE", 5, "REVERSE", 6, "PANORAMA_3D"), 1.0, 0.0, "");
        }

        /**
         * panorama.output — 全景输出视角.
         * <p>查看全景影像当前显示的摄像头视角。
         * <p>读取 Panorama.PANORAMA_OUTPUT_STATE (dev=1031, fid=1329598480).
         */
        public static SensorValue output() {
            return read(1031, 1329598480, 5, false, map(0, "INVALID", 1, "OFF", 2, "FRONT", 3, "REAR", 4, "LEFT", 5, "RIGHT", 6, "COMPOSE", 7, "MATCHING", 8, "FRONT_LEFT", 9, "FRONT_RIGHT", 10, "REAR_LEFT", 11, "REAR_RIGHT", 12, "FRONT_WIDE", 13, "REAR_WIDE", 14, "WIDTH_LIMIT"), 1.0, 0.0, "");
        }

        /**
         * panorama.rotation — 全景显示方向.
         * <p>查看全景影像当前横向还是纵向显示。
         * <p>读取 Panorama.PANORAMA_ROTATION (dev=1031, fid=1329598492).
         */
        public static SensorValue rotation() {
            return read(1031, 1329598492, 5, false, map(1, "HORIZONTAL", 2, "VERTICAL"), 1.0, 0.0, "");
        }

        public static final class displayParameters {
            private displayParameters() {}

            /**
             * panorama.display_parameters — 全景显示设置.
             * <p>一起设置全景影像的视角、显示模式和横竖方向，或读取这三项设置。
             * <p>字段 output：Panorama.PANORAMA_OUTPUT_STATE (dev=1031, fid=1329598480).
             */
            public static SensorValue output() {
                return read(1031, 1329598480, 5, false, map(0, "INVALID", 1, "OFF", 2, "FRONT", 3, "REAR", 4, "LEFT", 5, "RIGHT", 6, "COMPOSE", 7, "MATCHING", 8, "FRONT_LEFT", 9, "FRONT_RIGHT", 10, "REAR_LEFT", 11, "REAR_RIGHT", 12, "FRONT_WIDE", 13, "REAR_WIDE", 14, "WIDTH_LIMIT"), 1.0, 0.0, "");
            }

            /**
             * panorama.display_parameters — 全景显示设置.
             * <p>一起设置全景影像的视角、显示模式和横竖方向，或读取这三项设置。
             * <p>字段 mode：Panorama.PANORAMA_WORK_MODE (dev=1031, fid=1329598484).
             */
            public static SensorValue mode() {
                return read(1031, 1329598484, 5, false, map(0, "PANORAMA", 1, "FULL_SCREEN", 3, "WIDGET", 4, "RF_REVERSE", 5, "REVERSE", 6, "PANORAMA_3D"), 1.0, 0.0, "");
            }

            /**
             * panorama.display_parameters — 全景显示设置.
             * <p>一起设置全景影像的视角、显示模式和横竖方向，或读取这三项设置。
             * <p>字段 rotation：Panorama.PANORAMA_ROTATION (dev=1031, fid=1329598492).
             */
            public static SensorValue rotation() {
                return read(1031, 1329598492, 5, false, map(1, "HORIZONTAL", 2, "VERTICAL"), 1.0, 0.0, "");
            }

        }

    }

    public static final class power {
        private power() {}

        /**
         * power.acc_status — 车机 ACC 电源.
         * <p>查看车机 ACC 电源是否上电。
         * <p>读取 Power.POWER_ACC_STATUS (dev=1005, fid=-1728053193).
         */
        public static SensorValue accStatus() {
            return read(1005, -1728053193, 5, false, map(0, "OFF", 1, "ON"), 1.0, 0.0, "");
        }

    }

    public static final class radar {
        private radar() {}

        public static final class distance {
            private distance() {}

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 leftFront；读取 Radar.RADAR_OBSTACLE_DISTANCE_LEFT_FRONT (dev=1025, fid=-1728053151).
             */
            public static SensorValue leftFront() {
                return read(1025, -1728053151, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 rightFront；读取 Radar.RADAR_OBSTACLE_DISTANCE_RIGHT_FRONT (dev=1025, fid=-1728053148).
             */
            public static SensorValue rightFront() {
                return read(1025, -1728053148, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 leftRear；读取 Radar.RADAR_OBSTACLE_DISTANCE_LEFT_REAR (dev=1025, fid=-1728053146).
             */
            public static SensorValue leftRear() {
                return read(1025, -1728053146, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 rightRear；读取 Radar.RADAR_OBSTACLE_DISTANCE_RIGHT_REAR (dev=1025, fid=-1728053145).
             */
            public static SensorValue rightRear() {
                return read(1025, -1728053145, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 leftSide；读取 Radar.RADAR_OBSTACLE_DISTANCE_LEFT (dev=1025, fid=-1728053147).
             */
            public static SensorValue leftSide() {
                return read(1025, -1728053147, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 frontLeftMid；读取 Radar.RADAR_OBSTACLE_DISTANCE_FRONT_LEFT_MID (dev=1025, fid=-1728053150).
             */
            public static SensorValue frontLeftMid() {
                return read(1025, -1728053150, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 frontRightMid；读取 Radar.RADAR_OBSTACLE_DISTANCE_FRONT_RIGHT_MID (dev=1025, fid=-1728053149).
             */
            public static SensorValue frontRightMid() {
                return read(1025, -1728053149, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * radar.distance — 雷达障碍物距离.
             * <p>查看指定雷达探头报告的障碍物距离读数。
             * <p>变体 middleRear；读取 feature-1728053143 (dev=1025, fid=-1728053143).
             */
            public static SensorValue middleRear() {
                return read(1025, -1728053143, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class safetyBelt {
        private safetyBelt() {}

        /**
         * safety_belt.warning — 副驾安全带提醒.
         * <p>查看副驾安全带是否正在报警提醒。
         * <p>读取 SafetyBelt.SAFETY_BELT_REMINDER (dev=1042, fid=89129027).
         */
        public static SensorValue warning() {
            return read(1042, 89129027, 5, false, map(1, "WARNING", 2, "NORMAL"), 1.0, 0.0, "");
        }

        public static final class state {
            private state() {}

            /**
             * safety_belt.state — 安全带状态.
             * <p>查看指定座位的安全带是否扣合。
             * <p>变体 driver；读取 SafetyBelt.SAFETY_BELT_COMMAND_AREA_MAIN (dev=1042, fid=89129025).
             */
            public static SensorValue driver() {
                return read(1042, 89129025, 5, false, map(0, "UNBUCKLED", 1, "BUCKLED", 2, "INVALID"), 1.0, 0.0, "");
            }

            /**
             * safety_belt.state — 安全带状态.
             * <p>查看指定座位的安全带是否扣合。
             * <p>变体 frontPassenger；读取 SafetyBelt.SAFETY_BELT_COMMAND_AREA_DEPUTY (dev=1042, fid=315621439).
             */
            public static SensorValue frontPassenger() {
                return read(1042, 315621439, 5, false, map(0, "UNBUCKLED", 1, "BUCKLED", 2, "INVALID"), 1.0, 0.0, "");
            }

            /**
             * safety_belt.state — 安全带状态.
             * <p>查看指定座位的安全带是否扣合。
             * <p>变体 rearLeft；读取 SafetyBelt.SAFETY_BELT_COMMAND_AREA_SECOND_ROW_SEAT_LEFT (dev=1042, fid=1196425240).
             */
            public static SensorValue rearLeft() {
                return read(1042, 1196425240, 5, false, map(0, "UNBUCKLED", 1, "BUCKLED", 2, "INVALID"), 1.0, 0.0, "");
            }

            /**
             * safety_belt.state — 安全带状态.
             * <p>查看指定座位的安全带是否扣合。
             * <p>变体 rearRight；读取 SafetyBelt.SAFETY_BELT_COMMAND_AREA_SECOND_ROW_SEAT_RIGHT (dev=1042, fid=1196425242).
             */
            public static SensorValue rearRight() {
                return read(1042, 1196425242, 5, false, map(0, "UNBUCKLED", 1, "BUCKLED", 2, "INVALID"), 1.0, 0.0, "");
            }

            /**
             * safety_belt.state — 安全带状态.
             * <p>查看指定座位的安全带是否扣合。
             * <p>变体 rearCenter；读取 SafetyBelt.SAFETY_BELT_COMMAND_AREA_SECOND_ROW_SEAT_MID (dev=1042, fid=1196425244).
             */
            public static SensorValue rearCenter() {
                return read(1042, 1196425244, 5, false, map(0, "UNBUCKLED", 1, "BUCKLED", 2, "INVALID"), 1.0, 0.0, "");
            }

        }

    }

    public static final class sensor {
        private sensor() {}

        /**
         * sensor.steering_wheel_angle — 方向盘转角.
         * <p>查看方向盘当前转动角度的读数。
         * <p>读取 Bodywork.BODYWORK_STEERING_WHEEL_ANGEL (dev=1001, fid=300941320).
         */
        public static SensorValue steeringWheelAngle() {
            return read(1001, 300941320, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * sensor.steering_wheel_speed — 方向盘转速.
         * <p>查看方向盘转动速度的读数。
         * <p>读取 Bodywork.BODYWORK_STEERING_WHEEL_SPEED (dev=1001, fid=300941336).
         */
        public static SensorValue steeringWheelSpeed() {
            return read(1001, 300941336, 7, false, null, 1.0, 0.0, "");
        }

        public static final class attitude {
            private attitude() {}

            /**
             * sensor.attitude — 车辆姿态角.
             * <p>查看车辆俯仰、侧倾或倾斜角度，单位为度。
             * <p>变体 pitch；读取 Setting.SETTING_PITCH_ANGLE_CALCULATION (dev=1023, fid=754975024).
             */
            public static SensorValue pitch() {
                return read(1023, 754975024, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * sensor.attitude — 车辆姿态角.
             * <p>查看车辆俯仰、侧倾或倾斜角度，单位为度。
             * <p>变体 roll；读取 Setting.SETTING_ROLL_ANGLE_CALCULATION (dev=1023, fid=754975032).
             */
            public static SensorValue roll() {
                return read(1023, 754975032, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * sensor.attitude — 车辆姿态角.
             * <p>查看车辆俯仰、侧倾或倾斜角度，单位为度。
             * <p>变体 slope；读取 Sensor.SENSOR_AUTO_SLOPE (dev=1043, fid=573571116).
             */
            public static SensorValue slope() {
                return read(1043, 573571116, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class setting {
        private setting() {}

        /**
         * setting.anti_motion — 智能防晕车.
         * <p>打开或关闭智能防晕车功能。
         * <p>读取 Setting.ADAS_ANTIMOTION_CONTROL_SWITCH_SIGNAL (dev=1000, fid=320864462).
         */
        public static SensorValue antiMotion() {
            return read(1000, 320864462, 5, false, map(1, "OFF", 2, "ON"), 1.0, 0.0, "");
        }

        /**
         * setting.comfort_mode — 舒适控制强度.
         * <p>选择舒适控制的较强、适中或偏弱档位。
         * <p>读取 Setting.BODYWORK_CDE_SWITCH (dev=1000, fid=938475534).
         */
        public static SensorValue comfortMode() {
            return read(1000, 938475534, 5, false, map(1, "STRONG", 2, "MEDIUM", 3, "WEAK"), 1.0, 0.0, "");
        }

        /**
         * setting.cruise_prompt — 巡航提示.
         * <p>查看车辆当前是否有巡航提示。
         * <p>读取 Setting.SETTING_AUTO_CRUISE_PROMPT (dev=1023, fid=852492318).
         */
        public static SensorValue cruisePrompt() {
            return read(1023, 852492318, 5, false, map(0, "NO_PROMPT", 1, "PROMPT"), 1.0, 0.0, "");
        }

        /**
         * setting.hud_brightness — HUD 亮度.
         * <p>调节抬头显示的亮度档位。
         * <p>读取 Setting.SET_BRIGHTNESS_GEAR_FEEDBACK (dev=1000, fid=951058448).
         */
        public static SensorValue hudBrightness() {
            return read(1000, 951058448, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * setting.hud_power — HUD 开关.
         * <p>打开或关闭抬头显示。
         * <p>读取 Setting.SET_HUD_SWITCH_STATUS_FEEDBACK (dev=1000, fid=951058460).
         */
        public static SensorValue hudPower() {
            return read(1000, 951058460, 5, false, map(1, "ON", 2, "OFF"), 1.0, 0.0, "");
        }

        /**
         * setting.road_preview — 路面预瞄.
         * <p>打开或关闭路面预瞄功能。
         * <p>读取 Setting.ADAS_PREVIEW_SWITCH (dev=1000, fid=629145674).
         */
        public static SensorValue roadPreview() {
            return read(1000, 629145674, 5, false, map(0, "OFF", 1, "ON"), 1.0, 0.0, "");
        }

        /**
         * setting.soc_target — SOC 保电目标.
         * <p>设置希望车辆保留的电量百分比。
         * <p>读取 Setting.SET_DR_SOC_TARGET (dev=1000, fid=873463848).
         */
        public static SensorValue socTarget() {
            return read(1000, 873463848, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * setting.steering_assist_mode — 转向助力模式.
         * <p>选择舒适或运动转向助力，调整方向盘的操控感受。
         * <p>读取 Setting.SET_DR_ST_ASSIS (dev=1000, fid=616562704).
         */
        public static SensorValue steeringAssistMode() {
            return read(1000, 616562704, 5, false, map(1, "COMFORT", 2, "SPORT"), 1.0, 0.0, "");
        }

        /**
         * setting.steering_wheel_heating — 方向盘加热开关.
         * <p>打开或关闭方向盘加热。
         * <p>读取 Setting.SET_STEERING_WHEEL_HEAT_STATE (dev=1000, fid=1116733454).
         */
        public static SensorValue steeringWheelHeating() {
            return read(1000, 1116733454, 5, false, map(1, "false", 2, "true"), 1.0, 0.0, "");
        }

        /**
         * setting.steering_wheel_heating_level — 方向盘加热档位.
         * <p>调节方向盘加热强度，适用于三档或五档车型。
         * <p>读取 Setting.SET_AUTO_STEER_WHEEL_HEATING_GEAR (dev=1000, fid=1116733465).
         */
        public static SensorValue steeringWheelHeatingLevel() {
            return read(1000, 1116733465, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * setting.ambient_light_brightness — 全车氛围灯亮度.
         * <p>一起调节全车氛围灯亮度，也可将亮度设为零关闭。DiLink 3、4 车机（Android 12 以下）没有氛围灯总开关，打开或关闭氛围灯也使用本能力：关闭设为 0，打开可设为最高亮度 5。
         * <p>读取 Setting.SET_IAL_FRONT_BRIGHTNESS (dev=1000, fid=1121976328).
         */
        public static SensorValue ambientLightBrightness() {
            return read(1000, 1121976328, 5, false, null, 1.0, -1, "");
        }

        /**
         * setting.ambient_light_power — 氛围灯总开关.
         * <p>打开或关闭全车氛围灯，适用于 DiLink 5 及以上车机（Android 12 及以上）；DiLink 3、4 车机不支持，应使用全车氛围灯亮度。
         * <p>读取 Setting.LIGHT_ATMOSPHERE_MAIN_SWITCH (dev=1000, fid=1060110406).
         */
        public static SensorValue ambientLightPower() {
            return read(1000, 1060110406, 5, false, map(1, "OFF", 2, "ON"), 1.0, 0.0, "");
        }

        /**
         * setting.mirror_auto_brightness — 电子后视镜自动亮度.
         * <p>选择电子后视镜自动调节亮度或手动调节亮度。
         * <p>读取 Setting.REAR_VIEW_MIRROR_EXTERNAL_BACKLIGHT_AUTO_ADJUST (dev=1000, fid=1041236000).
         */
        public static SensorValue mirrorAutoBrightness() {
            return read(1000, 1041236000, 5, false, map(1, "AUTO", 2, "MANUAL"), 1.0, 0.0, "");
        }

        /**
         * setting.mirror_brightness — 电子后视镜亮度.
         * <p>调节左侧电子后视镜显示屏亮度。
         * <p>读取 Setting.REAR_VIEW_MIRROR_EXTERNAL_CMS_LEFT_DISPLAY_SCREEN_ADJUST_LIGHT_GEAR (dev=1000, fid=1041235987).
         */
        public static SensorValue mirrorBrightness() {
            return read(1000, 1041235987, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * setting.mirror_reverse_tilt — 倒车后视镜翻转.
         * <p>设置倒车时是否将后视镜翻转至记忆角度。
         * <p>读取 Setting.SET_CAR_REAR_VIEW (dev=1000, fid=1228931096).
         */
        public static SensorValue mirrorReverseTilt() {
            return read(1000, 1228931096, 5, false, map(0, "OFF", 1, "ON"), 1.0, 0.0, "");
        }

        /**
         * setting.suspension_height_lock — 悬架高度锁定.
         * <p>锁定或解除悬架高度锁定。
         * <p>读取 Setting.SETTING_SUSPENSION_HEIGHT_MODE_SWITCH (dev=1000, fid=629145680).
         */
        public static SensorValue suspensionHeightLock() {
            return read(1000, 629145680, 5, false, map(1, "UNLOCKED", 2, "LOCKED"), 1.0, 0.0, "");
        }

        /**
         * setting.suspension_mode — 云辇模式.
         * <p>选择云辇悬架的舒适或运动模式。
         * <p>读取 Setting.SET_DYNAMIC_SUSPENSION_ACTUAL_OPERATING_MODE (dev=1000, fid=938475528).
         */
        public static SensorValue suspensionMode() {
            return read(1000, 938475528, 5, false, map(1, "COMFORT", 2, "SPORT"), 1.0, 0.0, "");
        }

        /**
         * setting.vector_control — 矢量控制模式.
         * <p>选择 CVC 矢量控制的舒适或运动模式。
         * <p>读取 Setting.SETTING_CVC_WORKING_MODE (dev=1000, fid=533725224).
         */
        public static SensorValue vectorControl() {
            return read(1000, 533725224, 5, false, map(1, "comfort", 2, "sport"), 1.0, 0.0, "");
        }

        /**
         * setting.rollover_airbag_suppression — 气囊翻滚抑制.
         * <p>开启或关闭气囊翻滚抑制。
         * <p>读取 Setting.RESCUE_AIRBAG_ROLL_SUPPRESSION_STATUS (dev=1000, fid=146800695).
         */
        public static SensorValue rolloverAirbagSuppression() {
            return read(1000, 146800695, 5, false, map(1, "ON", 2, "OFF"), 1.0, 0.0, "");
        }

        /**
         * setting.sand_abs — 沙地 ABS.
         * <p>开启或关闭沙地 ABS。
         * <p>读取 Setting.SETTING_SAND_ABS_ENABLE_STATUS (dev=1000, fid=572523572).
         */
        public static SensorValue sandAbs() {
            return read(1000, 572523572, 5, false, map(1, "OFF", 2, "ON"), 1.0, 0.0, "");
        }

        /**
         * setting.camping_leveling — 露营调平.
         * <p>开启或关闭露营调平。
         * <p>读取 Setting.SETTING_CAMPING_LEVELING_FUNCTION_SWITCH (dev=1000, fid=629145690).
         */
        public static SensorValue campingLeveling() {
            return read(1000, 629145690, 5, false, map(1, "ON", 2, "OFF"), 1.0, 0.0, "");
        }

        public static final class headlampHeight {
            private headlampHeight() {}

            /**
             * setting.headlamp_height — 前照灯高度.
             * <p>调节前照灯照射高度，并分别查看左右灯的高度档位。
             * <p>变体 frontLeft；读取 feature985661459 (dev=1023, fid=985661459).
             */
            public static SensorValue frontLeft() {
                return read(1023, 985661459, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.headlamp_height — 前照灯高度.
             * <p>调节前照灯照射高度，并分别查看左右灯的高度档位。
             * <p>变体 frontRight；读取 feature986710035 (dev=1023, fid=986710035).
             */
            public static SensorValue frontRight() {
                return read(1023, 986710035, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class rearEntertainmentScreen {
            private rearEntertainmentScreen() {}

            /**
             * setting.rear_entertainment_screen — 后排娱乐屏.
             * <p>打开或关闭后排娱乐屏。
             * <p>变体 ceiling；读取 Setting.MOTOR_OVERHEAD_SCREEN_LOCATION_STATUS_SET (dev=1000, fid=814743574).
             */
            public static SensorValue ceiling() {
                return read(1000, 814743574, 5, false, map(0, "OPEN_0", 1, "OPEN_1", 2, "CLOSED"), 1.0, 0.0, "");
            }

        }

        public static final class seatHeating {
            private seatHeating() {}

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * <p>变体 driver；读取 Ac.AC_MAIN_DRIVE_SEAT_HEATING_LEVEL (dev=1000, fid=702545948).
             */
            public static SensorValue driver() {
                return read(1000, 702545948, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * <p>变体 frontPassenger；读取 Ac.AC_PASSENGER_SEAT_HEATING_LEVEL (dev=1000, fid=711983132).
             */
            public static SensorValue frontPassenger() {
                return read(1000, 711983132, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * <p>变体 rearLeft；读取 Ac.AC_REAR_LEFT_SEAT_HEATING_LEVEL (dev=1000, fid=655360044).
             */
            public static SensorValue rearLeft() {
                return read(1000, 655360044, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_heating — 座椅加热.
             * <p>调节指定座椅的加热档位。
             * <p>变体 rearRight；读取 Ac.AC_REAR_RIGHT_SEAT_HEATING_LEVEL (dev=1000, fid=655360052).
             */
            public static SensorValue rearRight() {
                return read(1000, 655360052, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class seatVentilation {
            private seatVentilation() {}

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * <p>变体 driver；读取 Ac.AC_MAIN_DRIVE_SEAT_VENTILATING_LEVEL (dev=1000, fid=702545944).
             */
            public static SensorValue driver() {
                return read(1000, 702545944, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * <p>变体 frontPassenger；读取 Ac.AC_PASSENGER_SEAT_VENTILATING_LEVEL (dev=1000, fid=711983128).
             */
            public static SensorValue frontPassenger() {
                return read(1000, 711983128, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * <p>变体 rearLeft；读取 Ac.AC_REAR_LEFT_SEAT_VENTILATING_LEVEL (dev=1000, fid=655360040).
             */
            public static SensorValue rearLeft() {
                return read(1000, 655360040, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_ventilation — 座椅通风.
             * <p>调节指定座椅的通风档位。
             * <p>变体 rearRight；读取 Ac.AC_REAR_RIGHT_SEAT_VENTILATING_LEVEL (dev=1000, fid=655360048).
             */
            public static SensorValue rearRight() {
                return read(1000, 655360048, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class seatMassageLevel {
            private seatMassageLevel() {}

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * <p>变体 driver；读取 Setting.SET_FRONT_LEFT_SEAT_MASSAGE_LEVEL (dev=1000, fid=1335885876).
             */
            public static SensorValue driver() {
                return read(1000, 1335885876, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * <p>变体 frontPassenger；读取 Setting.SET_FRONT_RIGHT_SEAT_MASSAGE_LEVEL (dev=1000, fid=1335885884).
             */
            public static SensorValue frontPassenger() {
                return read(1000, 1335885884, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * <p>变体 rearLeft；读取 Setting.SET_REAR_LEFT_SEAT_MASSAGE_LEVEL (dev=1000, fid=1335885860).
             */
            public static SensorValue rearLeft() {
                return read(1000, 1335885860, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * setting.seat_massage_level — 座椅按摩强度.
             * <p>调节指定座椅的按摩强度。
             * <p>变体 rearRight；读取 Setting.SET_REAR_RIGHT_SEAT_MASSAGE_LEVEL (dev=1000, fid=1335885868).
             */
            public static SensorValue rearRight() {
                return read(1000, 1335885868, 5, false, null, 1.0, 0.0, "");
            }

        }

        public static final class seatMassageMode {
            private seatMassageMode() {}

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * <p>变体 driver；读取 Setting.SET_FRONT_LEFT_SEAT_MASSAGE_MODE (dev=1000, fid=1335885872).
             */
            public static SensorValue driver() {
                return read(1000, 1335885872, 5, false, map(1, "0", 2, "1", 3, "2", 4, "3", 5, "4", 6, "5"), 1.0, 0.0, "");
            }

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * <p>变体 frontPassenger；读取 Setting.SET_FRONT_RIGHT_SEAT_MASSAGE_MODE (dev=1000, fid=1335885880).
             */
            public static SensorValue frontPassenger() {
                return read(1000, 1335885880, 5, false, map(1, "0", 2, "1", 3, "2", 4, "3", 5, "4", 6, "5"), 1.0, 0.0, "");
            }

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * <p>变体 rearLeft；读取 Setting.SET_REAR_LEFT_SEAT_MASSAGE_MODE (dev=1000, fid=1335885856).
             */
            public static SensorValue rearLeft() {
                return read(1000, 1335885856, 5, false, map(1, "0", 2, "1", 3, "2", 4, "3", 5, "4", 6, "5"), 1.0, 0.0, "");
            }

            /**
             * setting.seat_massage_mode — 座椅按摩模式.
             * <p>选择指定座椅的按摩模式，或关闭按摩。
             * <p>变体 rearRight；读取 Setting.SET_REAR_RIGHT_SEAT_MASSAGE_MODE (dev=1000, fid=1335885864).
             */
            public static SensorValue rearRight() {
                return read(1000, 1335885864, 5, false, map(1, "0", 2, "1", 3, "2", 4, "3", 5, "4", 6, "5"), 1.0, 0.0, "");
            }

        }

    }

    public static final class shortcutWiper {
        private shortcutWiper() {}

        /**
         * shortcut_wiper.front_speed — 前雨刮速度.
         * <p>仅在前雨刮档位未知时用于判断是否工作。
         * <p>读取 Setting.SETTING_FRONT_RAIN_WIPER_SPEED (dev=1023, fid=1196425250).
         */
        public static SensorValue frontSpeed() {
            return read(1023, 1196425250, 5, false, null, 1.0, 0.0, "");
        }

    }

    public static final class shortcutWiperGear {
        private shortcutWiperGear() {}

        /**
         * shortcut_wiper_gear.front_gear — 前雨刮原始档位.
         * <p>保留原始整数，由宿主判断档位是否有效。
         * <p>读取 Wiper.WIPER_FRONT_WIPER_LEVEL (dev=1046, fid=321912848).
         */
        public static SensorValue frontGear() {
            return read(1046, 321912848, 5, false, null, 1.0, 0.0, "");
        }

    }

    public static final class vehicle {
        private vehicle() {}

        /**
         * vehicle.speed — 当前车速.
         * <p>查看车辆当前速度读数。
         * <p>读取 Speed.SPEED_AUTO_SPEED (dev=1013, fid=303038472).
         */
        public static SensorValue speed() {
            return read(1013, 303038472, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * vehicle.brake_pedal_depth — 制动踏板深度.
         * <p>查看制动踏板踩下的百分比。
         * <p>读取 Speed.SPEED_BRAKE_S (dev=1013, fid=874512400).
         */
        public static SensorValue brakePedalDepth() {
            return read(1013, 874512400, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * vehicle.accelerator_depth — 加速踏板深度.
         * <p>查看加速踏板踩下的百分比。
         * <p>读取 Speed.SPEED_ACCELERATOR_S (dev=1013, fid=874512392).
         */
        public static SensorValue acceleratorDepth() {
            return read(1013, 874512392, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * vehicle.longitudinal_acceleration — 纵向加速度.
         * <p>查看车辆前后方向的加速度，单位为米每二次方秒。
         * <p>读取 Speed.SPEED_ACCELERATE_VALUE (dev=1013, fid=1033220112).
         */
        public static SensorValue longitudinalAcceleration() {
            return read(1013, 1033220112, 7, true, null, 1.0, 0.0, "");
        }

    }

    public static final class statistic {
        private statistic() {}

        /**
         * statistic.battery_soc — 动力电池电量.
         * <p>查看动力电池剩余电量百分比。
         * <p>读取 Statistic.STATISTIC_ELEC_PERCENTAGE (dev=1014, fid=1246777400).
         */
        public static SensorValue batterySoc() {
            return read(1014, 1246777400, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * statistic.total_mileage — 总里程.
         * <p>查看车辆累计行驶里程读数。
         * <p>读取 Statistic.STATISTIC_TOTAL_MILEAGE (dev=1014, fid=1033543696).
         */
        public static SensorValue totalMileage() {
            return read(1014, 1033543696, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * statistic.fuel_percent — 剩余油量.
         * <p>查看油箱剩余燃油百分比。
         * <p>读取 Statistic.STATISTIC_FUEL_PERCENTAGE (dev=1014, fid=1246785600).
         */
        public static SensorValue fuelPercent() {
            return read(1014, 1246785600, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * statistic.total_electric_consumption — 累计电耗.
         * <p>查看车辆累计用电量读数。
         * <p>读取 Statistic.STATISTIC_TOTAL_ELEC_CONSUMPTION (dev=1014, fid=1033199632).
         */
        public static SensorValue totalElectricConsumption() {
            return read(1014, 1033199632, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * statistic.total_fuel_consumption — 累计油耗.
         * <p>查看车辆累计燃油消耗读数。
         * <p>读取 Statistic.STATISTIC_TOTAL_FUEL_CONSUMPTION (dev=1014, fid=1033211920).
         */
        public static SensorValue totalFuelConsumption() {
            return read(1014, 1033211920, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * statistic.coolant_temperature — 冷却液温度.
         * <p>查看发动机冷却液温度读数。
         * <p>读取 Statistic.STATISTIC_WATER_TEMPERATURE (dev=1014, fid=1033199672).
         */
        public static SensorValue coolantTemperature() {
            return read(1014, 1033199672, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * statistic.electric_consumption_per_100km — 平均电耗.
         * <p>查看车辆报告的平均电耗读数。
         * <p>读取 Statistic.STATISTIC_TOTAL_ELEC_CON_PHM (dev=1014, fid=1033195580).
         */
        public static SensorValue electricConsumptionPer100km() {
            return read(1014, 1033195580, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * statistic.low_voltage_battery_voltage — 低压电池电压.
         * <p>查看低压蓄电池电压读数。
         * <p>读取 Ota.OTA_BATTERY_POWER_VOLTAGE (dev=1032, fid=1196425280).
         */
        public static SensorValue lowVoltageBatteryVoltage() {
            return read(1032, 1196425280, 7, true, null, 1.0, 0.0, "");
        }

        /**
         * statistic.battery_capacity — 动力电池容量.
         * <p>查看车辆报告的动力电池容量读数。
         * <p>读取 Bodywork.BODYWORK_BATTERY_CAPACITY (dev=1001, fid=1146095632).
         */
        public static SensorValue batteryCapacity() {
            return read(1001, 1146095632, 7, false, null, 0.1, 0.0, "");
        }

        /**
         * statistic.battery_health — 电池健康参考值.
         * <p>查看动力电池健康度参考百分比。
         * <p>读取 Statistic.STATISTIC_BATTERY_HEALTHY_INDEX (dev=1014, fid=1145045032).
         */
        public static SensorValue batteryHealth() {
            return read(1014, 1145045032, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * statistic.electric_range — 纯电剩余续航.
         * <p>查看剩余电量预计可行驶的公里数。
         * <p>读取 Statistic.STATISTIC_ELEC_DRIVING_RANGE (dev=1014, fid=1246765118).
         */
        public static SensorValue electricRange() {
            return read(1014, 1246765118, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * statistic.fuel_range — 燃油剩余续航.
         * <p>查看剩余燃油预计可行驶的公里数。
         * <p>读取 Statistic.STATISTIC_FUEL_DRIVING_RANGE (dev=1014, fid=1246773304).
         */
        public static SensorValue fuelRange() {
            return read(1014, 1246773304, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * statistic.electric_consumption_last_200m — 近 200 米平均电耗.
         * <p>查看最近 200 米的平均电耗，单位为千瓦时每百公里。
         * <p>读取 Statistic.STATISTICS_AVERAGE_INSTANT_EV_LAST_200M (dev=1014, fid=1032871952).
         */
        public static SensorValue electricConsumptionLast200m() {
            return read(1014, 1032871952, 7, false, null, 0.1, 0.0, "");
        }

        /**
         * statistic.fuel_consumption_last_200m — 近 200 米平均油耗.
         * <p>查看最近 200 米的平均油耗，单位为升每百公里。
         * <p>读取 Statistic.STATISTICS_AVERAGE_INSTANT_FUEL_LAST_200M (dev=1014, fid=1032871964).
         */
        public static SensorValue fuelConsumptionLast200m() {
            return read(1014, 1032871964, 7, true, null, 1.0, 0.0, "");
        }

        public static final class batteryTemperature {
            private batteryTemperature() {}

            /**
             * statistic.battery_temperature — 电芯温度.
             * <p>查看电芯最高、平均或最低温度，单位为摄氏度。
             * <p>变体 max；读取 Statistic.STATISTIC_HIGHEST_BATTERY_TEMP (dev=1014, fid=1148190752).
             */
            public static SensorValue max() {
                return read(1014, 1148190752, 5, false, null, 1.0, -40, "");
            }

            /**
             * statistic.battery_temperature — 电芯温度.
             * <p>查看电芯最高、平均或最低温度，单位为摄氏度。
             * <p>变体 average；读取 Statistic.STATISTIC_AVERAGE_BATTERY_TEMP (dev=1014, fid=1148190776).
             */
            public static SensorValue average() {
                return read(1014, 1148190776, 5, false, null, 1.0, -40, "");
            }

            /**
             * statistic.battery_temperature — 电芯温度.
             * <p>查看电芯最高、平均或最低温度，单位为摄氏度。
             * <p>变体 min；读取 Statistic.STATISTIC_LOWEST_BATTERY_TEMP (dev=1014, fid=1148190736).
             */
            public static SensorValue min() {
                return read(1014, 1148190736, 5, false, null, 1.0, -40, "");
            }

        }

        public static final class batteryVoltage {
            private batteryVoltage() {}

            /**
             * statistic.battery_voltage — 电芯电压.
             * <p>查看电芯最高或最低电压，单位为伏特。
             * <p>变体 max；读取 Statistic.STATISTIC_HIGHEST_BATTERY_VOLTAGE (dev=1014, fid=1147142192).
             */
            public static SensorValue max() {
                return read(1014, 1147142192, 7, false, null, 0.001, 0.0, "");
            }

            /**
             * statistic.battery_voltage — 电芯电压.
             * <p>查看电芯最高或最低电压，单位为伏特。
             * <p>变体 min；读取 Statistic.STATISTIC_LOWEST_BATTERY_VOLTAGE (dev=1014, fid=1147142160).
             */
            public static SensorValue min() {
                return read(1014, 1147142160, 7, false, null, 0.001, 0.0, "");
            }

        }

    }

    public static final class tyre {
        private tyre() {}

        public static final class pressure {
            private pressure() {}

            /**
             * tyre.pressure — 轮胎压力.
             * <p>查看指定车轮的胎压读数。
             * <p>变体 leftFront；读取 Tyre.TYRE_PRESSURE_VALUE_LEFT_FRONT (dev=1016, fid=-1728052956).
             */
            public static SensorValue leftFront() {
                return read(1016, -1728052956, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * tyre.pressure — 轮胎压力.
             * <p>查看指定车轮的胎压读数。
             * <p>变体 rightFront；读取 Tyre.TYRE_PRESSURE_VALUE_RIGHT_FRONT (dev=1016, fid=-1728052952).
             */
            public static SensorValue rightFront() {
                return read(1016, -1728052952, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * tyre.pressure — 轮胎压力.
             * <p>查看指定车轮的胎压读数。
             * <p>变体 leftRear；读取 Tyre.TYRE_PRESSURE_VALUE_LEFT_REAR (dev=1016, fid=-1728052948).
             */
            public static SensorValue leftRear() {
                return read(1016, -1728052948, 5, false, null, 1.0, 0.0, "");
            }

            /**
             * tyre.pressure — 轮胎压力.
             * <p>查看指定车轮的胎压读数。
             * <p>变体 rightRear；读取 Tyre.TYRE_PRESSURE_VALUE_RIGHT_REAR (dev=1016, fid=-1728052944).
             */
            public static SensorValue rightRear() {
                return read(1016, -1728052944, 5, false, null, 1.0, 0.0, "");
            }

        }

    }

    public static final class wiper {
        private wiper() {}

        /**
         * wiper.relay_state — 雨刮继电器状态.
         * <p>查看雨刮继电器是否吸合。
         * <p>读取 Wiper.WIPER_RELAY_STATE (dev=1046, fid=1336934438).
         */
        public static SensorValue relayState() {
            return read(1046, 1336934438, 5, false, map(1, "ACTUATION", 2, "DISCONNECT"), 1.0, 0.0, "");
        }

        /**
         * wiper.sensitivity — 雨刮灵敏度.
         * <p>查看雨刮当前灵敏度档位。
         * <p>读取 Wiper.WIPER_WINDSCREEN_WIPER_SENSITIVITY (dev=1046, fid=321912864).
         */
        public static SensorValue sensitivity() {
            return read(1046, 321912864, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * wiper.front_wiper_level — 前雨刮档位.
         * <p>查看前雨刮当前工作档位。
         * <p>读取 Wiper.WIPER_FRONT_WIPER_LEVEL (dev=1046, fid=321912848).
         */
        public static SensorValue frontWiperLevel() {
            return read(1046, 321912848, 5, false, null, 1.0, 0.0, "");
        }

        /**
         * wiper.automatic — 自动雨刮.
         * <p>开启或关闭自动感应雨刮。
         * <p>读取 Setting.SET_AUTO_RAIN_WIPER_SWITCH (dev=1023, fid=321912862).
         */
        public static SensorValue automatic() {
            return read(1023, 321912862, 5, false, map(1, "ON", 2, "OFF"), 1.0, 0.0, "");
        }

        /**
         * wiper.service_mode — 雨刮检修模式.
         * <p>开启或退出雨刮检修模式，便于更换雨刮片。
         * <p>读取 Setting.SET_FRONT_WINDSCREEN_WIPER_OVERHAUL_STATE (dev=1000, fid=1196425244).
         */
        public static SensorValue serviceMode() {
            return read(1000, 1196425244, 5, false, map(1, "ON", 2, "OFF"), 1.0, 0.0, "");
        }

        public static final class resetState {
            private resetState() {}

            /**
             * wiper.reset_state — 雨刮复位状态.
             * <p>查看前雨刮或后雨刮是否已经回到复位位置。
             * <p>变体 frontWindshield；读取 Wiper.WIPER_AREA_FRONT_STATE (dev=1046, fid=1196425224).
             */
            public static SensorValue frontWindshield() {
                return read(1046, 1196425224, 5, false, map(1, "NOT_RESET", 2, "RESET"), 1.0, 0.0, "");
            }

            /**
             * wiper.reset_state — 雨刮复位状态.
             * <p>查看前雨刮或后雨刮是否已经回到复位位置。
             * <p>变体 rearWindshield；读取 Wiper.WIPER_AREA_REAR_STATE (dev=1046, fid=1196425226).
             */
            public static SensorValue rearWindshield() {
                return read(1046, 1196425226, 5, false, map(1, "NOT_RESET", 2, "RESET"), 1.0, 0.0, "");
            }

        }

    }

}
