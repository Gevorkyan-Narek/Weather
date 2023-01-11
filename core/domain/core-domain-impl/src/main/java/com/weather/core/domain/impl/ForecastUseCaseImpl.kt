package com.weather.core.domain.impl

import com.weather.core.data.api.ForecastRepository
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.models.forecast.ForecastDomain
import com.weather.core.domain.models.geo.CityDomain

class ForecastUseCaseImpl(
    private val repository: ForecastRepository
) : ForecastUseCase {

    override suspend fun downloadForecast(cityDomain: CityDomain): ForecastDomain? {
        return repository.getForecast(cityDomain)
    }

}