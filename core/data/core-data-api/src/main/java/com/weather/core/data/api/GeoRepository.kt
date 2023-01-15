package com.weather.core.data.api

import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.Flow

interface GeoRepository {

    val isHasMoreCities: Flow<Boolean>

    fun getCities(): Flow<GeoDomain>

    suspend fun downloadCities(namePrefix: String, offset: Int)

    suspend fun downloadMoreCities()
}