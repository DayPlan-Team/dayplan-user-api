package com.user.adapter.location.entity

import com.user.adapter.share.BaseEntity
import com.user.util.address.CityCode
import com.user.util.address.DistrictCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "user_location_history",
    indexes = [Index(name = "idx_userId", columnList = "userId")],
)
data class UserLocationHistoryEntity(
    @Column
    val userId: Long,

    @Column
    val latitude: Double,

    @Column
    val longitude: Double,

    @Column
    @Enumerated(value = EnumType.STRING)
    val cityCode: CityCode,

    @Column
    @Enumerated(value = EnumType.STRING)
    val districtCode: DistrictCode,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {

    companion object {
        fun fromUserLocationEntity(userLocationEntity: UserLocationEntity): UserLocationHistoryEntity {
            return UserLocationHistoryEntity(
                userId = userLocationEntity.userId,
                latitude = userLocationEntity.latitude,
                longitude = userLocationEntity.longitude,
                cityCode = userLocationEntity.cityCode,
                districtCode = userLocationEntity.districtCode,
            )
        }
    }

}
