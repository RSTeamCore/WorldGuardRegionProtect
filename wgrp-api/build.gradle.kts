plugins {
    `java-library`
    `maven-publish`
}

dependencies {
    //WorldGuard 7+
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.5")
    //Paper 1.17.1
    implementation("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = group as String?
            artifactId = property("name") as String
            version = property("projectVersion") as String // from gradle.properties

            from(components["java"])
        }
    }

    repositories {
        val mavenUrl: String? by project
        val mavenSnapshotUrl: String? by project
        val projectVersion = project.version

        (if(projectVersion.toString().endsWith("SNAPSHOT")) mavenSnapshotUrl else mavenUrl)?.let { url ->
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