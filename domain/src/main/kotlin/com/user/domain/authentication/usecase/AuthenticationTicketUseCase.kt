package com.user.domain.authentication.usecase

import com.user.domain.authentication.AuthenticationTicket
import org.springframework.stereotype.Component

@Component
interface AuthenticationTicketUseCase {
    fun createAuthenticationTicket(userId: Long): AuthenticationTicket

    fun reissueAuthenticationTicket(userId: Long): String

    fun deleteAuthenticationTicket(userId: Long)
}