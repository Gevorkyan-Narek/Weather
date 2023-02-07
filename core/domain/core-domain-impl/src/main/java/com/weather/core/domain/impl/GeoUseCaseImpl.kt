package com.weather.core.domain.impl

import com.weather.android.utils.emptyString
import com.weather.core.data.api.GeoRepository
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class GeoUseCaseImpl(
    private val repository: GeoRepository,
) : GeoUseCase {

    companion object {
        private const val DEBOUNCE = 1500L
        private const val NO_OFFSET = 0
    }

    private val cityPrefixFlow = MutableStateFlow(emptyString())

    private val offsetFlow = MutableStateFlow(NO_OFFSET)

    private val isMoreDownload = MutableStateFlow(true)

    override val selectedCity = repository.selectedCity

    override val savedCities = repository.savedCities

    override val isDownloading = MutableSharedFlow<Unit>()

    override val downloadCities = combine(
        cityPrefixFlow.debounce(DEBOUNCE),
        offsetFlow.debounce(DEBOUNCE),
        isMoreDownload
    ) { cityPrefix, offset, isMoreDownload ->
        when {
            cityPrefix.isBlank() -> {
                DownloadStateDomain.NoStart to NO_OFFSET
            }
            isMoreDownload -> {
                isDownloading.emit(Unit)
                val cities = repository.downloadCities(cityPrefix, offset)
                updateIsMoreDownload(cities)
                cities to offset
            }
            else -> null
        }
    }.filterNotNull()

    override suspend fun downloadCities(namePrefix: String) {
        cityPrefixFlow.emit(namePrefix)
        isMoreDownload.emit(true)
    }

    override suspend fun updateOffset(offset: Int) {
        offsetFlow.emit(offset)
    }

    override suspend fun saveCity(city: CityDomain) {
        repository.saveCity(city)
    }

    override suspend fun removeSavedCity(city: CityDomain) {
        repository.removeSavedCity(city)
    }

    override suspend fun reSelectCity(city: CityDomain) {
        repository.updateSelectedCity(city)
    }

    private suspend fun updateIsMoreDownload(state: DownloadStateDomain) {
        isMoreDownload.emit(
            when (state) {
                is DownloadStateDomain.Success -> state.geoDomain.data.isNotEmpty()
                is DownloadStateDomain.Loading -> true
                else -> false
            }
        )
    }

}