import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

defaultTasks("clean", "build")

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

subprojects {
    apply (plugin = "java")
    apply (plugin = "maven-publish")

    val javaVersion = JavaVersion.VERSION_21

    tasks {
        // Configure reobfJar to run when invoking the build task
        withType<JavaCompile> {
            options.encoding = "UTF-8"
            sourceCompatibility = javaVersion.toString()
            targetCompatibility = javaVersion.toString()
        }
    }

    tasks {
        withType<Test> {
            testLogging {
                events = mutableSetOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
                exceptionFormat = TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }
        }
    }
}
