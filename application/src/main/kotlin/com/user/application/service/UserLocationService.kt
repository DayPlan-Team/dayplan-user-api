package com.user.application.service

import com.user.application.port.out.GeocodeMapPort
import com.user.application.port.out.UserLocationPort
import com.user.application.request.GeocodeRequest
import com.user.domain.location.UserAdministrativeLocation
import com.user.domain.location.UserLocation
import com.user.domain.user.User
import com.user.domain.userlocation.Coordinates
import com.user.util.address.AddressUtil
import jakarta.transaction.Transactional
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
@Transactional
class UserLocationService(
    private val geocodeMapPort: GeocodeMapPort,
    private val coroutineScope: CoroutineScope,
    private val userLocationPort: UserLocationPort,
) {
    fun verifyUserGeoCodeRequestLimitedCount() {
        TODO()
    }

    fun upsertUserLocation(user: User, coordinates: Coordinates) {
        val userLocation = UserLocation(
            user = user,
            latitude = coordinates.latitude,
            longitude = coordinates.longitude,
        )

        userLocationPort.upsertUserLocation(userLocation)

        coroutineScope.launch {
            userLocationPort.sendUserLocation(userLocation)
        }

    }

//    fun getUserLocation(user: User, geocodeRequest: GeocodeRequest) {
//        val response = geocodeMapPort.getGeoCodingResponse(geocodeRequest)
//
//        val region = response.results[0].region
//        val cityArea = region.area1
//        val districtArea = region.area2
//
//        val location =  AddressUtil.transformToAddress(cityArea.name, districtArea.name)
//
//        val userLocation = UserAdministrativeLocation(
//            user = user,
//            latitude = geocodeRequest.latitude,
//            longitude = geocodeRequest.longitude,
//            cityCode = location.cityCode,
//            districtCode = location.districtCode,
//        )
//
//        userLocationPort.upsertUserLocation(userLocation)
//    }
}