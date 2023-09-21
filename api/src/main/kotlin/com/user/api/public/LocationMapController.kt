package com.user.api.public

import com.user.application.service.UserVerifyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/location")
class LocationMapController(
    private val userVerifyService: UserVerifyService,
) {

    @GetMapping("/city")
    fun getCity(@RequestHeader("UserId") userId: Long) {
        val user = userVerifyService.verifyAndGetUser(userId)
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

}