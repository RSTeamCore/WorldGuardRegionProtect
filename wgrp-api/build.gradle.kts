plugins {
    `java-library`
    `maven-publish`
}

dependencies {
    //WorldGuard 7+
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.5")
    //Paper 1.18
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}