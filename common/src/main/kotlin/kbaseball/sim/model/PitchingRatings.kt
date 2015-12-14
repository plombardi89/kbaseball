package kbaseball.sim.model

import com.fasterxml.jackson.annotation.JsonProperty


data class PitchingRatings(
    @JsonProperty("velocity") val velocity: Int,
    @JsonProperty("movement") val finesse: Int,
    @JsonProperty("control") val control: Int,
    @JsonProperty("stuff") val stuff: Int,
    @JsonProperty("holdRunners") val holdRunners: Int
)