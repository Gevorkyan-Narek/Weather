package com.weather.core.domain.models.forecast

import java.util.Date

data class WeatherDomain(
    val dateTime: Date,
    val metrics: WeatherMetricsDomain,
    val weatherDescription: List<WeatherDescriptionDomain>,
    val clouds: WeatherCloudsDomain,
    val wind: WeatherWindDomain,
    /** Вероятность осадков, в %*/
    val pop: Double
)