plugins {
    `java-library`
    `maven-publish`
}

group = "net.ritasister"

dependencies {
    //WorldGuard 7+
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.5")
    //Paper 1.17.1
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = group as String?
            artifactId = "net.ritasister"
            version = project.version as String?

            from(components["java"])
        }
    }

    repositories {
        val mavenUrl: String? by project
        val mavenSnapshotUrl: String? by project

        (if(version.toString().endsWith("SNAPSHOT")) mavenSnapshotUrl else mavenUrl)?.let { url ->
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