package com.weather.android.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun getDrawable(context: Context, @DrawableRes drawableRes: Int): Drawable =
    ContextCompat.getDrawable(context, drawableRes) ?: throw NotFoundException()

@ColorInt
fun View.themeColor(@AttrRes attrRes: Int): Int = TypedValue()
    .apply { context.theme.resolveAttribute(attrRes, this, true) }
    .data

fun View.getColorIf(condition: Boolean, @AttrRes ifTrue: Int, @AttrRes ifElse: Int) = themeColor(
    if (condition) ifTrue else ifElse
)