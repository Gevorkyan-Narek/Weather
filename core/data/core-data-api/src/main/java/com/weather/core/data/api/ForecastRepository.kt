package com.weather.core.data.api

import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    val isDownloading: Flow<Boolean>

    suspend fun downloadForecast(cityDomain: CityDomain)

    fun getTodayForecast(): Flow<List<WeatherDomain>>

    fun getForecast(): Flow<List<WeatherDomain>>

}