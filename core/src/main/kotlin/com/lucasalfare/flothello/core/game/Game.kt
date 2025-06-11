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

  fun step(): Boolean {
    if (gameState.gameStatus != GameStatus.Playing) return false

    val moved = if (currentPlayer.hasValidMoves(gameState.board)) {
      val boardCopy = gameState.board.deepCopy()
      val moved = currentPlayer.doMove(boardCopy)

      val nextBoard = if (moved) boardCopy else gameState.board

      gameState = gameState.copy(
        board = nextBoard,
        round = gameState.round + 1,
        gameStatus = GameStatus.Playing
      )
      moved
    } else {
      if (players.any { it.hasValidMoves(gameState.board) }) {
        println("${currentPlayer.piece} skipped its round.")
        gameState = gameState.copy(
          board = gameState.board,
          round = gameState.round + 1,
          gameStatus = GameStatus.Playing
        )
        false
      } else {
        println("Game over.")
        gameState = gameState.copy(
          board = gameState.board,
          round = gameState.round,
          gameStatus = GameStatus.FinishedByNoMoves
        )

        false
      }
    }

    onChangeState.invoke(gameState, calculateScores())
    return moved;
  }

  private fun calculateScores(): List<Pair<Player, Int>> =
    players.map { it to gameState.board.pieces.count { p -> p == it.piece } }
}