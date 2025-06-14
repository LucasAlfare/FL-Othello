package com.lucasalfare.flothello.ui

import androidx.compose.runtime.Composable
import com.lucasalfare.flothello.ui.screens.GameScreen
import com.lucasalfare.flothello.ui.screens.HomeScreen
import com.lucasalfare.flothello.ui.state.NavigationState
import com.lucasalfare.flothello.ui.state.Screen

@Composable
fun MainApp() {
  when (NavigationState.currentScreen) {
    is Screen.Home -> HomeScreen {
      NavigationState.navigate(Screen.Game)
    }

    is Screen.Game -> GameScreen {
      NavigationState.navigate(Screen.Home)
    }
  }
}