package kbaseball.sim.model

import com.fasterxml.jackson.annotation.JsonProperty


data class FieldingRatings(
    @JsonProperty("arm") val arm: Int,
    @JsonProperty("range") val range: Int,
    @JsonProperty("glove") val glove: Int
)
