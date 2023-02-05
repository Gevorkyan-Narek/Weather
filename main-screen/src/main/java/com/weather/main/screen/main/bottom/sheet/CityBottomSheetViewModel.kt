package com.weather.main.screen.main.bottom.sheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.emptyString
import com.weather.android.utils.liveData
import com.weather.android.utils.mapList
import com.weather.android.utils.postEvent
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain
import com.weather.main.screen.city.changer.CityAdapterInfo
import com.weather.main.screen.city.changer.model.CityInfoItemPres
import com.weather.main.screen.mapper.CityPresMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
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

    private val _showDeleteWarning = MutableLiveData<Unit>()
    val showDeleteWarning = _showDeleteWarning.liveData()

    private val _popBackStackEvent = MutableLiveData<Unit>()
    val popBackStackEvent = _popBackStackEvent.liveData()

    private val _clearSearchList = MutableLiveData<Unit>()
    val clearSearchList = _clearSearchList.liveData()

    private val _addLoading = MutableLiveData<Unit>()
    val addLoading = _addLoading.liveData()

    private val _showSavedCities = MutableLiveData<Boolean>()
    val showSavedCities = _showSavedCities.liveData()

    init {
        viewModelScope.launch {
            cityTextChanged.collectLatest { cityPrefix ->
                logger.debug("Search new city: $cityPrefix")
                val cities = geoUseCase.downloadCities(cityPrefix)
                _searchList.postValue(mapToCityAdapterInfo(cities))
            }
        }
        viewModelScope.launch {
            onScrolled.collectLatest { link ->
                logger.debug("Scrolled: ${link.href}")
                val nextCities = geoUseCase.downloadNextCities(link)
                _loadMoreCitiesList.postValue(mapToCityAdapterInfo(nextCities))
            }
        }
    }

    fun onCityPrefixChanged(cityPrefix: String) {
        geoLinks.postValue(emptyList())
        _clearSearchList.postValue(Unit)
        _showSavedCities.postValue(cityPrefix.isBlank())
        viewModelScope.launch(Dispatchers.IO) {
            _cityTextChanged.emit(cityPrefix)
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
                logger.debug("Next link")
                _addLoading.postValue(Unit)
                _onScrolled.emit(nextLink)
            }
        }
    }

    private fun mapToCityAdapterInfo(
        downloadStateDomain: DownloadStateDomain,
    ): List<CityAdapterInfo> {
        return when (downloadStateDomain) {
            is DownloadStateDomain.Success -> {
                downloadStateDomain.geoDomain.run {
                    geoLinks.postValue(links)
                    if (data.isEmpty()) {
                        listOf(CityAdapterInfo.NoMatch)
                    } else {
                        data.map { city ->
                            CityAdapterInfo.NewCityInfo(mapper.toPres(city))
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