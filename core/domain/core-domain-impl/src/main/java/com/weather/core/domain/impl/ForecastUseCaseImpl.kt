package com.weather.core.domain.impl

import com.weather.core.data.api.ForecastRepository
import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ForecastUseCaseImpl(
    private val repository: ForecastRepository,
    private val geoRepository: GeoRepository
) : ForecastUseCase {

    override fun getCities(): Flow<List<CityDomain>> {
        return geoRepository.getCities()
    }

    override suspend fun downloadForecast() {
        return repository.downloadForecast(geoRepository.getCities().first().first())
    }

    override fun getTodayForecast(): Flow<List<WeatherDomain>> {
        return repository.getTodayForecast()
    }

}