package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Player

data class State(
  val board: Board,
  val players: List<Player>,
  val currentPlayerIndex: Int = 0,
  val gameStatus: Status = Status.PLAYING
) {

  val currentPlayer: Player get() = players[currentPlayerIndex]
}