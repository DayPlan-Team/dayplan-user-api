package com.user.domain.user.usecase

import com.user.domain.user.User
import com.user.domain.user.request.UserProfileRequest

class UserProfileUpsertion : UserProfileUpsertionUseCase {
    override fun upsertUserProfile(
        user: User,
        userProfileRequest: UserProfileRequest,
    ): User {
        return user.from(userProfileRequest)
    }
}
