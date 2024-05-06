plugins {
    id("java-library")
    id("jacoco")
    kotlin("jvm")
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

    //Libs
    implementation("ninja.leaping.configurate:configurate-yaml:3.7.1")
    compileOnly ("org.slf4j:slf4j-api:1.7.30")
    compileOnly ("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    jacocoTestReport {
        dependsOn.toMutableSet() to test
    }
}

kotlin {
    jvmToolchain(21)
}
