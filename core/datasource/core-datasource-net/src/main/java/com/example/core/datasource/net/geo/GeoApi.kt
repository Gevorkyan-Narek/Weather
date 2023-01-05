package com.example.core.datasource.net.geo

import com.example.core.datasource.net.geo.model.GeoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {

    @GET("cities")
    suspend fun getCities(
        @Query(value = "namePrefix") namePrefix: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "languageCode") languageCode: String,
        @Query(value = "offset") offset: Int
    ): GeoResponse

}