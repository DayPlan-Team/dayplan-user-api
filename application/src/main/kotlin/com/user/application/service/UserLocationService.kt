package com.user.application.service

import com.user.domain.location.UserLocation
import com.user.domain.user.User
import com.user.domain.userlocation.Coordinates
import com.user.domain.userlocation.port.UserLocationPort
import jakarta.transaction.Transactional
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
@Transactional
class UserLocationService(
    private val coroutineScope: CoroutineScope,
    private val userLocationPort: UserLocationPort,
) {
    fun upsertUserLocation(user: User, coordinates: Coordinates) {
        val userLocation = UserLocation(
            user = user,
            latitude = coordinates.latitude,
            longitude = coordinates.longitude,
        )

        userLocationPort.upsertUserLocation(userLocation)

        coroutineScope.launch {
            userLocationPort.sendUserLocation(userLocation)
        }

    }
}