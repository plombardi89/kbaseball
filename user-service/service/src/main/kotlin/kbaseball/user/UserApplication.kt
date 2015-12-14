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

import com.github.javaplugs.mybatis.InstantHandler
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenHeader
import com.loginbox.dropwizard.mybatis.MybatisBundle
import io.dropwizard.Application
import io.dropwizard.db.PooledDataSourceFactory
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import kbaseball.email.EmailContentTemplate
import kbaseball.service.auth.JwtJerseyConfigurator
import kbaseball.user.exception.mapper.UserExistsAlreadyExceptionMapper
import kbaseball.user.exception.mapper.UserNotFoundExceptionMapper
import kbaseball.user.resource.LoginResource
import kbaseball.user.resource.UserResource
import org.apache.ibatis.session.Configuration
import org.passay.*
import java.time.Instant


class UserApplication : Application<UserApplicationConfiguration>() {

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      UserApplication().run(*args)
    }
  }

  private val mybatisBundle = object : MybatisBundle<UserApplicationConfiguration>() {

    override fun getDataSourceFactory(configuration: UserApplicationConfiguration?): PooledDataSourceFactory? {
      return configuration!!.dataSourceFactory
    }

    override fun configureMybatis(configuration: Configuration?) {
      configuration?.typeHandlerRegistry?.register("com.github.javaplugs.mybatis")
      configuration?.mapperRegistry?.addMappers("kbaseball.user.db.mapper")
    }
  }

  override fun initialize(bootstrap: Bootstrap<UserApplicationConfiguration>?) {
    bootstrap!!

    bootstrap.addBundle(object : MigrationsBundle<UserApplicationConfiguration>() {
      override fun getDataSourceFactory(configuration: UserApplicationConfiguration?): PooledDataSourceFactory? {
        return configuration!!.dataSourceFactory
      }
    })

    bootstrap.addBundle(mybatisBundle)
  }

  override fun run(config: UserApplicationConfiguration, environment: Environment?) {
    environment!!

    val emailer = config.email.buildEmailer()
    val welcomeEmail = EmailContentTemplate.Factory.newTemplate("email/welcome.html", "email/welcome.txt")
        .withVariable("kbaseballUrl", "https://example.org/kbaseball")

    val userResource = UserResource(buildPasswordValidator(), emailer, welcomeEmail)

    // configure jersey to work with JSON Web Tokens
    JwtJerseyConfigurator().configureJersey(environment.jersey(), config.getJwtTokenSecret())

    val loginResource = LoginResource(
        JsonWebTokenHeader.HS512(),
        HmacSHA512Signer(config.getJwtTokenSecret()))

    environment.jersey().register(loginResource)

    environment.jersey().register(userResource)

    environment.jersey().register(UserExistsAlreadyExceptionMapper::class.java)
    environment.jersey().register(UserNotFoundExceptionMapper::class.java)
  }

  private fun buildPasswordValidator(): PasswordValidator {
    return PasswordValidator(listOf(
        LengthRule(8, 100),
        CharacterRule(EnglishCharacterData.Digit, 1),
        CharacterRule(EnglishCharacterData.LowerCase, 1)
    ))
  }
}