package com.user.api.public

import com.user.api.public.mapper.UserAccountVerificationStartApiRequestMapper
import com.user.api.public.request.UserAccountVerificationStartApiRequest
import com.user.application.service.UserAccountVerificationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user/verification")
@RestController
class UserVerificationController(
    private val userAccountVerificationService: UserAccountVerificationService,
) {

    @PostMapping("/verification")
    fun verifyUserAccount(@RequestBody request: UserAccountVerificationStartApiRequest) {

        val refinedRequest = request.parseAndCreateRequestByMeans()
        val userAccountVerificationStartRequest = UserAccountVerificationStartApiRequestMapper.map(refinedRequest)
        userAccountVerificationService.startVerify(userAccountVerificationStartRequest)
    }
}