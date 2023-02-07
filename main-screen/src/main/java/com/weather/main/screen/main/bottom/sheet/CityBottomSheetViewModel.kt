package com.weather.main.screen.main.bottom.sheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.emptyString
import com.weather.android.utils.liveData
import com.weather.android.utils.postEvent
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.main.screen.city.changer.CityAdapterInfo
import com.weather.main.screen.city.changer.model.CityInfoItemPres
import com.weather.main.screen.mapper.CityPresMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

@OptIn(FlowPreview::class)
class CityBottomSheetViewModel(
    private val geoUseCase: GeoUseCase,
    private val mapper: CityPresMapper,
    private val logger: Logger = LoggerFactory.getLogger(CityBottomSheetViewModel::class.java),
) : ViewModel() {

    companion object {
        private const val SAVED_ONE_CITY = 1
        private const val DEBOUNCE = 1500L
        private const val NO_OFFSET = 0
    }

    private val savedCities = MutableLiveData<List<CityAdapterInfo>>()

    private val _searchList = MutableLiveData<List<CityAdapterInfo>>()
    val searchList = _searchList.distinctUntilChanged()

    private val _loadMoreCitiesList = MutableLiveData<List<CityAdapterInfo>>()
    val loadMoreCitiesList = _loadMoreCitiesList.distinctUntilChanged()

    private val cityTextChanged = MutableStateFlow(emptyString())
    private val onScrolled = MutableStateFlow(NO_OFFSET)
    private val isMoreDownload = MutableStateFlow(true)

    private val listUpdateFlow = combine(
        cityTextChanged.debounce(DEBOUNCE),
        onScrolled.debounce(DEBOUNCE),
        isMoreDownload
    ) { cityPrefix, offset, isMoreDownload ->
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


    private val _showDeleteWarning = MutableLiveData<Unit>()
    val showDeleteWarning = _showDeleteWarning.liveData()

    private val _popBackStackEvent = MutableLiveData<Unit>()
    val popBackStackEvent = _popBackStackEvent.liveData()

    private val _clearSearchList = MutableLiveData<Unit>()
    val clearSearchList = _clearSearchList.liveData()

    private val _addLoading = MutableLiveData<Unit>()
    val addLoading = _addLoading.liveData()

    private val singleThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    init {
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
        viewModelScope.launch {
            geoUseCase.savedCities.collect { cities ->
                val mapped =
                    cities.map { domain -> CityAdapterInfo.CityInfo(mapper.toPres(domain)) }
                savedCities.postValue(mapped)
                _searchList.postValue(mapped)
            }
        }
    }

    fun onCityPrefixChanged(cityPrefix: String) {
        _clearSearchList.postValue(Unit)
        viewModelScope.launch(singleThread) {
            logger.debug("Emit prefix: $cityPrefix")
            cityTextChanged.emit(cityPrefix)
            isMoreDownload.emit(true)
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

    fun onScrolled(offset: Int) {
        viewModelScope.launch(singleThread) {
            onScrolled.emit(offset)
        }
    }

    private suspend fun mapToCityAdapterInfo(
        downloadStateDomain: DownloadStateDomain,
    ): List<CityAdapterInfo> {
        return when (downloadStateDomain) {
            is DownloadStateDomain.Success -> {
                downloadStateDomain.geoDomain.run {
                    isMoreDownload.emit(data.isNotEmpty())
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
                isMoreDownload.emit(false)
                listOf(CityAdapterInfo.Error)
            }
            DownloadStateDomain.Loading -> {
                isMoreDownload.emit(true)
                listOf(CityAdapterInfo.Loading)
            }
            DownloadStateDomain.NoStart -> {
                savedCities.value.orEmpty()
            }
        }
    }
}