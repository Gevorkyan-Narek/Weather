package com.weather.android.utils

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

private const val PICTURE_URL = "http://openweathermap.org/img/wn/"
private const val PNG = "@2x.png"

fun Fragment.setWeatherIcon(id: String, imageView: ImageView) {
    Glide
        .with(this)
        .load(PICTURE_URL + id + PNG)
        .into(imageView)
}