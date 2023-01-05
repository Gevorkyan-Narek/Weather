package com.example.core.domain.api

import com.example.core.domain.models.ResultWrapper
import com.example.core.domain.models.geo.GeoDomain

interface GeoUseCase {

    suspend fun getCities(namePrefix: String, offset: Int = 0): ResultWrapper<GeoDomain>

}