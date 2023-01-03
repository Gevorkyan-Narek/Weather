package com.example.startscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.utils.fragment.liveData
import com.example.android.utils.fragment.postEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartScreenViewModel : ViewModel() {

    companion object {
        private const val MOTION_DELAY = 1500L
    }

    private val _motionStartEvent = MutableLiveData<Unit>()
    val motionStartEvent = _motionStartEvent.liveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(MOTION_DELAY)
            _motionStartEvent.postEvent()
        }
    }

    fun onCityTextChanged(city: String) {

    }

}