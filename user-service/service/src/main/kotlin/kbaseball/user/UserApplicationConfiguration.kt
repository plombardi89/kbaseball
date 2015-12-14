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

package kbaseball.user

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import kbaseball.email.EmailerFactory
import org.hibernate.validator.constraints.NotEmpty
import javax.validation.constraints.NotNull

class UserApplicationConfiguration
  @JsonCreator
  constructor(
      @JsonProperty("jwtTokenSecret")
      @NotEmpty
      private val jwtTokenSecret: String,

      @JsonProperty("database")
      @NotNull
      val dataSourceFactory: DataSourceFactory,

      @JsonProperty("email")
      @NotNull
      val email: EmailerFactory
  ): Configuration() {

  fun getJwtTokenSecret(): ByteArray {
    return jwtTokenSecret.toByteArray(Charsets.UTF_8)
  }
}