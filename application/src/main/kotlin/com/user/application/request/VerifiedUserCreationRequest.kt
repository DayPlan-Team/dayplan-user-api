package com.user.application.request

data class VerifiedUserCreationRequest(
    val verifiedTxId: String,
    val nickName: String,
    val email: String,
    val password: String,
    val passwordVerification: String,
)