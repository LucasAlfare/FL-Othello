package com.lucasalfare.flothello.core

import com.lucasalfare.flothello.core.Constants.AUTOMATIC_MOVE_EASY
import com.lucasalfare.flothello.core.Constants.AUTOMATIC_MOVE_EXPERT
import com.lucasalfare.flothello.core.Constants.AUTOMATIC_MOVE_MEDIUM
import com.lucasalfare.flothello.core.Constants.BOARD_SIZE

/**
 * Represents an AI player that can choose and execute a move
 * based on difficulty level and current board state.
 */
object ArtificialPlayer {

  /**
   * Chooses and performs a move for `color` on the given `board`,
   * based on AI difficulty level.
   * Returns true if a move was made.
   *
   * Strategy:
   * - For each cell on the board, check if it's a valid move;
   * - Track valid move options along with number of pieces flipped;
   * - Choose move based on difficulty level:
   *   - EASY: minimal gain;
   *   - MEDIUM: random among non-min/max;
   *   - EXPERT: maximal gain.
   */
  fun doAutomaticMove(board: Board, color: Int, level: Int = Constants.AUTOMATIC_MOVE_EASY): Boolean {
    require(level in listOf(AUTOMATIC_MOVE_EASY, AUTOMATIC_MOVE_MEDIUM, AUTOMATIC_MOVE_EXPERT))

    val movesCandidates = mutableListOf<Triple<Int, Int, Int>>() // x, y, affected count

    // Scan board for valid moves and record the number of affected pieces
    for (y in 0 until BOARD_SIZE) {
      for (x in 0 until BOARD_SIZE) {
        val affected = board.getAffectedCoordinates(color, x, y)
        if (affected.isNotEmpty()) {
          movesCandidates += Triple(x, y, affected.size)
        }
      }
    }

    if (movesCandidates.isEmpty()) return false

    // Select move according to AI difficulty
    val selected = when (level) {
      AUTOMATIC_MOVE_EASY -> movesCandidates.minByOrNull { it.third }
      AUTOMATIC_MOVE_EXPERT -> movesCandidates.maxByOrNull { it.third }
      AUTOMATIC_MOVE_MEDIUM -> {
        val min = movesCandidates.minOf { it.third }
        val max = movesCandidates.maxOf { it.third }
        movesCandidates.filter { it.third != min && it.third != max }.randomOrNull() ?: return false
      }

      else -> return false
    }

    return selected?.let { (x, y, _) -> board.tryApplyMove(color, x, y) } ?: false
  }
}