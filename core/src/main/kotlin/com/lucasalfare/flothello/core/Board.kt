package com.lucasalfare.flothello.core

/**
 * Mapping from internal cell values to visual symbols for display purposes.
 * Only for debugging in console.
 */
val signs = mapOf(
  Pair(Constants.BLACK, "⚫"),         // Black disc
  Pair(Constants.WHITE, "⚪"),         // White disc
  Pair(Constants.EMPTY, "🔷"),         // Empty cell
  Pair(Constants.UNREACHABLE, "❌")    // Invalid/out-of-bounds cell
)

/**
 * Represents the game board and contains all board-related logic,
 * including piece placement, flipping logic, and board state access.
 */
class Board(
  val content: IntArray =
  // @formatter:off
  intArrayOf(
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.BLACK, Constants.WHITE, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.WHITE, Constants.BLACK, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
    Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
  )// @formatter:on
) {

  companion object {
    /**
     * All 8 directions around a cell for evaluating captures candidates.
     * I made this static in companion to avoid reallocating.
     */
    // @formatter:off
    val directions = listOf(
      -1 to -1, 0 to -1, 1 to -1, // top-left,    top,      top-right
      -1 to  0,          1 to  0, // left,        [center], right
      -1 to  1, 0 to  1, 1 to  1  // bottom-left, bottom,   bottom-right
    )// @formatter:on
  }

  /**
   * Attempts to apply a move at (x, y) for the given color.
   * Returns true if the move is valid and discs are flipped.
   */
  fun tryApplyMove(color: Int, x: Int, y: Int): Boolean {
    val affected = getAffectedCoordinates(color, x, y)
    if (affected.isEmpty()) return false

    for ((cx, cy) in affected) set(color, cx, cy) // Flip captured pieces
    set(color, x, y) // Place the new piece

    return true
  }

  /**
   * Returns the list of coordinates that would be flipped
   * if the player of given color played at (x, y).
   */
  fun getAffectedCoordinates(color: Int, x: Int, y: Int): List<Pair<Int, Int>> {
    if (color != Constants.BLACK && color != Constants.WHITE) return emptyList()
    if (!inBounds(x, y) || get(x, y) != Constants.EMPTY) return emptyList()

    val result = mutableListOf<Pair<Int, Int>>()

    // Check each direction for capturable sequences
    for ((dx, dy) in directions) {
      val captured = captureDirection(color, x, y, dx, dy)
      result.addAll(captured)
    }

    return result
  }

  /**
   * In the direction (dx, dy) from (x, y), returns the list of
   * opponent pieces to be flipped if the move is legal.
   * Requires at least one opponent disc followed by a friendly one.
   */
  private fun captureDirection(color: Int, x: Int, y: Int, dx: Int, dy: Int): List<Pair<Int, Int>> {
    val captured = mutableListOf<Pair<Int, Int>>()
    var cx = x + dx
    var cy = y + dy

    // Continue in the direction as long as opponent discs are found
    while (inBounds(cx, cy) && get(cx, cy) == -color) {
      captured += cx to cy
      cx += dx
      cy += dy
    }

    // Valid capture only if ended with a piece of the same color
    if (inBounds(cx, cy) && get(cx, cy) == color) return captured
    return emptyList()
  }

  fun numWhite(): Int = content.count { it == Constants.WHITE }
  fun numBlack(): Int = content.count { it == Constants.BLACK }
  fun isFull(): Boolean = content.none { it == Constants.EMPTY }
  fun hasAtLeastOneValidMove(color: Int): Boolean {
    for (y in 0 until Constants.BOARD_SIZE) {
      for (x in 0 until Constants.BOARD_SIZE) {
        if (get(x, y) == Constants.EMPTY && getAffectedCoordinates(color, x, y).isNotEmpty()) {
          return true
        }
      }
    }
    return false
  }

  /**
   * Retrieves the value at position (x, y).
   * Returns [Constants.UNREACHABLE] if out-of-bounds.
   */
  fun get(x: Int, y: Int): Int {
    if (!inBounds(x, y)) return Constants.UNREACHABLE
    return content[x + y * Constants.BOARD_SIZE]
  }

  /**
   * Sets value n at position (x, y) if within bounds.
   * Returns true if successful.
   */
  fun set(n: Int, x: Int, y: Int): Boolean {
    if (!inBounds(x, y)) return false
    content[x + y * Constants.BOARD_SIZE] = n
    return true
  }

  /**
   * Validates whether (x, y) lies within the board.
   */
  fun inBounds(x: Int, y: Int) = x in 0..<Constants.BOARD_SIZE && y in 0..<Constants.BOARD_SIZE

  fun copy(): Board {
    return Board(content.copyOf(Constants.BOARD_SIZE * Constants.BOARD_SIZE))
  }

  /**
   * Prints the current board to console using symbolic representation.
   * Used for debugging.
   */
  override fun toString(): String = buildString {
    content.forEachIndexed { i, n ->
      if (i % Constants.BOARD_SIZE == 0) append("\n")
      append("${signs[n]}")
      append(" ")
    }
    append("\n")
  }
}