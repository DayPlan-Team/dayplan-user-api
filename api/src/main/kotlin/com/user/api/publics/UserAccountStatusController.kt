package com.user.api.publics

import com.user.application.service.UserVerifyService
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.usecase.UserAccountStatusUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/status")
class UserAccountStatusController(
    private val userVerifyService: UserVerifyService,
    private val userAccountStatusUseCase: UserAccountStatusUseCase,
) {
    @PostMapping("/withdraw")
    fun withdrawUserStatus(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<Unit> {

        val user = userVerifyService.verifyAndGetUser(userId)
        userAccountStatusUseCase.upsertUserStatus(
            user = user,
            userAccountStatus = UserAccountStatus.WITHDRAWAL,
        )

        return ResponseEntity.ok().build()
    }
}