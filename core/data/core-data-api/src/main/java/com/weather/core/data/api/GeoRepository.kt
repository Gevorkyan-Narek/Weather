package com.weather.core.data.api

import com.weather.core.domain.models.ResultWrapper
import com.weather.core.domain.models.geo.GeoDomain

interface GeoRepository {

    suspend fun getCities(namePrefix: String, offset: Int): ResultWrapper<GeoDomain>

}