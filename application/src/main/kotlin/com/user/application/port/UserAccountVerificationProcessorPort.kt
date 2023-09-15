package com.user.application.port

import com.user.application.request.UserAccountVerificationProcessRequest
import com.user.application.response.UserAccountVerificationProcessResponse
import org.springframework.stereotype.Service

@Service
fun interface UserAccountVerificationProcessorPort {
    fun processVerification(request: UserAccountVerificationProcessRequest): UserAccountVerificationProcessResponse
}