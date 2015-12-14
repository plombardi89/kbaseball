package kbaseball.user.model


class PendingUserNotFound(activationCode: String): RuntimeException("pending user not found (token: ${activationCode})")