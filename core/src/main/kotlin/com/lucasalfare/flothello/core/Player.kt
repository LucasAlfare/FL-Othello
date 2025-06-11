package com.lucasalfare.flothello.core

interface Player {

  val piece: Piece

  fun doMove(board: Board): Boolean

  fun hasValidMoves(board: Board): Boolean
}