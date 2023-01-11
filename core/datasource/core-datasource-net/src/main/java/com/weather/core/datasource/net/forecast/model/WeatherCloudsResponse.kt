package com.weather.core.datasource.net.forecast.model

import com.google.gson.annotations.SerializedName

data class WeatherCloudsResponse(
    /** Облачность, в %*/
    @SerializedName("all")
    val cloudiness: Int
)