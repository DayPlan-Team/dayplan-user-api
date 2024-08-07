package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.api.publics.mapper.UserAccountSocialCreationMapper
import com.user.application.service.UserRegistrationService
import com.user.domain.authentication.usecase.AuthenticationTicketUseCase
import com.user.util.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/registration")
class UserRegistrationController(
    private val userRegistrationService: UserRegistrationService,
    private val authenticationTicketUseCase: AuthenticationTicketUseCase,
) {
    @GetMapping("/social/{registrationId}")
    fun createUserIfNotAndCreateAuthenticationTicketBySocial(
        @RequestParam("code") code: String,
        @PathVariable("registrationId") registrationId: String,
    ): ResponseEntity<AuthenticationTicketResponse> {
        val request = UserAccountSocialCreationMapper.mapper(code, registrationId)
        val user = userRegistrationService.createUserIfSocialRegistrationNotExists(request)
        val authenticationTicket = authenticationTicketUseCase.createAuthenticationTicket(user.userId)

        return ResponseEntity.ok(
            AuthenticationTicketResponse(
                accessToken = authenticationTicket.accessToken,
                refreshToken = authenticationTicket.refreshToken,
            ),
        )
    }

    companion object : Logger()

    data class AuthenticationTicketResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("refresh_token") val refreshToken: String,
    )
}
