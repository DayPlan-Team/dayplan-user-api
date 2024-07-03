package com.user.domain.user.request

import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User

data class UserCreationRequest(
    val email: String,
    val accountStatus: UserAccountStatus,
) {
    fun toDomainModel(): User {
        return User(
            email = email,
            userAccountStatus = accountStatus,
        )
    }
}
