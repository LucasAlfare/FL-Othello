package com.lucasalfare.flothello.core.game

class Game(
  var state: State,
  var onChangeState: (state: State) -> Unit = { }
) {

  fun step() {
    val boardCopy = state.board.copy()
    if (state.currentPlayer.doMove(boardCopy)) {
      val nextPlayerIndex = (state.currentPlayerIndex + 1) % state.players.size
      state = state.copy(board = boardCopy, currentPlayerIndex = nextPlayerIndex)
      onChangeState(state)
    }
  }
}