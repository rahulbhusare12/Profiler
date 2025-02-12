// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.dagger.hilt) apply false
   // alias(li)
   //id("com.google.dagger.hilt.android") version "2.43.2" apply false
}
