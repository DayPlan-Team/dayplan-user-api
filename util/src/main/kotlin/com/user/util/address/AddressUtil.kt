package com.user.util.address

import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
object AddressUtil {

    private val citiesByKoreanName = CityCode
        .values()
        .filter { it != CityCode.DEFAULT }
        .groupBy { it.koreanName }

    private val districtsByKoreanName = DistrictCode
        .values()
        .filter { it != DistrictCode.DEFAULT }
        .groupBy { it.koreanName }

    private val districtsByCityCode = DistrictCode
        .values()
        .filter { it != DistrictCode.DEFAULT }
        .groupBy { it.city.code }

    private val districtByDistrictCode = DistrictCode
        .values()
        .filter { it != DistrictCode.DEFAULT }
        .associateBy { it.code }

    private val cityByCityCode = CityCode
        .values()
        .filter { it != CityCode.DEFAULT }
        .associateBy { it.code }

    val cities = CityCode
        .values()
        .filter { it != CityCode.DEFAULT }


    fun transformToAddress(cityName: String, districtName: String): AddressCode {
        val cityCode = citiesByKoreanName[cityName]?.get(0) ?: return AddressCode()
        val districtCode = districtsByKoreanName[districtName]?.get(0) ?: return AddressCode(
            cityCode = cityCode,
            districtCode = DistrictCode.DEFAULT,
        )

        return AddressCode(
            cityCode = cityCode,
            districtCode = districtCode,
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

    fun verifyAndGetAddress(cityCode: Long, districtCode: Long): String {
        verifyAddressCode(cityCode, districtCode)
        return StringBuilder()
            .append(cityByCityCode[cityCode]!!.koreanName)
            .append("\t")
            .append(districtByDistrictCode[districtCode]!!.koreanName)
            .toString()
    }

    fun verifyAndGetAddress(cityCode: Long, districtCode: Long, place: String): String {
        verifyAddressCode(cityCode, districtCode)
        return StringBuilder()
            .append(cityByCityCode[cityCode]!!.koreanName)
            .append("\t")
            .append(districtByDistrictCode[districtCode]!!.koreanName)
            .append("\t")
            .append(place)
            .toString()
    }

    private fun verifyAddressCode(cityCode: Long, districtCode: Long) {
        verifyCityCode(cityCode)
        verifyDistrictCode(districtCode)
        val district = districtByDistrictCode[districtCode]!!
        require(district.city.code == cityCode) { throw UserException(UserExceptionCode.BAD_REQUEST_DISTRICT_CODE) }
    }

}