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
          movesCandidates += Triple(x, y, affected.size)
        }
      }
    }

    if (movesCandidates.isEmpty()) return false
    val (x, y, _) = movesCandidates.minBy { it.third }
    println("AI trying to apply ($x, $y)...")
    return board.applyMove(piece, x, y)
  }
}