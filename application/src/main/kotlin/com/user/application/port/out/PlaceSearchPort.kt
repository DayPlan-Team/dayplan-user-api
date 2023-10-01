package com.user.application.port.out

import com.user.application.response.PlaceItemResponse
import org.springframework.stereotype.Component

@Component
interface PlaceSearchPort {

    fun searchLocation(place: String, start: Int): PlaceItemResponse

}