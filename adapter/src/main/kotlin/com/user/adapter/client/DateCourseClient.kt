package com.user.adapter.client

import com.user.domain.location.UserLocation
import org.springframework.stereotype.Component
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

@Component
interface DateCourseClient {
    @Headers(ApiClientUtil.CONTENT_TYPE_APPLICATION_JSON)
    @POST("/content/internal/datecourse/visitedstatus")
    suspend fun sendUserLocation(
        @Body userLocation: UserLocation,
    ): Response<Unit>
}
