plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
}
//
//buildscript {
//
//    repositories {
//        gradlePluginPortal()
//        google()
//        mavenCentral()
//    }
//
//    dependencies {
//        classpath(libs.kotlin.gradle.plugin)
//        classpath(libs.gradle)
//    }
//}
//
//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
