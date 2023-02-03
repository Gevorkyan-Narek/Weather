package com.weather.startscreen

import androidx.lifecycle.*
import com.weather.android.utils.liveData
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain
import com.weather.navigation.NavigationGraph
import com.weather.navigation.NavigationInfo
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
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

    val searchList = geoUseCase.downloadedCities
        .map { domain ->
            geoLinks.postValue(domain.links)
            if (domain.data.isEmpty()) {
                listOf(CityAdapterInfo.NoMatch)
            } else {
                domain.data.map { city ->
                    CityAdapterInfo.CityInfo(geoMapper.toPres(city))
                }
            }
        }
        .asLiveData()

    val loadMoreCitiesList = geoUseCase.downloadedNextCities
        .map { domain ->
            geoLinks.postValue(domain.links)
            domain.data.map { city ->
                CityAdapterInfo.CityInfo(geoMapper.toPres(city))
            }
        }
        .asLiveData()

    private val _cityTextChanged =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val cityTextChanged = _cityTextChanged
        .filterNot { text -> text.isBlank() }
        .distinctUntilChanged()
        .debounce(DEBOUNCE)

    private val _onScrolled =
        MutableSharedFlow<GeoLinkDomain?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val onScrolled = _onScrolled
        .distinctUntilChanged()
        .debounce(DEBOUNCE)

    private val _navigationEvent = MutableLiveData<NavigationInfo.NavigationToWithPopup>()
    val navigationEvent = _navigationEvent.liveData()

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    private val _isLoading = MutableLiveData<Unit>()
    val isLoading = _isLoading.liveData()

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
                geoUseCase.downloadCities(cityPrefix)
            }
        }
        viewModelScope.launch {
            onScrolled.collectLatest { link ->
                if (link != null) {
                    logger.debug("Scrolled: ${link.href}")
                    geoUseCase.downloadNextCities(link)
                }
            }
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        _motionEvent.postValue(cityPrefix.isBlank())
        _isLoading.postValue(Unit)
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

}