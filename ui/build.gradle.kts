plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.androidLibrary)
}

repositories {
  mavenCentral()
  maven { url = uri("https://plugins.gradle.org/m2/") }
}

kotlin {
  jvm()
  androidTarget()

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

android {
  namespace = "com.lucasalfare.flothello.android"
  compileSdk = 35
  defaultConfig {
    minSdk = 24
  }
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}