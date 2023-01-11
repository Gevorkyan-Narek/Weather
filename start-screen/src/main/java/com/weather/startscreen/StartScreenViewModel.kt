package com.weather.startscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.weather.android.utils.fragment.liveData
import com.weather.android.utils.fragment.postEvent
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
    private val logger: Logger = LoggerFactory.getLogger(StartScreenViewModel::class.java)
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

    private val _insertNewCitiesLiveData = MutableLiveData<List<CityPres>>()
    val insertNewCitiesLiveData = _insertNewCitiesLiveData.liveData()

    private val _loadingEvent = MutableLiveData<Unit>()
    val loadingEvent = _loadingEvent.liveData()

    private var job: Job? = null

    init {
        viewModelScope.launch {
            delay(MOTION_DELAY)
            _motionStartEvent.postEvent()
        }
    }

    fun onCityTextChanged(cityPrefix: String) {
        viewModelScope.launch {
            val isPrefixNotBlank = cityPrefix.isNotBlank()
            _emptySearchLiveData.postValue(isPrefixNotBlank)
            if (isPrefixNotBlank) {
                geoUseCase.downloadCities(cityPrefix)?.let { domain ->
                    _matchCitiesLiveData.postValue(geoMapper.toPres(domain).data)
                }
            }
        }
    }

    fun onScrolled() {
        viewModelScope.launch {
            if (job != null) return@launch

            job = launch {
                _loadingEvent.postValue(Unit)
                delay(3000)
                val domain = geoUseCase.downloadMoreCities()
                job = null
                if (domain == null) {
                    logger.debug("No new cities")
                } else {
                    _insertNewCitiesLiveData.postValue(geoMapper.toPres(domain).data)
                }
            }
        }
    }

    fun onCitySelect(city: CityPres) {
        viewModelScope.launch {
            forecastUseCase.downloadForecast(geoMapper.toDomain(city))
        }
    }

}