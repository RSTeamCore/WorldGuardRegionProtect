pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.23"
    }
}

rootProject.name = "WorldGuardRegionProtect"

include (
        "wgrp-api",
        "wgrp-common",
        "wgrp-paper")
