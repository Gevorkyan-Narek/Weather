package com.weather.core.datasource.net.geo

import com.weather.core.datasource.net.geo.model.GeoResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GeoApi {

    @GET("/v1/geo/cities")
    suspend fun getCities(
        @Query(value = "namePrefix") namePrefix: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "languageCode") languageCode: String,
        @Query(value = "offset") offset: Int
    ): GeoResponse

    @GET
    suspend fun getNextCities(@Url href: String): GeoResponse

}