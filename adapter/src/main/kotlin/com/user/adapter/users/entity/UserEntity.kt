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
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx__users_email", columnList = "email"),
    ],
)
data class UserEntity(
    @Column(name = "nick_name", columnDefinition = "varchar(100)", nullable = false)
    val nickName: String,
    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
    val email: String,
    @Column(name = "user_account_status", columnDefinition = "varchar(64)", nullable = false)
    @Enumerated(EnumType.STRING)
    val userAccountStatus: UserAccountStatus,
    @Column(name = "is_mandatory_terms_agreed", columnDefinition = "bit", nullable = false)
    val isMandatoryTermsAgreed: Boolean = false,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0L,
) : BaseEntity() {
    fun toDomainModel(): User {
        return User(
            email = email,
            userAccountStatus = userAccountStatus,
            mandatoryTermsAgreed = isMandatoryTermsAgreed,
            nickName = nickName,
            userId = id,
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                email = user.email,
                userAccountStatus = user.userAccountStatus,
                isMandatoryTermsAgreed = user.mandatoryTermsAgreed,
                nickName = user.nickName,
                id = user.userId,
            )
        }
    }
}
