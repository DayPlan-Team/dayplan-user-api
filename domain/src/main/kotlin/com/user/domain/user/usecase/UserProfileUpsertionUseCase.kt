package com.user.domain.user.usecase

import com.user.domain.user.User
import com.user.domain.user.request.UserProfileRequest
import org.springframework.stereotype.Component

@Component
interface UserProfileUpsertionUseCase {
    fun upsertUserProfile(
        user: User,
        userProfileRequest: UserProfileRequest,
    ): User
}
