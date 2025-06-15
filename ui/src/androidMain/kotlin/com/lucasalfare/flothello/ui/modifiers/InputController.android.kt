package com.lucasalfare.flothello.ui.modifiers

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.ui.state.GameStateHolder

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

