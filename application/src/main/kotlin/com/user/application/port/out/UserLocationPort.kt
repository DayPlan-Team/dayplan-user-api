package com.user.application.port.out

import com.user.domain.location.UserLocation
import org.springframework.stereotype.Component

@Component
interface UserLocationPort {
    fun upsertUserLocation(userLocation: UserLocation)

}