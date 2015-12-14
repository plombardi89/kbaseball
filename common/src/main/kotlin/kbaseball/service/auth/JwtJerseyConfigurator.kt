package kbaseball.service.auth

import com.github.toastshaman.dropwizard.auth.jwt.JWTAuthFilter
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Verifier
import com.github.toastshaman.dropwizard.auth.jwt.parser.DefaultJsonWebTokenParser
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.jersey.setup.JerseyEnvironment
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature
import java.security.Principal


class JwtJerseyConfigurator {

  fun configureJersey(jersey: JerseyEnvironment, tokenSecret: ByteArray) {
    jersey.register(AuthenticationFailureExceptionMapper())
    jersey.register(AuthDynamicFeature(buildJWTAuthFilter(tokenSecret)))
    jersey.register(AuthValueFactoryProvider.Binder<Principal>(Principal::class.java))
    jersey.register(RolesAllowedDynamicFeature::class.java)
  }

  private fun buildJWTAuthFilter(tokenSecret: ByteArray): JWTAuthFilter<Principal> {
    val tokenParser = DefaultJsonWebTokenParser()
    val tokenVerifier = HmacSHA512Verifier(tokenSecret)

    return JWTAuthFilter.Builder<Principal>()
        .setTokenParser(tokenParser)
        .setTokenVerifier(tokenVerifier)
        .setRealm("realm")
        .setPrefix("Bearer")
        .setAuthenticator(JwtAuthenticator())
        .buildAuthFilter()
  }
}