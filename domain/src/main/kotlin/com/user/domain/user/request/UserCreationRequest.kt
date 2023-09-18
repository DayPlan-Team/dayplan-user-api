package com.user.domain.user.request

import com.user.domain.share.UserAccountStatus

data class UserCreationRequest(
    val email: String,
    val isVerified: Boolean,
    val accountStatus: UserAccountStatus,
)
