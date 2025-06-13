package com.lucasalfare.flothello.core


fun main() {
  println("We", "are", "not", "doing", "nothing", "here", "for", "now", ":)")
}

// I created my own println() function implementation to accept multiple args in same call (multiple prints in same line). Why doesn't kotlin has this by default in stdlib?
fun println(vararg items: Any) {
  items.forEach { print("$it ") }
  print("\n")
}