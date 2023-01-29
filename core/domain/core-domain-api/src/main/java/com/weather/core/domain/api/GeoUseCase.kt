package com.weather.core.domain.api

import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

interface GeoUseCase {

    val searchStateFlow: Flow<Unit>

    val downloadMoreCitiesStateFlow: Flow<Unit>

    val savedCities: Flow<List<CityDomain>>

    val downloadedCities: Flow<List<CityDomain>>

    val isLoading: Flow<Unit>

    val selectedCity: Flow<CityDomain>

    suspend fun downloadCities(namePrefix: String, offset: Int = 0)

    suspend fun downloadMoreCities()

    suspend fun saveCity(city: CityDomain)

    suspend fun reSelectCity(city: CityDomain)

    suspend fun searchCity(cityName: String)

    suspend fun removeSavedCity(city: CityDomain)

}