plugins {
    `java-library`
    `maven-publish`
}

repositories {
    maven ("https://repo.codemc.org/repository/maven-public")
    //WorldGuard
    maven ("https://maven.enginehub.org/repo/")
    //PaperMC
    maven ("https://papermc.io/repo/repository/maven-public/")
    mavenCentral()
}

dependencies {
    //WorldGuard 7+
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    //Paper 1.18
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}