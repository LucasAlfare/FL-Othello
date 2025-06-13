plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.composeCompiler)
}

repositories {
  mavenCentral()
  maven { url = uri("https://plugins.gradle.org/m2/") }
}

kotlin {
  androidTarget()
  sourceSets {
    val androidMain by getting {
      dependencies {
        implementation(project(":core"))
        implementation(project(":ui"))
        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation("androidx.activity:activity-compose:1.10.0")
      }
    }
  }
}

android {
  namespace = "com.lucasalfare.flothello"
  compileSdk = 33
}