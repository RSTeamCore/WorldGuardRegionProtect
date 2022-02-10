plugins {
    java
    `maven-publish`
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    java { toolchain {languageVersion.set(JavaLanguageVersion.of(16))} }
}

group = "net.ritasister.srp"
version = "0.7.1-pre5-1.17-1.18"
description = "WorldGuardRegionProtect"

tasks.compileJava {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    //WorldGuard
    maven {url = uri("https://maven.enginehub.org/repo/")}
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
}

dependencies {
    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.3")
    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.0")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
    //Paper 1.17.1
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    //Paper 1.16.5
    //compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    configurations["compileClasspath"].forEach { file: File ->
        if(file.name.contains("HikariCP"))
            from(zipTree(file.absoluteFile))
    }
}