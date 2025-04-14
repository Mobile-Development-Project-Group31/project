// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kapt) apply false
    


    // Firebase
    id("com.google.gms.google-services") version "4.4.2" apply false

    // NEW: Add these plugins for navigation and storage
    id("androidx.navigation.safeargs.kotlin") version "2.8.9" apply false


}