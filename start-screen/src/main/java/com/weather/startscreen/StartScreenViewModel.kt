package com.weather.startscreen

import androidx.lifecycle.*
import com.weather.android.utils.liveData
import com.weather.android.utils.mapList
import com.weather.android.utils.postEvent
import com.weather.core.domain.api.GeoUseCase
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper,
) : ViewModel() {

    companion object {
        private const val MOTION_DELAY = 1500L
    }

    private val _motionEvent = MutableLiveData<Boolean>()
    val motionEvent = _motionEvent.liveData().distinctUntilChanged()

    private val _clearSearchListEvent = MutableLiveData<Unit>()
    val clearSearchListEvent = _clearSearchListEvent.liveData()

    val searchList = geoUseCase.downloadedCities
        .mapList { domain -> CityAdapterInfo.CityInfo(geoMapper.toPres(domain)) }
        .asLiveData()

    val loadingLiveData = geoUseCase.isLoading.asLiveData()

    private val _navigationEvent = MutableLiveData<Unit>()
    val navigationEvent = _navigationEvent.liveData()

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    init {
        geoUseCase.init(viewModelScope)
        viewModelScope.launch {
            geoUseCase.savedCities.collect { cities ->
                delay(MOTION_DELAY)
                if (cities.isEmpty()) {
                    _motionStartEvent.postValue(Unit)
                } else {
                    _navigationEvent.postEvent()
                }
            }
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        _motionEvent.postValue(cityPrefix.isBlank())
        _clearSearchListEvent.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.searchCity(cityPrefix)
        }
    }

    fun onScrolled() {
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.downloadMoreCities()
        }
    }

    fun onCitySelect(city: CityPres) {
        _navigationEvent.postEvent()
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.saveCity(geoMapper.toDomain(city))
        }
    }

}