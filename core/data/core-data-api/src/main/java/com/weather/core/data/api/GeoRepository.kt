package com.weather.core.data.api

import com.weather.core.domain.models.geo.GeoDomain

interface GeoRepository {

    suspend fun downloadCities(namePrefix: String, offset: Int): GeoDomain?

    suspend fun downloadMoreCities(): GeoDomain?
}