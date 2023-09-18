package com.user.domain.user

import com.user.domain.share.UserAccountStatus
import com.user.domain.user.request.UserProfileRequest

data class User(
    val email: String,
    val userAccountStatus: UserAccountStatus,
    val isVerified: Boolean,
    val nickName: String = "",
    val userId: Long = 0L,
) {

    fun from(userProfileRequest: UserProfileRequest): User {
        return User(
            email = email,
            userAccountStatus = userAccountStatus,
            isVerified = isVerified,
            nickName = userProfileRequest.nickName,
            userId = userId
        )
    }

}