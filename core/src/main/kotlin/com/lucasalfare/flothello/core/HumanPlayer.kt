package com.lucasalfare.flothello.core

data class HumanPlayer(
  override val piece: Piece,
  var targetCoordsDefiner: () -> Pair<Int, Int> = { -1 to -1 }
) : Player {
  override fun doMove(board: Board): Boolean {
    val (x, y) = targetCoordsDefiner.invoke()
    println("human trying to apply ($x, $y)...")
    return board.applyMove(piece, x, y)
  }
}