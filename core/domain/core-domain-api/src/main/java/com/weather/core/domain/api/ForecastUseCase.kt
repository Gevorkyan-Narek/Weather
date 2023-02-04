package com.weather.core.domain.api

import com.weather.core.domain.models.forecast.WeatherDomain
import kotlinx.coroutines.flow.Flow

interface ForecastUseCase {

    val isDownloading: Flow<Boolean>

    fun getTodayForecast(): Flow<List<WeatherDomain>>

    fun getForecast(): Flow<List<WeatherDomain>>

}