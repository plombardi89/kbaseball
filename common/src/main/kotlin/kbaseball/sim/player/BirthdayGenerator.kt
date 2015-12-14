package kbaseball.sim.player

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Generates random birthdays given the current year and an age.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 */


class BirthdayGenerator(private val random: Random) {
  fun generate(currentDate: LocalDate, age: Int): LocalDate {
    val start = currentDate.minusYears(age.toLong() + 1)
    val end = start.plusYears(1)
    val daysBetween = ChronoUnit.DAYS.between(start, end).toInt()
    return start.plusDays(random.nextInt((daysBetween + 1)).toLong())
  }
}