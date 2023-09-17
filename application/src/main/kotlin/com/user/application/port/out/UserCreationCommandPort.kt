package com.user.application.port.out

import com.user.domain.user.model.User
import org.springframework.stereotype.Service

@Service
interface UserCreationCommandPort {

    fun save(user: User): Long
}