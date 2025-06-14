package com.lucasalfare.flothello.ui.state

sealed class Screen {
  object Home : Screen()
  object Game : Screen()
}
