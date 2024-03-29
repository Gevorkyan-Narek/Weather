package com.weather.core.datasource.net.geo

import com.weather.core.datasource.net.geo.model.GeoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {

    @GET("/v1/geo/cities")
    suspend fun downloadCities(
        @Query(value = "namePrefix") namePrefix: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "languageCode") languageCode: String,
        @Query(value = "offset") offset: Int
    ): GeoResponse

}