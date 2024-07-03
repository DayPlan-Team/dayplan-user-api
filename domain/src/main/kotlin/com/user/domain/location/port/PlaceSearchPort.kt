package com.user.domain.location.port

import org.springframework.stereotype.Component

@Component
interface PlaceSearchPort {
    fun searchLocation(
        place: String,
        start: Int,
    ): PlacePortItemResponse
}
