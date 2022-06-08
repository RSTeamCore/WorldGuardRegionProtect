plugins {
    `java-library`
}

repositories {
    maven ("https://repo.codemc.org/repository/maven-public")
    //WorldGuard
    maven ("https://maven.enginehub.org/repo/")
    //PaperMC
    //maven ("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    /*
     As Spigot-API depends on the BungeeCord ChatComponent-API,
    we need to add the Sonatype OSS repository, as Gradle,
    in comparison to maven, doesn't want to understand the ~/.m2
    directory unless added using mavenLocal(). Maven usually just gets
    it from there, as most people have run the BuildTools at least once.
    This is therefore not needed if you're using the full Spigot/CraftBukkit,
    or if you're using the Bukkit API.
    */
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    mavenCentral()
}

dependencies {
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    compileOnly("org.jetbrains:annotations:23.0.0")
    //SpigotMC 1.19+
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    //Inject
    compileOnly("com.google.inject:guice:5.1.0")
}