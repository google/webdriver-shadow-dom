plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.gfiber"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.seleniumhq.selenium:selenium-java:4.24.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("webdriver-shadow-dom") // Set the base name of the JAR file
    archiveVersion.set("1.0") // Set the version of the JAR file
    manifest {
        attributes["Implementation-Title"] = "ByShadow Library"
        attributes["Implementation-Version"] = archiveVersion.get()
        attributes["Main-Class"] = "com.gfiber.ByShadowCss" // Specify the main class if creating an executable JAR
    }
}
