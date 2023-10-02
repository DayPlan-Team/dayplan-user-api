package com.user.adapter.location

import com.user.adapter.client.NaverSearchClient
import com.user.application.port.out.PlaceSearchPort
import com.user.application.response.PlaceItemResponse
import com.user.util.Logger
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
class NaverPlaceSearchAdapter(
    private val naverSearchClient: NaverSearchClient,
) : PlaceSearchPort {
    override fun searchLocation(place: String, start: Int): PlaceItemResponse {
        val location = naverSearchClient.searchLocation(place)

        val response = location.execute()
        if (response.isSuccessful) {
            log.info("response = ${response.body()}")
            return response.body() ?: PlaceItemResponse()
        }

        throw UserException(UserExceptionCode.NOT_FOUND_LOCATION_PLACE)
    }

    companion object : Logger()
}