package com.lucasalfare.flothello.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(
      text = "Othello",
      fontSize = 36.sp,
      modifier = Modifier
        .padding(18.dp)
        .align(Alignment.TopCenter)
    )

    Column(modifier = Modifier.align(Alignment.Center)) {
      Button(onClick = onStartGame) {
        Text("Jogar")
      }
    }
  }
}