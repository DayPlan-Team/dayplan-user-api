package com.user.adapter.creation

import com.user.application.port.UserCreationCommandPort
import com.user.domain.model.user.User
import org.springframework.stereotype.Component

@Component
class UserCreationCommandAdapter : UserCreationCommandPort {
    override fun save(user: User): Long {
        TODO("Not yet implemented")
    }
}