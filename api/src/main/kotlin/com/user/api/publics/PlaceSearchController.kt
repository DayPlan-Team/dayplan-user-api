package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.PlaceSearchService
import com.user.application.service.UserVerifyService
import com.user.domain.location.PlaceCategory
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
    ): ResponseEntity<PlaceItemApiOuterResponse> {

        log.info("result.request test")

        userVerifyService.verifyAndGetUser(userId)

        val placeSearchQuery = AddressUtil.verifyAndGetAddress(cityCode, districtCode, placeCategory.koreanName)
        val actualStart = start ?: 1

        val placeItemResponse = placeSearchService.searchPlace(placeSearchQuery, placeCategory, actualStart)

        val result = PlaceItemApiOuterResponse(
            total = placeItemResponse.total,
            start = placeItemResponse.start,
            display = placeItemResponse.display,
            items = placeItemResponse.items.map {
                PlaceItemApiResponse(
                    title = it.title,
                    link = it.link,
                    category = it.category,
                    description = it.description,
                    telephone = it.telephone,
                    address = it.address,
                    roadAddress = it.roadAddress,
                    mapx = it.mapx,
                    mapy = it.mapy,
                )
            }
        )
        log.info("result.size = ${result.items.size}")

        return ResponseEntity.ok(result)
    }

    data class PlaceItemApiOuterResponse(
        @JsonProperty("total") val total: Int = 0,
        @JsonProperty("start") val start: Int = 1,
        @JsonProperty("display") val display: Int = 10,
        @JsonProperty("items") val items: List<PlaceItemApiResponse> = emptyList(),
    )

    data class PlaceItemApiResponse(
        @JsonProperty("title") val title: String = "",
        @JsonProperty("link") val link: String = "",
        @JsonProperty("category") val category: String = "",
        @JsonProperty("description") val description: String = "",
        @JsonProperty("telephone") val telephone: String = "",
        @JsonProperty("address") val address: String = "",
        @JsonProperty("roadAddress") val roadAddress: String = "",
        @JsonProperty("mapx") val mapx: String = "",
        @JsonProperty("mapy") val mapy: String = "",
    )

    companion object : Logger()
}