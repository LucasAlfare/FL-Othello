package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Player

data class State(
  val board: Board,
  var round: Int,
  val currentPlayer: Player,
  val status: Status = Status.Playing
)