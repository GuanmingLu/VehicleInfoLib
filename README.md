# VehicleInfoLib

一个用于读取与控制比亚迪车机车辆数据的 Android 库。通过本机无线 ADB 拉起一个持有 `shell` 权限的守护进程，再经由 Binder IPC 调用 BYD autoservice 协议，实现对车辆传感器字段的读取与控制动作的写入。

> ⚠️ **适配声明**
>
> 本库**仅针对初代钛7顶配（34 控制器）**进行适配与验证。其他车型（不同控制器数量、不同 ECU 配置、不同协议版本）的字段 ID（fid）、设备号（device）、事务码（transact）及枚举映射可能存在差异，**需自行测试与调整**。作者不保证在其他车型上的可用性，也不对因使用本库造成的任何后果负责。

## 工作原理

```
┌─────────────┐   ADB(127.0.0.1:5555)   ┌──────────────────────┐
│  宿主 App    │ ─────────────────────▶ │  VehicleDaemon        │
│ (普通权限)   │   拉起 app_process      │  (shell 权限进程)      │
│             │ ◀───────────────────── │  注册 binder 服务      │
│  Vehicle    │   Binder IPC            │  vehicleinfo_helper   │
│  Sensor     │                         │                       │
│  Control    │                         │  ↓ BYD autoservice    │
└─────────────┘                         │  transact 5/7/9 读    │
                                        │  transact 6     写    │
                                        └──────────────────────┘
```

1. **解除隐藏 API 限制**：通过 [HiddenApiBypass](https://github.com/LSPosed/AndroidHiddenApiBypass) 反射调用 `ServiceManager.getService/addService`。
2. **建立 ADB 连接**：连接车机本机 `127.0.0.1:5555`，使用 RSA 持久化密钥完成认证。
3. **拉起守护进程**：通过 ADB shell 以 `app_process` 启动 `VehicleDaemon`，进程名 `vehicleinfo_helper`，持有 shell 权限。
4. **Binder IPC**：daemon 注册名为 `vehicleinfo_helper` 的 binder 服务，宿主 App 通过该服务发起读写请求。
5. **BYD autoservice 协议**：daemon 内部通过 `transact(5/7/9)` 读取 INT/FLOAT/STRING 字段，通过 `transact(6)` 写入控制值。

## 核心特性

- **97 个传感器读取方法**：车速、挡位、SOC、电压、温度、里程、油耗、电耗、ADAS 状态等，封装在 `Sensor` 类。
- **138 个控制写入方法**：车窗、车门、空调、座椅、灯光、ADAS 开关等，封装在 `Control` 类。
- **统一解码**：`Sensor` 方法返回 `SensorValue`，同时包含原始值（raw）、解码值（value）、显示字符串（str），避免重复解码。
- **无需传入 Context**：`init` 时保存 Context，后续 `read`/`write`/`Sensor`/`Control` 调用均复用，简化调用方代码。
- **动态加载友好**：自动解析 daemon 的 CLASSPATH，支持将 dex 动态加载到第三方 App 的场景。

## 快速开始

### 1. 添加依赖

将 `vehicle-infolib.jar` 放入 App 的 `app/libs/` 目录，并在 `build.gradle.kts` 中引用：

```kotlin
dependencies {
    implementation(files("libs/vehicle-infolib.jar"))
    // HiddenApiBypass（必需，用于解除隐藏 API 限制）
    implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")
}
```

### 2. 初始化

在调用任何读写方法前，必须先初始化一次（幂等，重复调用直接返回成功）：

```java
import me.guanming.vehicle.infolib.Vehicle;
import me.guanming.vehicle.infolib.VehicleLogger;

// 不带 logger
Vehicle.InitResult r = Vehicle.init(context);
if (!r.success) {
    Log.e("TAG", "init failed: " + r.message);
    return;
}

// 或带 logger（可选，用于排查问题）
Vehicle.InitResult r = Vehicle.init(context, (level, tag, msg, t) -> {
    switch (level) {
        case "E": Log.e(tag, msg, t); break;
        case "W": Log.w(tag, msg);    break;
        case "I": Log.i(tag, msg);    break;
        case "D": Log.d(tag, msg);    break;
    }
});
```

**前提条件**：车机已开启无线 ADB（本机 `127.0.0.1:5555` 可连）。普通 App 通过本机回环连接自身 ADB 即可获得 shell 权限的 daemon。

### 3. 读取传感器（Sensor）

```java
import me.guanming.vehicle.infolib.Sensor;
import me.guanming.vehicle.infolib.SensorValue;

SensorValue speed = Sensor.speed();
// speed.raw   = 1115684864   (IEEE 754 位模式)
// speed.value = 60.0
// speed.str   = "60 km/h"

SensorValue gear = Sensor.gear();
// gear.raw   = 4
// gear.value = 4.0
// gear.str   = "D"

SensorValue soc = Sensor.soc();
// soc.str = "78.5%"

// 枚举全部字段元数据（便于 UI 展示）
for (Sensor.Field f : Sensor.ALL) {
    SensorValue v = Sensor.read(f);
    System.out.println(f.displayName + ": " + v.str);
}
```

`SensorValue` 字段说明：

| 字段    | 类型      | 说明                                              |
|---------|-----------|---------------------------------------------------|
| `raw`   | `Integer` | 原始 int 值（FLOAT 字段为 IEEE 754 位模式），`null` 表示读取失败 |
| `value` | `Double`  | 解码后的数值，`null` 表示无效（sentinel/越界/NaN）   |
| `str`   | `String`  | 显示字符串（含单位或枚举名）                        |

### 4. 写入控制（Control）

```java
import me.guanming.vehicle.infolib.Control;

// 无参方法：唯一写入值已作为默认值
boolean ok = Control.acOn();              // 空调-开（默认 value=1）
Control.windowDriverOpen();               // 主驾窗-开（默认 value=1）

// 带参方法：需显式传入值
Control.acTempMain(24);                   // 主驾温度 24℃（范围 16~30）
Control.windowDriverPos(50);              // 主驾窗位置 50%（无默认值）
```

所有写入方法返回 `boolean`，`true` 表示写入成功（status ≥ 0）。**本库不对写入值做校验，也不回读，请谨慎使用控制类方法。**

## API 概览

### `Vehicle`（底层入口）

| 方法 | 说明 |
|------|------|
| `Vehicle.init(Context)` | 初始化（不带 logger） |
| `Vehicle.init(Context, VehicleLogger)` | 初始化（带 logger） |
| `Vehicle.read(dev, fid, transact)` | 读取原始 int 值（不解码） |
| `Vehicle.write(dev, fid, value)` | 写入控制值 |

### `Sensor`（97 个字段）

按类别划分：

- **动力与速度**：车速、挡位、功率、油门/刹车开度、驾驶模式、工作模式、能量状态、电源挡位、ACC 巡航等
- **能源与续航**：油量、SOC、12V 电压、单体电压、电池温度、耗电量/油耗、续航里程、充电状态等
- **里程信息**：总里程、小计里程、EV/HEV 里程、本次行程里程/电耗/油耗/时长等
- **空调与温控**：AC 状态、主副驾温度、风量、循环模式、内外循环等
- **车身与门窗**：四门状态、四窗位置、后视镜、后备箱、引擎盖、天窗等
- **灯光与信号**：近/远光、转向灯、雾灯、日行灯、双闪等
- **座椅与安全**：座椅加热/通风、安全带状态等
- **ADAS**：ACC、LKA、TJA、APA、自动泊车等状态

### `Control`（138 个动作）

按类别划分：

- **空调与除霜**：AC 开关、温度设定、风量、循环模式、前后除霜等
- **车窗与车门**：四窗开/关/位置、四门开/关、后备箱开/关等
- **座椅**：座椅加热/通风/记忆等
- **灯光**：近/远光、转向灯、雾灯、双闪等
- **后视镜**：折叠/展开等
- **ADAS**：ACC 开关、LKA 开关等

## 构建

### 环境要求

- JDK 17
- Android SDK（compileSdk 35，minSdk 24）
- Android Build Tools 35.0.0（含 `d8`）

### 编译 jar 与 dex

```bash
cd VehicleInfoLib

JAVAC=javac    # JDK 17
JAR=jar
D8=$ANDROID_HOME/build-tools/35.0.0/d8

# 1. 编译 .java -> .class
rm -rf build/classes && mkdir -p build/classes
find lib/src/main/java -name "*.java" > sources.txt
$JAVAC -source 17 -target 17 \
    -classpath "$ANDROID_HOME/platforms/android-35/android.jar:libs/hiddenapibypass-4.3.jar" \
    -d build/classes @sources.txt

# 2. 打包 jar
mkdir -p build/outputs/jar
( cd build/classes && $JAR cf ../outputs/jar/vehicle-infolib.jar me/ )

# 3. 生成 dex（合并 lib + hiddenapibypass）
mkdir -p build/outputs/dex
$D8 --min-api 24 --output build/outputs/dex \
    build/outputs/jar/vehicle-infolib.jar libs/hiddenapibypass-4.3.jar
```

产物：

- `build/outputs/jar/vehicle-infolib.jar` — 供 App 编译期引用
- `build/outputs/dex/classes.dex` — 供动态加载场景使用

## 目录结构

```
VehicleInfoLib/
├── lib/
│   ├── src/main/java/me/guanming/vehicle/infolib/
│   │   ├── Vehicle.java          # 入口：init / read / write
│   │   ├── Sensor.java           # 97 个字段读取 + 解码
│   │   ├── SensorValue.java      # 读取结果（raw/value/str）
│   │   ├── Control.java          # 138 个控制动作写入
│   │   ├── VehicleProtocol.java  # Binder 协议常量
│   │   ├── VehicleDaemon.java    # shell 权限守护进程
│   │   ├── VehicleAdbClient.java # ADB 协议客户端
│   │   ├── VehicleAdbKeyStore.java # RSA 密钥持久化
│   │   └── VehicleLogger.java    # 日志回调接口
│   └── build.gradle.kts
├── libs/
│   └── hiddenapibypass-4.3.jar
└── build.gradle.kts
```

## 注意事项

1. **无线 ADB 必须开启**：daemon 通过本机 ADB 拉起，若 `127.0.0.1:5555` 不可连，初始化会失败。
2. **shell 权限**：daemon 以 shell 权限运行，可访问 `ServiceManager` 与 BYD autoservice。宿主 App 本身无需 root。
3. **写入风险**：控制类方法直接写入车辆 ECU，**不做任何校验**。错误写入可能导致车辆异常行为，请充分了解每个字段的含义与取值范围后再使用。
4. **车型差异**：本库的字段 ID、设备号、事务码基于**初代钛7顶配（34 控制器）**抓包适配。其他车型需自行抓包比对，修改 `Sensor` / `Control` 中的常量定义。
5. **动态加载**：若将 dex 动态加载到第三方 App（非静态编译），库会自动将 daemon 所需的 dex 推送到 `/data/local/tmp/` 并拼接 CLASSPATH，无需额外处理。

## 许可证

本项目仅供学习与研究使用。使用本库读取或控制车辆数据时，请遵守当地法律法规，并自行承担相关风险。
