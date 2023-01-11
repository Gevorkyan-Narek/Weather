package com.weather.core.domain.impl

import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.GeoDomain

class GeoUseCaseImpl(
    private val repository: GeoRepository
) : GeoUseCase {

    override suspend fun downloadCities(namePrefix: String, offset: Int): GeoDomain? {
        return repository.downloadCities(namePrefix, offset)
    }

    override suspend fun downloadMoreCities(): GeoDomain? {
        return repository.downloadMoreCities()
    }
}