package com.weather.startscreen.adapter

import com.weather.startscreen.models.CityPres

sealed class CityAdapterInfo(val viewType: Int) {

    companion object {
        const val CITY_INFO_VIEW_TYPE = 0
        const val LOADING_VIEW_TYPE = 1
        const val NO_MATCH_VIEW_TYPE = 2
        const val ERROR_VIEW_TYPE = 3
    }

    object NoMatch : CityAdapterInfo(NO_MATCH_VIEW_TYPE)

    object Loading : CityAdapterInfo(LOADING_VIEW_TYPE)

    object Error : CityAdapterInfo(ERROR_VIEW_TYPE)

    data class CityInfo(val city: CityPres) : CityAdapterInfo(CITY_INFO_VIEW_TYPE)

}