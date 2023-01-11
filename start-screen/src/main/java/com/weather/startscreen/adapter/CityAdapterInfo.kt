package com.weather.startscreen.adapter

import com.weather.startscreen.models.CityPres

sealed class CityAdapterInfo {

    object Loading : CityAdapterInfo()

    data class CityInfo(val city: CityPres) : CityAdapterInfo()

}