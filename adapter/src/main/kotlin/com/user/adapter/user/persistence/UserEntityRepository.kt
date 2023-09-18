package com.user.adapter.user.persistence

import com.user.adapter.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}