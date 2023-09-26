package com.user.adapter.location

import com.user.adapter.location.entity.UserStayedPlaceEntity
import com.user.adapter.location.persistence.PlaceEntityRepository
import com.user.adapter.location.persistence.UserStayedPlaceEntityRepository
import com.user.application.port.out.UserStayedPlacePort
import com.user.domain.location.Place
import com.user.domain.location.UserStayedPlace
import com.user.domain.user.User
import com.user.util.exception.SystemException
import com.user.util.exceptioncode.SystemExceptionCode
import org.springframework.stereotype.Component

@Component
class UserStayedPlaceAdapter(
    private val userStayedPlaceEntityRepository: UserStayedPlaceEntityRepository,
    private val placeEntityRepository: PlaceEntityRepository,
) : UserStayedPlacePort {
    override fun upsertUserStayedPlace(userStayedPlace: UserStayedPlace) {
        val userStayedPlaceEntity = UserStayedPlaceEntity.fromUserStayedPlace(userStayedPlace)
        userStayedPlaceEntityRepository.save(userStayedPlaceEntity)
    }

    override fun getUserStayedPlaceByUser(user: User): List<UserStayedPlace> {
        val userStayedPlaceMapByPlaceId =
            userStayedPlaceEntityRepository.findUserStayedPlaceEntitiesByUserId(user.userId)
                .associateBy { it.placeId }

        val placeEntities = placeEntityRepository.findPlaceEntitiesByIdIn(userStayedPlaceMapByPlaceId.keys.toList())

        val userStayedPlaces = placeEntities.map {
            val userStayedPlaceEntity = userStayedPlaceMapByPlaceId[it.id] ?: throw SystemException(SystemExceptionCode.NOT_MATCH_PLACE)
            UserStayedPlace(
                user = user,
                place = it.toPlace(),
                placeUserDescription = userStayedPlaceEntity.placeUserDescription,
                id = userStayedPlaceEntity.id,
            )
        }

        return userStayedPlaces
    }

    override fun getUserStayedPlaceOrNullByPlace(user: User, place: Place): UserStayedPlace? {
        val userStayedPlace = userStayedPlaceEntityRepository.findByUserIdAndPlaceId(
            userId = user.userId,
            placeId = place.id,
        )?.let {
            UserStayedPlace(
                user = user,
                place = place,
                placeUserDescription = it.placeUserDescription,
                id = it.id,
            )
        }

        return userStayedPlace
    }
}