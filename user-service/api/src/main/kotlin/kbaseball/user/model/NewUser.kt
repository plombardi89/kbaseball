/*
Copyright (C) 2015  Philip Lombardi <plombardi89@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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