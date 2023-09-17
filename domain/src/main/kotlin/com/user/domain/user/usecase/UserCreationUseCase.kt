package com.user.domain.user.usecase

import com.user.domain.user.model.User
import com.user.domain.user.request.UserCreationRequest
import org.springframework.stereotype.Service

@Service
interface UserCreationUseCase {
    fun createUser(userCreationRequest: UserCreationRequest): User
}