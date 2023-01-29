package com.weather.core.data.impl.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.weather.core.data.api.ConnectionManager
import kotlinx.coroutines.flow.MutableStateFlow

class ConnectionManagerImpl(context: Context) : ConnectionManager {
    private var network: Network? = null

    private val manager: ConnectivityManager? = context.getSystemService()

    private val _connectionState = MutableStateFlow(false)
    override val connectionState = _connectionState

    override val isConnected: Boolean
        get() = _connectionState.value

    init {
        observeConnectionState()
    }

    private fun observeConnectionState() {
        manager?.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    this@ConnectionManagerImpl.network = network
                    _connectionState.value = true
                }

                override fun onLost(network: Network) {
                    if (this@ConnectionManagerImpl.network == network) {
                        _connectionState.value = false
                    }
                }
            })
    }
}