package com.user.application.port.out

import com.user.domain.user.model.User
import org.springframework.stereotype.Service

@Service
interface UserQueryPort {
    fun findUserByUserId(userId: Long): User

    fun findUserByEmailOrNull(email: String): User?
}