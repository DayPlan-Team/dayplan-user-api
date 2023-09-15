package com.user.domain.usecase.impl

import com.user.domain.model.user.User
import com.user.domain.usecase.UserCreationUseCase
import com.user.domain.usecase.request.UserCreationRequest

class UserCreator : UserCreationUseCase {

    override fun createUser(userCreationRequest: UserCreationRequest): User {
        TODO()
    }
}