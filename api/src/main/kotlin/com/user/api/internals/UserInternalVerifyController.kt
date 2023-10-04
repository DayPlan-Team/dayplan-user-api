package com.user.api.internals

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.UserVerifyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/internal")
class UserInternalVerifyController(
    private val userVerifyService: UserVerifyService,
) {

    @GetMapping("/verify")
    fun verifyAndGetUser(
        @RequestParam("userId") userId: Long,
    ): ResponseEntity<UserResponse> {
        val user = userVerifyService.verifyAndGetUser(userId = userId)

        return ResponseEntity.ok(
            UserResponse(
                userId = userId,
            )
        )
    }

    data class UserResponse(
        @JsonProperty("userId") val userId: Long,
    )
}