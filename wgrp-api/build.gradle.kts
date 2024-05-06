group = "net.ritasister"
project.version = "'1.0"

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
