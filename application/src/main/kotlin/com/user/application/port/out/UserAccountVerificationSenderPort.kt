package com.user.application.port.out

import com.user.application.request.UserAccountVerificationStartRequest
import com.user.application.response.UserAccountVerificationStartResponse
import org.springframework.stereotype.Service

@Service
fun interface UserAccountVerificationSenderPort {
    fun sendVerification(request: UserAccountVerificationStartRequest): UserAccountVerificationStartResponse
}