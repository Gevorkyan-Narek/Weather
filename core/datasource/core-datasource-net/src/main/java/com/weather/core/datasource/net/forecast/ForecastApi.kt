package com.weather.core.datasource.net.forecast

import com.weather.core.datasource.net.forecast.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("forecast")
    suspend fun downloadForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appId") appId: String,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",
    ): ForecastResponse

}