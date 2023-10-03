package com.user.adapter.location.persistence

import com.user.adapter.location.entity.UserCurrentLocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCurrentLocationEntityRepository : JpaRepository<UserCurrentLocationEntity, Long> {
    fun findByUserId(userId: Long): UserCurrentLocationEntity?
}