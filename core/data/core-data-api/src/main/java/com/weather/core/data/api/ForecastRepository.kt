package com.weather.core.data.api

import com.weather.core.domain.models.geo.CityDomain

interface ForecastRepository {

    suspend fun downloadForecast(cityDomain: CityDomain)

//    suspend fun getTodayForecast(): ForecastDomain

}