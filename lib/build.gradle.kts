plugins {
    id("com.android.library")
}

android {
    namespace = "me.guanming.vehicle.infolib"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // HiddenApiBypass：解除隐藏 API 限制（反射 ServiceManager.getService/addService）
    implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")

    // hiddenapibypass 传递依赖 kotlin-stdlib 1.8.22 与 kotlin-stdlib-jdk7/jdk8 1.6.21 冲突，
    // 统一锁定为 1.8.22 以消除 "Duplicate class" 错误。
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    }
}

// 拷贝 release 和 debug 的 classes.jar 到 build/outputs/jar/，方便外部引用
tasks.register<Copy>("exportJar") {
    group = "build"
    description = "合并 debug/release classes.jar 到 build/outputs/jar/vehicle-infolib.jar"
    from(
        layout.buildDirectory.dir("intermediates/aar_main_jar/debug/classes.jar"),
        layout.buildDirectory.dir("intermediates/aar_main_jar/release/classes.jar")
    )
    into(layout.buildDirectory.dir("outputs/jar"))
    rename { _ -> "vehicle-infolib.jar" }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

// assemble 完成后自动 exportJar
tasks.named("assemble") {
    finalizedBy("exportJar")
}
