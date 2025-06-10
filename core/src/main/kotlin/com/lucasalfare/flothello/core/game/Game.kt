package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Player

class Game(
  var gameState: GameState,
  val players: List<Player>,
  var onChangeState: (GameState) -> Unit = {}
) {

  val currentPlayer get() = players[gameState.round % players.size]

  fun step(): Boolean {
    val hasMoves = players.any { gameState.board.getValidMoves(it.piece).isNotEmpty() }
    val gameStatus = if (hasMoves) GameStatus.Playing else GameStatus.FinishedByNoMoves

    val nextBoard = gameState.board.deepCopy()

    return if (gameStatus == GameStatus.Playing && currentPlayer.doMove(nextBoard)) {
      gameState = gameState.copy(
        board = nextBoard,
        round = gameState.round + 1,
        gameStatus = gameStatus
      )
      onChangeState.invoke(gameState)
      true
    } else {
      gameState = gameState.copy(round = gameState.round + 1, gameStatus = gameStatus)
      onChangeState.invoke(gameState)
      false
    }
  }
}