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
    name = "user_current_location",
    indexes = [
        Index(name = "idx_current_location_userId", columnList = "userId"),
    ],
)
data class UserCurrentLocationEntity(

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
        fun from(
            userCurrentLocationEntity: UserCurrentLocationEntity,
            userLocation: UserLocation
        ): UserCurrentLocationEntity {
            return UserCurrentLocationEntity(
                userId = userLocation.user.userId,
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
                id = userCurrentLocationEntity.id,
            )
        }

        fun from(
            userLocation: UserLocation,
        ): UserCurrentLocationEntity {
            return UserCurrentLocationEntity(
                userId = userLocation.user.userId,
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
            )
        }
    }
}