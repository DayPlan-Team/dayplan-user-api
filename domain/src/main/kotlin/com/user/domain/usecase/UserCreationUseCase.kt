package com.user.domain.usecase

import com.user.domain.model.user.User
import com.user.domain.usecase.request.UserCreationRequest
import org.springframework.stereotype.Service

@Service
interface UserCreationUseCase {
    fun createUser(userCreationRequest: UserCreationRequest): User
}