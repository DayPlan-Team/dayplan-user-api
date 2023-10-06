package com.user.api.internals

import com.user.application.service.UserVerifyService
import com.user.util.Logger
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

        log.info("request = $userId")
        val user = userVerifyService.verifyAndGetUser(userId = userId)

        return ResponseEntity.ok(
            UserResponse(
                userId = user.userId,
                nickName = user.nickName,
            )
        )
    }

    data class UserResponse(
        val userId: Long,
        val nickName: String,
    )

    companion object : Logger()
}