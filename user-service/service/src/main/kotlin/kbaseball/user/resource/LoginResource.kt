package kbaseball.user.resource

import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenSigner
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenClaim
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenHeader
import kbaseball.service.auth.AuthenticationFailure
import kbaseball.service.auth.UserCredential
import kbaseball.user.db.mapper.UserMapper
import org.apache.ibatis.session.SqlSession
import org.joda.time.DateTime
import org.springframework.security.crypto.bcrypt.BCrypt
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context

/**
 * Handles authentication requests from the main application.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 * @since 1.0
 */

@Path("/users/login")
class LoginResource(
    private val hmacAlgorithm: JsonWebTokenHeader,
    private val tokenSigner: JsonWebTokenSigner
) {

  private fun getUserMapper(sql: SqlSession): UserMapper = sql.getMapper(UserMapper::class.java)

  /**
   * Verifies if a users credentials are valid or not.
   *
   * @param sql injected [SqlSession] for mybatis.
   * @param credentials the provided user authentication details.
   * @throws AuthenticationFailure if the user does not exist or the password is invalid.
   */
  @POST
  fun login(@Context sql: SqlSession, credentials: UserCredential): Map<String, String> {
    val users = getUserMapper(sql)

    val user = users.getUserByUsername(credentials.username) ?: throw AuthenticationFailure()

    if (BCrypt.checkpw(credentials.password, user.password)) {
      val jwt = JsonWebToken.builder()
          .header(hmacAlgorithm)
          .claim(JsonWebTokenClaim.builder().subject(user.username).issuedAt(DateTime()).build())
          .build()
      val signedJwt = tokenSigner.sign(jwt)
      return mapOf("token" to signedJwt)
    } else {
      throw AuthenticationFailure()
    }
  }
}