package com.user.util.address

data class AddressCode(
    val city: String = DEFAULT_CITY,
    val district: String = DEFAULT_DISTRICT,
    val cityCode: Long = CityCode.SEOUL.code,
    val districtCode: Long = DistrictCode.SEOUL_SEOCHO.code,
) {
    companion object {
        const val DEFAULT_CITY = "서울특별시"
        const val DEFAULT_DISTRICT = "서초구"
    }
}