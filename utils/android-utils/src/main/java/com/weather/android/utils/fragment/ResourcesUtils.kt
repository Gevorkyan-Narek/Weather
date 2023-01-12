package com.weather.android.utils.fragment

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun getDrawable(context: Context, @DrawableRes drawableRes: Int): Drawable =
    ContextCompat.getDrawable(context, drawableRes) ?: throw NotFoundException()