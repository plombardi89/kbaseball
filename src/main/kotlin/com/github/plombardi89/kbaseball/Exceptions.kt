package com.github.plombardi89.kbaseball


fun notExpected(message: String, expected: Any?, was: Any?): String {
  return "${message} (expected: ${expected.toString()}, was: ${was.toString()})"
}

fun belowIntBound(target: String, floor: Int, was: Int): IllegalArgumentException {
  return IllegalArgumentException("${target.capitalize()} is below allowed bound (floor: ${floor}, was: ${was})")
}

fun aboveIntBound(target: String, ceiling: Int, was: Int): IllegalArgumentException {
  return IllegalArgumentException("${target.capitalize()} is above allowed bound (ceiling: ${ceiling}, was: ${was})")
}
