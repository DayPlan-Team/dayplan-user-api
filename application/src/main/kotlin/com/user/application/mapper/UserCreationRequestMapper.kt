package com.user.application.mapper

import com.user.application.request.VerifiedUserCreationRequest
import com.user.domain.usecase.request.UserCreationRequest

object UserCreationRequestMapper {

    fun map(request: VerifiedUserCreationRequest): UserCreationRequest {
        return UserCreationRequest(
            nickName = request.nickName,
            email = request.email,
            password = request.password,
        )
    }

}