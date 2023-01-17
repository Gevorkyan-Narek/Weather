package com.weather.main.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.weather.core.domain.api.ForecastUseCase
import kotlinx.coroutines.launch

class MainScreenViewModel(
    forecastUseCase: ForecastUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            forecastUseCase.downloadForecast()
        }
    }

    val cityTitle = forecastUseCase.getCities().asLiveData()

}