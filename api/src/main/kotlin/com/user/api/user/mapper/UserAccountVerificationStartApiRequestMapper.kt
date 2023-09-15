package com.user.api.user.mapper

import com.user.api.user.request.UserAccountVerificationStartApiRequest
import com.user.application.request.UserAccountVerificationStartRequest

object UserAccountVerificationStartApiRequestMapper {

    fun map(request: UserAccountVerificationStartApiRequest): UserAccountVerificationStartRequest {
        return UserAccountVerificationStartRequest(
            accountVerificationMeans = request.accountVerificationMeans,
            account = request.account,
        )
    }

}