package com.weather.core.data.api

import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.Flow

interface GeoRepository {

    val selectedCity: Flow<CityDomain>

    val isHasMoreCities: Flow<Boolean>

    fun getDownloadCities(): Flow<GeoDomain>

    suspend fun downloadCities(namePrefix: String, offset: Int)

    suspend fun downloadMoreCities()

    suspend fun saveCity(city: CityDomain)

    fun getCities(): Flow<List<CityDomain>>

    suspend fun updateSelectedCity(cityName: String)

}