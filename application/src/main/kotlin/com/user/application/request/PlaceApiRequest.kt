package com.user.application.request

import com.user.domain.location.PlaceCategory

data class PlaceApiRequest(
    val placeName: String,
    val placeCategory: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val roadAddress: String,
    val placeUserDescription: String,
)
