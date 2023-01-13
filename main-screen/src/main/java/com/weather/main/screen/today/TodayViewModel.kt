package com.weather.main.screen.today

import androidx.lifecycle.ViewModel
import com.weather.core.domain.api.ForecastUseCase

class TodayViewModel(
    private val forecastUseCase: ForecastUseCase
) : ViewModel() {
}