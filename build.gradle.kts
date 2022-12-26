import org.ajoberstar.grgit.Grgit

plugins {
    id("org.ajoberstar.grgit") version "5.0.0-rc.3"
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

allprojects {
    apply(plugin = "maven-publish")
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

var rootVersion by extra(rootProject.version)
var snapshot by extra("SNAPSHOT")
var revision: String by extra("")
var buildNumber by extra("")
var date: String by extra("")
ext {
    val git: Grgit = Grgit.open {
        dir = File("$rootDir/.git")
    }
    date = git.head().dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yy.MM.dd"))
    revision = "-${git.head().abbreviatedId}"
    buildNumber = if (project.hasProperty("buildnumber")) {
        snapshot + "-" + project.properties["buildnumber"] as String
    } else {
        project.properties["snapshot"] as String
    }
}

version = String.format("%s-%s", rootVersion, buildNumber)

if (!project.hasProperty("gitCommitHash")) {
    apply(plugin = "org.ajoberstar.grgit")
    ext["gitCommitHash"] = try {
        extensions.getByName<Grgit>("grgit").head()?.abbreviatedId
    } catch (e: Exception) {
        logger.warn("Error getting commit hash", e)
        "no.git.id"
    }
}


tasks {
    runServer {
        minecraftVersion("1.19.3")
        pluginJars(project(":wgrp-bukkit").file("build/libs/WorldGuardRegionProtect-Bukkit-$version.jar"))
        jvmArgs("-Xms1G", "-Xmx1G")
    }
}