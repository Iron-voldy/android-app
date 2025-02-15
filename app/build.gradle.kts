plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services") // Apply the Google Services plugin here

}

android {
    namespace = "com.example.shopease"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shopease"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Firebase dependencies
    implementation("com.google.firebase:firebase-auth:22.0.0")
    implementation("com.google.firebase:firebase-database:20.2.0")

    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:1.0.5")
    implementation("androidx.compose.material:material:1.0.5")
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")
    implementation(libs.androidx.ui.tooling.preview.android)  // Add this line for tooling support (Preview)
    debugImplementation("androidx.compose.ui:ui-tooling:1.0.5")  // For debugging and Preview

    // Other dependencies
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Core Android libraries
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    // Jetpack Compose libraries
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.material3:material3:1.0.0-alpha01")

    // AppCompat for backward compatibility
    implementation("androidx.appcompat:appcompat:1.3.1")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")
}
