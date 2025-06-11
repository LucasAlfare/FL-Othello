@file:OptIn(ExperimentalComposeUiApi::class)

package com.lucasalfare.flothello.desktop

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.flothello.core.*
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.GameState

class GameStateHolder(val game: Game) {
  var currentState by mutableStateOf(game.gameState)
    private set

  init {
    game.onChangeState = { state, scores ->
      currentState = state
    }
  }
}

fun main() = application {
  val p1 = HumanPlayer(Piece.Black)
  val p2 = AIPlayer(p1.piece.opposite())
  val board = Board()
  val gameState = GameState(board, 0, p1)
  val game = remember { Game(gameState, listOf(p1, p2)) }
  val stateHolder = remember { GameStateHolder(game) }

  Window(
    state = WindowState(position = WindowPosition(Alignment.Center)),
    onCloseRequest = { exitApplication() }
  ) {
    GameBoard(stateHolder)
  }
}

@Composable
fun GameBoard(state: GameStateHolder) {
  var affected by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }

  Column(modifier = Modifier.padding(4.dp).background(Color.DarkGray)) {
    repeat(BOARD_SIZE) { y ->
      Row {
        repeat(BOARD_SIZE) { x ->
          val piece = state.currentState.board.get(x, y)
          val isAffected = (x to y) in affected
          GameBoardCell(
            piece = piece,
            isAffected = isAffected,
            onHover = {
              val currentPlayer = state.currentState.currentPlayer
              affected = state.currentState.board.findAffectedPositions(currentPlayer.piece, x, y)
            },
            onExit = {
              affected = emptyList()
            },
            onClick = {
              state.currentState.currentPlayer.let {
                if (it is HumanPlayer) {
                  it.targetCoordsDefiner = { x to y }
                }

                if (affected.isNotEmpty()) {
                  state.game.step()
                }

                affected = emptyList()
              }
            },
            onAnimationEnd = {
              affected = emptyList()
              if (state.currentState.currentPlayer is AIPlayer) {
                state.game.step()
              }
            }
          )
        }
      }
    }
  }
}

@Composable
fun GameBoardCell(
  piece: Piece,
  isAffected: Boolean = false,
  onHover: () -> Unit = {},
  onExit: () -> Unit = {},
  onClick: () -> Unit = {},
  onAnimationEnd: () -> Unit = {}
) {
  val discColor = when (piece) {
    Piece.Black -> Color.Black
    Piece.White -> Color.White
    else -> null
  }

  val affectedColor = if (isAffected) Color(0xFF8899cc) else Color(0xFF1bb380)

  val scale = remember { Animatable(0f) }

  LaunchedEffect(piece) {
    if (discColor != null) {
      scale.snapTo(0f)
      scale.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 100)
      )
      onAnimationEnd()
    }
  }

  Box(
    modifier = Modifier
      .size(40.dp)
      .pointerMoveFilter(
        onEnter = {
          onHover()
          false
        },
        onExit = {
          onExit()
          false
        }
      )
      .border(1.dp, Color(0xFF636363), RoundedCornerShape(4.dp))
      .padding(1.dp)
      .background(affectedColor)
      .clickable { onClick() },
    contentAlignment = Alignment.Center
  ) {
    if (discColor != null) {
      Canvas(
        modifier = Modifier
          .size(24.dp)
          .graphicsLayer(
            scaleX = scale.value,
            scaleY = scale.value
          )
      ) {
        drawCircle(color = discColor)
      }
    }
  }
}