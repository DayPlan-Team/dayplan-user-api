package com.user.domain.authentication

data class AuthenticationTicket(
    val accessToken: String,
    val refreshToken: String,
)
