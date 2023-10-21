package com.user.adapter.authenticationticket

import com.user.domain.authentication.AuthenticationTicket
import com.user.domain.authentication.usecase.AuthenticationTicketUseCase
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

@Component
class AuthenticationTicketUseCaseImpl(
    private val jwtTokenBuilder: JwtTokenBuilder,
) : InitializingBean, AuthenticationTicketUseCase {

    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${jwt.access-expiration-time}")
    private var accessExpirationTime = 0L

    @Value("\${jwt.refresh-expiration-time}")
    private var refreshExpirationTime = 0L

    private lateinit var key: Key
    override fun afterPropertiesSet() {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    override fun createAuthenticationTicket(userId: Long): AuthenticationTicket {
        val accessToken = jwtTokenBuilder.buildJwtToken(userId.toString(), accessExpirationTime)
        val refreshToken = jwtTokenBuilder.buildJwtToken(userId.toString(), refreshExpirationTime)

        return AuthenticationTicket(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    override fun reissueAuthenticationTicket(userId: Long): String {
        return jwtTokenBuilder.buildJwtToken(userId.toString(), accessExpirationTime)
    }
}