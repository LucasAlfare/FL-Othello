package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Player

class Game(
  private val player1: Player,
  private val player2: Player,
  initialState: State = State(board = Board(), currentPlayer = player1, round = 0),
  var onStateUpdate: (State) -> Unit = {}
) {

  var state = initialState
  var currentPlayer = initialState.currentPlayer

  fun step(): Boolean {
    if (state.status != Status.Playing) return false

    val board = state.board
    val nextPlayer = if (currentPlayer == player1) player2 else player1

    when {
      currentPlayer.hasValidMoves(board) && currentPlayer.doMove(board) -> {
        currentPlayer = nextPlayer
        state = state.copy(currentPlayer = currentPlayer, round = state.round + 1)
      }

      !nextPlayer.hasValidMoves(board) -> {
        state = state.copy(status = Status.FinishedByNoMoves)
      }

      else -> {
        currentPlayer = nextPlayer
        state = state.copy(currentPlayer = currentPlayer, round = state.round + 1)
      }
    }

    onStateUpdate(state)
    return state.status == Status.Playing
  }
}