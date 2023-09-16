package com.user.application.request

import com.user.util.social.SocialType

data class UserAccountSocialCreationRequest(
    val code: String,
    val socialType: SocialType,
)