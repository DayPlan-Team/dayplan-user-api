package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.domain.authentication.port.AuthenticationTicketPort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/authentication/reissue")
class UserAuthenticationTicketReissueController(
    private val authenticationTicketPort: AuthenticationTicketPort,
) {

    @GetMapping("/accesstoken")
    fun reissueAccessToken(@RequestHeader("UserId") userId: Long): ResponseEntity<AccessTokenReissueResponse>{
        val accessToken = authenticationTicketPort.reissueAuthenticationTicket(userId)

        return ResponseEntity.ok(
            AccessTokenReissueResponse(
                accessToken = accessToken,
            ),
        )
    }

    data class AccessTokenReissueResponse(
        @JsonProperty("access_token") val accessToken: String,
    )
}