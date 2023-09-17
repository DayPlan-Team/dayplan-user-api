package com.user.domain.user.request

data class UserCreationRequest(
    val nickName: String,
    val email: String,
    val password: String,
)
