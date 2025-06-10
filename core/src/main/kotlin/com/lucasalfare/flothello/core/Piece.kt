package com.lucasalfare.flothello.core

enum class Piece(val sign: String) {
  White("⚪"),
  Black("⚫"),
  Empty("🔷"),
  Invalid("❌");

  fun opposite(): Piece = when (this) {
    Black -> White
    White -> Black
    Empty -> Empty
    else -> Invalid
  }
}