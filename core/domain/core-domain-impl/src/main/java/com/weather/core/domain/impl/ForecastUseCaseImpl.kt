package com.weather.core.domain.impl

import com.weather.core.data.api.ForecastRepository
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

class ForecastUseCaseImpl(
    private val repository: ForecastRepository
) : ForecastUseCase {

    override suspend fun downloadForecast(cityDomain: CityDomain) {
        return repository.downloadForecast(cityDomain)
    }

    override fun getTodayForecast(): Flow<List<WeatherDomain>> {
        return repository.getTodayForecast()
    }

}