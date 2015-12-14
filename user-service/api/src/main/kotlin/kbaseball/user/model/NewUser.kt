package kbaseball.user.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.Length
import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotNull


data class NewUser(
    val username: String,
    val password: String,
    val email: String,
    val creationTime: OffsetDateTime,
    val activationCode: UUID
) {
  @JsonCreator constructor(
      @JsonProperty("username")
      @NotNull
      //@Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{3,30}$")
      username: String,

      @JsonProperty("password")
      @Length(min = 8)
      @NotNull
      password: String,

      @JsonProperty("email")
      @Email
      @NotNull
      email: String
  ): this(username, password, email, OffsetDateTime.now(), UUID.randomUUID())
}