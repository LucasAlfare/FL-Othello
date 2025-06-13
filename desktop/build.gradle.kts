plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.compose)
  alias(libs.plugins.composeCompiler)
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