package com.weather.main.screen.model

data class WeatherMetricsPres(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    /** Влажность */
    val humidity: Int,
    /** Облачность, в %*/
    val cloudiness: Int,
    /** Вероятность осадков, в %*/
    val pop: Int,
)