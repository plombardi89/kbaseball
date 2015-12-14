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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime


@JsonIgnoreProperties("password")
class User @JsonCreator constructor(
    @JsonProperty("id")
    id: Int,

    @JsonProperty("username")
    val username: String,

    @JsonProperty("password")
    val password: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("creationTime")
    val creationTime: OffsetDateTime,

    @JsonProperty("activationTime")
    val activationTime: OffsetDateTime?,

    @JsonProperty("banished")
    val banished: Boolean,

    @JsonProperty("banishmentTime")
    val banishmentTime: OffsetDateTime?,

    @JsonProperty("banishmentLength")
    val banishmentLength: Long
) {
  val id: String = id.toString()
}