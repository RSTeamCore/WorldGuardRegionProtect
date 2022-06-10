plugins {
    `java-library`
}

repositories {
    maven ("https://repo.codemc.org/repository/maven-public")
    //WorldGuard
    maven ("https://maven.enginehub.org/repo/")
    //PaperMC
    maven ("https://repo.papermc.io/repository/maven-public/")
    mavenCentral()
}

dependencies {
    //Inject
    implementation("com.google.inject:guice:5.1.0")

    //Annotations
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    compileOnly("net.kyori:adventure-api:4.11.0")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    //Paper 1.19
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
}