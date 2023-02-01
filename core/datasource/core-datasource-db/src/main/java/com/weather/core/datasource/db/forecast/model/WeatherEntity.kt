package com.weather.core.datasource.db.forecast.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val dateTime: Long,
    val date: String,
    @Embedded
    val metrics: WeatherMetricsEntity,
    @Embedded
    val wind: WeatherWindEntity,
    @Embedded
    val shortInfo: WeatherShortInfoEntity?,
)