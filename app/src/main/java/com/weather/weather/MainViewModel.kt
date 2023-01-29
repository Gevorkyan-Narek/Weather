package com.weather.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.weather.core.domain.api.ConnectionUseCase

class MainViewModel(
    connectionUseCase: ConnectionUseCase
) : ViewModel() {

    val connectionLiveData = connectionUseCase.connectionState.asLiveData()

}