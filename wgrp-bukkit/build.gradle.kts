import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.8.0"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
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
}

dependencies {
    //Api for this plugin
    api(project(":wgrp-api"))
    //RSLibs by RSTeamCore
    implementation("net.rsteamcore:RSLibs-api:0.0.6")
    //PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.2")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    //Paper 1.19
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    //Kyori and MiniMessage
    compileOnly("net.kyori:adventure-api:4.12.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.bstats:bstats-bukkit:3.0.0")
    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.6")
    //DI google guice
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.google.inject.extensions:guice-assistedinject:5.1.0")

    //Annotations
    implementation("org.jetbrains:annotations:23.1.0")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("aopalliance:aopalliance:1.0")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0-RC2")
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
    jar {
        archiveFileName.set("${rootProject.name}-Bukkit-no-shade-${project.version}.${archiveExtension.getOrElse("jar")}")
    }
    shadowJar {
        archiveFileName.set("${rootProject.name}-Bukkit-${project.version}.${archiveExtension.getOrElse("jar")}")
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
            include(dependency(":wgrp-api"))
            include(dependency("net.rsteamcore:"))
            //Shaded components for using bstats
            include(dependency("org.bstats:"))
            //DI google guice
            include(dependency("com.google.inject:"))
            include(dependency("com.google.inject.extensions:"))
            include(dependency("aopalliance:aopalliance:"))
            //Storage base
            include(dependency("com.zaxxer.hikari:"))
            include(dependency("org.mariadb.jdbc:"))
            //Kotlin
            include(dependency("org.jetbrains.kotlin:"))
        }
        //Main need libs from us API
        relocate("net.rsteamcore", "${project.group}.wgrp.rslibs.rsteamcore")
        //Shaded components for using bstats
        relocate("org.bstats", "${project.group}.wgrp.rslibs.lib.bstats")
        //DI google guice
        relocate("com.google.inject", "${project.group}.wgrp.rslibs.lib.inject")
        relocate("com.google.inject.extensions", "${project.group}.wgrp.rslibs.lib.inject.extensions")
        relocate("org.aopalliance", "${project.group}.wgrp.rslibs.lib.aopalliance")
        //Storage base
        relocate("com.zaxxer.hikari", "${project.group}.wgrp.rslibs.lib.hikari")
        relocate("org.mariadb.jdbc", "${project.group}.wgrp.rslibs.lib.mariadb")
        //Kotlin
        relocate("kotlin", "${project.group}.wgrp.rslibs.lib.kotlin")
    }
    artifacts {
        archives(shadowJar)
    }
}
