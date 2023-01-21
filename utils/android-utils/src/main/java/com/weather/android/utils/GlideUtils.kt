package com.weather.android.utils

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide

private const val PICTURE_URL = "http://openweathermap.org/img/wn/"
private const val PNG = "@2x.png"

fun Fragment.setWeatherIcon(id: String, imageView: ImageView) {
    Glide
        .with(this)
        .load(PICTURE_URL + id + PNG)
        .fitCenter()
        .into(imageView)
}

fun ViewBinding.setWeatherIcon(id: String, imageView: ImageView) {
    Glide
        .with(root)
        .load(PICTURE_URL + id + PNG)
        .fitCenter()
        .into(imageView)
}