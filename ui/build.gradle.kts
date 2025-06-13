plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.composeCompiler)
}

repositories {
  mavenCentral()
  maven { url = uri("https://plugins.gradle.org/m2/") }
}

kotlin {
  jvm()
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(project(":core"))
        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material)
      }
    }
  }
}