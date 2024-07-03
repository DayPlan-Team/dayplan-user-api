package com.user.util.social

import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode

enum class SocialType(
    val registrationId: String,
) {
    GOOGLE("google"),
    ;

    companion object {
        fun matchSocialType(registrationId: String): SocialType {
            return when (registrationId) {
                GOOGLE.registrationId -> GOOGLE
                else -> throw UserException(UserExceptionCode.USER_SOCIAL_INVALID_TYPE)
            }
        }
    }
}
