package com.weather.startscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.fragment.checkResult
import com.weather.android.utils.fragment.liveData
import com.weather.android.utils.fragment.postEvent
import com.weather.core.domain.api.GeoUseCase
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper
) : ViewModel() {

    companion object {
        private const val MOTION_DELAY = 1500L
    }

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    private val _emptySearchLiveData = MutableLiveData<Boolean>()
    val emptySearchLiveData = _emptySearchLiveData.liveData().distinctUntilChanged()

    private val _matchCitiesLiveData = MutableLiveData<List<CityPres>>()
    val matchCitiesLiveData = _matchCitiesLiveData.liveData()

    init {
        viewModelScope.launch {
            delay(MOTION_DELAY)
            _motionStartEvent.postEvent()
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        viewModelScope.launch {
            val isSearching = cityPrefix.isNotBlank()
            _emptySearchLiveData.postValue(isSearching)
            if (isSearching) {
                geoUseCase.getCities(cityPrefix).checkResult { result ->
                    _matchCitiesLiveData.postValue(geoMapper.toPres(result).data)
                }
            }
        }
    }

}