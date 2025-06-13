package com.lucasalfare.flothello.desktop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.Status
import com.lucasalfare.flothello.ui.composables.GameBoard
import com.lucasalfare.flothello.ui.state.UIStateHolder

fun main() = application {
  val p1 = HumanPlayer(Piece.Black)
  val p2 = AIPlayer(p1.piece.opposite())
  val game = remember { Game(p1, p2) }
  val stateHolder = remember { UIStateHolder(game) }

  Window(
    state = WindowState(position = WindowPosition(Alignment.Center)),
    onCloseRequest = { exitApplication() }
  ) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
      Column {
        val s = when (stateHolder.currentState.status) {
          Status.Playing -> "Make your move ${stateHolder.currentPlayer.piece.sign}!"
          else -> "Game finished!"
        }

        Text(s)
        GameBoard(stateHolder)
      }
    }
  }
}