package com.user.application.service

import com.user.application.port.out.UserProfileCommandPort
import com.user.application.port.out.UserQueryPort
import com.user.domain.user.request.UserProfileRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserProfileUpdateService(
    private val userQueryPort: UserQueryPort,
    private val userCommandPort: UserProfileCommandPort,
) {

    fun upsertUserProfile(userId: Long, userProfileRequest: UserProfileRequest) {
        val user = userQueryPort.findUserByUserId(userId)
        userCommandPort.upsertUserProfile(user, userProfileRequest)
    }
}