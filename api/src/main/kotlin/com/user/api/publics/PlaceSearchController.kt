package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.PlaceSearchService
import com.user.application.service.UserVerifyService
import com.user.util.address.PlaceCategory
import com.user.util.Logger
import com.user.util.address.AddressUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/place")
class PlaceSearchController(
    private val userVerifyService: UserVerifyService,
    private val placeSearchService: PlaceSearchService,
) {

    @GetMapping("/search")
    suspend fun searchLocation(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("citycode") cityCode: Long,
        @RequestParam("districtcode") districtCode: Long,
        @RequestParam("place") placeCategory: PlaceCategory,
        @RequestParam("start", required = false) start: Int? = 1,
    ): ResponseEntity<PlaceSearchItemApiOuterResponse> {

        userVerifyService.verifyAndGetUser(userId)

        val placeSearchQuery = AddressUtil.verifyAndGetAddress(cityCode, districtCode, placeCategory.koreanName)
        val actualStart = start ?: 1
        val administrativeSequence = AddressUtil.createAdministrativeCategorySequence(cityCode, districtCode, placeCategory, actualStart)

        val places = placeSearchService.searchPlace(placeSearchQuery, placeCategory, actualStart, administrativeSequence)

        val result = PlaceSearchItemApiOuterResponse(
            items = places.map {
                PlaceSearchItemApiResponse(
                    placeId = it.id,
                    title = it.placeName,
                    link = it.link,
                    category = it.placeCategory.koreanName,
                    description = it.description,
                    telephone = it.telephone,
                    address = it.address,
                    roadAddress = it.roadAddress,
                    latitude = it.latitude,
                    longitude = it.longitude,
                )
            }
        )
        log.info("result.size = ${result.items.size}")

        return ResponseEntity.ok(result)
    }

    data class PlaceSearchItemApiOuterResponse(
        @JsonProperty("items") val items: List<PlaceSearchItemApiResponse> = emptyList(),
    )

    data class PlaceSearchItemApiResponse(
        @JsonProperty("placeId") val placeId: Long = 0L,
        @JsonProperty("title") val title: String = "",
        @JsonProperty("link") val link: String = "",
        @JsonProperty("category") val category: String = "",
        @JsonProperty("description") val description: String = "",
        @JsonProperty("telephone") val telephone: String = "",
        @JsonProperty("address") val address: String = "",
        @JsonProperty("roadAddress") val roadAddress: String = "",
        @JsonProperty("latitude") val latitude: Double = 0.0,
        @JsonProperty("longitude") val longitude: Double = 0.0,
    )

    companion object : Logger()
}