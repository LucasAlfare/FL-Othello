package com.lucasalfare.flothello.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.ui.composables.MainGame
import com.lucasalfare.flothello.ui.state.GameStateHolder

@Composable
fun GameScreen(
  onBackToMenu: () -> Unit
) {
  var gameId by remember { mutableStateOf(0) }

  val game = remember(gameId) {
    Game(HumanPlayer(Piece.Black), AIPlayer(Piece.White))
  }

  val stateHolder = remember(game) {
    GameStateHolder(game)
  }

  Column(modifier = Modifier.fillMaxSize()) {
    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
      MainGame(stateHolder)
    }

    Button(
      onClick = { gameId++ },
      modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
    ) {
      Text("Nova Partida")
    }

    Button(
      onClick = onBackToMenu,
      modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
    ) {
      Text("Voltar ao Menu")
    }
  }
}