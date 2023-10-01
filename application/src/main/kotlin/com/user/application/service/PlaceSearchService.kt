package com.user.application.service

import com.user.application.port.out.PlaceSearchPort
import com.user.application.response.PlaceItemResponse
import org.springframework.stereotype.Service

@Service
class PlaceSearchService(
    private val placeSearchPort: PlaceSearchPort,
) {

    fun searchPlace(place: String, start: Int): PlaceItemResponse {
        return placeSearchPort.searchLocation(place, start)
    }

}