plugins {
    `java-library`
}

//applyPlatformAndCoreConfiguration()

dependencies {
    api(projects.wgrpBukkit)

    //MariaDB for DataBase
    compileOnly("org.mariadb.jdbc:mariadb-java-client:2.7.3")
    //HikariCP
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
    //Paper 1.17.1
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}
repositories {
    //WorldGuard
    maven {url = uri("https://maven.enginehub.org/repo/")}
    //PaperMC
    maven {url = uri("https://papermc.io/repo/repository/maven-public/")}
    mavenCentral()
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(":worldguard-api:build")
}