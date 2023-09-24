package com.user.domain.location

data class Place(
    val placeName: String,
    val placeCategory: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val roadAddress: String,
    val userRegistrationCount: Long = 0L,
    val id: Long = 0L,
)
