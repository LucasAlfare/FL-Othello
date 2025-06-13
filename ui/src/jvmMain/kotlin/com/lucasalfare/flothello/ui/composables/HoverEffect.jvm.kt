package com.lucasalfare.flothello.ui.composables

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

// @formatter:off
@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.hoverEffect(onHover: () -> Unit, onExit: () -> Unit): Modifier =
  onPointerEvent(PointerEventType.Enter) { onHover() }
    .onPointerEvent(PointerEventType.Exit) { onExit() }
// @formatter:off