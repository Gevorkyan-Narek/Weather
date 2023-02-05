package com.weather.core.domain.impl

import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoLinkDomain

class GeoUseCaseImpl(
    private val repository: GeoRepository,
) : GeoUseCase {

    override val selectedCity = repository.selectedCity

    override val savedCities = repository.savedCities

    override suspend fun downloadCities(namePrefix: String): DownloadStateDomain {
        return repository.downloadCities(namePrefix)
    }

    override suspend fun downloadNextCities(geoLinkDomain: GeoLinkDomain): DownloadStateDomain {
        return repository.downloadNextCities(geoLinkDomain)
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