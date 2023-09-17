package com.user.domain.user.model

import com.user.domain.share.UserAccountStatus

data class User(
    val nickName: String,
    val email: String,
    val password: String,
    val userAccountStatus: UserAccountStatus,
    val userId: Long,
)