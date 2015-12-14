package kbaseball.sim.datagen

import kbaseball.sim.model.*
import kbaseball.sim.player.AgeGenerator
import kbaseball.sim.player.BirthdayGenerator
import kbaseball.sim.player.PersonNameGenerator
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDate

/**
 * A generator of [Player] instances.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 */


class PlayerGenerator(
    private val options: PlayerGeneratorOptions,
    private val ageGenerator: AgeGenerator,
    private val nameGenerator: PersonNameGenerator,
    private val birthdayGenerator: BirthdayGenerator
): Generator<Player> {

  companion object {
    val log = LoggerFactory.getLogger(PlayerGenerator::class.java)
  }

  override fun generate(): Player {
    log.debug("generating new player...")
    val name = generateName()
    val age = ageGenerator.nextAge(17..30)
    val birthday = birthdayGenerator.generate(options.generationDate, age)



    val pitchingPotentials = generatePitchingPotentials()
    val pitchingActuals = generatePitchingActuals(pitchingPotentials)

    val battingPotentials = generateBattingPotentials()
    val battingActuals = generateBattingActuals(battingPotentials)

    val fieldingPotentials = generateFieldingPotentials()
    val fieldingActuals = generateFieldingActuals(fieldingPotentials)

    return Player("", "", 0, LocalDate.now(), Handedness.LEFT, Handedness.LEFT)
  }

  private fun generateName(): String {
    return nameGenerator.nextFullName(Sex.MALE)
  }

  private fun generatePitchingPotentials(): PitchingRatings {
    return PitchingRatings(1, 1, 1, 1, 1)
  }

  private fun generatePitchingActuals(potentials: PitchingRatings): PitchingRatings {
    return PitchingRatings(1, 1, 1, 1, 1)
  }

  private fun generateBattingPotentials(): BattingRatings {
    return BattingRatings(1, 1, 1, 1)
  }

  private fun generateBattingActuals(potentials: BattingRatings): BattingRatings {
    return BattingRatings(1, 1, 1, 1)
  }

  private fun generateFieldingPotentials(): FieldingRatings {
    return FieldingRatings(1, 1, 1)
  }

  private fun generateFieldingActuals(potentials: FieldingRatings): FieldingRatings {
    return FieldingRatings(1, 1, 1)
  }
}