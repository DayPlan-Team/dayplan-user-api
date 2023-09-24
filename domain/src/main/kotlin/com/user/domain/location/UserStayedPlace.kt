package com.user.domain.location

import com.user.domain.user.User
data class UserStayedPlace(
    val user: User,
    val place: Place,
    val placeUserDescription: String = "",
    val id: Long = 0L,
)
