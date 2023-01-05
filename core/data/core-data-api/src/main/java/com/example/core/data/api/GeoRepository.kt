package com.example.core.data.api

import com.example.core.domain.models.ResultWrapper
import com.example.core.domain.models.geo.GeoDomain

interface GeoRepository {

    suspend fun getCities(namePrefix: String, offset: Int): ResultWrapper<GeoDomain>

}