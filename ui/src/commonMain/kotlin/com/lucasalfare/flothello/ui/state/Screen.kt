package com.lucasalfare.flothello.ui.state

sealed class Screen {
  data object Home : Screen()
  data object Game : Screen()
}
