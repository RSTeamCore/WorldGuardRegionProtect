plugins {
    `java-library`
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