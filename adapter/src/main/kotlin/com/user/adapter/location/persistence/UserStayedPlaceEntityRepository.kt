package com.user.adapter.location.persistence

import com.user.adapter.location.entity.UserStayedPlaceEntity
import com.user.domain.location.UserStayedPlace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStayedPlaceEntityRepository : JpaRepository<UserStayedPlaceEntity, Long> {

    fun findUserStayedPlaceEntitiesByUserId(userId: Long): List<UserStayedPlaceEntity>

    fun findByUserIdAndPlaceId(userId: Long, placeId: Long): UserStayedPlaceEntity?
}