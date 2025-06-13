plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.compose)
  alias(libs.plugins.composeCompiler)
}

repositories {
  mavenCentral()
  maven { url = uri("https://plugins.gradle.org/m2/") }
  google()
}

dependencies {
  implementation(project(":core"))
  implementation(project(":ui"))
  implementation(compose.desktop.currentOs)
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(21)
}