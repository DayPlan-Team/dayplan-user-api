package com.user.adapter.users

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.response.UserSourceResponse
import com.user.util.Logger
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode
import com.user.util.social.SocialType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Qualifier("userAccountGoogleSourceAdapter")
@Component
class UserAccountGoogleSourceAdapter(
    private val webClient: WebClient,
) : UserAccountSocialSourcePort {
    override fun getSocialUserSource(code: String, socialType: SocialType): UserSourceResponse {

        val response = getGoogleValidatedTokenResponseFromIdToken(code)

        return UserSourceResponse(
            email = response.email,
            isVerified = response.emailVerified,
        )
    }

    private fun getGoogleValidatedTokenResponseFromIdToken(idToken: String): GoogleValidatedTokenResponse {
        return webClient.get()
            .uri("$GOOGLE_TOKEN_URI?id_token=$idToken")
            .retrieve()
            .bodyToMono(GoogleValidatedTokenResponse::class.java)
            .block()
            ?: throw SystemException(SystemExceptionCode.SOCIAL_LOGIN_TIME_ERROR)
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

    companion object : Logger() {
        private const val GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/tokeninfo"
    }
}

