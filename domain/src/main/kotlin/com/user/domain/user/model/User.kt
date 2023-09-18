package com.user.domain.user.model

import com.user.domain.share.AccountVerificationStatus
import com.user.domain.share.UserAccountStatus

data class User(
    val id: Long,
    val nickName: String,
    val userAccountStatus: UserAccountStatus,
    val accountVerificationStatus: AccountVerificationStatus
)