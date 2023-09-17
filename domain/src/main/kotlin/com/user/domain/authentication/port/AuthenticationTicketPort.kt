package com.user.domain.authentication.port

import com.user.domain.authentication.AuthenticationTicket
import org.springframework.stereotype.Component

@Component
interface AuthenticationTicketPort {
    fun createAuthenticationTicket(userId: Long): AuthenticationTicket

    fun reissueAuthenticationTicket(userId: Long): String

    fun deleteAuthenticationTicket(userId: Long)
}