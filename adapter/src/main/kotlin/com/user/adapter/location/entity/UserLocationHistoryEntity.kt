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
    name = "user_location_history",
    indexes = [Index(name = "idx_location_history_userId", columnList = "userId")],
)
data class UserLocationHistoryEntity(
    @Column
    val userId: Long,

    @Column
    val latitude: Double,

    @Column
    val longitude: Double,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {

    companion object {
        fun fromUserLocationEntity(userCurrentLocationEntity: UserCurrentLocationEntity): UserLocationHistoryEntity {
            return UserLocationHistoryEntity(
                userId = userCurrentLocationEntity.userId,
                latitude = userCurrentLocationEntity.latitude,
                longitude = userCurrentLocationEntity.longitude,
            )
        }
    }

}
