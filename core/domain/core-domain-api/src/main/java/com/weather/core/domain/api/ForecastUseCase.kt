package com.weather.core.domain.api

import com.weather.core.domain.models.forecast.WeatherDomain
import kotlinx.coroutines.flow.Flow

interface ForecastUseCase {

    val isDownloading: Flow<String?>

    fun getTodayForecast(): Flow<List<WeatherDomain>>

    fun getForecast(): Flow<List<WeatherDomain>>

}