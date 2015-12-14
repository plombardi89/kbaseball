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

package kbaseball.user.db.mapper

import kbaseball.user.model.NewUser
import kbaseball.user.model.User
import org.apache.ibatis.annotations.Param
import java.util.*


public interface UserMapper {

  /**
   * Activates a user given a record can be located with the associated email address and activation token.
   *
   * @param email the email address of the activating user.
   * @param token the generated activation token.
   */
  public fun activateUser(@Param("email") email: String, @Param("token") token: UUID)

  /**
   * Creates a new user in the system.
   *
   * @param user the new user to create.
   */
  public fun createUser(user: NewUser)

  /**
   * Deletes a user by their system identifier (database ID).
   *
   * @param id the ID of the user to delete.
   */
  public fun deleteUserById(@Param("id") id: Int)

  /**
   * Deletes a user by their username.
   *
   * @param username the user's username.
   */
  public fun deleteUserByUsername(@Param("username") username: String)

  /**
   * Retrieves all the users in the database.
   *
   * todo(plombardi): really need to add pagination
   */
  public fun getUsers(): Collection<User>

  /**
   * Retrieves a user by their system identifier.
   *
   * @param id the system identifier (database ID) for the user.
   */
  public fun getUserById(@Param("id") id: String): User?

  /**
   * Retrieves a user by their username or handle.
   *
   * @param username the users username.
   */
  public fun getUserByUsername(@Param("username") username: String): User?

  /**
   * Retrieves a User by their username or email address. Mostly useful for validating existence during user creation.
   *
   * @param username the user's username.
   */
  public fun getUserByUsernameOrEmail(@Param("username") username: String, @Param("email") email: String): User?

  /**
   * Retrieve a user their email address.
   *
   * @param email the users email address.
   */
  public fun getUserByEmail(@Param("email") email: String): User?
}