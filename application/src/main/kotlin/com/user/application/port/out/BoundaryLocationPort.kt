package com.user.application.port.out

import com.user.domain.location.BoundaryLocation
import org.springframework.stereotype.Component

@Component
interface BoundaryLocationPort {

    fun getCityBoundaryLocation(cityCode: Long): BoundaryLocation

    fun getDistrictBoundaryLocation(districtCode: Long): BoundaryLocation
}