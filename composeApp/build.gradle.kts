import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.room.sqlite.wrapper)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.gson)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        val commonMain by getting {
            resources.srcDirs("src/commonMain/composeResources")
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}
dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    debugImplementation(compose.uiTooling)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.example.project"
}

// Ensure KSP depends on Compose resource generation for iOS targets
listOf(
    "kspKotlinIosSimulatorArm64",
    "kspKotlinIosX64",
    "kspKotlinIosArm64"
).forEach { taskName ->
    tasks.matching { it.name == taskName }.configureEach {
        dependsOn(
            "generateComposeResClass",
            "generateExpectResourceCollectorsForCommonMain",
            "generateResourceAccessorsForAppleMain",
            "generateResourceAccessorsForNativeMain",
            "generateResourceAccessorsForIosMain",
            "generateResourceAccessorsForCommonMain"
        )
        // Target-specific accessors when present
        when (taskName) {
            "kspKotlinIosSimulatorArm64" -> dependsOn(
                "generateResourceAccessorsForIosSimulatorArm64Main",
                "generateActualResourceCollectorsForIosSimulatorArm64Main"
            )
            "kspKotlinIosX64" -> dependsOn(
                "generateResourceAccessorsForIosX64Main",
                "generateActualResourceCollectorsForIosX64Main"
            )
            "kspKotlinIosArm64" -> dependsOn(
                "generateResourceAccessorsForIosArm64Main",
                "generateActualResourceCollectorsForIosArm64Main"
            )
        }
    }
}

// Ensure KSP depends on Compose resource generation for Android variants
listOf(
    "kspDebugKotlinAndroid",
    "kspReleaseKotlinAndroid"
).forEach { taskName ->
    tasks.matching { it.name == taskName }.configureEach {
        dependsOn(
            "generateComposeResClass",
            "generateExpectResourceCollectorsForCommonMain",
            "generateResourceAccessorsForCommonMain",
            "generateResourceAccessorsForAndroidMain",
            "generateActualResourceCollectorsForAndroidMain"
        )
        if (taskName == "kspDebugKotlinAndroid") {
            dependsOn("generateResourceAccessorsForAndroidDebug")
        }
    }
}
