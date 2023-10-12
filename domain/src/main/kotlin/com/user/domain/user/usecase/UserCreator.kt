package com.user.domain.user.usecase

import com.user.domain.user.User
import com.user.domain.user.request.UserCreationRequest
import org.springframework.stereotype.Component

@Component
class UserCreator : UserCreationUseCase {
    override fun createUser(userCreationRequest: UserCreationRequest): User {
        return User(
            email = userCreationRequest.email,
            userAccountStatus = userCreationRequest.accountStatus,
        )
    }

}