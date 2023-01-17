package com.weather.core.datasource.net.forecast.model

import com.google.gson.annotations.SerializedName

data class WeatherWindResponse(
    val speed: Double,
    @SerializedName("deg")
    val degree: Int,
    val gust: Double,
)