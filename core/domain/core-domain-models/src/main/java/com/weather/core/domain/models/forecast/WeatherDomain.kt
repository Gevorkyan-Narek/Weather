package com.weather.core.domain.models.forecast

import org.threeten.bp.LocalDateTime

data class WeatherDomain(
    val dateTime: LocalDateTime,
    val metrics: WeatherMetricsDomain,
    val wind: WeatherWindDomain,
    val shortInfo: List<WeatherShortInfoDomain>,
)