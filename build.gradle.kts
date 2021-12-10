plugins {
    java
    `maven-publish`
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {languageVersion.set(JavaLanguageVersion.of(16))}
    }
}

group = "net.ritasister.srp"
version = "0.7.1-pre2"
description = "WorldGuardRegionProtect"

repositories {
    mavenCentral()
    //WorldGuard
    maven {url = uri("https://maven.enginehub.org/repo/")}
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
}

dependencies {
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
    //Paper 1.17.1
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    //try to support old verions Paper 1.12.2
    //compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
    //compileOnly("com.sk89q.worldguard:worldguard-legacy:6.2")
    //compileOnly("com.sk89q.worldedit:worldedit-bukkit:6.1.4-SNAPSHOT")
}