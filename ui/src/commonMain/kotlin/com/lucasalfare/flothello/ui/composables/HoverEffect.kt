package com.lucasalfare.flothello.ui.composables

import androidx.compose.ui.Modifier

expect fun Modifier.hoverEffect(onHover: () -> Unit, onExit: () -> Unit): Modifier