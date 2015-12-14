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

package kbaseball.user.resource

import kbaseball.email.EmailContentTemplate
import kbaseball.email.Emailer
import kbaseball.user.db.UserMapper
import kbaseball.user.exception.InvalidPassword
import kbaseball.user.model.*
import org.apache.ibatis.session.SqlSession
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import java.time.Instant
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

/**
 * The user account resource. Provides an API for viewing and managing user accounts.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 * @since 1.0
 */


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
class UserResource(
    private val passwordValidator: PasswordValidator,
    private val emailer: Emailer,
    private val welcomeEmail: EmailContentTemplate) {

  private val log = LoggerFactory.getLogger(UserResource::class.java)

  private fun getUserMapper(sql: SqlSession): UserMapper = sql.getMapper(UserMapper::class.java)

  /**
   * Returns a collection of all known users in the system.
   *
   * @param sql an injected mybatis sql session.
   */
  @GET
  fun getUsers(@Context sql: SqlSession): Collection<User> {
    val users = getUserMapper(sql)
    return users.getUsers()
  }

  /**
   * Registers a new user in the system.
   *
   * @param sql an injected mybatis sql session.
   * @param newUser the new user's registration record.
   * @throws UserExistsAlready if the username is already taken by another user.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  fun registerUser(@Context sql: SqlSession, newUser: NewUser) {
    val users = getUserMapper(sql)
    validatePasswordMeetsRequirements(newUser.password)

    if (users.getUserByUsernameOrEmail(newUser.username, newUser.email) == null) {
      val hashedNewUser = newUser.copy(password = BCrypt.hashpw(newUser.password, BCrypt.gensalt()))

      users.createUser(hashedNewUser)
      try {
        sql.commit()
      } catch (any: Exception) {
        throw any
      }

      //val userWelcomeEmail = welcomeEmail.withVariable("activationUrl", newUser.activationCode)
      //emailer.sendEmail(newUser.email, "Welcome to KBaseball!", userWelcomeEmail.render())
    } else {
      throw UserExistsAlready(newUser.username)
    }
  }

  /**
   * Activates a pending user in the system.
   *
   * @param sql an injected mybatis sql session.
   * @param email the email used during user registration.
   * @param token the auto-generated registration token.
   * @throws PendingUserNotFound if the provided email and token do not map to a known pending user.
   */
  @POST
  @Path("/activate")
  fun activateUser(@Context sql: SqlSession, @QueryParam("email") email: String, @QueryParam("token") token: UUID) {
    val users = getUserMapper(sql)
    users.activateUser(email, token)
    sql.commit()
  }

  /**
   * Retrieves an existing user in the system.
   *
   * @param sql an injected mybatis sql session.
   * @param username the username of the user.
   * @throws UserNotFound if the provided username does not map to a known user.
   */
  @GET
  @Path("/{username}")
  fun getUser(@Context sql: SqlSession, @PathParam("username") username: String): User {
    val users = getUserMapper(sql)
    return users.getUserByUsername(username) ?: throw UserNotFound(username)
  }

  private fun validatePasswordMeetsRequirements(password: String): String {
    val result = passwordValidator.validate(PasswordData(password))
    return if (result.isValid) password else throw InvalidPassword("invalid password", result.details)
  }
}