group = "net.ritasister"
project.version = "'1.0"

dependencies {
    compileOnly ("org.checkerframework:checker-qual:3.21.2")
    compileOnly ("org.jetbrains:annotations:23.1.0")
}

java {
    withJavadocJar()
}
