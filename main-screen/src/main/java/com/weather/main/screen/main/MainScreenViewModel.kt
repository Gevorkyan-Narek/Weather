package com.weather.main.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.weather.android.utils.liveData
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.CityDomain
import com.weather.navigation.NavigationGraph
import com.weather.navigation.NavigationInfo

class MainScreenViewModel(
    forecastUseCase: ForecastUseCase,
    geoUseCase: GeoUseCase,
    private val navigationGraph: NavigationGraph,
) : ViewModel() {

    val cityTitle = geoUseCase.selectedCity.asLiveData().map(CityDomain::name)

    private val _openBottomSheetDialog = MutableLiveData<NavigationInfo.NavigationTo>()
    val openBottomSheetFragment = _openBottomSheetDialog.liveData()

    val isDownloadLoadingWarning = forecastUseCase.isDownloading.asLiveData()

    fun locationClicked() {
        _openBottomSheetDialog.postValue(
            NavigationInfo.NavigationTo(navigationGraph.cityBottomSheetFragment)
        )
    }

}