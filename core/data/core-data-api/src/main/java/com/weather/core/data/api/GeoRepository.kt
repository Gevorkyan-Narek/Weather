package com.weather.core.data.api

import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import kotlinx.coroutines.flow.Flow

interface GeoRepository {

    val savedCities: Flow<List<CityDomain>>

    val downloadedCities: Flow<GeoDomain>

    val downloadedNextCities: Flow<GeoDomain>

    val selectedCity: Flow<CityDomain>

    suspend fun downloadCities(namePrefix: String)

    suspend fun downloadNextCities(geoLink: GeoLinkDomain)

    suspend fun saveCity(city: CityDomain)

    suspend fun updateSelectedCity(city: CityDomain)

    suspend fun removeSavedCity(city: CityDomain)

}