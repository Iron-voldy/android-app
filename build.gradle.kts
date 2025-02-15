// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()  // Ensure google repository is added
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")  // Ensure this matches your project’s gradle version
        classpath("com.google.gms:google-services:4.3.10")  // Add this line to include Google Services plugin

    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
