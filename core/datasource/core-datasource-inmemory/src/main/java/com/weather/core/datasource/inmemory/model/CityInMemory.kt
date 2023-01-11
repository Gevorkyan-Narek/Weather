package com.weather.core.datasource.inmemory.model

data class CityInMemory(
    val name: String,
    val countryCode: String,
    val lat: Double,
    val lon: Double
)