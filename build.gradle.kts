plugins {
    id("com.android.application") version "8.0.2"
    kotlin("android") version "1.9.0"
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.example.fractalapp"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    namespace = "com.example.fractalapp" // Add this line
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
}
