package com.user.adapter.location

import com.user.adapter.location.entity.PlaceEntity
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.application.port.out.PlacePort
import com.user.domain.location.Place
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
class PlaceAdapter(
    private val placeEntityRepository: PlaceEntityRepository,
) : PlacePort {
    override fun upsertPlace(place: Place): Place {
        val savedPlace = placeEntityRepository.save(
            PlaceEntity.fromPlace(place)
        )

        return savedPlace.toPlace()
    }

    override fun getPlaceOrNullByAddress(address: String): Place? {
        return placeEntityRepository.findByAddress(address)
            ?.toPlace()
    }

    override fun getPlaceOrNullById(id: Long): Place {
        return placeEntityRepository.findById(id)
            .orElseThrow { SystemException(SystemExceptionCode.NOT_MATCH_PLACE) }
            .toPlace()
    }
}