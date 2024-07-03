package com.user.adapter.client

import com.user.application.response.GeocodeResponse
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Component
interface NaverGeocodeClient {
    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @GET("/map-reversegeocode/v2/gc?output=json")
    fun getGeocodeResponse(
        @Query("coords") coords: String,
    ): Call<GeocodeResponse>
}
