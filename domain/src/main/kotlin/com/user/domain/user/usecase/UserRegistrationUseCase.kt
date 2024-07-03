package com.user.domain.user.usecase

import com.user.domain.user.User
import com.user.domain.user.request.UserAccountSocialCreationRequest
import org.springframework.stereotype.Component

@Component
interface UserRegistrationUseCase {
    fun createUserIfSocialRegistrationNotExists(request: UserAccountSocialCreationRequest): User
}
