package com.user.adapter.location

import com.user.adapter.location.entity.PlaceEntity
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.application.port.out.PlacePort
import com.user.domain.location.Place
import org.springframework.stereotype.Component

@Component
class PlaceAdapter(
    private val placeEntityRepository: PlaceEntityRepository,
) : PlacePort {
    override fun savePlace(place: Place): Place {
        val savedPlace = placeEntityRepository.save(
            PlaceEntity.fromPlace(place)
        )

        return savedPlace.toPlace()
    }

    override fun getPlaceOrNullByAddress(address: String): Place? {
        return placeEntityRepository.findByAddress(address)
            ?.toPlace()
    }
}