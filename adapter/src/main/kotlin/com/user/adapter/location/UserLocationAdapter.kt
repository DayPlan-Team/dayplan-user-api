package com.user.adapter.location

import com.user.adapter.client.DateCourseClient
import com.user.adapter.location.entity.UserCurrentLocationEntity
import com.user.adapter.location.entity.UserLocationHistoryEntity
import com.user.adapter.location.persistence.UserCurrentLocationEntityRepository
import com.user.adapter.location.persistence.UserLocationHistoryEntityRepository
import com.user.domain.userlocation.port.UserLocationPort
import com.user.domain.location.UserLocation
import com.user.util.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserLocationAdapter(
    private val userCurrentLocationEntityRepository: UserCurrentLocationEntityRepository,
    private val userLocationHistoryEntityRepository: UserLocationHistoryEntityRepository,
    private val dateCourseClient: DateCourseClient,
) : UserLocationPort {
    override fun upsertUserLocation(userLocation: UserLocation) {
        val userLocationEntity = userCurrentLocationEntityRepository.findByUserId(userLocation.user.userId)

        val userLocationToUpsertEntity = userLocationEntity?.let {
            UserCurrentLocationEntity.from(userLocationEntity, userLocation)
        } ?: UserCurrentLocationEntity.from(userLocation)

        userCurrentLocationEntityRepository.save(userLocationToUpsertEntity)
        userLocationHistoryEntityRepository.save(UserLocationHistoryEntity.fromEntity(userLocationToUpsertEntity))
    }

    override suspend fun sendUserLocation(userLocation: UserLocation) {
        try {
            dateCourseClient.sendUserLocation(userLocation)
        } catch (e: Exception) {
            log.error(e.message)
        }
    }

    companion object : Logger()
}