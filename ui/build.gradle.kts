plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.compose.compiler)
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