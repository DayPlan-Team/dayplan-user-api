package com.user.api.user.request

import com.user.domain.share.AccountVerificationMeans
import com.user.util.EmailUtil
import com.user.util.PhoneNumberUtil

data class UserAccountVerificationStartApiRequest(
    val accountVerificationMeans: AccountVerificationMeans,
    val account: String,
) {

    init {
        when (accountVerificationMeans) {
            AccountVerificationMeans.SMS -> PhoneNumberUtil.validatePhoneNumber(account)
            AccountVerificationMeans.EMAIL -> EmailUtil.validateEmail(account)
        }
    }

    fun parseAndCreateRequestByMeans(): UserAccountVerificationStartApiRequest {
        val refinedAccount = when (accountVerificationMeans) {
            AccountVerificationMeans.SMS -> PhoneNumberUtil.parsePhoneNumber(account)
            AccountVerificationMeans.EMAIL -> account
        }

        return UserAccountVerificationStartApiRequest(
            accountVerificationMeans = accountVerificationMeans,
            account = refinedAccount,
        )
    }
}