package com.user.adapter.verification

import com.user.application.port.UserAccountVerificationProcessorPort
import com.user.application.request.UserAccountVerificationProcessRequest
import com.user.application.response.UserAccountVerificationProcessResponse
import org.springframework.stereotype.Component

@Component
class UserAccountVerificationProcessorAdapter : UserAccountVerificationProcessorPort {
    override fun processVerification(request: UserAccountVerificationProcessRequest): UserAccountVerificationProcessResponse {
        TODO("Not yet implemented")
    }
}