package com.user.adapter.users

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.adapter.client.GoogleAccountClient
import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.response.UserSourceResponse
import com.user.util.exception.SystemException
import com.user.util.exception.UserException
import com.user.util.exceptioncode.SystemExceptionCode
import com.user.util.exceptioncode.UserExceptionCode
import com.user.util.social.SocialType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Qualifier("userAccountGoogleSourceAdapter")
@Component
class UserAccountGoogleSourceAdapter(
    private val googleAccountClient: GoogleAccountClient,
) : UserAccountSocialSourcePort {
    override fun getSocialUserSource(code: String, socialType: SocialType): UserSourceResponse {

        val response = getGoogleValidatedTokenResponseFromIdToken(code)

        return UserSourceResponse(
            email = response.email,
            isVerified = response.emailVerified,
        )
    }

    private fun getGoogleValidatedTokenResponseFromIdToken(idToken: String): GoogleValidatedTokenResponse {
        val callback = try {
            googleAccountClient.getGoogleResponseByIdToken(idToken)
        } catch (e: Exception) {
            throw SystemException(SystemExceptionCode.SOCIAL_LOGIN_TIME_ERROR)
        }

        val response = callback.execute()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: throw UserException(UserExceptionCode.USER_NOT_VERIFIED)
        }
        else throw SystemException(SystemExceptionCode.SOCIAL_LOGIN_TIME_ERROR)
    }

    data class GoogleValidatedTokenResponse(
        @JsonProperty("iss") val iss: String,
        @JsonProperty("azp") val azp: String,
        @JsonProperty("aud") val aud: String,
        @JsonProperty("sub") val sub: String,
        @JsonProperty("email") val email: String,
        @JsonProperty("email_verified") val emailVerified: Boolean,
        @JsonProperty("name") val name: String,
        @JsonProperty("picture") val picture: String,
        @JsonProperty("given_name") val givenName: String,
        @JsonProperty("family_name") val familyName: String,
        @JsonProperty("locale") val locale: String,
        @JsonProperty("iat") val iat: String,
        @JsonProperty("exp") val exp: String,
        @JsonProperty("alg") val alg: String,
        @JsonProperty("kid") val kid: String,
        @JsonProperty("typ") val idToken: String,
    )
}

