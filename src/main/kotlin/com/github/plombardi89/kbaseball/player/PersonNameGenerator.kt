package com.github.plombardi89.kbaseball.player

import java.util.*


class PersonNameGenerator(
    private val random: Random,
    private val maleNames: List<String>,
    private val femaleNames: List<String>,
    private val surnames: List<String>) {

  private val DEFAULT_NAME = "Winthrop"

  /**
   * Retrieves the next person's name without concern for gender of the forename.
   */
  fun nextName(): String {
    return nextForename() + " " + nextSurname()
  }

  /**
   * Retrieves the next person's forename without concern for gender.
   */
  fun nextForename(): String {
    return if (random.nextBoolean()) nextMaleName() else nextFemaleName()
  }

  /**
   * Retrieves the next male name.
   */
  fun nextMaleName(): String {
    return maleNames.random()
  }

  /**
   * Retrieves the next female name.
   */
  fun nextFemaleName(): String {
    return femaleNames.random()
  }

  /**
   * Retrieves the next surname.
   */
  fun nextSurname(): String {
    return surnames.random()
  }

  /**
   * Retrieves a random entry from the list.
   */
  private fun List<String>.random(): String {
    return if (this.isNotEmpty()) this[random.nextInt(this.size)] else DEFAULT_NAME
  }
}