package com.user.api.publics

import com.fasterxml.jackson.annotation.JsonProperty
import com.user.application.service.UserProfileUpdateService
import com.user.domain.user.request.UserProfileRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/profile")
class UserProfileController(
    private val userProfileUpdateService: UserProfileUpdateService,
) {

    @PostMapping
    fun upsertUserProfile(
        @RequestHeader("UserId") userId: Long,
        @RequestBody userProfileApiRequest: UserProfileApiRequest,
    ): ResponseEntity<Unit> {
        val userProfileRequest = userProfileApiRequest.toUserProfileRequest()
        userProfileUpdateService.upsertUserProfile(userId, userProfileRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    data class UserProfileApiRequest(
        @JsonProperty("nickName") val nickName: String,
    ) {
        fun toUserProfileRequest(): UserProfileRequest {
            return UserProfileRequest(
                nickName = nickName,
            )
        }
    }
}