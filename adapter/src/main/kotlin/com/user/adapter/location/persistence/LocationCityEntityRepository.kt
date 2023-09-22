package com.user.adapter.location.persistence

import com.user.adapter.location.entity.LocationCityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationCityEntityRepository : JpaRepository<LocationCityEntity, Long> {
    fun findLocationCityEntityByCode(code: Long): LocationCityEntity?
}