package com.weather.core.domain.api

import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

interface GeoUseCase {

    val savedCities: Flow<List<CityDomain>>

    val selectedCity: Flow<CityDomain>

    val downloadCities: Flow<Pair<DownloadStateDomain, Int>>

    val isDownloading: Flow<Unit>

    suspend fun downloadCities(namePrefix: String)

    suspend fun updateOffset(offset: Int)

    suspend fun saveCity(city: CityDomain)

    suspend fun removeSavedCity(city: CityDomain)

    suspend fun reSelectCity(city: CityDomain)

}