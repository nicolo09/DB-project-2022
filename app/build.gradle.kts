/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.2/userguide/building_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "7.0.0"

    id ("org.openjfx.javafxplugin") version "0.0.10"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val supportedPlatforms = listOf("linux", "mac", "win") // All required for OOP
val jUnitVersion = "5.7.1"
val javaFxVersion = "18"

javafx {
    version = javaFxVersion
    modules = listOf(
    "javafx.base",
    "javafx.controls",
    "javafx.fxml",
    "javafx.swing",
    "javafx.graphics"
)}

dependencies {
    // JavaFX: comment out if you do not need them
    // for (platform in supportedPlatforms) {
    //     for (module in javaFXModules) {
    //         implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
    //     }
    // }

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("mysql:mysql-connector-java:8.0.29")

    implementation("no.tornado:tornadofx-controls:1.0.6")

    implementation("org.apache.ibatis:ibatis-core:3.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    // Define the main class for the application.
    mainClass.set("db.project.Launcher")
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
