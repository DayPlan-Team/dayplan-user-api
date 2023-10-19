package com.user.domain.user.port

import com.user.domain.user.User
import org.springframework.stereotype.Service

@Service
interface UserCreationCommandPort {
    fun save(user: User): User
}