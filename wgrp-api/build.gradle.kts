plugins {
    `java-library`
}

dependencies {
    //Paper 1.17.1
    implementation("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}
repositories {
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
    mavenCentral()
}