package com.weather.core.domain.impl

import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class GeoUseCaseImpl(
    private val repository: GeoRepository,
) : GeoUseCase {

    companion object {
        private const val TEXT_CHANGE_DEBOUNCE = 500L
        private const val SCROLL_DEBOUNCE = 1500L
    }

    override val isLoading =
        MutableSharedFlow<Unit>(replay = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val selectedCity = repository.selectedCity

    private val _downloadMoreCitiesStateFlow =
        MutableSharedFlow<Unit>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val downloadMoreCitiesStateFlow = _downloadMoreCitiesStateFlow
        .filterNotNull()
        .map {
            if (repository.isHasMoreCities()) {
                isLoading.emit(Unit)
            }
        }
        .debounce(SCROLL_DEBOUNCE)
        .map {
            repository.downloadMoreCities()
        }

    private val _searchStateFlow =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val searchStateFlow = _searchStateFlow
        .distinctUntilChanged()
        .filterNotNull()
        .map { searchText ->
            isLoading.emit(Unit)
            searchText
        }
        .debounce(TEXT_CHANGE_DEBOUNCE)
        .mapLatest { searchText ->
            if (searchText.isNotBlank()) {
                downloadCities(searchText)
            }
        }

    override val savedCities = repository.savedCities

    override val downloadedCities = repository.downloadedCities.map { geo -> geo.data }

    override suspend fun downloadCities(namePrefix: String, offset: Int) {
        return repository.downloadCities(namePrefix, offset)
    }

    override suspend fun saveCity(city: CityDomain) {
        repository.saveCity(city)
    }

    override suspend fun reSelectCity(city: CityDomain) {
        repository.updateSelectedCity(city)
    }

    override suspend fun searchCity(cityName: String) {
        _searchStateFlow.emit(cityName)
    }

    override suspend fun downloadMoreCities() {
        _downloadMoreCitiesStateFlow.emit(Unit)
    }

    override suspend fun removeSavedCity(city: CityDomain) {
        repository.removeSavedCity(city)
    }

}