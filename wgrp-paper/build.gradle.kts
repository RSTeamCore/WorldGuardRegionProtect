import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.runpaper.task.RunServer
import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
    alias(libs.plugins.shadow)
    alias(libs.plugins.runPaper)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "PaperMC"
    }
    maven("https://maven.enginehub.org/repo/") {
        name = "EngineHub"
    }
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        name = "PlaceholderApi"
    }
    mavenCentral()
}

paperweight.reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    implementation(project(":wgrp-common"))

    //Paper
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    //Plugins
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.3") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
        exclude(module = "adventure-nbt")
    }

    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-core:7.3.9")
    compileOnly("me.clip:placeholderapi:2.11.6")

    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("org.jetbrains:annotations:24.1.0")

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("it.unimi.dsi:fastutil:8.5.15")
}

configurations.all {
    resolutionStrategy {
        force("com.google.code.gson:gson:2.11.0")
        force("com.google.guava:guava:33.3.1-jre")
        force("it.unimi.dsi:fastutil:8.5.15")

        eachDependency {
            if (requested.group == "com.google.code.gson" && requested.name == "gson") {
                useVersion("2.11.0")
            }
            if (requested.group == "com.google.guava" && requested.name == "guava") {
                useVersion("33.3.1-jre")
            }
            if (requested.group == "it.unimi.dsi" && requested.name == "fastutil") {
                useVersion("8.5.15")
            }
        }
    }
}

tasks.withType<ProcessResources> {
    filteringCharset = Charsets.UTF_8.name()
    filesMatching("plugin.yml") {
        expand(
                "name" to rootProject.name,
                "version" to project.version,
                "group" to project.group,
                "author" to project.property("author"),
                "contributor" to project.property("contributor"),
                "description" to project.property("description"))
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("${rootProject.name}-${project.version}.${archiveExtension.getOrElse("jar")}")
    mergeServiceFiles() // Automatically merge service files for shading
    dependencies {
        include(dependency(":wgrp-api:"))
        include(dependency(":wgrp-common:"))

        include(dependency("org.bstats:"))
        include(dependency("org.jetbrains.kotlin:"))
    }
    relocate("org.bstats", "${project.group}.wgrp.rslibs.lib.bstats")
    relocate("org.jetbrains.kotlin", "${project.group}.wgrp.rslibs.lib.kotlin")
}

tasks.named<RunServer>("runServer") {
    dependsOn(tasks.shadowJar)
}

tasks.named<RunServer>("runServer") {
    minecraftVersion("1.21.4")
    jvmArgs(
        "-Xms4G", 
        "-Xmx4G",
        "-XX:+UseG1GC",
        "-XX:MaxGCPauseMillis=50",
        "-XX:+UnlockExperimentalVMOptions",
        "-XX:+DisableExplicitGC")
    runDirectory.set(file("run"))
}
