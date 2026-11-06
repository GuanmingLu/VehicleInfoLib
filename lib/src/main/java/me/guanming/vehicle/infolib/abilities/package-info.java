/**
 * 由 APK 内置能力目录（{@code assets/byd/devices}，revision 2026.10.27.1）自动展开的车辆能力命名空间。
 *
 * <p>本包不修改、也不复制原
 * {@link me.guanming.vehicle.infolib.Sensor} / {@link me.guanming.vehicle.infolib.Control}：
 * 每个方法直接调用 {@link me.guanming.vehicle.infolib.Vehicle#read(int, int, int)} 或
 * {@link me.guanming.vehicle.infolib.Vehicle#write(int, int, int)}。
 *
 * <p>API 形状：
 * <pre>
 *   // 读取：无参数，返回原 SensorValue
 *   SensorValue ac = me.guanming.vehicle.infolib.abilities.Sensor.ac.compressor();
 *   SensorValue w  = me.guanming.vehicle.infolib.abilities.Sensor.bodywork.window.position.driver();
 *
 *   // 控制：返回 boolean；布尔能力同时提供 int / boolean 重载
 *   boolean ok1 = me.guanming.vehicle.infolib.abilities.Control.ac.compressor(1);
 *   boolean ok2 = me.guanming.vehicle.infolib.abilities.Control.ac.compressor(true);
 *   boolean ok3 = me.guanming.vehicle.infolib.abilities.Control.bodywork.window.position.driver(50);
 * </pre>
 *
 * <p>变体（区域、侧别、音响系统等）通过调用链区分；参数为原始整数，
 * 具体枚举值见各方法 Javadoc。
 *
 * <p>生成规则：跳过 observe-only 能力，跳过所有特征都缺少数字 featureId 的能力；
 * 其余能力全部加入。当前共生成 157 项读取能力、114 项控制能力（同一能力可能同时具备读/控），
 * 23 项因上述规则跳过（5 项纯 observe、18 项缺少数字 featureId）。
 */
package me.guanming.vehicle.infolib.abilities;
