package kbaseball.sim.model

import com.fasterxml.jackson.annotation.JsonProperty


data class BattingRatings (
    @JsonProperty("discipline") val discipline: Int,
    @JsonProperty("contact") val contact: Int,
    @JsonProperty("power") val power: Int,
    @JsonProperty("speed") val speed: Int
)