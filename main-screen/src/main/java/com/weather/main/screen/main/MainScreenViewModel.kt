package com.weather.main.screen.main

import androidx.lifecycle.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.weather.android.utils.liveData
import com.weather.android.utils.mapList
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.CityDomain
import com.weather.main.screen.city.changer.CityAdapterInfo
import com.weather.main.screen.city.changer.model.CityInfoItemPres
import com.weather.main.screen.mapper.CityPresMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val mapper: CityPresMapper,
) : ViewModel() {

    private val _stateBottomSheetLiveData = MutableLiveData<Int>()
    val stateBottomSheetLiveData = _stateBottomSheetLiveData.liveData()

    val cityTitle = geoUseCase.selectedCity.asLiveData().map(CityDomain::name)

    val savedCities = geoUseCase.savedCities
        .mapList { domain -> CityAdapterInfo.CityInfo(mapper.toPres(domain)) }
        .asLiveData()

    val downloadCities = geoUseCase.downloadedCities
        .mapList { domain -> CityAdapterInfo.NewCityInfo(mapper.toPres(domain)) }
        .asLiveData()

    private val _cityPrefixChangedLiveData = MutableLiveData(true)
    val cityPrefixChangedLiveData = _cityPrefixChangedLiveData.liveData()

    val loadingLiveData = geoUseCase.isLoading.asLiveData()

    private val _showDeleteWarning = MutableLiveData<Unit>()
    val showDeleteWarning = _showDeleteWarning.liveData()

    init {
        viewModelScope.launch {
            geoUseCase.searchStateFlow.collect()
        }
        viewModelScope.launch {
            geoUseCase.downloadMoreCitiesStateFlow.collect()
        }
    }

    fun locationClicked() {
        _stateBottomSheetLiveData.postValue(BottomSheetBehavior.STATE_HALF_EXPANDED)
    }

    fun onCitySelect(city: CityInfoItemPres) {
        _stateBottomSheetLiveData.postValue(BottomSheetBehavior.STATE_HIDDEN)
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.reSelectCity(mapper.toDomain(city))
        }
    }

    fun onNewCitySelect(newCity: CityInfoItemPres) {
        _stateBottomSheetLiveData.postValue(BottomSheetBehavior.STATE_HIDDEN)
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.saveCity(mapper.toDomain(newCity))
        }
    }

    fun onScrolled() {
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.downloadMoreCities()
        }
    }

    fun onCityPrefixChanged(cityPrefix: String) {
        _cityPrefixChangedLiveData.postValue(cityPrefix.isBlank())
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.searchCity(cityPrefix)
        }
    }

    fun onCityDelete(city: CityInfoItemPres) {
        viewModelScope.launch(Dispatchers.IO) {
            if (savedCities.value?.size == 1) {
                _showDeleteWarning.postValue(Unit)
            } else {
                geoUseCase.removeSavedCity(mapper.toDomain(city))
            }
        }
    }

}