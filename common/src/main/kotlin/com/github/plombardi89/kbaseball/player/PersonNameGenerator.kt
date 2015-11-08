package com.github.plombardi89.kbaseball.player

import com.github.plombardi89.kbaseball.model.Sex
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
  fun nextFullName(): String {
    return nextForename() + " " + nextSurname()
  }

  /**
   * Retrieves the next person's name with respect for a specific sex of the forename.
   */
  fun nextFullName(sex: Sex): String {
    return if (sex == Sex.MALE) nextMaleForename() else nextFemaleForename() + " " + nextSurname()
  }

  /**
   * Retrieves the next person's forename without concern for gender.
   */
  fun nextForename(): String {
    return if (random.nextBoolean()) nextMaleForename() else nextFemaleForename()
  }

  /**
   * Retrieves the next male name.
   */
  fun nextMaleForename(): String {
    return maleNames.random()
  }

  /**
   * Retrieves the next female name.
   */
  fun nextFemaleForename(): String {
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