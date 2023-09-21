package com.user.domain.location

import com.user.domain.user.User
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode
import java.time.LocalDateTime

data class UserLocation(
    val user: User,
    val latitude: Double,
    val longitude: Double,
    val cityCode: CityCode,
    val districtCode: DistrictCode,
    val stayedLastTime: LocalDateTime,
)