package com.weather.core.datasource.db.geo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val countryCode: String,
    val lat: Double,
    val lon: Double,
)