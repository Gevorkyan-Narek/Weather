package com.weather.main.screen.today

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.weather.android.utils.liveData
import com.weather.base.utils.DateFormatter
import com.weather.core.domain.api.ForecastUseCase
import com.weather.main.screen.mapper.ForecastPresMapper
import com.weather.main.screen.model.WeatherTimeEnum
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDateTime

class TodayViewModel(
    private val forecastUseCase: ForecastUseCase,
    private val mapper: ForecastPresMapper,
) : ViewModel() {

    private val _todayDate = MutableLiveData(DateFormatter.getTodayDate())
    val todayDate = _todayDate.liveData()

    private val todayForecastFlow = forecastUseCase.getTodayForecast().map { weatherList ->
        weatherList.map(mapper::toPres)
    }.filter { list -> list.isNotEmpty() }

    val todayForecastLiveData = todayForecastFlow.asLiveData()

    val currentForecastLiveData = todayForecastFlow.map { weatherPres ->
        weatherPres.find { pres -> pres.weatherTimeEnum == WeatherTimeEnum.valueOf(LocalDateTime.now()) }
    }.filterNotNull().asLiveData()

}