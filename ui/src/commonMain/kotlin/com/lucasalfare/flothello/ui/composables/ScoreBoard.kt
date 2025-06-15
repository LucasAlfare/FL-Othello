package com.lucasalfare.flothello.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.core.game.State
import com.lucasalfare.flothello.core.game.Status

@Composable
fun ScoreBoard(state: State) {
  val (black: Int?, white: Int?) = remember(state) {
    val counts = state.board.countPieces()
    counts[Piece.Black] to counts[Piece.White]
  }

  Column {
    Row(
      horizontalArrangement = Arrangement.spacedBy(32.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(16.dp)
    ) {
      ScoreItem("⚫", black!!, Color.Black)
      ScoreItem("⚪", white!!, Color.White)
    }

    if (state.status == Status.FinishedByNoMoves) {
      Text(text = "Game finished!")
    }
  }
}

@Composable
private fun ScoreItem(label: String, score: Int, color: Color) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      text = label,
      color = color,
      modifier = Modifier.padding(end = 8.dp)
    )
    Text(text = score.toString())
  }
}