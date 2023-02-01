package com.weather.core.datasource.net.forecast.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list")
    val forecast: List<WeatherResponse>,
)