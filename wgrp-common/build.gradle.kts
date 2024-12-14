plugins {
    id("jacoco")
}

repositories {
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    mavenCentral()
}

dependencies {
    api (project(":wgrp-api"))
    api("org.checkerframework:checker-qual:3.42.0")

    api("net.kyori:adventure-api:4.17.0") {
        exclude(module = "adventure-bom")
        exclude(module = "checker-qual")
        exclude(module = "annotations")
    }
    api("net.kyori:adventure-text-minimessage:4.17.0") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
    }

    api("com.google.code.gson:gson:2.10")
    api("com.google.code.gson:gson:2.10")

    //Libs
    compileOnly("org.jetbrains:annotations:24.1.0")
    compileOnly("org.slf4j:slf4j-api:2.0.12")
    compileOnly("org.apache.logging.log4j:log4j-api:3.0.0-beta1")
    implementation("com.zaxxer:HikariCP:2.3.2")
    implementation(dependencyNotation = "ninja.leaping.configurate:configurate-yaml:3.7.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0-RC2")
}

tasks {
    test {
        useJUnitPlatform()
        /*testLogging {
            exceptionFormat = "full"
        }*/
    }

    jacocoTestReport {
        dependsOn.toMutableSet() to test
    }
}
