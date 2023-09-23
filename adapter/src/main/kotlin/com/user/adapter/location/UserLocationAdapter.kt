package com.user.adapter.location

import com.user.adapter.location.entity.UserLocationEntity
import com.user.adapter.location.entity.UserLocationHistoryEntity
import com.user.adapter.location.persistence.UserLocationEntityRepository
import com.user.adapter.location.persistence.UserLocationHistoryEntityRepository
import com.user.application.port.out.UserLocationPort
import com.user.domain.location.UserLocation
import org.springframework.stereotype.Component

@Component
class UserLocationAdapter(
    private val userLocationEntityRepository: UserLocationEntityRepository,
    private val userLocationHistoryEntityRepository: UserLocationHistoryEntityRepository,
) : UserLocationPort {
    override fun upsertUserLocation(userLocation: UserLocation) {
        val userLocationEntity = userLocationEntityRepository.findByUserId(userLocation.user.userId)

        val userLocationToUpsertEntity = userLocationEntity?.let {
            UserLocationEntity(
                userId = userLocation.user.userId,
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
                cityCode = userLocation.cityCode,
                districtCode = userLocation.districtCode,
                id = it.id,
            )
        } ?: UserLocationEntity(
            userId = userLocation.user.userId,
            latitude = userLocation.latitude,
            longitude = userLocation.longitude,
            cityCode = userLocation.cityCode,
            districtCode = userLocation.districtCode,
        )

        userLocationEntityRepository.save(userLocationToUpsertEntity)
        userLocationHistoryEntityRepository.save(UserLocationHistoryEntity.from(userLocationToUpsertEntity))
    }
}