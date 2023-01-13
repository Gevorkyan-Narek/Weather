package com.weather.core.datasource.db

data class WeatherMetricsEntity(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val cloudiness: Int,
    val windSpeed: Double,
    val pop: Double,
)