package com.lucasalfare.flothello.core

import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.State
import java.util.*

fun main() {
  val human = HumanPlayer(Piece.Black)
  val ai = AIPlayer(human.piece.opposite())
  val board = Board()
  val state = State(board = board, players = listOf(human, ai))

  println("initial state is: $state")

  val game = Game(state) { s ->
    clear()
    println("new state is: $s")
  }

  while (true) {
    if (game.state.currentPlayer is HumanPlayer) {
      val (x, y) = Scanner(System.`in`)
        .nextLine()
        .split(" ")
        .map { it.toInt() }
      human.targetCoordsDefiner = { x to y }
    }

    game.step()
  }
}

private fun clear() {
  repeat(25) {
    println("\n")
  }
}