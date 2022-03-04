enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "WorldGuardRegionProtect"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven ("https://repo.codemc.org/repository/maven-public")
        //WorldGuard
        maven ("https://maven.enginehub.org/repo/")
        //PaperMC
        maven ("https://papermc.io/repo/repository/maven-public/")
    }
}

include("wgrp-api")
include("wgrp-bukkit")
