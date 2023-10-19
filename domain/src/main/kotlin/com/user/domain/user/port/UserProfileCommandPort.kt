package com.user.domain.user.port

import com.user.domain.user.User
import com.user.domain.user.request.UserProfileRequest
import org.springframework.stereotype.Service

@Service
interface UserProfileCommandPort {

    fun upsertUserProfile(user: User, userProfileRequest: UserProfileRequest)
}