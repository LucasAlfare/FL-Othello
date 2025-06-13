@file:Suppress("ArrayInDataClass")

package com.lucasalfare.flothello.core

data class Board(val pieces: Array<Piece> = Array(BOARD_SIZE * BOARD_SIZE) { Piece.Empty }) {

  init {
    if (pieces.all { it == Piece.Empty }) {
      set(Piece.Black, 3, 3)
      set(Piece.White, 4, 3)
      set(Piece.White, 3, 4)
      set(Piece.Black, 4, 4)
    }
  }

  fun applyMove(piece: Piece, x: Int, y: Int): Boolean {
    val affectedPositions = findAffectedPositions(piece, x, y)
    if (affectedPositions.isEmpty()) return false

    set(piece, x, y)
    affectedPositions.forEach { set(piece, it.first, it.second) }
    return true
  }

  fun isValidMove(piece: Piece, x: Int, y: Int): Boolean = inBounds(x, y) &&
          get(x, y) == Piece.Empty &&
          findAffectedPositions(piece, x, y).isNotEmpty()

  fun getValidMoves(piece: Piece): List<Pair<Int, Int>> {
    val moves = mutableListOf<Pair<Int, Int>>()
    for (y in 0 until BOARD_SIZE) {
      for (x in 0 until BOARD_SIZE) {
        if (isValidMove(piece, x, y)) {
          moves.add(x to y)
        }
      }
    }
    return moves
  }

  fun findAffectedPositions(piece: Piece, x: Int, y: Int): List<Pair<Int, Int>> {
    val affected = mutableListOf<Pair<Int, Int>>()

    if (get(x, y) != Piece.Empty) {
      return emptyList()
    }

    for (yDir in -1..1) {
      for (xDir in -1..1) {
        // skips center, because center is the played position itself!
        if (!(xDir == 0 && yDir == 0)) {
          if (get(x + xDir, y + yDir) == piece.opposite()) {
            val searches = exploreDirection(
              piece = piece,
              startingExplorationX = x + xDir,
              startingExplorationY = y + yDir,
              xDir = xDir,
              yDir = yDir
            )

            if (searches.isNotEmpty()) {
              affected.addAll(searches)
            }
          }
        }
      }
    }

    return affected
  }

  internal fun exploreDirection(
    piece: Piece,
    startingExplorationX: Int,
    startingExplorationY: Int,
    xDir: Int,
    yDir: Int
  ): List<Pair<Int, Int>> {
    val searches = mutableListOf<Pair<Int, Int>>()
    var x = startingExplorationX
    var y = startingExplorationY
    while (inBounds(x, y)) {
      when (get(x, y)) {
        piece.opposite() -> searches.add(x to y)
        piece -> return searches
        else -> return emptyList()
      }

      x += xDir
      y += yDir
    }

    return emptyList()
  }

  fun countPieces(): Map<Piece, Int> {
    val counts = mutableMapOf(Piece.Black to 0, Piece.White to 0)
    pieces.forEach {
      if (it != Piece.Empty && it != Piece.Invalid) {
        counts[it] = counts.getValue(it) + 1
      }
    }
    return counts
  }

  fun get(x: Int, y: Int): Piece {
    if (!inBounds(x, y)) return Piece.Invalid
    return pieces[x + y * BOARD_SIZE]
  }

  fun set(piece: Piece, x: Int, y: Int) {
    if (!inBounds(x, y)) return
    pieces[x + y * BOARD_SIZE] = piece
  }

  fun inBounds(x: Int, y: Int): Boolean {
    return (x in 0 until BOARD_SIZE) && (y in 0 until BOARD_SIZE)
  }

  fun deepCopy(): Board = Board(pieces.copyOf())

  override fun toString(): String = buildString {
    appendLine()
    for (y in 0 until BOARD_SIZE) {
      append("$y ".padStart(3))
      for (x in 0 until BOARD_SIZE) {
        append("${get(x, y).sign}|")
      }
      appendLine()
    }
  }
}

/*
 0 🔷|⚪|⚪|⚪|⚪|⚪|⚫|🔷|
 1 🔷|🔷|⚪|⚪|⚪|⚫|⚫|⚫|
 2 🔷|🔷|🔷|⚪|⚪|⚫|⚫|🔷|
 3 🔷|🔷|🔷|⚫|⚫|⚫|🔷|🔷|
 4 🔷|🔷|⚫|⚪|⚫|🔷|🔷|🔷|
 5 🔷|🔷|🔷|⚫|🔷|🔷|🔷|🔷|
 6 🔷|🔷|🔷|🔷|🔷|🔷|🔷|🔷|
 7 🔷|🔷|🔷|🔷|🔷|🔷|🔷|🔷|

 >>> AI (White) trying to apply (2, 3)...
 0 🔷|⚪|⚪|⚪|⚪|⚪|⚫|🔷|
 1 🔷|🔷|⚪|⚪|⚪|⚫|⚫|⚫|
 2 🔷|🔷|🔷|⚪|⚪|⚫|⚫|🔷|
 3 🔷|🔷|⚪|⚪|⚪|⚫|🔷|🔷|
 4 🔷|🔷|⚫|⚪|⚫|🔷|🔷|🔷|
 5 🔷|🔷|🔷|⚫|🔷|🔷|🔷|🔷|
 6 🔷|🔷|🔷|🔷|🔷|🔷|🔷|🔷|
 7 🔷|🔷|🔷|🔷|🔷|🔷|🔷|🔷|
 */