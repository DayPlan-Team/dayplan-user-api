package com.user.application.request

import com.user.domain.share.AccountVerificationMeans

data class UserAccountVerificationStartRequest(
    val accountVerificationMeans: AccountVerificationMeans,
    val account: String,
)