package com.user.adapter.verification

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