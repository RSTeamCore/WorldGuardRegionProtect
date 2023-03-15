plugins {
    `java-library`
}

repositories {
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        name = "EngineHub"
        url = uri("https://maven.enginehub.org/repo/")
    }
    mavenCentral()
}

dependencies {
    //RSLibs by RSTeamCore
    implementation("net.rsteamcore:RSLibs-api:0.0.6")
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
    //Paper 1.19
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    //Inject
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.google.inject.extensions:guice-assistedinject:5.1.0")

    //Annotations
    compileOnly("org.jetbrains:annotations:23.1.0")

    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    implementation("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")

    compileOnly("net.kyori:adventure-api:4.12.0")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "WorldGuardRegionProtect-api"
            groupId = rootProject.group as String?
            version = rootProject.version as String?

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
