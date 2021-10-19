plugins {
    kotlin("jvm") version "1.5.31"
    java
}

group = "net.ritasister.srp"
version = "0.7.1-pre1"
description = "WorldGuardRegionProtect"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven {url = uri("https://maven.enginehub.org/repo/")}
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly ("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}