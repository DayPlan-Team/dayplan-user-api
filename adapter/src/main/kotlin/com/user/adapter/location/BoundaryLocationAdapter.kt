package com.user.adapter.location

import com.user.adapter.location.persistence.LocationCityEntityRepository
import com.user.adapter.location.persistence.LocationDistrictEntityRepository
import com.user.application.port.out.BoundaryLocationPort
import com.user.domain.location.BoundaryLocation
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
class BoundaryLocationAdapter(
    private val locationCityEntityRepository: LocationCityEntityRepository,
    private val locationDistrictEntityRepository: LocationDistrictEntityRepository,
) : BoundaryLocationPort {
    override fun getCityBoundaryLocation(cityCode: Long): BoundaryLocation {
        val location = locationCityEntityRepository.findLocationCityEntityByCode(cityCode)
            ?: throw UserException(UserExceptionCode.BAD_REQUEST_CITY_CODE)

        return BoundaryLocation(
            name = location.name,
            code = location.code,
            coordinates = location.coordinates,
        )
    }

    override fun getDistrictBoundaryLocation(districtCode: Long): BoundaryLocation {
        val location = locationDistrictEntityRepository.findLocationDistrictEntityByCode(districtCode)
            ?: throw UserException(UserExceptionCode.BAD_REQUEST_DISTRICT_CODE)

        return BoundaryLocation(
            name = location.name,
            code = location.code,
            coordinates = location.coordinates,
        )
    }
}