package com.weather.android.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> MutableLiveData<T>.liveData(): LiveData<T> = this

fun MutableLiveData<Unit>.postEvent() = postValue(Unit)

fun <T> Fragment.observe(liveData: LiveData<T>, observer: Observer<T>) =
    liveData.observe(viewLifecycleOwner, observer)

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, observer: Observer<T>) =
    liveData.observe(this, observer)
