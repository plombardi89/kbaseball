package kbaseball.sim.model

import com.fasterxml.jackson.annotation.JsonProperty


abstract class Model(@JsonProperty("id") protected val id: String)