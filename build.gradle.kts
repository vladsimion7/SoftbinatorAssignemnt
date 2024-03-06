// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    repositories{
        google()
        mavenCentral()
    }
    dependencies{
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
}