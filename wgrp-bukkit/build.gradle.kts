import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

defaultTasks("clean", "build")

repositories {
    maven ("https://repo.codemc.org/repository/maven-public")
    //WorldGuard
    maven ("https://maven.enginehub.org/repo/")
    //PaperMC
    maven ("https://papermc.io/repo/repository/maven-public/")
    maven ("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    mavenCentral()
}

configurations {
    compileClasspath.get().extendsFrom(create("shadeOnly"))
}

dependencies {
    api(project(":wgrp-api"))
    implementation("org.projectlombok:lombok:1.18.22")
    compileOnlyApi ("org.yaml:snakeyaml:1.30")
    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.4")
    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")
    //PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.1")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    //Paper 1.18+
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = rootProject.group as String?
            artifactId = "wgrp"
            version = rootProject.version as String?

            from(components["java"])
        }
    }

    repositories {
        val mavenUrl: String? by project
        val mavenSnapshotUrl: String? by project

        (if(rootProject.version.toString().endsWith("SNAPSHOT")) mavenSnapshotUrl else mavenUrl)?.let { url ->
            maven(url) {
                val mavenUsername: String? by project
                val mavenPassword: String? by project
                if(mavenUsername != null && mavenPassword != null) {
                    credentials {
                        username = mavenUsername
                        password = mavenPassword
                    }
                }
            }
        }
    }
}

tasks {
    runServer {
        minecraftVersion("1.18.2")
    }
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
    archiveFileName.set("${project.name}-${project.version}-SNAPSHOT.${archiveExtension.getOrElse("jar")}")

    dependencies {
        include(dependency(":wgrp-api"))
    }
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    configurations["compileClasspath"].forEach { file: File ->
        if(file.name.contains("HikariCP"))
            from(zipTree(file.absoluteFile))
    }
    configurations["compileClasspath"].forEach { file: File ->
        if(file.name.contains("mariadb-java-client"))
            from(zipTree(file.absoluteFile))
    }
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
