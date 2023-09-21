package com.user.adapter.location.persistence

import com.user.adapter.location.entity.LocationDistrictEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationDistrictEntityRepository : JpaRepository<LocationDistrictEntity, Long>