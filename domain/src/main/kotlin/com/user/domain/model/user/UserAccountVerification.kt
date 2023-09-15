package com.user.domain.model.user

import com.user.domain.share.AccountVerificationMeans
import com.user.domain.share.AccountVerificationStatus

data class UserAccountVerification(
    val accountVerificationStatus: AccountVerificationStatus,
    val accountVerificationMeans: AccountVerificationMeans,
)
