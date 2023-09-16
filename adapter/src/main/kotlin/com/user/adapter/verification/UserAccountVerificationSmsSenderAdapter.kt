package com.user.adapter.verification

import com.user.application.port.out.UserAccountVerificationSenderPort
import com.user.application.request.UserAccountVerificationStartRequest
import com.user.application.response.UserAccountVerificationStartResponse
import org.springframework.stereotype.Component

@Component
class UserAccountVerificationSmsSenderAdapter : UserAccountVerificationSenderPort {
    override fun sendVerification(request: UserAccountVerificationStartRequest): UserAccountVerificationStartResponse {
        TODO("Not yet implemented")
    }

}