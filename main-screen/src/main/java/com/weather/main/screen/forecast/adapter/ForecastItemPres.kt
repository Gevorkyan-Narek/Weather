package com.weather.main.screen.forecast.adapter

import org.threeten.bp.LocalDate

data class ForecastItemPres(
    val dateTime: LocalDate,
    val icon: Int?,
    val description: String,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
)