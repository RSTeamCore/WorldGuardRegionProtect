import org.ajoberstar.grgit.Grgit
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED

plugins {
    id ("org.ajoberstar.grgit") version "2.3.0"
}

logger.lifecycle("""
*******************************************
 You are building WorldGuardRegionProtect!
 If you encounter trouble:
 1) Try running 'build' in a separate Gradle run
 2) Use gradlew and not gradle
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

allprojects {
    gradle.projectsEvaluated {
        tasks.withType(JavaCompile::class) {
            options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "1000"))
        }
        tasks.withType(Test::class) {
            testLogging {
                events(FAILED)
                exceptionFormat = FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }
        }
    }
}
