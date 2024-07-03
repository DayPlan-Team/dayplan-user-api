package com.user.domain.location

import com.user.domain.user.User

data class UserLocation(
    val user: User,
    val latitude: Double,
    val longitude: Double,
)
