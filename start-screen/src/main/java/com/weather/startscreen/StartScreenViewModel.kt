package com.weather.startscreen

import androidx.lifecycle.*
import com.weather.android.utils.emptyString
import com.weather.android.utils.liveData
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.navigation.NavigationGraph
import com.weather.navigation.NavigationInfo
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

@OptIn(FlowPreview::class)
class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper,
    private val navigationGraph: NavigationGraph,
    private val logger: Logger = LoggerFactory.getLogger(StartScreenViewModel::class.java),
) : ViewModel() {

    companion object {
        private const val DEBOUNCE = 1000L
        private const val MOTION_DELAY = 1500L
        private const val NO_OFFSET = 0
    }

    private val geoLinks = MutableLiveData<List<GeoLinkDomain>>(emptyList())

    private val _searchList = MutableLiveData<List<CityAdapterInfo>>()
    val searchList = _searchList.liveData()

    private val _loadMoreCitiesList = MutableLiveData<List<CityAdapterInfo>>()
    val loadMoreCitiesList = _loadMoreCitiesList.liveData()

    private val _cityTextChanged = MutableStateFlow(emptyString())
    private val cityTextChanged = _cityTextChanged.debounce(DEBOUNCE)

    private val _onScrolled = MutableStateFlow(NO_OFFSET)
    private val onScrolled = _onScrolled.debounce(DEBOUNCE)

    private val isMoreDownload = MutableStateFlow(true)

    private val listUpdateFlow = combine(
        cityTextChanged,
        onScrolled,
        isMoreDownload
    ) { cityPrefix, offset, isMoreDownload ->
        geoLinks.postValue(emptyList())
        logger.debug("Offset: $offset, prefix: $cityPrefix, isMore: $isMoreDownload")
        when {
            cityPrefix.isBlank() -> {
                DownloadStateDomain.NoStart to NO_OFFSET
            }
            isMoreDownload -> {
                _addLoading.postValue(Unit)
                val cities = geoUseCase.downloadCities(cityPrefix, offset)
                cities to offset
            }
            else -> null
        }
    }.filterNotNull()

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
        viewModelScope.launch {
            cityTextChanged.collect()
        }
        viewModelScope.launch {
            onScrolled.collect()
        }
        viewModelScope.launch(singleThread) {
            listUpdateFlow.collectLatest { (cities, offset) ->
                logger.debug("Pre map: $offset, cities: $cities")
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
            logger.debug("Emit prefix: $cityPrefix")
            _cityTextChanged.emit(cityPrefix)
            isMoreDownload.emit(true)
        }
    }

    fun onScrolled(offset: Int) {
        viewModelScope.launch(singleThread) {
            _onScrolled.emit(offset)
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

    private suspend fun mapToCityAdapterInfo(downloadStateDomain: DownloadStateDomain): List<CityAdapterInfo> {
        return when (downloadStateDomain) {
            is DownloadStateDomain.Success -> {
                downloadStateDomain.geoDomain.run {
                    if (data.isEmpty()) {
                        isMoreDownload.emit(false)
                        listOf(CityAdapterInfo.NoMatch)
                    } else {
                        isMoreDownload.emit(true)
                        geoLinks.postValue(links)
                        data.map { city ->
                            CityAdapterInfo.CityInfo(geoMapper.toPres(city))
                        }
                    }
                }
            }
            DownloadStateDomain.Error -> {
                isMoreDownload.emit(false)
                listOf(CityAdapterInfo.Error)
            }
            DownloadStateDomain.Loading -> {
                isMoreDownload.emit(true)
                listOf(CityAdapterInfo.Loading)
            }
            DownloadStateDomain.NoStart -> {
                isMoreDownload.emit(false)
                emptyList()
            }
        }
    }

}