package com.user.util.address

import org.springframework.stereotype.Component

@Component
object AddressUtil {

    val cityToDistrictMap = DistrictCode
        .values()
        .groupBy { it.city }

    val codeToDistrictMap = DistrictCode
        .values()
        .groupBy { it.code }
}