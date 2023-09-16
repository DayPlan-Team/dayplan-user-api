package com.user.api.user.mapper

import com.user.application.request.UserAccountSocialCreationRequest
import com.user.util.social.SocialType

object UserAccountSocialCreationMapper {

    fun mapper(code: String, registrationId: String): UserAccountSocialCreationRequest {
        return UserAccountSocialCreationRequest(code, SocialType.matchSocialType(registrationId))
    }
}