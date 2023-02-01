package com.weather.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.weather.android.utils.liveData
import com.weather.core.domain.api.ConnectionUseCase

class MainViewModel(
    connectionUseCase: ConnectionUseCase,
) : ViewModel() {

    private val _backPressed = MutableLiveData<Unit>()
    val backPressed = _backPressed.liveData()

    val connectionLiveData = connectionUseCase.connectionState.asLiveData()

    fun onBackPressed() {
        _backPressed.postValue(Unit)
    }

}