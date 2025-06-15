package com.lucasalfare.flothello.ui.composables

import androidx.compose.ui.Modifier

actual fun Modifier.hoverEffect(
  onHover: () -> Unit,
  onExit: () -> Unit
): Modifier = this