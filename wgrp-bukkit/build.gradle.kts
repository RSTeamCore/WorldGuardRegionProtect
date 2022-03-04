import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

configurations {
    compileClasspath.get().extendsFrom(create("shadeOnly"))
}

repositories {
    mavenCentral()
    maven {url = uri("https://repo.codemc.org/repository/maven-public")}
    //WorldGuard
    maven {url = uri("https://maven.enginehub.org/repo/")}
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}

    flatDir { dir(File("src/main/resources")) }
}

dependencies {
    api(project(":wgrp-api"))

    //MariaDB for DataBase
    compileOnly("org.mariadb.jdbc:mariadb-java-client:2.7.3")
    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")
    //WorldGuard 7+
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
    //Paper 1.17.1
    implementation("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    "shadeOnly"("org.bstats:bstats-bukkit:3.0.0")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.named<Copy>("processResources") {
    val internalVersion = project.ext["internalVersion"]
    inputs.property("internalVersion", internalVersion)
    filesMatching("plugin.yml") {
        expand("internalVersion" to internalVersion)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations["shadeOnly"], project.configurations["runtimeClasspath"])

    dependencies {
        include(dependency(":wgrp-api"))
    }
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
