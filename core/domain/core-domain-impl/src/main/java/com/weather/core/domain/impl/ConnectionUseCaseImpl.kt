package com.weather.core.domain.impl

import com.weather.core.data.api.ConnectionManager
import com.weather.core.domain.api.ConnectionUseCase

class ConnectionUseCaseImpl(
    private val connectionManager: ConnectionManager
) : ConnectionUseCase {

    override val connectionState = connectionManager.connectionState

    override fun isConnected(): Boolean = connectionManager.isConnected
}