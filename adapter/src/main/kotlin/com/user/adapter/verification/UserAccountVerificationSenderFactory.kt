package com.user.adapter.verification

import com.user.application.port.UserAccountVerificationSenderPort
import com.user.application.request.UserAccountVerificationStartRequest
import com.user.application.response.UserAccountVerificationStartResponse
import com.user.domain.share.AccountVerificationMeans
import org.springframework.stereotype.Component

//@Component
//class UserAccountVerificationSenderFactory(
//    val userAccountVerificationEmailSenderAdapter: UserAccountVerificationEmailSenderAdapter,
//    val userAccountVerificationSmsSenderAdapter: UserAccountVerificationSmsSenderAdapter,
//) : UserAccountVerificationSenderPort {
//    override fun sendVerification(request: UserAccountVerificationStartRequest): UserAccountVerificationStartResponse {
//        return when (request.accountVerificationMeans) {
//            AccountVerificationMeans.EMAIL -> userAccountVerificationEmailSenderAdapter.sendVerification(request)
//            AccountVerificationMeans.SMS -> userAccountVerificationSmsSenderAdapter.sendVerification(request)
//        }
//    }
//}