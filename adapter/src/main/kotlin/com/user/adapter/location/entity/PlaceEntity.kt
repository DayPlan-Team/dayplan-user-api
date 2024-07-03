package com.user.adapter.location.entity

import com.user.adapter.share.BaseEntity
import com.user.domain.location.Place
import com.user.util.address.PlaceCategory
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
    name = "places",
    indexes = [
        Index(name = "idx__places_place_name", columnList = "place_name"),
        Index(name = "idx__places_address", columnList = "address"),
        Index(name = "idx__places_administrative_category_id", columnList = "administrative_category_id"),
    ],
)
data class
PlaceEntity(
    @Column(name = "administrative_category_id", columnDefinition = "varchar(64)", nullable = false)
    val administrativeCategoryId: String,
    @Column(name = "place_name", columnDefinition = "varchar(255)", nullable = false)
    val placeName: String,
    @Column(name = "place_category", columnDefinition = "varchar(64)", nullable = false)
    @Enumerated(EnumType.STRING)
    val placeCategory: PlaceCategory,
    @Column(name = "latitude", columnDefinition = "double", nullable = false)
    val latitude: Double,
    @Column(name = "longitude", columnDefinition = "double", nullable = false)
    val longitude: Double,
    @Column(name = "address", columnDefinition = "varchar(255)", nullable = false)
    val address: String,
    @Column(name = "road_address", columnDefinition = "varchar(255)", nullable = false)
    val roadAddress: String,
    @Column(name = "user_registration_count", columnDefinition = "bigint", nullable = false)
    val userRegistrationCount: Long = 0L,
    @Column(name = "link", columnDefinition = "text", nullable = true)
    val link: String?,
    @Column(name = "telephone", columnDefinition = "varchar(100)", nullable = true)
    val telephone: String?,
    @Column(name = "description", columnDefinition = "varchar(255)", nullable = true)
    val description: String?,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    companion object {
        fun fromDomainModel(place: Place): PlaceEntity {
            return PlaceEntity(
                administrativeCategoryId = place.administrativeCategoryId,
                placeName = place.placeName,
                placeCategory = place.placeCategory,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                roadAddress = place.roadAddress,
                userRegistrationCount = place.userRegistrationCount,
                link = place.link,
                telephone = place.telephone,
                description = place.description,
                id = place.id,
            )
        }
    }

    fun toDomainModel(): Place {
        return Place(
            administrativeCategoryId = administrativeCategoryId,
            placeName = placeName,
            placeCategory = placeCategory,
            latitude = latitude,
            longitude = longitude,
            address = address,
            roadAddress = roadAddress,
            userRegistrationCount = userRegistrationCount,
            link = link,
            telephone = telephone,
            description = description,
            id = id,
        )
    }
}
