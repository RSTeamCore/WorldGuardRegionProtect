plugins {
    java
    `maven-publish`
}

allprojects {
    apply(plugin = "maven-publish")
    group = "net.ritasister"
    version = property("projectVersion") as String // from gradle.properties
}

logger.lifecycle("""
*******************************************
 You are building WorldGuardRegionProtect!
 If you encounter trouble:
 1) Try running 'build' in a separate Gradle run
 2) Use gradlew and not gradle
 3) If you have a problem, you can join us discord https://discord.gg/kvqvA3GTVF
 Output files will be in [subproject]/build/libs
*******************************************
""")

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