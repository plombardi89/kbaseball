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