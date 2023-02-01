package com.weather.main.screen.city.changer.model

data class CityInfoItemPres(
    val name: String,
    val countryCode: String,
    val lat: Double,
    val lon: Double,
    val isSelected: Boolean,
)