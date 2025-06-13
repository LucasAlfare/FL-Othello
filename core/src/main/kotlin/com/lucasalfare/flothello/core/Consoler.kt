package com.lucasalfare.flothello.core

import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.State

fun badBoard(): Board {
  val b = Board()

  val black = Piece.Black
  val white = Piece.White

// Linha 0
  b.set(black, 0, 0); b.set(black, 1, 0); b.set(black, 2, 0); b.set(black, 3, 0)
  b.set(black, 4, 0); b.set(black, 5, 0); b.set(black, 6, 0); b.set(white, 7, 0)

// Linha 1
  b.set(white, 0, 1); b.set(black, 1, 1); b.set(black, 2, 1); b.set(white, 3, 1)
  b.set(black, 4, 1); b.set(black, 5, 1); b.set(white, 6, 1); b.set(white, 7, 1)

// Linha 2
  b.set(black, 0, 2); b.set(black, 1, 2); b.set(black, 2, 2); b.set(white, 3, 2)
  b.set(black, 4, 2); b.set(white, 5, 2); b.set(black, 6, 2); b.set(white, 7, 2)

// Linha 3
  b.set(black, 0, 3); b.set(black, 1, 3); b.set(black, 2, 3); b.set(white, 3, 3)
  b.set(white, 4, 3); b.set(black, 5, 3); b.set(black, 6, 3); b.set(white, 7, 3)

// Linha 4
  b.set(black, 0, 4); b.set(black, 1, 4); b.set(black, 2, 4); b.set(black, 3, 4)
  b.set(white, 4, 4); b.set(white, 5, 4); b.set(black, 6, 4);

// Linha 5
  b.set(black, 0, 5); b.set(black, 1, 5); b.set(black, 2, 5); b.set(black, 3, 5)
  b.set(black, 4, 5); b.set(white, 5, 5); b.set(black, 6, 5); b.set(black, 7, 5)

// Linha 6
  b.set(Piece.Empty, 0, 6); b.set(black, 1, 6); b.set(black, 2, 6); b.set(black, 3, 6)
  b.set(black, 4, 6); b.set(black, 5, 6); b.set(white, 6, 6); b.set(white, 7, 6)

// Linha 7
  b.set(black, 0, 7); b.set(black, 1, 7); b.set(black, 2, 7); b.set(black, 3, 7)
  b.set(black, 4, 7); b.set(black, 5, 7); b.set(black, 6, 7); b.set(white, 7, 7)
  return b
}

fun main() {
  val b = badBoard()

  val bot1 = AIPlayer(Piece.Black)
  val bot2 = AIPlayer(bot1.piece.opposite())
  val game = Game(bot1, bot2, initialState = State(board = b, round = 0, currentPlayer = bot1)) {
    println(it)
  }

  println()
  println(game.step())

//
//  println("Initial state: ${game.state}")
//
//  while (game.state.status == Status.Playing) {
//    Scanner(System.`in`).nextLine()
//    game.step()
//  }
//
//  println("Game finished!!!! ")
}

// I created my own println() function implementation to accept multiple args in same call (multiple prints in same line). Why doesn't kotlin has this by default in stdlib?
fun println(vararg items: Any) {
  items.forEach { print("$it ") }
  print("\n")
}