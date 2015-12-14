package kbaseball.service.auth

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper


class AuthenticationFailureExceptionMapper: ExceptionMapper<AuthenticationFailure> {
  override fun toResponse(exception: AuthenticationFailure?): Response? {
    return Response.status(401).build()
  }
}