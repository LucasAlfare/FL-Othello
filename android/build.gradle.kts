plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.composeCompiler)
}

repositories {
  google()
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
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.lifecycle.runtime.ktx)
      }
    }
  }
}

android {
  namespace = "com.lucasalfare.flothello"
  compileSdk = 35
  defaultConfig {
    applicationId = "com.lucasalfare.flothello"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures {
    compose = true
  }
}
//dependencies {
//  implementation(libs.androidx.lifecycle.runtime.ktx)
//  implementation(libs.androidx.activity.compose)
//}
