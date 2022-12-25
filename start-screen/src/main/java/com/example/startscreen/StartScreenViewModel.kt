package com.example.startscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartScreenViewModel : ViewModel() {

    private val _motionEvent = MutableLiveData<Unit>()
    val motionEvent: LiveData<Unit> = _motionEvent

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            _motionEvent.postValue(Unit)
        }
    }

}