package com.user.domain.location.port

import com.user.domain.location.Place
import org.springframework.stereotype.Component

@Component
interface PlacePort {

    fun upsertPlace(place: Place): Place

    fun getPlaceOrNullByAddress(address: String): Place?

    fun getPlaceById(id: Long): Place

    fun getPlaceByIds(ids: List<Long>): List<Place>

    fun getPlacesByAdministrativeCategoryId(administrativeCategoryId: String): List<Place>

}