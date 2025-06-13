plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "FL-Othello"

include(":core", ":ui", ":desktop")

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
    maven { url = uri("https://plugins.gradle.org/m2/") }
  }
}