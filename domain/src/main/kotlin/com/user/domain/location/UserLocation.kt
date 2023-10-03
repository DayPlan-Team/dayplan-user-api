package com.user.domain.location

import com.user.domain.user.User
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode

data class UserLocation(
    val user: User,
    val latitude: Double,
    val longitude: Double,
)

data class UserAdministrativeLocation(
    val user: User,
    val latitude: Double,
    val longitude: Double,
    val cityCode: CityCode,
    val districtCode: DistrictCode,
)