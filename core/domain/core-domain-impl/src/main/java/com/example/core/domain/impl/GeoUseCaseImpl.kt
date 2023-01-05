package com.example.core.domain.impl

import com.example.core.data.api.GeoRepository
import com.example.core.domain.api.GeoUseCase
import com.example.core.domain.models.ResultWrapper
import com.example.core.domain.models.geo.GeoDomain

class GeoUseCaseImpl(
    private val repository: GeoRepository
) : GeoUseCase {

    override suspend fun getCities(namePrefix: String, offset: Int): ResultWrapper<GeoDomain> {
        return repository.getCities(namePrefix, offset)
    }

}