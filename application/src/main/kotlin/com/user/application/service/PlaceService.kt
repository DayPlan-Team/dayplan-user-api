package com.user.application.service

import com.user.application.port.out.PlacePort
import com.user.domain.location.Place
import com.user.util.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlaceService(
    private val placePort: PlacePort,
) {

    fun getOrCreatePlace(place: Place): Place {
        return placePort.getPlaceOrNullByAddress(place.address) ?: placePort.upsertPlace(place)
    }

    fun updatePlaceCounter(place: Place) {
        placePort.upsertPlace(
            place = place.plusUserRegistrationCountAndReturn()
        )
    }

    companion object : Logger()
}