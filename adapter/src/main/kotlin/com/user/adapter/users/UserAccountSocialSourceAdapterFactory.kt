package com.user.adapter.users

import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.response.UserSourceResponse
import com.user.util.social.SocialType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Qualifier("userAccountSocialSourceAdapterFactory")
@Component
class UserAccountSocialSourceAdapterFactory(
    private val userAccountGoogleSourceAdapter: UserAccountGoogleSourceAdapter,
) : UserAccountSocialSourcePort {

    override fun getSocialUserSource(code: String, socialType: SocialType): UserSourceResponse {
        return when (socialType) {
            SocialType.GOOGLE -> userAccountGoogleSourceAdapter.getSocialUserSource(code, socialType)
        }
    }
}