import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    kotlin("jvm") version "1.8.0"
}

val javaVersion = 17

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = javaVersion.toString()
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = javaVersion.toString()
}

defaultTasks("clean", "build")

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
    maven {
        name = "PlaceholderAPI"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    mavenCentral()
}

dependencies {
    //Api for this plugin
    api(project(":wgrp-api"))
    //RSLibs by RSTeamCore
    implementation("net.rsteamcore:RSLibs-api:0.0.6")

    //Paper
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    //Alternate run-task Paper if runServer is don't updated to the new version of MC.
    //paperweight.paperDevBundle("1.20-R0.1-SNAPSHOT")

    //Plugins
    //PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.3")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")
    //Kyori and MiniMessage
    compileOnly("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    //Libs
    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.bstats:bstats-bukkit:3.0.2")
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
    // Configure reobfJar to run when invoking the build task
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    shadowJar {
        archiveFileName.set("${rootProject.name}-${project.version}.${archiveExtension.getOrElse("jar")}")
    }
    build {
        dependsOn(shadowJar)
    }
}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand(
                    "name" to rootProject.name,
                    "version" to project.version,
                    "group" to project.group,
                    "author" to project.property("author"),
                    "description" to project.property("description"),
            )
        }
    }
    shadowJar {
        dependencies {
            //Main need libs from us API
            include(dependency(":wgrp-api:"))
            include(dependency("net.rsteamcore:"))
            //Shaded components for using bstats
            include(dependency("org.bstats:"))
        }
        //Main need libs from us API
        relocate("net.rsteamcore", "${project.group}.wgrp.rslibs.rsteamcore")
        //Shaded components for using bstats
        relocate("org.bstats", "${project.group}.wgrp.rslibs.lib.bstats")
    }
    artifacts {
        archives(shadowJar)
    }
}

tasks {
    runServer {
        minecraftVersion("1.20.1")
        pluginJars(project(":wgrp-spigot").file("build/libs/WorldGuardRegionProtect-Spigot-${rootProject.version}.jar"))
        jvmArgs("-Xms2G", "-Xmx2G")
    }
    /*runMojangMappedServer {
        pluginJars(project(":wgrp-spigot").file("build/libs/WorldGuardRegionProtect-Spigot-${rootProject.version}.jar"))
        jvmArgs("-Xms2G", "-Xmx2G")
    }*/
}
