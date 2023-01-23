package com.weather.core.domain.api

import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.Flow

interface GeoUseCase {

    val selectedCity: Flow<CityDomain>

    fun getCities(): Flow<List<CityDomain>>

    val isHasMoreCities: Flow<Boolean>

    fun getDownloadCities(): Flow<GeoDomain>

    suspend fun downloadCities(namePrefix: String, offset: Int = 0)

    suspend fun downloadMoreCities()

    suspend fun saveCity(city: CityDomain)

    suspend fun reSelectCity(cityName: String)

}