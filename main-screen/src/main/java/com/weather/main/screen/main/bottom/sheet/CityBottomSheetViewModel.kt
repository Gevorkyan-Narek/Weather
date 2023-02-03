package com.weather.main.screen.main.bottom.sheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.liveData
import com.weather.android.utils.mapList
import com.weather.android.utils.postEvent
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain
import com.weather.main.screen.city.changer.CityAdapterInfo
import com.weather.main.screen.city.changer.model.CityInfoItemPres
import com.weather.main.screen.mapper.CityPresMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@OptIn(FlowPreview::class)
class CityBottomSheetViewModel(
    private val geoUseCase: GeoUseCase,
    private val mapper: CityPresMapper,
    private val logger: Logger = LoggerFactory.getLogger(CityBottomSheetViewModel::class.java),
) : ViewModel() {

    companion object {
        private const val SAVED_ONE_CITY = 1
        private const val DEBOUNCE = 1500L
    }

    private val geoLinks = MutableLiveData<List<GeoLinkDomain>>(emptyList())

    val savedCities = geoUseCase.savedCities
        .mapList { domain -> CityAdapterInfo.CityInfo(mapper.toPres(domain)) }
        .asLiveData()

    val searchList = geoUseCase.downloadedCities
        .map { domain ->
            geoLinks.postValue(domain.links)
            if (domain.data.isEmpty()) {
                listOf(CityAdapterInfo.NoMatch)
            } else {
                domain.data.map { city ->
                    CityAdapterInfo.NewCityInfo(mapper.toPres(city))
                }
            }
        }
        .asLiveData()

    val loadMoreCitiesList = geoUseCase.downloadedNextCities
        .map { domain ->
            geoLinks.postValue(domain.links)
            domain.data.map { city ->
                CityAdapterInfo.NewCityInfo(mapper.toPres(city))
            }
        }
        .asLiveData()

    private val _cityTextChanged =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val cityTextChanged = _cityTextChanged
        .distinctUntilChanged()
        .debounce(DEBOUNCE)

    private val _onScrolled =
        MutableSharedFlow<GeoLinkDomain?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val onScrolled = _onScrolled
        .distinctUntilChanged()
        .debounce(DEBOUNCE)

    private val _showDeleteWarning = MutableLiveData<Unit>()
    val showDeleteWarning = _showDeleteWarning.liveData()

    private val _popBackStackEvent = MutableLiveData<Unit>()
    val popBackStackEvent = _popBackStackEvent.liveData()

    private val _isLoading = MutableLiveData<Unit>()
    val isLoading = _isLoading.liveData()

    private val _addLoading = MutableLiveData<Unit>()
    val addLoading = _addLoading.liveData()

    private val _showSavedCities = MutableLiveData<Boolean>()
    val showSavedCities = _showSavedCities.liveData()

    init {
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

    fun onCityPrefixChanged(cityPrefix: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (cityPrefix.isBlank()) {
                _showSavedCities.postValue(true)
            } else {
                _showSavedCities.postValue(false)
                _isLoading.postValue(Unit)
                _cityTextChanged.emit(cityPrefix)
            }
        }
    }

    fun onCityDelete(city: CityInfoItemPres) {
        viewModelScope.launch(Dispatchers.IO) {
            if (savedCities.value?.size == SAVED_ONE_CITY) {
                _showDeleteWarning.postValue(Unit)
            } else {
                geoUseCase.removeSavedCity(mapper.toDomain(city))
            }
        }
    }

    fun onCitySelect(city: CityInfoItemPres) {
        _popBackStackEvent.postEvent()
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.reSelectCity(mapper.toDomain(city))
        }
    }

    fun onNewCitySelect(newCity: CityInfoItemPres) {
        _popBackStackEvent.postEvent()
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.saveCity(mapper.toDomain(newCity))
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
}