import org.ajoberstar.grgit.Grgit

plugins {
    id ("org.ajoberstar.grgit") version "2.3.0"
}

allprojects {
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

if (!project.hasProperty("gitCommitHash")) {
    apply(plugin = "org.ajoberstar.grgit")
    ext["gitCommitHash"] = try {
        Grgit.open(mapOf("currentDir" to project.rootDir))?.head()?.abbreviatedId
    } catch (e: Exception) {
        logger.warn("Error getting commit hash", e)
        "no.git.id"
    }
}