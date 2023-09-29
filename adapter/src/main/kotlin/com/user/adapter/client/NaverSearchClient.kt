package com.user.adapter.client

import com.user.application.response.LocationItem
import com.user.application.response.LocationItemResponse
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Component
interface NaverSearchClient {

    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/v1/search/local.json")
    fun searchLocation(
        @Query("query") coords: String,
    ): Call<LocationItemResponse>

}