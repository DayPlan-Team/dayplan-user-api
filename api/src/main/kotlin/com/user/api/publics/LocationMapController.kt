package com.user.api.publics

import com.user.application.port.out.BoundaryLocationPort
import com.user.application.request.GeocodeRequest
import com.user.application.service.GeoCodeService
import com.user.application.service.UserVerifyService
import com.user.domain.location.BoundaryLocation
import com.user.util.Logger
import com.user.util.address.AddressCode
import com.user.util.address.AddressUtil
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
    private val boundaryLocationPort: BoundaryLocationPort,
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
    fun getCity(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<LocationOuterResponse<List<LocationResponse>>> {

        userVerifyService.verifyAndGetUser(userId)

        return ResponseEntity.ok(
            LocationOuterResponse(
                results = AddressUtil.cities.map {
                    LocationResponse(
                        name = it.koreanName,
                        code = it.code,
                    )
                }
            ),
        )
    }

    @GetMapping("/city/{cityCode}/boundary")
    fun getCityBoundary(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("cityCode") cityCode: Long,
    ): ResponseEntity<BoundaryLocation> {

        userVerifyService.verifyAndGetUser(userId)
        AddressUtil.verifyCityCode(cityCode)

        return ResponseEntity.ok(
            boundaryLocationPort.getCityBoundaryLocation(cityCode)
        )
    }

    @GetMapping("/city/{cityCode}/districts")
    fun getDistrictsInCity(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("cityCode") cityCode: Long,
    ): ResponseEntity<LocationOuterResponse<List<LocationResponse>>> {

        userVerifyService.verifyAndGetUser(userId)
        return ResponseEntity.ok(
            LocationOuterResponse(
                results = AddressUtil.getDistrictByCityCode(cityCode)
                    .map {
                        LocationResponse(
                            name = it.koreanName,
                            code = it.code,
                        )
                    },
            )
        )
    }


    @GetMapping("/districts/{districtCode}/boundary")
    fun getDistrictFromCity(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("districtCode") districtCode: Long,
    ): ResponseEntity<BoundaryLocation> {

        userVerifyService.verifyAndGetUser(userId)
        AddressUtil.verifyDistrictCode(districtCode)

        return ResponseEntity.ok(
            boundaryLocationPort.getDistrictBoundaryLocation(districtCode)
        )
    }

    data class LocationOuterResponse<T>(
        val results: T
    )

    data class LocationResponse(
        val name: String,
        val code: Long,
    )

    companion object : Logger()
}