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
    val mover = currentPlayer
    val opponent = if (mover == player1) player2 else player1

    val nextPlayer = when {
      mover.hasValidMoves(board) && mover.doMove(board) -> when {
        opponent.hasValidMoves(board) -> opponent
        mover.hasValidMoves(board) -> mover
        else -> null
      }

      !mover.hasValidMoves(board) && opponent.hasValidMoves(board) -> opponent
      else -> null
    }

    state = when (nextPlayer) {
      null -> state.copy(status = Status.FinishedByNoMoves, round = state.round + 1)
      else -> state.copy(currentPlayer = nextPlayer, round = state.round + 1)
    }

    currentPlayer = state.currentPlayer
    onStateUpdate(state)
    return state.status == Status.Playing
  }
}