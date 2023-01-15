package com.weather.core.domain.api

import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.Flow

interface GeoUseCase {

    fun getCities(): Flow<GeoDomain?>

    suspend fun downloadCities(namePrefix: String, offset: Int = 0)

    suspend fun downloadMoreCities(): Boolean

}