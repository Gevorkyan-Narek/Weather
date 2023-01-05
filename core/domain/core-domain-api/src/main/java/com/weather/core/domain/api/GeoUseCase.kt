package com.weather.core.domain.api

import com.weather.core.domain.models.ResultWrapper
import com.weather.core.domain.models.geo.GeoDomain

interface GeoUseCase {

    suspend fun getCities(namePrefix: String, offset: Int = 0): ResultWrapper<GeoDomain>

}