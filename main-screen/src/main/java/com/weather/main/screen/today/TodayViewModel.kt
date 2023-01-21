package com.weather.main.screen.today

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.liveData
import com.weather.base.utils.DateFormatter
import com.weather.core.domain.api.ForecastUseCase
import com.weather.main.screen.mapper.ForecastPresMapper
import com.weather.main.screen.model.WeatherPres
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime

class TodayViewModel(
    forecastUseCase: ForecastUseCase,
    private val mapper: ForecastPresMapper,
) : ViewModel() {

    private val _todayDate = MutableLiveData(DateFormatter.getTodayDate())
    val todayDate = _todayDate.liveData()

    private val todayForecastFlow = forecastUseCase.getTodayForecast()
        .map { weatherList -> weatherList.map(mapper::toPres) }
        .filter { list -> list.isNotEmpty() }

    val todayForecastLiveData = todayForecastFlow.asLiveData()

    val currentWeatherLiveData = todayForecastFlow.map { weatherPres ->
        weatherPres.find { pres -> LocalDateTime.now() <= pres.dateTime }
    }.filterNotNull().asLiveData()

    private val _detailLiveData = MutableLiveData<WeatherPres>()
    val detailLiveData = _detailLiveData.liveData()

    init {
        viewModelScope.launch {
            todayForecastFlow.collect { list ->
                _detailLiveData.postValue(list.first())
            }
        }
    }


    fun timeClicked(pres: WeatherPres) {
        _detailLiveData.postValue(pres)
    }

}