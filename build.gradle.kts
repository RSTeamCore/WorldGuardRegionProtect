import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

defaultTasks("clean", "build")

subprojects {
    apply (plugin = "java")
    apply (plugin = "maven-publish")

    group = "net.ritasister"
    version = "0.9.5-SNAPSHOT"

    val javaVersion = JavaVersion.VERSION_17

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
