package com.user.application.service

import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.port.out.UserCreationCommandPort
import com.user.application.port.out.UserQueryPort
import com.user.application.request.UserAccountSocialCreationRequest
import com.user.application.response.UserSourceResponse
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.domain.user.request.UserCreationRequest
import com.user.domain.user.usecase.UserCreationUseCase
import com.user.util.Logger
import org.springframework.stereotype.Service

@Service
class UserRegistrationService(
    private val userCreationUseCase: UserCreationUseCase,
    private val userCreationCommandPort: UserCreationCommandPort,
    private val userAccountSocialSourcePort: UserAccountSocialSourcePort,
    private val userQueryPort: UserQueryPort,
) {
    fun createUserIfSocialRegistrationNotExists(request: UserAccountSocialCreationRequest): User {
        val userSourceResponse = getSocialUserSourceBySocialRequest(request)

        val findUser = userQueryPort.findUserByEmailOrNull(userSourceResponse.email)
        if (findUser != null) return findUser

        val user = userCreationUseCase.createUser(
            UserCreationRequest(
                email = userSourceResponse.email,
                accountStatus = UserAccountStatus.NORMAL,
            )
        )

        return userCreationCommandPort.save(user)
    }

    private fun getSocialUserSourceBySocialRequest(request: UserAccountSocialCreationRequest): UserSourceResponse {
        return userAccountSocialSourcePort.getSocialUserSource(request.code, request.socialType)
    }

    companion object : Logger()
}