package com.user.adapter.location.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "location_district")
data class LocationDistrictEntity(

    @Column
    val code: Long,

    @Column
    val name: String,

    @Column
    val coordinates: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
)