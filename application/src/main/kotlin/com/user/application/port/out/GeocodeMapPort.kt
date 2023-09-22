package com.user.application.port.out

import com.user.application.request.GeocodeRequest
import com.user.application.response.GeocodeResponse
import org.springframework.stereotype.Component

@Component
interface GeocodeMapPort {
    fun getGeoCodingResponse(geocodeRequest: GeocodeRequest): GeocodeResponse
}