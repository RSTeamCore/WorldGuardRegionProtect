plugins {
    `java-library`
}

dependencies {
    //Paper 1.17.1
    implementation("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}
repositories {
    //WorldGuard
    maven {url = uri("https://maven.enginehub.org/repo/")}
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
    mavenCentral()
}

/*tasks.withType<JavaCompile>().configureEach {
    dependsOn(":wgrp-api:build")
}/*