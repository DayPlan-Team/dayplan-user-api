package com.user.api.publics

import com.user.application.port.out.UserQueryPort
import com.user.domain.share.UserAccountStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/verify")
class UserVerifyController(
    private val userQueryPort: UserQueryPort,
) {

    @GetMapping
    fun verifyUser(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<VerifyUserResponse> {
        val user = userQueryPort.findUserByUserId(userId)

        return ResponseEntity.ok(
            VerifyUserResponse(
                verified = user.isVerified,
                userStatus = user.userAccountStatus,
                mandatoryTermsAgreed = user.mandatoryTermsAgreed,
            ),
        )
    }

    data class VerifyUserResponse(
        val verified: Boolean,
        val userStatus: UserAccountStatus,
        val mandatoryTermsAgreed: Boolean,
    )

}