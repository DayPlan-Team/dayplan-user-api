package com.user.application.request

import com.user.util.address.PlaceCategory

data class PlaceRequest(
    val placeName: String,
    val placeCategory: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val roadAddress: String,
    val placeUserDescription: String,
    val link: String = "",
    val telephone: String = "",
)
