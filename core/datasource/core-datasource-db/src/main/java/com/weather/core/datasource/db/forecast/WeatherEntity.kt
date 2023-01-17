package com.weather.core.datasource.db.forecast

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val dateTime: Long,
    @Embedded
    val metrics: WeatherMetricsEntity,
    @Embedded
    val wind: WeatherWindEntity,
    val description: List<String>,
)