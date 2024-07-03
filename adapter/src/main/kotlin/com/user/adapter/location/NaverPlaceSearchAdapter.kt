package com.user.adapter.location

import com.user.adapter.client.NaverSearchClient
import com.user.domain.location.port.PlacePortItemResponse
import com.user.domain.location.port.PlaceSearchPort
import com.user.util.Logger
import com.user.util.exception.SystemException
import com.user.util.exception.UserException
import com.user.util.exceptioncode.SystemExceptionCode
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class NaverPlaceSearchAdapter(
    private val naverSearchClient: NaverSearchClient,
) : PlaceSearchPort {
    override fun searchLocation(
        place: String,
        start: Int,
    ): PlacePortItemResponse {
        try {
            val location = naverSearchClient.searchLocation(place)

            val response = location.execute()
            if (response.isSuccessful) {
                return response.body() ?: PlacePortItemResponse()
            }

            throw UserException(UserExceptionCode.NOT_FOUND_LOCATION_PLACE)
        } catch (e: IOException) {
            throw SystemException(SystemExceptionCode.NETWORK_SERVER_ERROR)
        }
    }

    companion object : Logger()
}
