package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.PlaceService
import com.user.application.service.UserStayedPlaceService
import com.user.application.service.UserVerifyService
import com.user.domain.location.Place
import com.user.domain.location.PlaceCategory
import com.user.util.Logger
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import com.user.util.lock.DistributeLock
import com.user.util.lock.DistributeLockType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/place")
class UserStayedPlaceController(
    private val userVerifyService: UserVerifyService,
    private val userStayedPlaceService: UserStayedPlaceService,
    private val placeService: PlaceService,
    private val distributeLock: DistributeLock<Place>
) {

    @PostMapping
    fun upsertUserStayedPlace(
        @RequestHeader("UserId") userId: Long,
        @RequestBody placeApiRequest: PlaceApiRequest,
    ): ResponseEntity<Unit> {

        val user = userVerifyService.verifyAndGetUser(userId)
        val placeRequest = placeApiRequest.toPlaceRequest()

        distributeLock.withLockUnit(
            distributeLockType = DistributeLockType.PLACE_REGISTRATION,
            key = placeRequest.address,
            lockTime = 2_000,
            exception = UserException(UserExceptionCode.PLACE_ALREADY_ROCK),
            action = {
                val place = placeService.getOrCreatePlace(
                    Place(
                        placeName = placeRequest.placeName,
                        placeCategory = placeRequest.placeCategory,
                        latitude = placeRequest.latitude,
                        longitude = placeRequest.longitude,
                        address = placeRequest.address,
                        roadAddress = placeRequest.roadAddress,
                    ),
                )

                userStayedPlaceService.upsertUserStayedPlace(
                    user = user,
                    place = place,
                    placeApiRequest = placeRequest,
                )
                placeService.updatePlaceCounter(place)
            }
        )

        return ResponseEntity.ok().build()
    }

    data class PlaceApiRequest(
        @JsonProperty("placeName") val placeName: String,
        @JsonProperty("placeCategory") val placeCategory: PlaceCategory,
        @JsonProperty("latitude") val latitude: Double,
        @JsonProperty("longitude") val longitude: Double,
        @JsonProperty("address") val address: String,
        @JsonProperty("roadAddress") val roadAddress: String,
        @JsonProperty("placeUserDescription") val placeUserDescription: String,
    ) {
        fun toPlaceRequest(): com.user.application.request.PlaceApiRequest {
            return com.user.application.request.PlaceApiRequest(
                placeName = placeName,
                placeCategory = placeCategory,
                latitude = latitude,
                longitude = longitude,
                address = address,
                roadAddress = roadAddress,
                placeUserDescription = placeUserDescription,
            )
        }
    }

    companion object : Logger()
}