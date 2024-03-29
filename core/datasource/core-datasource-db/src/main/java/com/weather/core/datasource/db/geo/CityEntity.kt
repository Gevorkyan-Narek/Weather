package com.weather.core.datasource.db.geo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey
    val name: String,
    val countryCode: String,
    val lat: Double,
    val lon: Double,
    val isSelected: Boolean
)