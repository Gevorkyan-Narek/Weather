package com.weather.core.domain.api

import com.weather.core.domain.models.geo.CityDomain

interface ForecastUseCase {

    suspend fun downloadForecast(cityDomain: CityDomain)

}