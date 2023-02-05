package com.weather.core.domain.api

import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.forecast.WeatherDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ForecastUseCase {

    val downloadState: StateFlow<DownloadStateDomain>

    suspend fun reDownloadForecast()

    fun getTodayForecast(): Flow<List<WeatherDomain>>

    fun getForecast(): Flow<List<WeatherDomain>>

}