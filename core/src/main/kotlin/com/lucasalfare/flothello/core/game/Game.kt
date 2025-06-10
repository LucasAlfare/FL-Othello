package com.lucasalfare.flothello.core.game

import com.lucasalfare.flothello.core.Player

class Game(
  var gameState: GameState,
  val players: List<Player>,
  var onChangeState: (state: GameState, scores: MutableList<Pair<Player, Int>>) -> Unit = { _, _ -> }
) {

  val currentPlayer get() = players[gameState.round % players.size]

  val scores
    get(): MutableList<Pair<Player, Int>> {
      val scores = mutableListOf<Pair<Player, Int>>()
      players.forEach { p ->
        scores += p to gameState.board.pieces.count { it == p.piece }
      }
      return scores
    }

  fun step(): Boolean {
    // TODO: probably doesn't checks existence of valid moves for all players but this seems not affect the game flow1
    val hasMoves = players.any { gameState.board.getValidMoves(it.piece).isNotEmpty() }
    val gameStatus = if (hasMoves) GameStatus.Playing else GameStatus.FinishedByNoMoves

    val nextBoard = gameState.board.deepCopy()
    val moved = if (gameStatus == GameStatus.Playing) currentPlayer.doMove(nextBoard) else false

    gameState = gameState.copy(
      board = if (moved) nextBoard else gameState.board,
      round = gameState.round + 1,
      gameStatus = gameStatus
    )
    onChangeState.invoke(gameState, scores)
    return moved
  }
}