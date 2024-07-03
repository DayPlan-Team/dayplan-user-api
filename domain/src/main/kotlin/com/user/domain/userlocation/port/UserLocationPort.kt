package com.user.domain.userlocation.port

import com.user.domain.location.UserLocation
import org.springframework.stereotype.Component

@Component
interface UserLocationPort {
    fun upsertUserLocation(userLocation: UserLocation)

    suspend fun sendUserLocation(userLocation: UserLocation)
}
