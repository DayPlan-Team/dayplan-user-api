package com.user.domain.user.port

import com.user.domain.user.User
import org.springframework.stereotype.Service

@Service
interface UserCommandPort {
    fun save(user: User): User
}
