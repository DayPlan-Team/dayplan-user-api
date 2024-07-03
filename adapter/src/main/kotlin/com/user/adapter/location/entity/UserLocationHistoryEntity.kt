package com.user.adapter.location.entity

import com.user.adapter.share.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "user_location_histories",
    indexes = [
        Index(name = "idx__user_location_histories_user_id", columnList = "user_id"),
    ],
)
data class UserLocationHistoryEntity(
    @Column(name = "user_id", columnDefinition = "bigint", nullable = false)
    val userId: Long,
    @Column(name = "latitude", columnDefinition = "double", nullable = false)
    val latitude: Double,
    @Column(name = "longitude", columnDefinition = "double", nullable = false)
    val longitude: Double,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    companion object {
        fun fromEntity(userCurrentLocationEntity: UserCurrentLocationEntity): UserLocationHistoryEntity {
            return UserLocationHistoryEntity(
                userId = userCurrentLocationEntity.userId,
                latitude = userCurrentLocationEntity.latitude,
                longitude = userCurrentLocationEntity.longitude,
            )
        }
    }
}
