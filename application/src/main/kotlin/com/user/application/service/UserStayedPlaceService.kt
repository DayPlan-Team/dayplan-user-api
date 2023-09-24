package com.user.application.service

import com.user.application.port.out.PlacePort
import com.user.application.port.out.UserStayedPlacePort
import com.user.application.request.PlaceRequest
import com.user.domain.location.Place
import com.user.domain.location.UserStayedPlace
import com.user.domain.user.User
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import com.user.util.lock.DistributeLock
import com.user.util.lock.DistributeLockType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserStayedPlaceService(
    private val placePort: PlacePort,
    private val userStayedPlacePort: UserStayedPlacePort,
    private val distributeLock: DistributeLock<Place>
) {

    fun upsertUserStayedPlace(user: User, placeRequest: PlaceRequest) {
        val place = distributeLock.withLock(
            distributeLockType = DistributeLockType.PLACE_REGISTRATION,
            key = placeRequest.address,
            lockTime = 1_000,
            exception = UserException(UserExceptionCode.PLACE_ALREADY_ROCK),
            action = {
                getOrCreatePlace(placeRequest)
            }
        )

        userStayedPlacePort.getUserStayedPlaceOrNullByPlace(user, place)
            ?.let {
                userStayedPlacePort.upsertUserStayedPlacePort(
                    UserStayedPlace(
                        user = user,
                        place = place,
                        placeUserDescription = placeRequest.placeUserDescription,
                        id = it.id
                    ),
                )
            } ?: userStayedPlacePort.upsertUserStayedPlacePort(
            UserStayedPlace(
                user = user,
                place = place,
                placeUserDescription = placeRequest.placeUserDescription,
            ),
        )
    }

    internal fun getOrCreatePlace(placeRequest: PlaceRequest): Place {
        return placePort.getPlaceOrNullByAddress(placeRequest.address) ?: placePort.savePlace(
            Place(
                placeName = placeRequest.placeName,
                placeCategory = placeRequest.placeCategory,
                latitude = placeRequest.latitude,
                longitude = placeRequest.longitude,
                address = placeRequest.address,
                roadAddress = placeRequest.roadAddress,
            )
        )
    }
}