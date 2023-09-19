package com.user.adapter.users

import com.user.adapter.users.entity.UserEntity
import com.user.adapter.users.persistence.UserEntityRepository
import com.user.application.port.out.UserProfileCommandPort
import com.user.domain.user.User
import com.user.domain.user.request.UserProfileRequest
import org.springframework.stereotype.Component

@Component
class UserProfileCommandAdapter(
    private val userEntityRepository: UserEntityRepository,
) : UserProfileCommandPort {
    override fun upsertUserProfile(user: User, userProfileRequest: UserProfileRequest) {
        val updateUser = user.from(userProfileRequest)
        userEntityRepository.save(UserEntity.from(updateUser))
    }
}