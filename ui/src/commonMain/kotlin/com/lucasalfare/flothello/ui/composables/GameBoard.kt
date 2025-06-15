package com.lucasalfare.flothello.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucasalfare.flothello.core.BOARD_SIZE
import com.lucasalfare.flothello.ui.state.GameStateHolder

@Composable
fun GameBoard(state: GameStateHolder) {
  Column(
    modifier = Modifier
      .padding(4.dp)
      .background(Color.DarkGray)
  ) {
    repeat(BOARD_SIZE) { y ->
      Row {
        repeat(BOARD_SIZE) { x ->
          GameBoardCell(x to y, state)
        }
      }
    }
  }
}