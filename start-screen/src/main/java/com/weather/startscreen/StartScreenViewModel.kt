package com.weather.startscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.liveData
import com.weather.android.utils.postEvent
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.api.GeoUseCase
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper,
    private val forecastUseCase: ForecastUseCase,
    private val logger: Logger = LoggerFactory.getLogger(StartScreenViewModel::class.java),
) : ViewModel() {

    companion object {
        private const val TEXT_CHANGE_DEBOUNCE = 500L
        private const val SCROLL_DEBOUNCE = 1500L
    }

    private val _searchStateFlow = MutableStateFlow<String?>(null)

    private val searchStateFlow = _searchStateFlow
        .filterNotNull()
        .map { searchText ->
            _motionEvent.postValue(searchText.isNotBlank())
            searchText
        }
        .debounce(TEXT_CHANGE_DEBOUNCE)
        .distinctUntilChanged()
        .mapLatest { searchText ->
            if (searchText.isNotBlank()) {
                geoUseCase.downloadCities(searchText)
            }
        }

    private val _scrollStateFlow =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val scrollStateFlow = _scrollStateFlow
        .map {
            if (geoUseCase.isHasMoreCities.first())
                addLoading()
        }
        .debounce(SCROLL_DEBOUNCE)
        .mapLatest {
            geoUseCase.downloadMoreCities()
        }

    private val _motionEvent = MutableLiveData<Boolean>()
    val motionEvent = _motionEvent.liveData().distinctUntilChanged()

    private val _searchList = MutableLiveData<List<CityAdapterInfo>>()
    val searchList = _searchList.liveData().distinctUntilChanged()

    private val _navigationEvent = MutableLiveData<Unit>()
    val navigationEvent = _navigationEvent.liveData()

    init {
        viewModelScope.launch {
            geoUseCase.getCities().collect { domain ->
                val matchList = geoMapper.toPres(domain).data
                with(_searchList) {
                    val validList = value.orEmpty().filterIsInstance<CityAdapterInfo.CityInfo>()
                    logger.debug("validList $validList")
                    postValue(
                        validList +
                                if (matchList.isEmpty()) {
                                    listOf(CityAdapterInfo.NoMatch)
                                } else {
                                    matchList.map(CityAdapterInfo::CityInfo)
                                }
                    )
                }
            }
        }
        viewModelScope.launch {
            searchStateFlow.collect()
        }
        viewModelScope.launch {
            scrollStateFlow.collect()
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        _searchList.postValue(listOf(CityAdapterInfo.Loading))
        _searchStateFlow.tryEmit(cityPrefix)
    }

    fun onScrolled() {
        _scrollStateFlow.tryEmit(Unit)
    }

    fun onCitySelect(city: CityPres) {
        _navigationEvent.postEvent()
        viewModelScope.launch {
            forecastUseCase.downloadForecast(geoMapper.toDomain(city))
        }
    }

    private fun addLoading() {
        if (_searchList.value?.find { it is CityAdapterInfo.Loading } == null) {
            _searchList.postValue(_searchList.value.orEmpty() + listOf(CityAdapterInfo.Loading))
        }
    }

}