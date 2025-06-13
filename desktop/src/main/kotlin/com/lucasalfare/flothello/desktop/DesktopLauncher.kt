package com.lucasalfare.flothello.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.ui.composables.MainGame
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
    MainGame(stateHolder)
  }
}