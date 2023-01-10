package com.weather.startscreen.adapter

sealed class CityAdapterInfo {

    object Loading : CityAdapterInfo()

    data class CityInfo(val cityName: String) : CityAdapterInfo()

}