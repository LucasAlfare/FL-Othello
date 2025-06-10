package com.lucasalfare.flothello.core

// simplest AI ever, basically dummy, just to fill in the game flow. Choose the move with fewer pieces affected.
data class AIPlayer(override val piece: Piece) : Player {
  override fun doMove(board: Board): Boolean {
    val validMoves = board.getValidMoves(piece)
    if (validMoves.isEmpty()) {
      println("AI $piece has no slots that can play!")
      return false
    }

    // TODO: pick a random choice instead of always the first "search with more captures"
    val move = validMoves.maxByOrNull { board.findAffectedPositions(piece, it.first, it.second).size }
      ?: return false
    println("AI $piece trying to move $move...")
    val moved = board.applyMove(piece, move.first, move.second)
    if (moved) println("success!") else println("failure.")
    return moved
  }
}