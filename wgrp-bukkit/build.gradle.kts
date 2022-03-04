import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

configurations {
    compileClasspath.get().extendsFrom(create("shadeOnly"))
}

dependencies {
    api(project(":wgrp-api"))

    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.3")
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
    val internalVersion = project.version
    inputs.property("internalVersion", internalVersion)
    filesMatching("plugin.yml") {
        expand("internalVersion" to internalVersion)
    }
}

tasks.named<Jar>("jar") {
    val projectVersion = project.version
    inputs.property("projectVersion", projectVersion)
    manifest {
        attributes("Implementation-Version" to projectVersion)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations["shadeOnly"], project.configurations["runtimeClasspath"])
    archiveFileName.set("${rootProject.name}-${rootProject.version}-1.16-1.18.${archiveExtension.getOrElse("jar")}")

    dependencies {
        include(dependency(":wgrp-api"))
        /*relocate ("com.zaxxer.hikari", "") {
            include(dependency("com.zaxxer:hikari"))
        }*/
    }
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    configurations["compileClasspath"].forEach { file: File ->
        if(file.name.contains("HikariCP"))
            from(zipTree(file.absoluteFile))
    }
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
