plugins {
    id("java-library")
    id("jacoco")
}

repositories {
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        name = "EngineHub"
        url = uri("https://maven.enginehub.org/repo/")
    }
    mavenCentral()
}

dependencies {

    api (project(":wgrp-api"))
    api ("org.checkerframework:checker-qual:3.33.0")

    //Kyori and MiniMessage
    api("net.kyori:adventure-api:4.11.0") {
        exclude(module = "adventure-bom")
        exclude(module = "checker-qual")
        exclude(module = "annotations")
    }
    api("net.kyori:adventure-text-minimessage:4.11.0") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
    }

    api("com.google.code.gson:gson:2.8.9")
    api("com.google.code.gson:gson:2.8.9")

    //Paper
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    //RSLibs by RSTeamCore
    implementation("net.rsteamcore:RSLibs-api:0.0.6")

    //Plugins
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")

    //Libs
    implementation("ninja.leaping.configurate:configurate-yaml:3.7.1")
    compileOnly ("org.slf4j:slf4j-api:1.7.30")
    compileOnly ("org.apache.logging.log4j:log4j-api:2.17.1")

    //HikariCP
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("org.bstats:bstats-bukkit:3.0.2")
    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")
    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20-RC")
    //Annotations
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("org.projectlombok:lombok:1.18.26")
    implementation("aopalliance:aopalliance:1.0")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks {
    test {
        useJUnitPlatform()
    }

    jacocoTestReport {
        dependsOn.toMutableSet() to test
    }
}
