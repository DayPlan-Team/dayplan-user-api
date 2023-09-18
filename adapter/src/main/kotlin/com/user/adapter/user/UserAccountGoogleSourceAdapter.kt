package com.user.adapter.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.port.out.UserAccountSocialSourcePort
import com.user.application.response.UserSourceResponse
import com.user.util.Logger
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode
import com.user.util.social.SocialType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient

@Qualifier("userAccountGoogleSourceAdapter")
@Component
class UserAccountGoogleSourceAdapter(
    private val webClient: WebClient,
) : UserAccountSocialSourcePort {

    @Value("\${social.client.google.client-id}")
    private lateinit var clientId: String

    @Value("\${social.client.google.client-secret}")
    private lateinit var clientSecretKey: String

    @Value("\${social.client.google.redirect-uri}")
    private lateinit var redirectUri: String

    @Value("\${social.client.google.token-uri}")
    private lateinit var tokenUri: String

    @Value("\${social.client.google.resource-uri}")
    private lateinit var resourceUri: String

    override fun getSocialUserSource(code: String, socialType: SocialType): UserSourceResponse {

        val param = mapOf(
            CODE to code,
            CLIENT_ID to clientId,
            CLIENT_SECRET to clientSecretKey,
            REDIRECT_URI to redirectUri,
            GRANT_TYPE to AUTHORIZATION_CODE,
        )

        val googleAccessResponse = postWebClientToAccessToken(param)
        val response = getWebClientToEmail(googleAccessResponse.accessToken)

        return UserSourceResponse(
            email = response.email,
            isVerified = response.verifiedEmail,
        )
    }

    private fun postWebClientToAccessToken(params: Map<String, String>): GoogleAccessResponse {

        val formData = LinkedMultiValueMap<String, String>()
        formData.setAll(params)

        return webClient.post()
            .uri(tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(formData)
            .retrieve()
            .bodyToMono(GoogleAccessResponse::class.java)
            .block() ?: throw SystemException(SystemExceptionCode.SOCIAL_LOGIN_TIME_ERROR)
    }

    private fun getWebClientToEmail(accessToken: String): GoogleUserResourceResponse {
        return webClient.get()
            .uri(resourceUri)
            .headers { header ->
                header[AUTHORIZATION] = BEARER.plus(accessToken)
            }
            .retrieve()
            .bodyToMono(GoogleUserResourceResponse::class.java)
            .block() ?: throw SystemException(SystemExceptionCode.SOCIAL_LOGIN_TIME_ERROR)
    }

    data class GoogleAccessResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("expires_in") val expiresIn: String,
        @JsonProperty("scope") val scope: String,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("id_token") val idToken: String,
    )

    data class GoogleUserResourceResponse(
        @JsonProperty("id") val id: String,
        @JsonProperty("email") val email: String,
        @JsonProperty("verified_email") val verifiedEmail: Boolean,
        @JsonProperty("picture") val picture: String,
    )

    companion object : Logger() {

        private const val CODE = "code"
        private const val CLIENT_ID = "client_id"
        private const val CLIENT_SECRET = "client_secret"
        private const val REDIRECT_URI = "redirect_uri"
        private const val GRANT_TYPE = "grant_type"
        private const val AUTHORIZATION_CODE = "authorization_code"
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer "
    }
}

