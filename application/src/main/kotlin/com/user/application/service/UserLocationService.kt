package com.user.application.service

import com.user.application.port.out.GeocodeMapPort
import com.user.application.port.out.UserLocationPort
import com.user.application.request.GeocodeRequest
import com.user.domain.location.UserLocation
import com.user.domain.user.User
import com.user.util.address.AddressUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserLocationService(
    private val geocodeMapPort: GeocodeMapPort,
    private val userLocationPort: UserLocationPort,
) {
    fun verifyUserGeoCodeRequestLimitedCount() {
        TODO()
    }

    fun getRegionAddress(user: User, geocodeRequest: GeocodeRequest) {
        val response = geocodeMapPort.getGeoCodingResponse(geocodeRequest)

        val region = response.results[0].region
        val cityArea = region.area1
        val districtArea = region.area2

        val location =  AddressUtil.transformToAddress(cityArea.name, districtArea.name)

        val userLocation = UserLocation(
            user = user,
            latitude = geocodeRequest.latitude,
            longitude = geocodeRequest.longitude,
            cityCode = location.cityCode,
            districtCode = location.districtCode,
        )

        userLocationPort.upsertUserLocation(userLocation)
    }
}