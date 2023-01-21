package com.weather.core.domain.models.forecast

data class WeatherMetricsDomain(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    /** Влажность */
    val humidity: Int,
    /** Облачность, в %*/
    val cloudiness: Int,
    /** Вероятность осадков, в %*/
    val pop: Double,
    val visibility: Int
)