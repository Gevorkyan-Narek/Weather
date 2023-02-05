package com.weather.startscreen

import androidx.lifecycle.*
import com.weather.android.utils.emptyString
import com.weather.android.utils.liveData
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain
import com.weather.navigation.NavigationGraph
import com.weather.navigation.NavigationInfo
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@OptIn(FlowPreview::class)
class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper,
    private val navigationGraph: NavigationGraph,
    private val logger: Logger = LoggerFactory.getLogger(StartScreenViewModel::class.java),
) : ViewModel() {

    companion object {
        private const val DEBOUNCE = 1500L
        private const val MOTION_DELAY = 1500L
    }

    private val geoLinks = MutableLiveData<List<GeoLinkDomain>>(emptyList())

    private val _searchList = MutableLiveData<List<CityAdapterInfo>>()
    val searchList = _searchList.liveData()

    private val _loadMoreCitiesList = MutableLiveData<List<CityAdapterInfo>>()
    val loadMoreCitiesList = _loadMoreCitiesList.liveData()

    private val _cityTextChanged = MutableStateFlow(emptyString())
    private val cityTextChanged = _cityTextChanged
        .filterNot { text -> text.isBlank() }
        .distinctUntilChanged()
        .debounce(DEBOUNCE)

    private val _onScrolled = MutableStateFlow<GeoLinkDomain?>(null)
    private val onScrolled = _onScrolled
        .filterNotNull()
        .debounce(DEBOUNCE)

    private val _navigationEvent = MutableLiveData<NavigationInfo.NavigationToWithPopup>()
    val navigationEvent = _navigationEvent.liveData()

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    private val _clearSearchList = MutableLiveData<Unit>()
    val clearSearchList = _clearSearchList.liveData()

    private val _addLoading = MutableLiveData<Unit>()
    val addLoading = _addLoading.liveData()

    private val _motionEvent = MutableLiveData<Boolean>()
    val motionEvent = _motionEvent.liveData().distinctUntilChanged()

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
        viewModelScope.launch {
            cityTextChanged.collectLatest { cityPrefix ->
                logger.debug("Search new city: $cityPrefix")
                val cities = geoUseCase.downloadCities(cityPrefix)
                _searchList.postValue(mapToCityAdapterInfo(cities))
            }
        }
        viewModelScope.launch {
            onScrolled.collectLatest { link ->
                logger.debug("Scrolling UI: ${link.href}")
                val nextCities = geoUseCase.downloadNextCities(link)
                _loadMoreCitiesList.postValue(mapToCityAdapterInfo(nextCities))
            }
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        _motionEvent.postValue(cityPrefix.isBlank())
        _clearSearchList.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            _cityTextChanged.emit(cityPrefix)
        }
    }

    fun onScrolled() {
        viewModelScope.launch(Dispatchers.IO) {
            geoLinks.value?.find { link ->
                link.rel == GeoRelEnumsDomain.NEXT
            }?.let { nextLink ->
                _addLoading.postValue(Unit)
                _onScrolled.emit(nextLink)
            }
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
                        geoLinks.postValue(emptyList())
                        listOf(CityAdapterInfo.NoMatch)
                    } else {
                        geoLinks.postValue(links)
                        data.map { city ->
                            CityAdapterInfo.CityInfo(geoMapper.toPres(city))
                        }
                    }
                }
            }
            DownloadStateDomain.Error -> {
                geoLinks.postValue(emptyList())
                listOf(CityAdapterInfo.Error)
            }
            DownloadStateDomain.Loading -> {
                geoLinks.postValue(emptyList())
                listOf(CityAdapterInfo.Loading)
            }
        }
    }

}