package com.user.domain.location

import com.user.util.address.PlaceCategory

data class Place(
    val administrativeCategoryId: String,
    val placeName: String,
    val placeCategory: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val roadAddress: String,
    val userRegistrationCount: Long = 0L,
    val link: String = "",
    val telephone: String = "",
    val description: String = "",
    val id: Long = 0L,
) {
    fun plusUserRegistrationCountAndReturn(): Place {
        return Place(
            administrativeCategoryId = administrativeCategoryId,
            placeName = placeName,
            placeCategory = placeCategory,
            latitude = latitude,
            longitude = longitude,
            address = address,
            roadAddress = roadAddress,
            userRegistrationCount = userRegistrationCount + 1,
            link = link,
            telephone = telephone,
            description = description,
            id = id,
        )
    }
}
