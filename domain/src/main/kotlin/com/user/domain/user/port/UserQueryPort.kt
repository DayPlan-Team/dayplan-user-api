package com.user.domain.user.port

import com.user.domain.user.User
import org.springframework.stereotype.Component

@Component
interface UserQueryPort {
    fun findUserByUserId(userId: Long): User

    fun findUserByEmailOrNull(email: String): User?

    fun findUsesByUserIds(userIds: List<Long>): List<User>
}