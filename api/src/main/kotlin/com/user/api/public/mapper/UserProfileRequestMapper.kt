package com.user.api.public.mapper

import com.user.api.public.request.UserProfileApiRequest
import com.user.domain.user.request.UserProfileRequest
import org.springframework.stereotype.Component

@Component
object UserProfileRequestMapper {

    fun mapper(userProfileApiRequest: UserProfileApiRequest): UserProfileRequest {
        return UserProfileRequest(
            nickName = userProfileApiRequest.nickName,
        )
    }
}