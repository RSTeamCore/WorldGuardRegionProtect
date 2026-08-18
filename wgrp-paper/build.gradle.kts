import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
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

dependencies {
    implementation(project(":wgrp-common"))

    //Paper or Folia
    paperweight.paperDevBundle("26.2.build.+")

    //Plugins api
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.3")
    compileOnly(dependencyNotation = "com.sk89q.worldguard:worldguard-bukkit:7.0.16-SNAPSHOT")
    //compileOnly("com.sk89q.worldedit:worldedit-core:7.4.4")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("org.bstats:bstats-bukkit:3.1.0")

    //Others implementation
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.4.0")
    implementation("org.jetbrains:annotations:24.1.0")

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("it.unimi.dsi:fastutil:8.5.15")

    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")

    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")

    //ORMLite
    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
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
        val gitCommitHash = try {
            "git rev-parse --short=7 HEAD".runCommand().trim().ifEmpty { "unknown" }
        } catch (_: Exception) {
            "unknown"
        }

        val version = project.version.toString()

        val versionWithGitHash = if (version.contains("-SNAPSHOT") || version.contains("-dev")) {
            "$version-$gitCommitHash"
        } else {
            version
        }

        expand(
            "name" to rootProject.name,
            "version" to versionWithGitHash,
            "group" to project.group,
            "author" to (project.findProperty("author") ?: ""),
            "contributor" to (project.findProperty("contributor") ?: ""),
            "description" to (project.findProperty("description") ?: "")
        )
    }
}

val gitCommitHash: String by lazy {
    try {
        val hash = "git rev-parse --short=7 HEAD".runCommand().trim()
        hash.ifEmpty { "unknown" }
    } catch (_: Exception) {
        "unknown"
    }
}

tasks.named<ShadowJar>("shadowJar") {
    val isDevBuild = project.version.toString().contains("-SNAPSHOT") || project.version.toString().contains("-dev")

    archiveFileName.set(
        if (isDevBuild) {
            "${rootProject.name}-${project.version}-$gitCommitHash.${archiveExtension.getOrElse("jar")}"
        } else {
            "${rootProject.name}-${project.version}.${archiveExtension.getOrElse("jar")}"
        }
    )

    mergeServiceFiles()

    dependencies {
        include(dependency(":wgrp-api"))
        include(dependency(":wgrp-common"))
        include(dependency("org.bstats:.*"))
        include(dependency("org.jetbrains.kotlin:.*"))
    }

    relocate("org.bstats", "${project.group}.wgrp.rslibs.lib.bstats")
    relocate("kotlin", "${project.group}.wgrp.rslibs.lib.kotlin")
    relocate("org.jetbrains", "${project.group}.wgrp.rslibs.lib.jetbrains")
}

artifacts {
    archives(tasks.named("shadowJar"))
}

tasks.named<RunServer>("runServer") {
    minecraftVersion("26.1.2")

    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(25))
    })

    jvmArgs(
            "-Xms2G",
            "-Xmx2G",
            "-XX:+UseG1GC",
            "-XX:MaxGCPauseMillis=50",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+DisableExplicitGC"
    )

    runDirectory.set(file("run"))

    val shadowJarTask = tasks.findByName("shadowJar") as? ShadowJar
    if (shadowJarTask != null) {
        pluginJars(shadowJarTask.archiveFile)
    }
}

fun String.runCommand(): String {
    return try {
        val process = ProcessBuilder(*split(" ").toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .start()
        process.inputStream.bufferedReader().readText()
    } catch (_: Exception) {
        ""
    }
}

tasks.named("reobfJar") {
    enabled = false
}
