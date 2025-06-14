package com.lucasalfare.flothello.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lucasalfare.flothello.core.game.Game

class GameStateHolder(val game: Game) {
  var currentState by mutableStateOf(game.state)
    private set

  var currentPlayer by mutableStateOf(game.currentPlayer)

  init {
    game.onStateUpdate = {
      currentState = it
      currentPlayer = game.currentPlayer
    }
  }
}