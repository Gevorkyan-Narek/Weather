package com.weather.main.screen.main

import androidx.lifecycle.*
import com.weather.android.utils.liveData
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.CityDomain
import com.weather.main.screen.mapper.DownloadStateMapper
import com.weather.navigation.NavigationGraph
import com.weather.navigation.NavigationInfo
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MainScreenViewModel(
    private val forecastUseCase: ForecastUseCase,
    geoUseCase: GeoUseCase,
    stateMapper: DownloadStateMapper,
    private val navigationGraph: NavigationGraph,
    private val logger: Logger = LoggerFactory.getLogger(MainScreenViewModel::class.java),
) : ViewModel() {

    val cityTitle = geoUseCase.selectedCity.asLiveData().map(CityDomain::name)

    private val _openBottomSheetDialog = MutableLiveData<NavigationInfo.NavigationTo>()
    val openBottomSheetFragment = _openBottomSheetDialog.liveData()

    val downloadStateLiveData = forecastUseCase.downloadState.map { state ->
        logger.debug("New download state: $state")
        stateMapper.toPres(state)
    }.asLiveData()

    fun locationClicked() {
        _openBottomSheetDialog.postValue(
            NavigationInfo.NavigationTo(navigationGraph.cityBottomSheetFragment)
        )
    }

    fun downloadErrorClicked() {
        viewModelScope.launch {
            forecastUseCase.reDownloadForecast()
        }
    }
}