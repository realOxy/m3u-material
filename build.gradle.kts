
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

group = "com.oxy"
version = "1.0-SNAPSHOT"


android {
    namespace = "com.oxy.material"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.activity.activity.compose)

    implementation(libs.androidx.lifecycle.lifecycle.runtime.ktx)

    api(libs.bundles.androidx.compose)
    api(libs.androidx.compose.material.material.icons.extended)
    api(libs.androidx.compose.material3.material3)
    debugApi(libs.androidx.compose.ui.ui.tooling)
    debugApi(libs.androidx.compose.ui.ui.tooling.preview)

    api(libs.io.coil.kt.coil)
    api(libs.io.coil.kt.coil.compose)

    implementation(libs.com.airbnb.android.lottie.compose)
}


kotlin {
    jvmToolchain(8)
}