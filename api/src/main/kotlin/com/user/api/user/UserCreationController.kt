package com.user.api.user

import com.user.api.user.mapper.UserAccountSocialCreationMapper
import com.user.application.service.UserCreationService
import com.user.util.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user/creation")
@RestController
class UserCreationController(
    private val userCreationService: UserCreationService,
) {
    @GetMapping("/social/{registrationId}")
    fun loginBySocial(@RequestParam("code") code: String,
                      @PathVariable("registrationId") registrationId: String): ResponseEntity<String> {

        val request = UserAccountSocialCreationMapper.mapper(code, registrationId)
        userCreationService.createUserBySocial(request)

        return ResponseEntity.ok().build()
    }

    companion object : Logger()
}