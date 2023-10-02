package com.user.application.port.out

import com.user.domain.location.Place
import org.springframework.stereotype.Component

@Component
interface PlacePort {

    fun createPlace(place: Place)

    fun upsertPlace(place: Place): Place

    fun getPlaceOrNullByAddress(address: String): Place?

    fun getPlaceById(id: Long): Place

}