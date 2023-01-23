package com.weather.core.domain.api

import com.weather.core.domain.models.forecast.WeatherDomain
import kotlinx.coroutines.flow.Flow

interface ForecastUseCase {

    fun getTodayForecast(): Flow<List<WeatherDomain>>

}