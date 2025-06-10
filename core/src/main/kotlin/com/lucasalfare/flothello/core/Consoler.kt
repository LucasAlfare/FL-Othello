package com.lucasalfare.flothello.core

import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.GameState

fun main() {
  val bot1 = AIPlayer(Piece.Black)
  val bot2 = AIPlayer(bot1.piece.opposite())
  val board = Board()
  val gameState = GameState(board, 0)
  val game = Game(gameState, listOf(bot1, bot2)) {
    println(it)
  }

  println(board)

//  while (true) {
//    Scanner(System.`in`).nextLine()
//    game.step()
//  }

  repeat(19) {
    game.step()
  }

  // até o round "19" funciona, todas as jogadas dão certinhas, mas quando faço o próximo step aí por algum motivo ele joga em White em [2, 3]!
  game.step()
}

private fun clear() {
  repeat(25) {
    println("\n")
  }
}