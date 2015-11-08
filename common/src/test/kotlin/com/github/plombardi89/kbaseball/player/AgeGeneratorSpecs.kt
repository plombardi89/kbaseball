package com.github.plombardi89.kbaseball.player

import org.jetbrains.spek.api.Spek
import org.assertj.core.api.Assertions.*

class AgeGeneratorSpecs: Spek() { init {
  given("Age generator with default configuration") {
    val generator = AgeGenerator()
    on("generating a draft eligible age 1000 times") {
      val values = 1000.downTo(0).fold(linkedListOf<Int>()) { ages, it ->
        ages.add(generator.nextAmateurDraftEligibleAge())
        ages
      }
      it("should generate ages between ${AgeGenerator.MIN_DRAFT_AGE} and ${AgeGenerator.MAX_DRAFT_AGE}") {
        for (age in values) {
          assertThat(age)
              .isGreaterThanOrEqualTo(AgeGenerator.MIN_DRAFT_AGE)
              .isLessThanOrEqualTo(AgeGenerator.MAX_DRAFT_AGE)
        }
      }
    }
  }
}}