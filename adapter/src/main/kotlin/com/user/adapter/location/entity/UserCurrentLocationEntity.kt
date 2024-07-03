package com.user.adapter.location.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.location.UserLocation
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "user_current_locations",
    indexes = [
        Index(name = "idx__current_locations_user_id", columnList = "user_id"),
    ],
)
data class UserCurrentLocationEntity(
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
        fun from(
            userCurrentLocationEntity: UserCurrentLocationEntity,
            userLocation: UserLocation,
        ): UserCurrentLocationEntity {
            return UserCurrentLocationEntity(
                userId = userLocation.user.userId,
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
                id = userCurrentLocationEntity.id,
            )
        }

        fun from(userLocation: UserLocation): UserCurrentLocationEntity {
            return UserCurrentLocationEntity(
                userId = userLocation.user.userId,
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
            )
        }
    }
}
