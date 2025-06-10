package com.lucasalfare.flothello.core

// simplest AI ever, basically dummy, just to fill in the game flow. Choose the move with fewer pieces affected.
data class AIPlayer(override val piece: Piece) : Player {
  override fun doMove(board: Board): Boolean {
    val validMoves = board.getValidMoves(piece)
    if (validMoves.isEmpty()) return false

    val move = validMoves.minByOrNull { board.findAffectedPositions(piece, it.first, it.second).size }
      ?: return false



    return board.applyMove(piece, move.first, move.second)
  }
}