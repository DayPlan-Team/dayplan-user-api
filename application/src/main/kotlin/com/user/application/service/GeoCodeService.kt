package com.user.application.service

import com.user.application.port.out.GeocodeMapPort
import com.user.application.request.GeocodeRequest
import com.user.util.address.AddressCode
import com.user.util.address.AddressUtil
import org.springframework.stereotype.Service

@Service
class GeoCodeService(
    private val geocodeMapPort: GeocodeMapPort,
) {
    fun verifyUserGeoCodeRequestLimitedCount() {
        TODO()
    }

    fun getRegionAddress(geocodeRequest: GeocodeRequest): AddressCode {
        val response = geocodeMapPort.getGeoCodingResponse(geocodeRequest)

        val region = response.results[0].region
        val cityArea = region.area1
        val districtArea = region.area2

        return AddressUtil.transformToAddress(cityArea.name, districtArea.name)
    }
}