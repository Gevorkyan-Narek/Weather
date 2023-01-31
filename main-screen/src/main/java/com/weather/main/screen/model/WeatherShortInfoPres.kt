package com.weather.main.screen.model

import androidx.annotation.DrawableRes

data class WeatherShortInfoPres(
    val description: String,
    @DrawableRes val icon: Int?,
)