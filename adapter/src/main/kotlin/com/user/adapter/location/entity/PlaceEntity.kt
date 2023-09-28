package com.user.adapter.location.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.location.Place
import com.user.domain.location.PlaceCategory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(
    name = "place",
    indexes = [
        Index(name = "idx_placeName", columnList = "placeName"),
        Index(name = "idx_address", columnList = "address"),
    ]
)
data class
PlaceEntity(

    @Column
    val placeName: String,

    @Column
    @Enumerated(EnumType.STRING)
    val placeCategory: PlaceCategory,

    @Column
    val latitude: Double,

    @Column
    val longitude: Double,

    @Column
    val address: String,

    @Column
    val roadAddress: String,

    @Column
    val userRegistrationCount: Long = 0L,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    companion object {
        fun fromPlace(place: Place): PlaceEntity {
            return PlaceEntity(
                placeName = place.placeName,
                placeCategory = place.placeCategory,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                roadAddress = place.roadAddress,
                userRegistrationCount = place.userRegistrationCount,
                id = place.id,
            )
        }
    }

    fun toPlace(): Place {
        return Place(
            placeName = placeName,
            placeCategory = placeCategory,
            latitude = latitude,
            longitude = longitude,
            address = address,
            roadAddress = roadAddress,
            userRegistrationCount = userRegistrationCount,
            id = id,
        )
    }
}
