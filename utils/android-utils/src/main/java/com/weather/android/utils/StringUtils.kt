package com.weather.android.utils

import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding

private const val EMPTY_STRING = ""

fun emptyString() = EMPTY_STRING

fun ViewBinding.getString(@StringRes resId: Int, vararg objects: Any): String {
    return root.getString(resId, *objects)
}

fun View.getString(@StringRes resId: Int, vararg objects: Any): String {
    return context.getString(resId, *objects)
}
