package com.weather.core.data.api

import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun downloadForecast(cityDomain: CityDomain)

    fun getTodayForecast(): Flow<List<WeatherDomain>>

}