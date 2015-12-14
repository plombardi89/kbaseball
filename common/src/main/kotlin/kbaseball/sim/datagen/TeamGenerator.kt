package kbaseball.sim.datagen

import kbaseball.sim.model.Team


class TeamGenerator: Generator<Team> {
  override fun generate(): Team {
    return Team("foo", "bar")
  }
}