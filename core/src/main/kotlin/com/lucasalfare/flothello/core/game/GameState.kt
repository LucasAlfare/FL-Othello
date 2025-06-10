package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Board

data class GameState(
  val board: Board,
  var round: Int,
  val gameStatus: GameStatus = GameStatus.Playing
)