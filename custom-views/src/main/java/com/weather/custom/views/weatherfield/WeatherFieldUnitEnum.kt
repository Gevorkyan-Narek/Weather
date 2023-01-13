package com.weather.custom.views.weatherfield

import androidx.annotation.StringRes
import com.weather.custom.views.R

enum class WeatherFieldUnitEnum(@StringRes val unit: Int) {

    NONE(R.string.none),
    PERCENT(R.string.percent),
    SPEED(R.string.speed);

    companion object {

        fun valueOf(unit: Int) = when (unit) {
            1 -> PERCENT
            2 -> SPEED
            else -> NONE
        }

    }

}