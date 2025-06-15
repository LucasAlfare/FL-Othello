package com.lucasalfare.flothello.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.ui.modifiers.inputController
import com.lucasalfare.flothello.ui.state.GameStateHolder

@Composable
fun GameBoardCell(
  coord: Pair<Int, Int>,
  stateHolder: GameStateHolder
) {
  val piece = stateHolder.currentState.board.get(coord.first, coord.second)
  val isAffected = coord in stateHolder.affected
  val affectedColor = if (isAffected) Color.Blue else Color(0xFF1bb380)

  val discColor = when (piece) {
    Piece.Black -> Color.Black
    Piece.White -> Color.White
    else -> null
  }

  val scale = remember { Animatable(0f) }

  LaunchedEffect(piece) {
    if (discColor != null) {
      scale.snapTo(0f)
      scale.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 100)
      )
      stateHolder.affected = emptyList()
      if (stateHolder.game.currentPlayer is AIPlayer) {
        stateHolder.game.step()
      }
    }
  }

  Box(
    modifier = Modifier
      .size(40.dp)
      .inputController(stateHolder, coord)
      .border(1.dp, Color(0xFF636363), RoundedCornerShape(4.dp))
      .padding(1.dp)
      .background(affectedColor),
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