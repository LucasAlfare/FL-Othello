package com.lucasalfare.flothello.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object NavigationState {
  var currentScreen by mutableStateOf<Screen>(Screen.Home)
    private set

  fun navigate(to: Screen) {
    currentScreen = to
  }
}