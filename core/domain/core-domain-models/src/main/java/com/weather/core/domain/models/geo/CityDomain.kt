package com.weather.core.domain.models.geo

data class CityDomain(
    val name: String,
    val countryCode: String,
    val lat: Double,
    val lon: Double
)