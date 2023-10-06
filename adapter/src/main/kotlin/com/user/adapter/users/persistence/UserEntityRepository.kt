package com.user.adapter.users.persistence

import com.user.adapter.users.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?

    fun findUserEntitiesByIdIn(ids: List<Long>): List<UserEntity>
}