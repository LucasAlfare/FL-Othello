package com.lucasalfare.flothello.core

import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.GameState
import com.lucasalfare.flothello.core.game.GameStatus

fun main() {
  val bot1 = AIPlayer(Piece.Black)
  val bot2 = AIPlayer(bot1.piece.opposite())
  val board = Board()
  val gameState = GameState(board, 0)
  val game = Game(gameState, listOf(bot1, bot2)) { state, scores ->
    println(state.board, "\n", state.round, scores)
    println()
  }

  println("Initial state: $gameState")

  while (game.gameState.gameStatus == GameStatus.Playing) {
    game.step()
  }

  println("Game finished!!!! ")
}

// I created my own println() function implementation to accept multiple args in same call (multiple prints in same line). Why doesn't kotlin has this by default in stdlib?
fun println(vararg items: Any) {
  items.forEach { print("$it ") }
  print("\n")
}