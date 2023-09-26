package com.user.application.port.out

import com.user.domain.location.Place
import org.springframework.stereotype.Component

@Component
interface PlacePort {

    fun upsertPlace(place: Place): Place

    fun getPlaceOrNullByAddress(address: String): Place?

    fun getPlaceOrNullById(id: Long): Place

}