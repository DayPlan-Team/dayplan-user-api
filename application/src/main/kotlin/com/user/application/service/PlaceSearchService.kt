package com.user.application.service

import com.user.application.port.out.PlacePort
import com.user.application.port.out.PlaceSearchPort
import com.user.application.response.PlaceItemResponse
import com.user.domain.location.Place
import com.user.domain.location.PlaceCategory
import com.user.util.Logger
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import com.user.util.lock.DistributeLock
import com.user.util.lock.DistributeLockType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PlaceSearchService(
    private val placeSearchPort: PlaceSearchPort,
    private val placePort: PlacePort,
    private val coroutineScope: CoroutineScope,
    private val distributeLock: DistributeLock<Place>
) {

    suspend fun searchPlace(place: String, placeCategory: PlaceCategory, start: Int): PlaceItemResponse {
        val placeItemResponse = placeSearchPort.searchLocation(place, start)

        coroutineScope.launch {
            placeItemResponse.items.map {
                val latitude = transformLatitude(it.mapy)
                val longitude = transformLongitude(it.mapx)

                withContext(Dispatchers.IO) {
                    placePort.getPlaceOrNullByAddress(it.address) ?: run {
                        distributeLock.withLockUnitAtomic(
                            distributeLockType = DistributeLockType.PLACE_REGISTRATION,
                            key = it.address,
                            lockTime = 2_000,
                            exception = UserException(UserExceptionCode.PLACE_ALREADY_ROCK),
                            action = {
                                placePort.createPlace(
                                    Place(
                                        placeName = it.title,
                                        placeCategory = placeCategory,
                                        latitude = latitude,
                                        longitude = longitude,
                                        address = it.address,
                                        roadAddress = it.roadAddress,
                                        link = it.link,
                                        telephone = it.telephone,
                                    ),
                                )
                            }
                        )
                    }
                }
            }
        }

        return placeItemResponse
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