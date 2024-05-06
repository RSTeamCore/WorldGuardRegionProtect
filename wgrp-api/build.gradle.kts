group = "net.ritasister"
project.version = "'1.0.0"

tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "net.ritasister.wgrp.api")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.checkerframework:checker-qual:3.42.0")
    compileOnly("org.jetbrains:annotations:24.1.0")
}

java {
    withJavadocJar()
}
