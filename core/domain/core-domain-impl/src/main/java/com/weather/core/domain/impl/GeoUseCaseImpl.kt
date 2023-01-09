package com.weather.core.domain.impl

import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.ResultWrapper
import com.weather.core.domain.models.geo.GeoDomain

class GeoUseCaseImpl(
    private val repository: GeoRepository
) : GeoUseCase {

    override suspend fun getCities(namePrefix: String?, offset: Int): ResultWrapper<GeoDomain> {
        return repository.getCities(namePrefix, offset)
    }

}