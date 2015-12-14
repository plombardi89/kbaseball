package kbaseball.sim.player

import kbaseball.exception.aboveIntBound
import kbaseball.exception.belowIntBound
import kbaseball.util.SkewedRandom
import org.slf4j.LoggerFactory


class AgeGenerator(private val random: SkewedRandom) {

  constructor(): this(SkewedRandom())

  companion object {
    val MIN_AGE = 17
    val MIN_DRAFT_AGE = 17
    val MAX_AGE = 45
    val MAX_DRAFT_AGE = 23

    val logger = LoggerFactory.getLogger(AgeGenerator::class.java)
  }

  /**
   * Generates a new [Int] representing an age between the minimum and maximum bounds of the provided range.
   *
   * @param range the range in which ages will be generated.
   * @param skew the degree to which the values cluster around the mode of the distribution; higher values mean tighter
   *              clustering
   * @param bias the tendency of the mode to approach the min, max or midpoint value; negative values bias toward min,
   *              positive values toward max.
   */
  public fun nextAge(range: IntRange, skew: Double = 0.0, bias: Double = 0.0): Int {
    val age = random.nextInt(checkRange(range), skew, bias)
    logger.debug("generated age (value: {}, range: {}, skew: {}, bias: {})", age, range, skew, bias)
    return age
  }

  /**
   * Generates an age that is within the parameters of allowed values for the amateur draft.
   */
  public fun nextAmateurDraftEligibleAge(): Int {
    return nextAge(MIN_DRAFT_AGE..MAX_DRAFT_AGE, 0.5, 0.7)
  }

  private fun checkRange(range: IntRange): IntRange {
    when {
      MIN_AGE > range.start    -> throw belowIntBound("Minimum age", MIN_AGE, range.start)
      MAX_AGE < range.end      -> throw aboveIntBound("Maximum age", MAX_AGE, range.end)
      range.start >= range.end -> throw IllegalArgumentException("Age minimum is greater than or equal to age maximum")
    }

    return range
  }
}