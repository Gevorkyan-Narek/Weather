package com.weather.core.datasource.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateTime: Long,
    @Embedded
    val metrics: WeatherMetricsEntity,
    val description: List<String>,
)