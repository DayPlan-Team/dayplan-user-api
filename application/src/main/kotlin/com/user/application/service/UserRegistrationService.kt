package com.user.application.service

import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.response.UserSourceResponse
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import com.user.domain.user.port.UserCommandPort
import com.user.domain.user.port.UserQueryPort
import com.user.domain.user.request.UserAccountSocialCreationRequest
import com.user.domain.user.request.UserCreationRequest
import com.user.domain.user.usecase.UserRegistrationUseCase
import com.user.util.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegistrationService(
    private val userCommandPort: UserCommandPort,
    private val userAccountSocialSourcePort: UserAccountSocialSourcePort,
    private val userQueryPort: UserQueryPort,
) : UserRegistrationUseCase {
    @Transactional
    override fun createUserIfSocialRegistrationNotExists(request: UserAccountSocialCreationRequest): User {
        val userSourceResponse = getSocialUserSourceBySocialRequest(request)

        val findUser = userQueryPort.findUserByEmailOrNull(userSourceResponse.email)
        if (findUser != null) return findUser

        return userCommandPort.save(
            UserCreationRequest(
                email = userSourceResponse.email,
                accountStatus = UserAccountStatus.NORMAL,
            ).toDomainModel(),
        )
    }

    private fun getSocialUserSourceBySocialRequest(request: UserAccountSocialCreationRequest): UserSourceResponse {
        return userAccountSocialSourcePort.getSocialUserSource(request.code, request.socialType)
    }

    companion object : Logger()
}
