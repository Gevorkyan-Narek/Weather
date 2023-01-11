package com.weather.core.domain.models.forecast

data class WeatherMetricsDomain(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    /** Влажность */
    val humidity: Int
)