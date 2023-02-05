package com.weather.core.domain.impl

import com.weather.core.data.api.ForecastRepository
import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.models.forecast.WeatherDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ForecastUseCaseImpl(
    private val repository: ForecastRepository,
    private val geoRepository: GeoRepository,
) : ForecastUseCase {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            geoRepository.selectedCity.collect { city ->
                repository.downloadForecast(city)
            }
        }
    }

    override val downloadState = repository.downloadState

    override fun getTodayForecast(): Flow<List<WeatherDomain>> {
        return repository.getTodayForecast()
    }

    override fun getForecast(): Flow<List<WeatherDomain>> {
        return repository.getForecast()
    }

    override suspend fun reDownloadForecast() {
        geoRepository.selectedCity.firstOrNull()?.let { city ->
            repository.downloadForecast(city)
        }
    }

}