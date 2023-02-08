package com.weather.main.screen.city.adapter

import com.weather.main.screen.city.adapter.model.CityInfoItemPres

sealed class CityAdapterInfo(val viewType: Int) {

    companion object {
        const val NO_MATCH_VIEW_TYPE = 0
        const val LOADING_VIEW_TYPE = 1
        const val NEW_CITY_VIEW_TYPE = 2
        const val CITY_VIEW_TYPE = 3
        const val ERROR_VIEW_TYPE = 4
    }

    object NoMatch : CityAdapterInfo(NO_MATCH_VIEW_TYPE)

    object Loading : CityAdapterInfo(LOADING_VIEW_TYPE)

    object Error : CityAdapterInfo(ERROR_VIEW_TYPE)

    data class NewCityInfo(val city: CityInfoItemPres) : CityAdapterInfo(NEW_CITY_VIEW_TYPE)

    data class CityInfo(val city: CityInfoItemPres) : CityAdapterInfo(CITY_VIEW_TYPE)

}