package com.user.application.port.out

import com.user.application.response.UserSourceResponse
import com.user.util.social.SocialType
import org.springframework.stereotype.Component

@Component
fun interface UserAccountSocialSourcePort {
    fun getSocialUserSource(
        code: String,
        socialType: SocialType,
    ): UserSourceResponse
}
