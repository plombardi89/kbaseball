package kbaseball.service.auth

import com.fasterxml.jackson.annotation.JsonProperty


data class UserCredential(
    @JsonProperty("username")
    val username: String,

    @JsonProperty("password")
    val password: String
)