package com.lucasalfare.flothello.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lucasalfare.flothello.ui.state.UIStateHolder

@Composable
fun MainGame(stateHolder: UIStateHolder) {
  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
    Column {
      ScoreBoard(stateHolder.currentState)
      GameBoard(stateHolder)
    }
  }
}