package com.weather.startscreen

import androidx.lifecycle.*
import com.weather.android.utils.liveData
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.navigation.NavigationGraph
import com.weather.navigation.NavigationInfo
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.mapper.GeoPresMapper
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper,
    private val navigationGraph: NavigationGraph,
) : ViewModel() {

    companion object {
        private const val MOTION_DELAY = 1500L
        private const val NO_OFFSET = 0
    }

    private val _searchList = MutableLiveData<List<CityAdapterInfo>>()
    val searchList = _searchList

    private val _loadMoreCitiesList = MutableLiveData<List<CityAdapterInfo>>()
    val loadMoreCitiesList = _loadMoreCitiesList.distinctUntilChanged()

    private val _navigationEvent = MutableLiveData<NavigationInfo.NavigationToWithPopup>()
    val navigationEvent = _navigationEvent.liveData()

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    private val _clearSearchList = MutableLiveData<Unit>()
    val clearSearchList = _clearSearchList.liveData()

    val addLoading = geoUseCase.isDownloading.asLiveData()

    private val _motionEvent = MutableLiveData<Boolean>()
    val motionEvent = _motionEvent.liveData().distinctUntilChanged()

    private val singleThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    init {
        viewModelScope.launch {
            geoUseCase.savedCities.collect { cities ->
                delay(MOTION_DELAY)
                if (cities.isEmpty()) {
                    _motionStartEvent.postValue(Unit)
                } else {
                    navigate()
                }
            }
        }
        viewModelScope.launch(singleThread) {
            geoUseCase.downloadCities.collectLatest { (cities, offset) ->
                val mappedCities = mapToCityAdapterInfo(cities)
                if (offset == NO_OFFSET) {
                    _searchList.postValue(mappedCities)
                } else {
                    _loadMoreCitiesList.postValue(mappedCities.filterNot { it is CityAdapterInfo.NoMatch })
                }
            }
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        _motionEvent.postValue(cityPrefix.isBlank())
        _clearSearchList.postValue(Unit)
        viewModelScope.launch(singleThread) {
            geoUseCase.downloadCities(cityPrefix)
        }
    }

    fun onScrolled(offset: Int) {
        viewModelScope.launch(singleThread) {
            geoUseCase.updateOffset(offset)
        }
    }

    fun onCitySelect(city: CityPres) {
        navigate()
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.saveCity(geoMapper.toDomain(city))
        }
    }

    private fun navigate() {
        _navigationEvent.postValue(
            NavigationInfo.NavigationToWithPopup(
                navigationGraph.mainScreen,
                navigationGraph.startScreen,
                inclusive = true
            )
        )
    }

    private fun mapToCityAdapterInfo(downloadStateDomain: DownloadStateDomain): List<CityAdapterInfo> {
        return when (downloadStateDomain) {
            is DownloadStateDomain.Success -> {
                downloadStateDomain.geoDomain.run {
                    if (data.isEmpty()) {
                        listOf(CityAdapterInfo.NoMatch)
                    } else {
                        data.map { city ->
                            CityAdapterInfo.CityInfo(geoMapper.toPres(city))
                        }
                    }
                }
            }
            DownloadStateDomain.Error -> {
                listOf(CityAdapterInfo.Error)
            }
            DownloadStateDomain.Loading -> {
                listOf(CityAdapterInfo.Loading)
            }
            DownloadStateDomain.NoStart -> {
                emptyList()
            }
        }
    }

}