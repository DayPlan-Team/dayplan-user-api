package com.user.domain.user.usecase

import com.user.domain.user.User
import com.user.domain.user.request.UserCreationRequest
import org.springframework.stereotype.Component

@Component
interface UserCreationUseCase {
    fun createUser(userCreationRequest: UserCreationRequest): User
}