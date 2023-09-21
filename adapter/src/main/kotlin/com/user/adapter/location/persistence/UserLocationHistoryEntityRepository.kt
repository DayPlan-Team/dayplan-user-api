package com.user.adapter.location.persistence

import com.user.adapter.location.entity.UserLocationHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLocationHistoryEntityRepository : JpaRepository<UserLocationHistoryEntity, Long>