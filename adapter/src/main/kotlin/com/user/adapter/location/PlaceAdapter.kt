package com.user.adapter.location

import com.user.adapter.location.entity.PlaceEntity
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.application.port.out.PlacePort
import com.user.domain.location.Place
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PlaceAdapter(
    private val placeEntityRepository: PlaceEntityRepository,
) : PlacePort {

    @Transactional
    override fun upsertPlace(place: Place): Place {
        val savedPlace = placeEntityRepository.saveAndFlush(
            PlaceEntity.fromPlace(place)
        )
        return savedPlace.toPlace()
    }

    override fun getPlaceOrNullByAddress(address: String): Place? {
        return placeEntityRepository.findByAddress(address)
            ?.toPlace()
    }

    override fun getPlaceById(id: Long): Place {
        return placeEntityRepository.findById(id)
            .orElseThrow { SystemException(SystemExceptionCode.NOT_MATCH_PLACE) }
            .toPlace()
    }
}