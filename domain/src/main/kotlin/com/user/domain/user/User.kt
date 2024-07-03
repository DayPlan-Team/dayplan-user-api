package com.user.domain.user

import com.user.domain.share.UserAccountStatus
import com.user.domain.user.request.UserProfileRequest

data class User(
    val email: String,
    val userAccountStatus: UserAccountStatus,
    val mandatoryTermsAgreed: Boolean = false,
    val nickName: String = DEFAULT_NICKNAME,
    val userId: Long = 0L,
) {
    fun from(userProfileRequest: UserProfileRequest): User {
        return User(
            email = email,
            userAccountStatus = userAccountStatus,
            mandatoryTermsAgreed = mandatoryTermsAgreed,
            nickName = userProfileRequest.nickName,
            userId = userId,
        )
    }

    fun from(mandatoryTermsAgreed: Boolean): User {
        return User(
            email = email,
            userAccountStatus = userAccountStatus,
            mandatoryTermsAgreed = mandatoryTermsAgreed,
            nickName = nickName,
            userId = userId,
        )
    }

    fun updateUserStatus(userAccountStatus: UserAccountStatus): User {
        return this.copy(userAccountStatus = userAccountStatus)
    }

    fun updateMandatoryTermsAgreed(mandatoryTermsAgreed: Boolean): User {
        return this.copy(mandatoryTermsAgreed = mandatoryTermsAgreed)
    }

    companion object {
        const val DEFAULT_NICKNAME = "익명"
    }
}
