plugins {
    java
    `maven-publish`
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {languageVersion.set(JavaLanguageVersion.of(17))}
    }
}

group = "net.ritasister.srp"
version = "0.7.1-pre1"
description = "WorldGuardRegionProtect"

repositories {
    mavenCentral()
    //WorldGuard
    maven {url = uri("https://maven.enginehub.org/repo/")}
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
}