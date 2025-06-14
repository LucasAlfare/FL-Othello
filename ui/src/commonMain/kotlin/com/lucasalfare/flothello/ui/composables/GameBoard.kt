package com.lucasalfare.flothello.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.BOARD_SIZE
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.ui.state.GameStateHolder

@Composable
fun GameBoard(state: GameStateHolder) {
  var affected by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }

  Column(modifier = Modifier.padding(4.dp).background(Color.DarkGray)) {
    repeat(BOARD_SIZE) { y ->
      Row {
        repeat(BOARD_SIZE) { x ->
          val piece = state.currentState.board.get(x, y)
          val isAffected = (x to y) in affected
          GameBoardCell(
            piece = piece,
            isAffected = isAffected,
            onHover = {
              val currentPlayer = state.game.currentPlayer
              if (currentPlayer is HumanPlayer) {
                affected = state.currentState.board.findAffectedPositions(currentPlayer.piece, x, y)
              }
            },
            onExit = {
              affected = emptyList()
            },
            onClick = {
              if (state.game.currentPlayer is HumanPlayer && affected.isNotEmpty()) {
                (state.game.currentPlayer as HumanPlayer).targetCoordsDefiner = { x to y }
                state.game.step()
                affected = emptyList()
              }
            },
            onAnimationEnd = {
              affected = emptyList()
              if (state.game.currentPlayer is AIPlayer) {
                state.game.step()
              }
            }
          )
        }
      }
    }
  }
}