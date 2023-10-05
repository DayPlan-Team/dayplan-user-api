package com.user.api.internals

import com.user.application.port.out.PlacePort
import com.user.util.Logger
import com.user.util.address.PlaceCategory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/internal/place")
class PlaceInternalController(
    private val placePort: PlacePort,
) {

    @GetMapping
    fun getPlaceResponse(
        @RequestParam("placeId") placeIds: List<Long>,
    ): ResponseEntity<PlaceResponse> {
        val placeItems = placePort.getPlaceByIds(placeIds)
            .map {
                PlaceItem(
                    placeName = it.placeName,
                    placeCategory = it.placeCategory,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    address = it.address,
                    roadAddress = it.roadAddress,
                    placeId = it.id,
                )
            }

        log.info("placeResponseSize = ${placeItems.size}")

        return ResponseEntity.ok(
            PlaceResponse(
                places = placeItems,
            )
        )
    }

    data class PlaceResponse(
        val places: List<PlaceItem>
    )

    data class PlaceItem(
        val placeName: String,
        val placeCategory: PlaceCategory,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val roadAddress: String,
        val placeId: Long,
    )

    companion object : Logger()
}