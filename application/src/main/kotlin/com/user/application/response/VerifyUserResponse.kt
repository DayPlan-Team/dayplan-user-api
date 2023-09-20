package com.user.application.response

import com.user.domain.share.UserAccountStatus

data class VerifyUserResponse(
    val verified: Boolean,
    val userStatus: UserAccountStatus,
    val mandatoryTermsAgreed: Boolean,
)