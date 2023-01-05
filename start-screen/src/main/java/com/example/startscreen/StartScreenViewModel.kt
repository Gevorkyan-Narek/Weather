package com.example.startscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.utils.fragment.checkResult
import com.example.android.utils.fragment.liveData
import com.example.android.utils.fragment.postEvent
import com.example.core.domain.api.GeoUseCase
import com.example.startscreen.models.GeoPres
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

    private val _matchCitiesLiveData = MutableLiveData<GeoPres>()
    val matchCitiesLiveData = _matchCitiesLiveData.liveData()

    init {
        viewModelScope.launch {
            delay(MOTION_DELAY)
            _motionStartEvent.postEvent()
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        viewModelScope.launch {
            geoUseCase.getCities(cityPrefix).checkResult { result ->
                _matchCitiesLiveData.postValue(geoMapper.toPres(result))
            }
        }
    }

}