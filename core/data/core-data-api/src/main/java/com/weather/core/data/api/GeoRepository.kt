package com.weather.core.data.api

import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

interface GeoRepository {

    val savedCities: Flow<List<CityDomain>>

    val selectedCity: Flow<CityDomain>

    suspend fun downloadCities(namePrefix: String, offset: Int): DownloadStateDomain

    suspend fun saveCity(city: CityDomain)

    suspend fun updateSelectedCity(city: CityDomain)

    suspend fun removeSavedCity(city: CityDomain)

}