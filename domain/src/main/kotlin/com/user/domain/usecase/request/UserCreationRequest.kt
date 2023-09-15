package com.user.domain.usecase.request

data class UserCreationRequest(
    val nickName: String,
    val email: String,
    val password: String,
)
