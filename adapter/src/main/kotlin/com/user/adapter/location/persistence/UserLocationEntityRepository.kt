package com.user.adapter.location.persistence

import com.user.adapter.location.entity.UserLocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLocationEntityRepository : JpaRepository<UserLocationEntity, Long>