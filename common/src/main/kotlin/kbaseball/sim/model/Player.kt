package kbaseball.sim.model

import java.time.LocalDate


class Player(
    val id: String,
    val name: String,
    val age: Int,
    val birthday: LocalDate,
    val throwingHand: Handedness,
    val battingHand: Handedness
) {
}