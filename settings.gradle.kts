enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.1.0"
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
    }
}

rootProject.name = "WorldGuardRegionProtect"
include (
        "wgrp-api",
        "wgrp-common",
        "wgrp-paper")
