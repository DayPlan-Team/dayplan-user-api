package com.user.api.publics

import com.user.application.request.GeocodeRequest
import com.user.application.service.GeoCodeService
import com.user.application.service.UserVerifyService
import com.user.util.Logger
import com.user.util.address.AddressCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/location")
class LocationMapController(
    private val userVerifyService: UserVerifyService,
    private val geoCodeService: GeoCodeService,
) {

    @GetMapping("/geocode")
    fun getAddressCodeByGeocode(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
    ): ResponseEntity<AddressCode> {

        log.info("latitude = $latitude, longitude = $longitude")

        val user = userVerifyService.verifyAndGetUser(userId)
        CoordinatesVerifier.verifyCoordinates(latitude to longitude)

        val addressCode = geoCodeService.getRegionAddress(
            GeocodeRequest(
                latitude = latitude,
                longitude = longitude,
            ),
        )

        log.info("response = $addressCode")

        return ResponseEntity.ok(addressCode)
    }


    @GetMapping("/city")
    fun getCity(@RequestHeader("UserId") userId: Long) {
        userVerifyService.verifyAndGetUser(userId)
    }

    @GetMapping("/city/{cityCode}/district")
    fun getDistrictFromCity(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("cityCode") cityCode: Long
    ) {

    }

    @GetMapping("/city/{cityCode}/district/{districtCode}/coordinate")
    fun getDistrictCoordinates(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("cityCode") cityCode: Long,
        @PathVariable("districtCode") districtCode: Long,
    ) {

    }

    data class LocationResponse<T>(
        val data: T,
    )

    companion object : Logger()
}