package com.weather.core.data.api

import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ForecastRepository {

    val downloadState: StateFlow<DownloadStateDomain>

    suspend fun downloadForecast(cityDomain: CityDomain)

    fun getTodayForecast(): Flow<List<WeatherDomain>>

    fun getForecast(): Flow<List<WeatherDomain>>

    suspend fun removeOldForecast()

}