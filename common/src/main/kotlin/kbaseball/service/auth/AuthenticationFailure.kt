package kbaseball.service.auth

/**
 * Represents an authentication failure situation. Should be thrown liberally for any authentication failure reasons,
 * for example, user not found or invalid password.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 */


class AuthenticationFailure(cause: Throwable?) : RuntimeException(cause) {
  constructor(): this(null)
}