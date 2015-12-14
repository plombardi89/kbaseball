package kbaseball.service.auth

import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken
import com.github.toastshaman.dropwizard.auth.jwt.validator.ExpiryValidator
import com.google.common.base.Optional
import io.dropwizard.auth.Authenticator
import java.security.Principal


class JwtAuthenticator: Authenticator<JsonWebToken, Principal> {

  override fun authenticate(token: JsonWebToken?): Optional<Principal>? {
    val expiry = ExpiryValidator()
    expiry.validate(token)

    if (true) {
      return Optional.of(Principal { token?.claim()?.subject() })
    }

    return Optional.absent()
  }
}
