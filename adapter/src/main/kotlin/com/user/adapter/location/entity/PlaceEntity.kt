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
    name = "place",
    indexes = [
        Index(name = "idx_placeName", columnList = "placeName"),
        Index(name = "idx_address", columnList = "address"),
        Index(name = "idx_administrativeCategoryId", columnList = "administrativeCategoryId")
    ]
)
data class
PlaceEntity(

    @Column
    val administrativeCategoryId: String,

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

    @Column
    val link: String = "",

    @Column
    val telephone: String = "",

    @Column
    val description: String = "",

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
