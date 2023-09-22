package com.user.api.publics.mapper

import com.user.application.request.UserAccountSocialCreationRequest
import com.user.util.social.SocialType
import org.springframework.stereotype.Component

@Component
object UserAccountSocialCreationMapper {

    fun mapper(code: String, registrationId: String): UserAccountSocialCreationRequest {
        return UserAccountSocialCreationRequest(code, SocialType.matchSocialType(registrationId))
    }
}