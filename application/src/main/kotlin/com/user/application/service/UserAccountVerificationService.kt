package com.user.application.service

import com.user.application.port.out.UserAccountVerificationProcessorPort
import com.user.application.port.out.UserAccountVerificationSenderPort
import com.user.application.request.UserAccountVerificationProcessRequest
import com.user.application.request.UserAccountVerificationStartRequest
import com.user.application.response.UserAccountVerificationProcessResponse
import com.user.application.response.UserAccountVerificationStartResponse
import org.springframework.stereotype.Service

@Service
class UserAccountVerificationService(
    private val userAccountVerificationSenderPort: UserAccountVerificationSenderPort,
    private val userAccountVerificationProcessorPort: UserAccountVerificationProcessorPort,
) {

    fun startVerify(request: UserAccountVerificationStartRequest): UserAccountVerificationStartResponse {
        return userAccountVerificationSenderPort.sendVerification(request)
    }

    fun processVerify(request: UserAccountVerificationProcessRequest): UserAccountVerificationProcessResponse {
        return userAccountVerificationProcessorPort.processVerification(request)
    }

}