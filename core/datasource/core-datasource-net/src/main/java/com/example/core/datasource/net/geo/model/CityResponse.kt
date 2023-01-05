package com.example.core.datasource.net.geo.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    val name: String,
    val countryCode: String,
    @SerializedName("latitude")
    val lat: Double,
    @SerializedName("longitude")
    val lon: Double
)