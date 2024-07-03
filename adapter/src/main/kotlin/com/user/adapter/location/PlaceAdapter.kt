package com.user.adapter.location

import com.user.adapter.location.entity.PlaceEntity
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.domain.location.Place
import com.user.domain.location.port.PlacePort
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
        val savedPlace =
            placeEntityRepository.save(
                PlaceEntity.fromDomainModel(place),
            )
        return savedPlace.toDomainModel()
    }

    override fun getPlaceOrNullByAddress(address: String): Place? {
        return placeEntityRepository.findByAddress(address)
            ?.toDomainModel()
    }

    override fun getPlaceById(id: Long): Place {
        return placeEntityRepository.findById(id)
            .orElseThrow { SystemException(SystemExceptionCode.NOT_MATCH_PLACE) }
            .toDomainModel()
    }

    override fun getPlaceByIds(ids: List<Long>): List<Place> {
        return placeEntityRepository.findPlaceEntitiesByIdIn(ids)
            .map {
                it.toDomainModel()
            }
    }

    override fun getPlacesByAdministrativeCategoryId(administrativeCategoryId: String): List<Place> {
        return placeEntityRepository.findPlaceEntitiesByAdministrativeCategoryId(administrativeCategoryId)
            .map { it.toDomainModel() }
    }
}
