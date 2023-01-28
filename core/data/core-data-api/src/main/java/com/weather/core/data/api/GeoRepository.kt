package com.weather.core.data.api

import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.Flow

interface GeoRepository {

    val savedCities: Flow<List<CityDomain>>

    val downloadedCities: Flow<GeoDomain>

    val selectedCity: Flow<CityDomain>

    suspend fun isHasMoreCities(): Boolean

    suspend fun downloadCities(namePrefix: String, offset: Int)

    suspend fun downloadMoreCities()

    suspend fun saveCity(city: CityDomain)

    suspend fun updateSelectedCity(city: CityDomain)

}