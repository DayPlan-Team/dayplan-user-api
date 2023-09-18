package com.user.adapter.user.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.share.UserAccountStatus
import com.user.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class UserEntity(

    @Column
    val nickName: String,

    @Column
    val email: String,

    @Column
    @Enumerated(EnumType.STRING)
    val userAccountStatus: UserAccountStatus,

    @Column
    val isVerified: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0L,
) : BaseEntity()
