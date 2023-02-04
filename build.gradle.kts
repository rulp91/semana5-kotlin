plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "es.uma.isia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.google.guava:guava:11.0.2")
    implementation("com.liferay:org.springframework.beans:5.2.22.LIFERAY-PATCHED-1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}