package com.user.application.service

import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.port.out.UserAccountVerificationSenderPort
import com.user.application.port.out.UserCreationCommandPort
import com.user.application.request.UserAccountSocialCreationRequest
import com.user.domain.usecase.UserCreationUseCase
import com.user.util.Logger
import org.springframework.stereotype.Service

@Service
class UserCreationService(
    private val userCreationUseCase: UserCreationUseCase,
    private val userAccountVerificationSenderPort: UserAccountVerificationSenderPort,
    private val userCreationCommandPort: UserCreationCommandPort,
    private val userAccountSocialSourcePort: UserAccountSocialSourcePort,
) {

    fun createUserBySocial(request: UserAccountSocialCreationRequest) {

        val userSourceResponse = userAccountSocialSourcePort.getSocialUserSource(request.code, request.socialType)

        log.info("email = ${userSourceResponse.email}, isVerified = ${userSourceResponse.isVerified}")

    }

    companion object : Logger()
}