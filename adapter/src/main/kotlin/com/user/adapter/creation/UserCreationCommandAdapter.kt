package com.user.adapter.creation

import com.user.application.port.out.UserCreationCommandPort
import com.user.domain.user.model.User
import org.springframework.stereotype.Component

@Component
class UserCreationCommandAdapter : UserCreationCommandPort {
    override fun save(user: User): Long {
        TODO("Not yet implemented")
    }
}