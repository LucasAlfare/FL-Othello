package com.lucasalfare.flothello.core

class Game(
  val userColor: Int,
  val userStarts: Boolean = true,
  var currentRoundColor: Int = if (userStarts) userColor else userColor * -1,
  val board: Board,
) {

  val artificialPlayer = ArtificialPlayer
  val artificialPlayerColor = userColor * -1
  var gameFinished = false

  init {
    if (!userStarts) {
      artificialPlayer.doAutomaticMove(board, artificialPlayerColor)
      currentRoundColor *= -1
    }
  }

  fun step(x: Int, y: Int, onStepDoneCallback: () -> Unit = {}): Game {
    if (isGameOver()) {
      gameFinished = true
      return this
    }

    if (currentRoundColor != userColor) { // IA time
      if (artificialPlayer.doAutomaticMove(board, artificialPlayerColor)) {
        currentRoundColor *= -1
      }
    } else { // player time
      if (board.tryApplyMove(currentRoundColor, x, y)) {
        currentRoundColor *= -1
      } else { // IA automatically skips when can't perform something
        skipOwnRound()
      }
    }

    onStepDoneCallback()

    return this.copy()
  }

  fun skipOwnRound() {
    currentRoundColor *= -1
  }

  fun isGameOver(): Boolean {
    return board.isFull() || (!board.hasAtLeastOneValidMove(userColor) && !board.hasAtLeastOneValidMove(
      artificialPlayerColor
    ))
  }

  fun copy(): Game {
    return Game(
      userColor = userColor,
      userStarts = userStarts,
      currentRoundColor = currentRoundColor,
      board = board.copy()
    )
  }
}