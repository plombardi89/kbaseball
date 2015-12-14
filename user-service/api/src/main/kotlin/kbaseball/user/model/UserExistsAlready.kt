package kbaseball.user.model

class UserExistsAlready(val username: String): RuntimeException("user already exists (username: ${username})")