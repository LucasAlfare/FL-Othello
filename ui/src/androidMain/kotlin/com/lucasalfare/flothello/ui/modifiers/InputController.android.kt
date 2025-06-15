package com.lucasalfare.flothello.ui.modifiers

import android.util.Log
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.ui.state.GameStateHolder
import kotlinx.coroutines.awaitCancellation

actual fun Modifier.inputController(
  state: GameStateHolder,
  coords: Pair<Int, Int>,
): Modifier = pointerInput(Unit) {
  detectTapGestures(
    onTap = {
      if (state.hoverCoord != coords) {
        state.hoverCoord = coords
        state.computeAffected()
      } else if (state.affected.isNotEmpty() && state.game.currentPlayer is HumanPlayer) {
        (state.game.currentPlayer as HumanPlayer).targetCoordsDefiner = { coords }
        state.game.step()
        state.hoverCoord = null
      }
    }
  )
}

