package com.user.adapter.location.persistence

import com.user.adapter.location.entity.PlaceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceEntityRepository : JpaRepository<PlaceEntity, Long> {
    fun findPlaceEntitiesByIdIn(ids: List<Long>): List<PlaceEntity>

    fun findByAddress(address: String): PlaceEntity?
}