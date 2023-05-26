plugins {
    id(libs.plugins.jetBrains.kotlin.android.get().pluginId)
    id(libs.plugins.agp.application.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.navSafeArgs.get().pluginId)
    id(libs.plugins.hilt.android.get().pluginId)
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = ("com.example.notesm")
    compileSdk = config.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = ("com.example.notesm")
        minSdk = config.versions.minSdk.get().toInt()
        targetSdk = config.versions.targetSdkVersion.get().toInt()
        versionCode = config.versions.versionCode.get().toInt()
        versionName = config.versions.versionName.get()

        testInstrumentationRunner = config.versions.testInstrumentationRunner.get()
    }

    buildTypes {
        getByName(config.versions.debugBuildType.get()) {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName(config.versions.releaseBuildType.get()) {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(config.versions.getDefaultProguardFileOptimizeTxt.get()),
                config.versions.getDefaultProguardFileRules.get()
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = kotlinJvmOptions.versions.kotlinJvmTargetOptions.get()
    }
    viewBinding.isEnabled = true
}

dependencies {

    // UI
    implementation(libs.ui.constraint)

    // Navigation
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    implementation(libs.ui.material)
    implementation(libs.ui.glide)

    // Coroutines
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.room.room.ktx)
    kapt(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ViewBinding
    implementation(libs.ui.viewBindingPropertyDelegate)

    //Peko
    implementation(libs.pekoAsync.pekoAsync)

}