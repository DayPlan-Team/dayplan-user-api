package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.PlaceSearchService
import com.user.application.service.UserVerifyService
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
    fun searchLocation(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("citycode") cityCode: Long,
        @RequestParam("districtcode") districtCode: Long,
        @RequestParam("place") place: String,
        @RequestParam("start", required = false) start: Int = 1,
    ): ResponseEntity<PlaceItemApiResponse> {
        userVerifyService.verifyAndGetUser(userId)

        val placeSearchQuery = AddressUtil.verifyAndGetAddress(cityCode, districtCode, place)
        val placeItemResponse = placeSearchService.searchPlace(placeSearchQuery, start)

        val result = PlaceItemApiResponse(
            total = placeItemResponse.total,
            start = placeItemResponse.start,
            display = placeItemResponse.display,
            items = placeItemResponse.items.map {
                PlaceApiItem(
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

        return ResponseEntity.ok(result)
    }

    data class PlaceItemApiResponse(
        @JsonProperty("total") val total: Int = 0,
        @JsonProperty("start") val start: Int = 1,
        @JsonProperty("display") val display: Int = 10,
        @JsonProperty("items") val items: List<PlaceApiItem> = emptyList(),
    )

    data class PlaceApiItem(
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
}