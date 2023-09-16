package com.user.application.port.out

import com.user.domain.model.user.User
import org.springframework.stereotype.Service

@Service
interface UserCreationCommandPort {

    fun save(user: User): Long
}