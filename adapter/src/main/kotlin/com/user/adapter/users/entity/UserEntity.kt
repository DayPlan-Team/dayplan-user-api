package com.user.adapter.users.entity

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
@Table(name = "users")
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

    @Column
    val mandatoryTermsAgreed: Boolean = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0L,
) : BaseEntity() {
    fun toUser(): User {
        return User(
            email = email,
            userAccountStatus = userAccountStatus,
            mandatoryTermsAgreed = mandatoryTermsAgreed,
            isVerified = isVerified,
            nickName = nickName,
            userId = id,
        )
    }

    companion object {
        fun from(user: User): UserEntity {

            return UserEntity(
                email = user.email,
                userAccountStatus = user.userAccountStatus,
                mandatoryTermsAgreed = user.mandatoryTermsAgreed,
                isVerified = user.isVerified,
                nickName = user.nickName,
                id = user.userId,
            )
        }
    }
}
