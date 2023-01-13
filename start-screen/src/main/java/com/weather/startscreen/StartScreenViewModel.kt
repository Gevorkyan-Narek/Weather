package com.weather.startscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.liveData
import com.weather.android.utils.postEvent
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.api.GeoUseCase
import com.weather.startscreen.models.CityPres
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class StartScreenViewModel(
    private val geoUseCase: GeoUseCase,
    private val geoMapper: GeoPresMapper,
    private val forecastUseCase: ForecastUseCase,
    private val logger: Logger = LoggerFactory.getLogger(StartScreenViewModel::class.java),
) : ViewModel() {

    companion object {
        private const val MOTION_DELAY = 1500L
    }

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    private val _emptySearchLiveData = MutableLiveData<Boolean>()
    val emptySearchLiveData = _emptySearchLiveData.liveData().distinctUntilChanged()

    private val _clearSearchList = MutableLiveData<Unit>()
    val clearSearchList = _clearSearchList.liveData()

    private val _insertNewCitiesLiveData = MutableLiveData<List<CityPres>>()
    val insertNewCitiesLiveData = _insertNewCitiesLiveData.liveData()

    private val _loadingEvent = MutableLiveData<Unit>()
    val loadingEvent = _loadingEvent.liveData()

    private val _navigationEvent = MutableLiveData<Unit>()
    val navigationEvent = _navigationEvent.liveData()

    private var job: Job? = null

    init {
        viewModelScope.launch {
            delay(MOTION_DELAY)
            _motionStartEvent.postEvent()
        }
        viewModelScope.launch {
            geoUseCase.getCities().collect { domain ->
                logger.info("$domain")
                domain?.let {
                    _insertNewCitiesLiveData.postValue(geoMapper.toPres(domain).data)
                }
            }
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        viewModelScope.launch {
            _clearSearchList.postValue(Unit)
            val isPrefixNotBlank = cityPrefix.isNotBlank()
            _emptySearchLiveData.postValue(isPrefixNotBlank)
            if (isPrefixNotBlank) {
                geoUseCase.downloadCities(cityPrefix)
            }
        }
    }

    fun onScrolled() {
        viewModelScope.launch {
            if (job != null) return@launch

            job = launch {
                _loadingEvent.postValue(Unit)
                delay(3000)
                geoUseCase.downloadMoreCities()
                job = null
            }
        }
    }

    fun onCitySelect(city: CityPres) {
        viewModelScope.launch {
            forecastUseCase.downloadForecast(geoMapper.toDomain(city))
            _navigationEvent.postEvent()
        }
    }

}