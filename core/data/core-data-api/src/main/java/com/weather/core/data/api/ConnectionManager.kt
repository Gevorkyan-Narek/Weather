package com.weather.core.data.api

import kotlinx.coroutines.flow.StateFlow

interface ConnectionManager {

    val connectionState: StateFlow<Boolean>

    val isConnected: Boolean

}