package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Player

data class GameState(
  val board: Board,
  var round: Int,
  val currentPlayer: Player,
  val gameStatus: GameStatus = GameStatus.Playing
)