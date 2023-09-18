package com.user.api.public.mapper

import com.user.api.public.request.UserAccountVerificationStartApiRequest
import com.user.application.request.UserAccountVerificationStartRequest
import org.springframework.stereotype.Component

@Component
object UserAccountVerificationStartApiRequestMapper {

    fun map(request: UserAccountVerificationStartApiRequest): UserAccountVerificationStartRequest {
        return UserAccountVerificationStartRequest(
            accountVerificationMeans = request.accountVerificationMeans,
            account = request.account,
        )
    }

}