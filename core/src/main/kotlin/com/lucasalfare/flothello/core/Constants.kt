package com.lucasalfare.flothello.core

object Constants {
  /**
   * The size of the Othello board.
   * A standard Othello game is played on an 8x8 grid.
   */
  const val BOARD_SIZE = 8

  /**
   * Represents an empty cell on the board, containing no disc.
   * Used to identify valid targets for new moves.
   */
  const val EMPTY = 0

  /**
   * Represents a disc belonging to the black player.
   * Internally denoted as a negative value for logical inversion (-color).
   */
  const val BLACK = -1

  /**
   * Represents a disc belonging to the white player.
   * Internally denoted as a positive value for logical inversion (-color).
   */
  const val WHITE = 1

  /**
   * Special sentinel value returned when accessing an out-of-bounds coordinate.
   * Prevents accidental logic based on invalid board access.
   */
  const val UNREACHABLE = Int.MIN_VALUE

  /**
   * Difficulty level: Easy.
   * The AI will select the move that results in the fewest captured opponent discs.
   */
  const val AUTOMATIC_MOVE_EASY = 0

  /**
   * Difficulty level: Medium.
   * The AI will select a move that results in a moderate number of captures—neither minimal nor maximal.
   */
  const val AUTOMATIC_MOVE_MEDIUM = 1

  /**
   * Difficulty level: Expert.
   * The AI will select the move that results in the highest number of captured opponent discs.
   */
  const val AUTOMATIC_MOVE_EXPERT = 2
}