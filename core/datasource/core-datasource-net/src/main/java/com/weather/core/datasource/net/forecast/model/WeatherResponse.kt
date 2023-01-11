package com.weather.core.datasource.net.forecast.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("main")
    val metrics: WeatherMetricsResponse,
    @SerializedName("weather")
    val weatherDescription: List<WeatherDescriptionResponse>,
    val clouds: WeatherCloudsResponse,
    val wind: WeatherWindResponse,
    /** Вероятность осадков, в %*/
    val pop: Double
)