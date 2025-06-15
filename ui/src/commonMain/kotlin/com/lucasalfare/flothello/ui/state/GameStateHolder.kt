package com.lucasalfare.flothello.ui.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.State

class GameStateHolder(val game: Game) {
  var hoverCoord by mutableStateOf<Pair<Int, Int>?>(null)

  var currentState by mutableStateOf(game.state)
    private set

  var affected by mutableStateOf<List<Pair<Int, Int>>>(emptyList())

  init {
    game.onStateUpdate = {
      currentState = it
    }
  }

  fun computeAffected(coord: Pair<Int, Int>? = hoverCoord) {
    if (coord != null) {
      affected = currentState.board.findAffectedPositions(
        currentState.currentPlayer.piece,
        coord.first,
        coord.second
      )
    }
  }
}

//class GameStateHolder(val game: Game) {
//  var currentState by mutableStateOf(game.state)
//    private set
//
//  var currentCoords by mutableStateOf(-1 to -1)
//
//  var affected by mutableStateOf<List<Pair<Int, Int>>>(emptyList())
//
//  init {
//    game.onStateUpdate = {
//      currentState = it
//    }
//  }
//
//  fun computeAffected(coord: Pair<Int, Int>) {
//    if (currentCoords != -1 to -1) {
//      affected = currentState.board.findAffectedPositions(
//        currentState.currentPlayer.piece,
//        coord.first,
//        coord.second
//      )
//    }
//  }
//}