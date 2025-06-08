package com.lucasalfare.flothello.core

object Constants {
  /**
   * Board size for Othello is 8x8.
   */
  const val BOARD_SIZE = 8

  // Cell states for the board representation:
  // EMPTY = unoccupied; BLACK = black player's disc; WHITE = white player's disc;
  // UNREACHABLE = value returned when accessing out-of-bounds coordinates.
  const val EMPTY = 0
  const val BLACK = -1
  const val WHITE = 1
  const val UNREACHABLE = Int.MIN_VALUE

  // Difficulty levels for the automatic move decision-making:
  // EASY: choose move with fewest captures;
  // MEDIUM: choose move that’s neither min nor max captures;
  // EXPERT: choose move with most captures.
  const val AUTOMATIC_MOVE_EASY = 0
  const val AUTOMATIC_MOVE_MEDIUM = 1
  const val AUTOMATIC_MOVE_EXPERT = 2
}