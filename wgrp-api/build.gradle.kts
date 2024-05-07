tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "net.ritasister.wgrp.api")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "wgrp-api"
            groupId = rootProject.group as String?
            version = rootProject.version as String?

            from(components["java"])
        }
    }

    repositories {
        val mavenUrl = "https://repo.codemc.io/repository/maven-releases/"

        maven(mavenUrl) {
            val mavenUsername: String? by project
            val mavenPassword: String? by project
            if (mavenUsername != null && mavenPassword != null) {
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.checkerframework:checker-qual:3.42.0")
    compileOnly("org.jetbrains:annotations:24.1.0")
    compileOnly("org.slf4j:slf4j-api:2.0.12")
}

java {
    withJavadocJar()
    withSourcesJar()
}
