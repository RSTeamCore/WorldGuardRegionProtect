plugins {
    id("java-library")
    alias(libs.plugins.shadow)
    kotlin("jvm")
}

repositories {
    maven {
        name = "SpigotMC"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        name = "EngineHub"
        url = uri("https://maven.enginehub.org/repo/")
    }
    maven {
        name = "PlaceholderApi"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    maven {
        name = "Sonatype-oss"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    mavenCentral()
}

dependencies {
    implementation(project(":wgrp-common"))

    compileOnly("net.kyori:adventure-platform-bukkit:4.3.2") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
        exclude(module = "adventure-nbt")
    }

    //Spigot
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

    //Plugins
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
    compileOnly("me.clip:placeholderapi:2.11.5")

    //Annotations
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.projectlombok:lombok:1.18.32")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0-RC2")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.projectlombok:lombok:1.18.32")
    implementation("aopalliance:aopalliance:1.0")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
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
                    "contributor" to project.property("contributor"),
                    "description" to project.property("description"))
        }
    }
    shadowJar {
        dependencies {
            //Main need libs from us API
            include(dependency(":wgrp-api:"))
            include(dependency(":wgrp-common:"))
            //Shaded components for using bstats
            include(dependency("org.bstats:"))
            include(dependency("org.jetbrains.kotlin:"))
        }
        //Shaded components for using bstats
        relocate("org.bstats", "${project.group}.wgrp.rslibs.lib.bstats")
        relocate("org.jetbrains.kotlin", "${project.group}.wgrp.rslibs.lib.kotlin")
    }
}

kotlin {
    jvmToolchain(21)
}
