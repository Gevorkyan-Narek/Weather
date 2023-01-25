package com.weather.main.screen.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.liveData
import com.weather.android.utils.mapList
import com.weather.core.domain.api.GeoUseCase
import com.weather.main.screen.dialog.adapter.CityAdapterItemPres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityDialogViewModel(private val geoUseCase: GeoUseCase) : ViewModel() {

    val cities = geoUseCase.getCities()
        .mapList { domain -> CityAdapterItemPres(domain.name, domain.isSelected) }.asLiveData()

    private val _dismissLiveData = MutableLiveData<Unit>()
    val dismissLiveData = _dismissLiveData.liveData()

    fun citySelected(cityName: String) {
        _dismissLiveData.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            geoUseCase.reSelectCity(cityName)
        }
    }

    fun addCity() {
        _dismissLiveData.postValue(Unit)
    }

}