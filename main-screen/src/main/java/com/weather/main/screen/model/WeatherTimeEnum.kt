package com.weather.main.screen.model

import org.threeten.bp.LocalDateTime

enum class WeatherTimeEnum {
    MORNING,
    DAY,
    EVENING;

    companion object {

        fun valueOf(day: LocalDateTime) = when {
            day.hour < 12 -> MORNING
            day.hour < 18 -> DAY
            else -> EVENING
        }

    }
}