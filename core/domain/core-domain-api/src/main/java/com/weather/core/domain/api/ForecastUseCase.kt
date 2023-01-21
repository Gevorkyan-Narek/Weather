package com.weather.core.domain.api

import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

interface ForecastUseCase {

    fun getCities(): Flow<List<CityDomain>>

    suspend fun downloadForecast()

    fun getTodayForecast(): Flow<List<WeatherDomain>>

}