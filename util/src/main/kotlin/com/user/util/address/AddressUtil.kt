package com.user.util.address

import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
object AddressUtil {

    private val citiesByKoreanName = CityCode
        .values()
        .groupBy { it.koreanName }

    private val districtsByKoreanName = DistrictCode
        .values()
        .groupBy { it.koreanName }

    private val districtsByCityCode = DistrictCode
        .values()
        .groupBy { it.city.code }

    private val districtByDistrictCode = DistrictCode
        .values()
        .groupBy { it.code }

    private val cityByCityCode = CityCode
        .values()
        .groupBy { it.code }

    val cities = CityCode
        .values()


    fun transformToAddress(city: String, district: String): AddressCode {
        val cityCode = citiesByKoreanName[city]?.get(0) ?: return AddressCode()
        val districtCode = districtsByKoreanName[district]?.get(0) ?: return AddressCode(
            city = city,
            district = "",
            cityCode = cityCode.code,
            districtCode = 0L,
        )

        return AddressCode(
            city = city,
            district = district,
            cityCode = cityCode.code,
            districtCode = districtCode.code
        )
    }

    fun getDistrictByCityCode(cityCode: Long): List<DistrictCode> {
        return districtsByCityCode[cityCode] ?: throw UserException(UserExceptionCode.BAD_REQUEST_CITY_CODE)
    }


    fun verifyCityCode(cityCode: Long) {
        cityByCityCode[cityCode] ?: throw UserException(UserExceptionCode.BAD_REQUEST_CITY_CODE)
    }

    fun verifyDistrictCode(districtCode: Long) {
        districtByDistrictCode[districtCode] ?: throw UserException(UserExceptionCode.BAD_REQUEST_DISTRICT_CODE)
    }

}