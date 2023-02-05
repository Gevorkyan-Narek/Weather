package com.weather.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.android.utils.liveData

class MainViewModel : ViewModel() {

    private val _backPressed = MutableLiveData<Unit>()
    val backPressed = _backPressed.liveData()

    fun onBackPressed() {
        _backPressed.postValue(Unit)
    }

}