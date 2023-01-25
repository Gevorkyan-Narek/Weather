package com.weather.main.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.weather.android.utils.liveData
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.CityDomain

class MainScreenViewModel(geoUseCase: GeoUseCase) : ViewModel() {

    private val _locationMenuLiveData = MutableLiveData<Unit>()
    val locationMenuLiveData = _locationMenuLiveData.liveData()

    val cityTitle = geoUseCase.selectedCity.asLiveData().map(CityDomain::name)

    fun locationClicked() {
        _locationMenuLiveData.postValue(Unit)
    }

}