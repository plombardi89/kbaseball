package kbaseball.user.model

class UserNotFound(val idOrName: String): RuntimeException("user not found (id|username: ${idOrName})")