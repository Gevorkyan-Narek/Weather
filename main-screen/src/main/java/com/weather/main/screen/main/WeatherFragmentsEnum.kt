package com.weather.main.screen.main

import androidx.annotation.StringRes
import com.weather.main.screen.R

enum class WeatherFragmentsEnum(@StringRes val titleId: Int) {
    TODAY(R.string.today),
    FORECAST(R.string.forecast);
}