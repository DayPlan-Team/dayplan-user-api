package com.user.application.service

import com.user.domain.location.port.PlacePort
import com.user.domain.location.port.PlaceSearchPort
import com.user.domain.location.Place
import com.user.util.address.PlaceCategory
import com.user.util.Logger
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import com.user.util.lock.DistributeLock
import com.user.util.lock.DistributeLockType
import org.springframework.stereotype.Service

@Service
class PlaceSearchService(
    private val placeSearchPort: PlaceSearchPort,
    private val placePort: PlacePort,
    private val distributeLock: DistributeLock<Place>
) {
    fun searchPlace(
        place: String,
        placeCategory: PlaceCategory,
        start: Int,
        administrativeCategoryId: String,
    ): List<Place> {

        val placesByAdministrativeCategory = placePort.getPlacesByAdministrativeCategoryId(administrativeCategoryId)

        if (placesByAdministrativeCategory.isNotEmpty()) {
            return placesByAdministrativeCategory
        }

        val placeItemResponse = placeSearchPort.searchLocation(place, start)

        val places = placeItemResponse.items.map {
            val latitude = transformLatitude(it.mapy)
            val longitude = transformLongitude(it.mapx)

            placePort.getPlaceOrNullByAddress(it.address) ?: run {
                distributeLock.withLockRetry(
                    distributeLockType = DistributeLockType.PLACE_REGISTRATION,
                    key = it.address,
                    lockTime = 2_000,
                    exception = UserException(UserExceptionCode.PLACE_ALREADY_ROCK),
                    action = {
                        placePort.getPlaceOrNullByAddress(it.address) ?: run {
                            placePort.upsertPlace(
                                Place(
                                    administrativeCategoryId = administrativeCategoryId,
                                    placeName = it.title,
                                    placeCategory = placeCategory,
                                    latitude = latitude,
                                    longitude = longitude,
                                    address = it.address,
                                    roadAddress = it.roadAddress,
                                    link = it.link,
                                    telephone = it.telephone,
                                    description = it.description,
                                ),
                            )
                        }
                    }
                )
            }
        }

        return places
    }

    private fun transformLatitude(latitude: String): Double {
        return StringBuilder()
            .append(latitude.substring(0, 2))
            .append(".")
            .append(latitude.substring(2))
            .toString()
            .toDouble()
    }

    private fun transformLongitude(longitude: String): Double {
        return StringBuilder()
            .append(longitude.substring(0, 3))
            .append(".")
            .append(longitude.substring(3))
            .toString()
            .toDouble()
    }

    companion object : Logger()
}