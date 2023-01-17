package com.weather.core.domain.models.forecast

data class WeatherWindDomain(
    val speed: Double,
    val degree: Int,
    val gust: Double,
)