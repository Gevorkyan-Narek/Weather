package com.weather.main.screen.model

import org.threeten.bp.LocalDateTime

data class WeatherPres(
    val dateTime: LocalDateTime,
    val metrics: WeatherMetricsPres,
    val wind: WeatherWindPres,
    val shortInfo: List<WeatherShortInfoPres>,
)