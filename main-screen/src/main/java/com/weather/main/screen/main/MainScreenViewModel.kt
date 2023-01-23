package com.weather.main.screen.main

import androidx.lifecycle.*
import com.weather.android.utils.liveData
import com.weather.android.utils.mapList
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val geoUseCase: GeoUseCase,
) : ViewModel() {

    val cities = geoUseCase.getCities().mapList(CityDomain::name).asLiveData()

    val cityTitle = geoUseCase.selectedCity.asLiveData().map(CityDomain::name)

    private val _menuClickLiveData = MutableLiveData<Unit>()
    val menuVisibilityLiveData = _menuClickLiveData.liveData()

    fun citySelected(cityName: String) {
        _menuClickLiveData.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.reSelectCity(cityName)
        }
    }

    fun menuClicked() {
        _menuClickLiveData.postValue(Unit)
    }

}