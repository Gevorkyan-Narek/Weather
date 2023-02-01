package com.weather.core.datasource.net.forecast.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("main")
    val metrics: WeatherMetricsResponse,
    val weather: List<WeatherShortInfoResponse>,
    val clouds: WeatherCloudsResponse,
    val wind: WeatherWindResponse,
    val visibility: Int,
    /** Вероятность осадков, в %*/
    val pop: Double
)