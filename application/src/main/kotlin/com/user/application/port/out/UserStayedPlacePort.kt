package com.user.application.port.out

import com.user.domain.location.Place
import com.user.domain.location.UserStayedPlace
import com.user.domain.user.User
import org.springframework.stereotype.Component

@Component
interface UserStayedPlacePort {
    fun upsertUserStayedPlacePort(userStayedPlace: UserStayedPlace)

    fun getUserStayedPlace(user: User): List<UserStayedPlace>

    fun getUserStayedPlaceOrNullByPlace(user: User, place: Place): UserStayedPlace?

}