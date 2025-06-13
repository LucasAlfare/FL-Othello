plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.compose.compiler)
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