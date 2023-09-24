package com.user.api.publics

import com.user.application.request.PlaceRequest
import com.user.application.service.UserStayedPlaceService
import com.user.application.service.UserVerifyService
import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import com.user.util.lock.DistributeLock
import com.user.util.lock.DistributeLockType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/place")
class UserStayedPlaceController(
    private val userVerifyService: UserVerifyService,
    private val userStayedPlaceService: UserStayedPlaceService,
) {

    @PostMapping
    fun upsertUserStayedPlace(
        @RequestHeader("UserId") userId: Long,
        @RequestBody placeRequest: PlaceRequest,
    ): ResponseEntity<Unit> {

        val user = userVerifyService.verifyAndGetUser(userId)

        userStayedPlaceService.upsertUserStayedPlace(
            user = user,
            placeRequest = placeRequest,
        )

        return ResponseEntity.ok().build()
    }
}