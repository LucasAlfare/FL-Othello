package com.lucasalfare.flothello.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lucasalfare.flothello.ui.state.GameStateHolder

@Composable
fun MainGame(stateHolder: GameStateHolder) {
  Column {
    ScoreBoard(stateHolder.currentState)
    GameBoard(stateHolder)
  }
}