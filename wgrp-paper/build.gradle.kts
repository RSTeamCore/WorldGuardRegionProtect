plugins {
    alias(libs.plugins.shadow)
}

repositories {
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "EngineHub"
        url = uri("https://maven.enginehub.org/repo/")
    }
    maven {
        name = "PlaceholderApi"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    implementation(project(":wgrp-common"))

    compileOnly("net.kyori:adventure-platform-bukkit:4.1.0") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
        exclude(module = "adventure-nbt")
    }

    //Paper
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    //Plugins
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
    compileOnly("me.clip:placeholderapi:2.11.5")

    //Annotations
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("org.projectlombok:lombok:1.18.26")

    //HikariCP
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("org.bstats:bstats-bukkit:3.0.2")
    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")
    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20-RC")
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
        archiveFileName.set("${rootProject.name}-Paper-${project.version}.${archiveExtension.getOrElse("jar")}")
    }
    build {
        dependsOn(shadowJar)
    }

}
