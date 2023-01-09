package com.weather.core.data.api

import com.weather.core.domain.models.geo.GeoDomain

interface GeoRepository {

    suspend fun getCities(namePrefix: String, offset: Int): GeoDomain?

    suspend fun getNextCities(): GeoDomain?
}