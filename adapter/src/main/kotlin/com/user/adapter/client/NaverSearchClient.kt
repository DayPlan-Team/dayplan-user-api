package com.user.adapter.client

import com.user.application.response.PlacePortItemResponse
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Component
interface NaverSearchClient {

    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/v1/search/local.json?display=10&sort=random")
    fun searchLocation(
        @Query("query") place: String,
        @Query("start") start: Int = 1,
    ): Call<PlacePortItemResponse>

}