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
import androidx.compose.material.Text
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
import com.lucasalfare.flothello.core.game.Status

class GameStateHolder(val game: Game) {
  var currentState by mutableStateOf(game.state)
    private set

  var currentPlayer by mutableStateOf(game.currentPlayer)

  init {
    game.onStateUpdate = {
      currentState = it
      currentPlayer = game.currentPlayer
      println(it.status)
    }
  }
}

fun main() = application {
  val p1 = HumanPlayer(Piece.Black)
  val p2 = AIPlayer(p1.piece.opposite())
  val game = remember { Game(p1, p2) }
  val stateHolder = remember { GameStateHolder(game) }

  Window(
    state = WindowState(position = WindowPosition(Alignment.Center)),
    onCloseRequest = { exitApplication() }
  ) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
      Column {
        val s = when (stateHolder.currentState.status) {
          Status.Playing -> "Make your move ${stateHolder.currentPlayer.piece.sign}!"
          else -> "Game finished!"
        }

        Text(s)
        GameBoard(stateHolder)
      }
    }
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
              val currentPlayer = state.game.currentPlayer
              if (currentPlayer is HumanPlayer) {
                affected = state.currentState.board.findAffectedPositions(currentPlayer.piece, x, y)
              }
            },
            onExit = {
              affected = emptyList()
            },
            onClick = {
              if (state.game.currentPlayer is HumanPlayer && affected.isNotEmpty()) {
                (state.game.currentPlayer as HumanPlayer).targetCoordsDefiner = { x to y }
                state.game.step()
                affected = emptyList()
              }
            },
            onAnimationEnd = {
              affected = emptyList()
              if (state.game.currentPlayer is AIPlayer) {
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