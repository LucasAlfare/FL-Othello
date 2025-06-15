package com.lucasalfare.flothello.ui.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.ui.state.GameStateHolder

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.inputController(
  state: GameStateHolder,
  coords: Pair<Int, Int>,
): Modifier = this
  .onPointerEvent(PointerEventType.Enter) {
    state.hoverCoord = coords
    state.computeAffected()
  }
  .onPointerEvent(PointerEventType.Exit) {
    state.hoverCoord = null
    state.affected = emptyList()
  }
  .clickable {
    if (state.game.currentPlayer is HumanPlayer && state.affected.isNotEmpty()) {
      (state.game.currentPlayer as HumanPlayer).targetCoordsDefiner = { coords }
      state.game.step()
      state.affected = emptyList()
      state.hoverCoord = null
    }
  }