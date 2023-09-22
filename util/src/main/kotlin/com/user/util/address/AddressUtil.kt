package com.user.util.address

import org.springframework.stereotype.Component

@Component
object AddressUtil {

    private val cityToCityNameMap = CityCode
        .values()
        .groupBy { it.koreanName }

    private val districtToDistrictNameMap = DistrictCode
        .values()
        .groupBy { it.koreanName }

    val cityToDistrictMap = DistrictCode
        .values()
        .groupBy { it.city }

    val codeToDistrictMap = DistrictCode
        .values()
        .groupBy { it.code }


    fun transformToAddress(city: String, district: String): AddressCode {
        val cityCode = cityToCityNameMap[city]?.get(0) ?: return AddressCode()
        val districtCode = districtToDistrictNameMap[district]?.get(0) ?: return AddressCode()

        return AddressCode(
            city = city,
            district = district,
            cityCode = cityCode.code,
            districtCode = districtCode.code
        )
    }
}