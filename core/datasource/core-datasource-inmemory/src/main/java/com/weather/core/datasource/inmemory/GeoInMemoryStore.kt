package com.weather.core.datasource.inmemory

import com.weather.core.datasource.inmemory.model.GeoInMemory
import kotlinx.coroutines.flow.MutableStateFlow

class GeoInMemoryStore {

    private val _geoInMemoryState = MutableStateFlow<GeoInMemory?>(null)
    val geoInMemoryState = _geoInMemoryState

    fun saveInMemory(inMemory: GeoInMemory) {
        _geoInMemoryState.value = inMemory
    }

}