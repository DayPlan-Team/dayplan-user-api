package com.user.application.service

import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.port.out.UserCreationCommandPort
import com.user.application.port.out.UserQueryPort
import com.user.application.request.UserAccountSocialCreationRequest
import com.user.domain.authentication.AuthenticationTicket
import com.user.domain.authentication.port.AuthenticationTicketPort
import com.user.domain.share.UserAccountStatus
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
    private val authenticationTicketPort: AuthenticationTicketPort,
) {

    fun createUserIfNotExistsAndCreateAuthenticationTicket(request: UserAccountSocialCreationRequest): AuthenticationTicket {
        val userSourceResponse = userAccountSocialSourcePort.getSocialUserSource(request.code, request.socialType)
        val findUser = userQueryPort.findUserByEmailOrNull(userSourceResponse.email)

        if (findUser != null) {
            return authenticationTicketPort.createAuthenticationTicket(findUser.userId)
        }

        val user = userCreationUseCase.createUser(
            UserCreationRequest(
                email = userSourceResponse.email,
                isVerified = userSourceResponse.isVerified,
                accountStatus = UserAccountStatus.NORMAL,
            )
        )
        val userId = userCreationCommandPort.save(user)

        return authenticationTicketPort.createAuthenticationTicket(userId)
    }

    companion object : Logger()
}