package com.user.application.port

import com.user.application.request.UserAccountVerificationStartRequest
import com.user.application.response.UserAccountVerificationStartResponse
import org.springframework.stereotype.Component

@Component
fun interface UserAccountVerificationSenderPort {
    fun sendVerification(request: UserAccountVerificationStartRequest): UserAccountVerificationStartResponse
}