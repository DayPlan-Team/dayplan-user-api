package com.user.api.public

import com.user.api.public.mapper.UserProfileRequestMapper
import com.user.api.public.request.UserProfileApiRequest
import com.user.application.service.UserProfileUpdateService
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
    ): ResponseEntity<HttpStatus> {
        val userProfileRequest = UserProfileRequestMapper.mapper(userProfileApiRequest)
        userProfileUpdateService.upsertUserProfile(userId, userProfileRequest)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}