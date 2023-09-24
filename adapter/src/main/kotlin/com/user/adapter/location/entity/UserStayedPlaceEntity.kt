package com.user.adapter.location.entity

import com.user.domain.location.UserStayedPlace
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "user_stayed_course_location",
    indexes = [Index(name = "idx_userId", columnList = "userId")]
)
data class UserStayedPlaceEntity(

    @Column
    val userId: Long,

    @Column
    val placeId: Long,

    @Column
    val placeUserDescription: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    companion object {
        fun fromUserStayedPlace(userStayedPlace: UserStayedPlace): UserStayedPlaceEntity {
            return UserStayedPlaceEntity(
                userId = userStayedPlace.user.userId,
                placeId = userStayedPlace.place.id,
                placeUserDescription = userStayedPlace.placeUserDescription,
                id = userStayedPlace.id,
            )
        }
    }
}