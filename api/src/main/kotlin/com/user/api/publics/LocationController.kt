package com.user.api.publics

import com.user.application.service.UserLocationService
import com.user.application.service.UserVerifyService
import com.user.domain.userlocation.Coordinates
import com.user.util.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty

@RestController
@RequestMapping("/user/location")
class LocationController(
    private val userVerifyService: UserVerifyService,
    private val userLocationService: UserLocationService,
) {
    @PostMapping
    fun upsertUserLocation(
        @RequestHeader("UserId") userId: Long,
        @RequestBody coordinates: Coordinates,
    ): ResponseEntity<Empty> {
        val user = userVerifyService.verifyAndGetUser(userId)
        CoordinatesVerifier.verifyCoordinates(coordinates.latitude to coordinates.longitude)

        userLocationService.upsertUserLocation(
            user = user,
            coordinates = coordinates,
        )

        return ResponseEntity.ok().build()
    }

    companion object : Logger()
}
