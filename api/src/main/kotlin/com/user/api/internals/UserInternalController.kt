package com.user.api.internals

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.port.out.UserQueryPort
import com.user.application.service.UserVerifyService
import com.user.domain.share.UserAccountStatus
import com.user.util.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/internal")
class UserInternalController(
    private val userVerifyService: UserVerifyService,
    private val userQueryPort: UserQueryPort,
) {

    @GetMapping("/verify")
    fun verifyAndGetUser(
        @RequestParam("userId") userId: Long,
    ): ResponseEntity<UserResponse> {

        log.info("request = $userId")
        val user = userVerifyService.verifyAndGetUser(userId = userId)

        return ResponseEntity.ok(
            UserResponse(
                userId = user.userId,
                userAccountStatus = user.userAccountStatus,
                nickName = user.nickName,
            )
        )
    }

    @GetMapping("/users")
    fun getUsers(
        @RequestParam("userId") userId: List<Long>,
    ): ResponseEntity<UserResponses> {
        val userResponses = userQueryPort.findUsesByUserIds(userId)
            .map {
                UserResponse(
                    userId = it.userId,
                    userAccountStatus = it.userAccountStatus,
                    nickName = it.nickName,
                )
            }
        log.info("response = $userResponses")
        return ResponseEntity.ok(
            UserResponses(
                users = userResponses
            )
        )
    }

    data class UserResponse(
        @JsonProperty("userId") val userId: Long,
        @JsonProperty("userAccountStatus") val userAccountStatus: UserAccountStatus,
        @JsonProperty("nickName") val nickName: String,
    )

    data class UserResponses(
        @JsonProperty("users") val users: List<UserResponse>,
    )


    companion object : Logger()
}