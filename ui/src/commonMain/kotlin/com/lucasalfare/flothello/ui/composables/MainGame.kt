package com.lucasalfare.flothello.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lucasalfare.flothello.ui.state.GameStateHolder

@Composable
fun MainGame(stateHolder: GameStateHolder) {
  Column {
    ScoreBoard(stateHolder.currentState)
    GameBoard(stateHolder)
  }
}