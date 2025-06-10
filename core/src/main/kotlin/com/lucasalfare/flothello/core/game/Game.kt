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
    val moved = if (gameStatus == GameStatus.Playing) currentPlayer.doMove(nextBoard) else false

    gameState = gameState.copy(
      board = if (moved) nextBoard else gameState.board,
      round = gameState.round + 1,
      gameStatus = gameStatus
    )
    onChangeState.invoke(gameState)
    return moved
  }
}