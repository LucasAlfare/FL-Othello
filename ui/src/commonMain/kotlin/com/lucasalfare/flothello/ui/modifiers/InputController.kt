package com.lucasalfare.flothello.ui.modifiers

import androidx.compose.ui.Modifier
import com.lucasalfare.flothello.ui.state.GameStateHolder

expect fun Modifier.inputController(
  state: GameStateHolder,
  coords: Pair<Int, Int>,
): Modifier