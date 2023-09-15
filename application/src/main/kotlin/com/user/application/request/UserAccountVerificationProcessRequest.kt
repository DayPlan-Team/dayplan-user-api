package com.user.application.request

data class UserAccountVerificationProcessRequest(
    val startedTxId: String,
    val verificationCode: String,
)
