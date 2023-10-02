package com.user.application.service

import com.user.application.port.out.UserStayedPlacePort
import com.user.application.request.PlaceRequest
import com.user.domain.location.Place
import com.user.domain.location.UserStayedPlace
import com.user.domain.user.User
import com.user.util.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserStayedPlaceService(
    private val userStayedPlacePort: UserStayedPlacePort,
) {

    fun upsertUserStayedPlace(user: User, place: Place, placeRequest: PlaceRequest) {
        userStayedPlacePort.getUserStayedPlaceOrNullByPlace(user, place)
            ?.let {
                userStayedPlacePort.upsertUserStayedPlace(
                    UserStayedPlace(
                        user = user,
                        place = place,
                        placeUserDescription = placeRequest.placeUserDescription,
                        id = it.id
                    ),
                )
            } ?: run {
            userStayedPlacePort.upsertUserStayedPlace(
                UserStayedPlace(
                    user = user,
                    place = place,
                    placeUserDescription = placeRequest.placeUserDescription,
                ),
            )
        }
    }

    companion object : Logger()
}