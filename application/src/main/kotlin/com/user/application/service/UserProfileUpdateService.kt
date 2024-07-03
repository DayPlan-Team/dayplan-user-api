package com.user.application.service

import com.user.domain.user.port.UserProfileCommandPort
import com.user.domain.user.port.UserQueryPort
import com.user.domain.user.request.UserProfileRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserProfileUpdateService(
    private val userQueryPort: UserQueryPort,
    private val userProfileCommandPort: UserProfileCommandPort,
) {
    fun upsertUserProfile(
        userId: Long,
        userProfileRequest: UserProfileRequest,
    ) {
        val user = userQueryPort.findUserByUserId(userId)
        userProfileCommandPort.upsertUserProfile(user, userProfileRequest)
    }
}
