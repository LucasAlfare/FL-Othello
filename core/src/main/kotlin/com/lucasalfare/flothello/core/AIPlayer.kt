package com.lucasalfare.flothello.core

// simplest AI ever, basically dummy, just to fill in the game flow
data class AIPlayer(override val piece: Piece) : Player {
  override fun doMove(board: Board): Boolean {
    val movesCandidates = mutableListOf<Triple<Int, Int, Int>>() // x, y, affected count

    // Scan board for valid moves and record the number of affected pieces
    for (y in 0 until BOARD_SIZE) {
      for (x in 0 until BOARD_SIZE) {
        val affected = board.findAffectedPositions(piece, x, y)

        if (affected.isNotEmpty()) {
          val nextTriple = Triple(x, y, affected.size)

          if (x == 2 && y == 3) {
            println("next triple: $nextTriple")
          }

          movesCandidates += nextTriple
        }
      }
    }

    if (movesCandidates.isEmpty()) {
      return false
    } else {
      val m = movesCandidates.minBy { it.third }
      println(">>> AI ($piece) trying to apply (${m.first}, ${m.second})...")
      val moved = board.applyMove(piece, m.first, m.second)

      if (moved) {
        println("Success")
      } else {
        "Didn't moved"
      }

      return moved
    }
  }
}