import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    //id("net.kyori.indra") version "4.0.0"
    //id("net.kyori.indra.checkstyle") version "4.0.0"
}

//val checkstyleVersion = "9.3"

logger.lifecycle("""
*******************************************
 You are building WorldGuardRegionProtect!
 If you encounter trouble:
 1) Try running 'build' in a separate Gradle run
 2) Use gradlew and not gradle
 3) If you have a problem, you can join us discord https://discord.gg/kvqvA3GTVF
 
 Output files will be in [subproject]/build/libs
*******************************************
"""
)

repositories {
    mavenCentral()
}

allprojects {
    plugins.apply("java")
    plugins.apply("java-library")
    plugins.apply("org.jetbrains.kotlin.jvm")
    //plugins.apply("net.kyori.indra")
    //plugins.apply("net.kyori.indra.checkstyle")
    plugins.apply("maven-publish")

    /*indra {
        checkstyle(checkstyleVersion)
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }*/
}

subprojects {
    configure<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension> {
        jvmToolchain(25)
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(25)
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_25)
            languageVersion.set(KotlinVersion.KOTLIN_2_0)

            freeCompilerArgs.add("-Xjdk-release=25")
        }
    }

    tasks.withType<Test> {
        testLogging {
            events = mutableSetOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
    }

    tasks.test {
        testLogging {
            events("PASSED", "SKIPPED", "FAILED")
        }
    }

}
