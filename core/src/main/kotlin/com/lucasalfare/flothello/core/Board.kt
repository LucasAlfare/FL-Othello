@file:Suppress("ArrayInDataClass")

package com.lucasalfare.flothello.core

data class Board(val pieces: Array<Piece> = Array(BOARD_SIZE * BOARD_SIZE) { Piece.Empty }) {

  init {
    set(Piece.Black, 3, 3)
    set(Piece.White, 4, 3)
    set(Piece.White, 3, 4)
    set(Piece.Black, 4, 4)
  }

  fun applyMove(piece: Piece, x: Int, y: Int): Boolean {
    if (!inBounds(x, y)) return false

    val affectedPositions = findAffectedPositions(piece, x, y)
    if (affectedPositions.isEmpty()) {
      println("no pieces affected by this ${x to y} move.")
      return false
    }

    set(piece, x, y)
    affectedPositions.forEach { set(piece, it.first, it.second) }

    return true
  }

  fun isValidMove(piece: Piece, x: Int, y: Int): Boolean {
    return inBounds(x, y) &&
            get(x, y) == Piece.Empty &&
            findAffectedPositions(piece, x, y).isNotEmpty()
  }

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

    for (yDirectionFactor in -1..1) {
      for (xDirectionFactor in -1..1) {
        // skips center, because center is the played position itself!
        if (!(xDirectionFactor == 0 && yDirectionFactor == 0)) {
          if (get(x + xDirectionFactor, y + yDirectionFactor) == piece.opposite()) {
            val searches = exploreDirection(
              piece = piece,
              startingExplorationX = x + xDirectionFactor,
              startingExplorationY = y + yDirectionFactor,
              xDirectionFactor = xDirectionFactor,
              yDirectionFactor = yDirectionFactor
            )
//            println("Affected positions for ${x to y}: $searches")

            searches.forEach { s ->
              if (!affected.contains(s)) {
                affected.add(s)
              }
            }
          }
        }
      }
    }

    return affected
  }

  private fun exploreDirection(
    piece: Piece,
    startingExplorationX: Int,
    startingExplorationY: Int,
    xDirectionFactor: Int,
    yDirectionFactor: Int
  ): MutableList<Pair<Int, Int>> {
    val searches = mutableListOf<Pair<Int, Int>>()
    var nextX = startingExplorationX
    var nextY = startingExplorationY
    while (true) {
      val current = get(nextX, nextY)

      when (current) {
        Piece.Invalid, Piece.Empty -> return mutableListOf()
        piece -> return searches
        piece.opposite() -> searches += nextX to nextY
        else -> {}
      }

      nextX += xDirectionFactor
      nextY += yDirectionFactor
    }
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