package com.user.adapter.client

import com.user.adapter.users.UserAccountGoogleSourceAdapter
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Component
interface GoogleAccountClient {
    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/tokeninfo")
    fun getGoogleResponseByIdToken(
        @Query("id_token") idToken: String,
    ): Call<UserAccountGoogleSourceAdapter.GoogleValidatedTokenResponse>
}
