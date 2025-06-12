package com.lucasalfare.flothello.core

import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.Status
import java.util.*

fun main() {
  val bot1 = AIPlayer(Piece.Black)
  val bot2 = AIPlayer(bot1.piece.opposite())
  val game = Game(bot1, bot2) {
    println(it)
  }

  println("Initial state: ${game.state}")

  while (game.state.status == Status.Playing) {
    Scanner(System.`in`).nextLine()
    game.step()
  }

  println("Game finished!!!! ")
}

// I created my own println() function implementation to accept multiple args in same call (multiple prints in same line). Why doesn't kotlin has this by default in stdlib?
fun println(vararg items: Any) {
  items.forEach { print("$it ") }
  print("\n")
}