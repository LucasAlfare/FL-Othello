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
import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Constants
import com.lucasalfare.flothello.core.Game

fun main() = application {
  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center)
    ),
    onCloseRequest = { exitApplication() }
  ) {
    GameBoard()
  }
}

@Composable
fun GameBoard() {
  Column(
    modifier = Modifier
      .padding(4.dp)
      .background(Color.DarkGray)
  ) {
    // está tudo bem essa variável ficar aqui? Ela deveria ficar em outro lugar? Exemplo: no componente pai? Sei lá, só uma dúvida mesmo.
    var game by remember {
      mutableStateOf(
        Game(
          userColor = Constants.BLACK,
          userStarts = true,
          board = Board()
        )
      )
    }

    var affected by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }

    repeat(Constants.BOARD_SIZE) { y: Int ->
      Row {
        repeat(Constants.BOARD_SIZE) { x: Int ->
          val value = game.board.get(x, y)
          val isAffected = (x to y) in affected

          GameBoardCell(
            value = value,
            affected = isAffected,
            onHover = {
              affected = game.board.getAffectedCoordinates(Constants.BLACK, x, y)
            },
            onExit = {
              affected = emptyList()
            },
            onClick = {
              affected = emptyList()
              game = game.step(x, y)
              println("black: ${game.board.numBlack()}, white: ${game.board.numWhite()}")
            },
            onAnimationEnd = {
              if (game.currentRoundColor != game.userColor) {
                affected = emptyList()
                // essa estratégia aqui é horrível?
                game = game.step(-1, -1) // forces step to make AI to play
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
  value: Int,
  affected: Boolean = false,
  onHover: () -> Unit = {},
  onExit: () -> Unit = {},
  onClick: () -> Unit = {},
  onAnimationEnd: () -> Unit = {}
) {
  val discColor = when (value) {
    Constants.BLACK -> Color.Black
    Constants.WHITE -> Color.White
    else -> null
  }

  val affectedColor = if (affected) Color(0xFF8899cc) else Color(0xFF1bb380)

  val scale = remember { Animatable(0f) }

  LaunchedEffect(value) {
    if (discColor != null) {
      scale.snapTo(0f)
      scale.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 500)
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