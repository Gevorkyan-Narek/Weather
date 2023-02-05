package com.weather.core.domain.api

import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import kotlinx.coroutines.flow.Flow

interface GeoUseCase {

    val savedCities: Flow<List<CityDomain>>

    val selectedCity: Flow<CityDomain>

    suspend fun downloadCities(namePrefix: String): DownloadStateDomain

    suspend fun downloadNextCities(geoLinkDomain: GeoLinkDomain): DownloadStateDomain

    suspend fun saveCity(city: CityDomain)

    suspend fun removeSavedCity(city: CityDomain)

    suspend fun reSelectCity(city: CityDomain)

}