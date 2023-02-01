package com.weather.core.domain.api

import kotlinx.coroutines.flow.StateFlow

interface ConnectionUseCase {

    val connectionState: StateFlow<Boolean>

    fun isConnected(): Boolean

}