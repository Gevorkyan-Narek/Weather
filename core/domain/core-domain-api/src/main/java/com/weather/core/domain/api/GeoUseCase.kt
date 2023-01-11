package com.weather.core.domain.api

import com.weather.core.domain.models.geo.GeoDomain

interface GeoUseCase {

    suspend fun downloadCities(namePrefix: String, offset: Int = 0): GeoDomain?

    suspend fun downloadMoreCities(): GeoDomain?

}