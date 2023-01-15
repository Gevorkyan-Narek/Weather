package com.weather.core.domain.impl

import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.Flow

class GeoUseCaseImpl(
    private val repository: GeoRepository,
) : GeoUseCase {

    override fun getCities(): Flow<GeoDomain?> {
        return repository.getCities()
    }

    override suspend fun downloadCities(namePrefix: String, offset: Int) {
        return repository.downloadCities(namePrefix, offset)
    }

    override suspend fun downloadMoreCities(): Boolean {
        return repository.downloadMoreCities()
    }
}