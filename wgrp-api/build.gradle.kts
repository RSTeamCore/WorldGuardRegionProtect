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

    //Paper
    compileOnly(dependencyNotation = "io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    //Plugins
    //WorldGuard 7+
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")
    //Kyori and MiniMessage
    compileOnly("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    //Libs
    //HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    //MariaDB for DataBase
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")
    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20-RC")
    //Annotations
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("org.projectlombok:lombok:1.18.26")
    implementation("aopalliance:aopalliance:1.0")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
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
