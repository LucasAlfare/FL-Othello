package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Player

class Game(
  gameState: GameState,
  val players: List<Player>,
  var onChangeState: (state: GameState, scores: List<Pair<Player, Int>>) -> Unit = { _, _ -> }
) {
  var gameState = gameState
    private set

  val currentPlayer: Player
    get() = players[gameState.round % players.size]

  private fun calculateScores(): List<Pair<Player, Int>> =
    players.map { it to gameState.board.pieces.count { p -> p == it.piece } }

  fun step(): Boolean {
    val validMoves = players.associateWith { gameState.board.getValidMoves(it.piece) }
    val hasMoves = validMoves.any { it.value.isNotEmpty() }
    val gameStatus = if (hasMoves) GameStatus.Playing else GameStatus.FinishedByNoMoves

    val boardCopy = gameState.board.deepCopy()
    val moved = if (gameStatus == GameStatus.Playing) currentPlayer.doMove(boardCopy) else false

    val nextRound = gameState.round + 1
    val updatedBoard = if (moved) boardCopy else gameState.board

    gameState = gameState.copy(
      board = updatedBoard,
      round = nextRound,
      currentPlayer = players[nextRound % players.size],
      gameStatus = gameStatus
    )

    onChangeState.invoke(gameState, calculateScores())
    return moved
  }
}