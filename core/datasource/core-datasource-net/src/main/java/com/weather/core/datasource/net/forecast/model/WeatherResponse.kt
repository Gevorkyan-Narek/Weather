package com.weather.core.datasource.net.forecast.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("dt")
    val dateTime: Long,
    val metrics: WeatherMetricsResponse,
    @SerializedName("weather")
    val weatherDescription: WeatherDescriptionResponse,
    val clouds: WeatherCloudsResponse,
    val wind: WeatherWindResponse,
    /** Вероятность осадков, в %*/
    val pop: Int
)