package com.weather.core.data.api

import com.weather.core.domain.models.forecast.ForecastDomain
import com.weather.core.domain.models.geo.CityDomain

interface ForecastRepository {

    suspend fun getForecast(cityDomain: CityDomain): ForecastDomain?

}