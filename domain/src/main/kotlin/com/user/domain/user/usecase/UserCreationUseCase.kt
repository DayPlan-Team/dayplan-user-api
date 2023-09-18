package com.user.domain.user.usecase

import com.user.domain.user.model.User
import org.springframework.stereotype.Service

@Service
interface UserCreationUseCase {
    fun createUser(email: String): User
}