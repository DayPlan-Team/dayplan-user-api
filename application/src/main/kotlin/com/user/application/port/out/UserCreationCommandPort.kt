package com.user.application.port.out

import com.user.domain.user.User
import org.springframework.stereotype.Service

@Service
interface UserCreationCommandPort {
    fun save(user: User): User
}