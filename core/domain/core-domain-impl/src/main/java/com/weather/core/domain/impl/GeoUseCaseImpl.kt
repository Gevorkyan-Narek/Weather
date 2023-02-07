package com.weather.core.domain.impl

import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain

class GeoUseCaseImpl(
    private val repository: GeoRepository,
) : GeoUseCase {

    override val selectedCity = repository.selectedCity

    override val savedCities = repository.savedCities

    override suspend fun downloadCities(namePrefix: String, offset: Int): DownloadStateDomain {
        return repository.downloadCities(namePrefix, offset)
    }

    override suspend fun saveCity(city: CityDomain) {
        repository.saveCity(city)
    }

    override suspend fun removeSavedCity(city: CityDomain) {
        repository.removeSavedCity(city)
    }

    override suspend fun reSelectCity(city: CityDomain) {
        repository.updateSelectedCity(city)
    }

}