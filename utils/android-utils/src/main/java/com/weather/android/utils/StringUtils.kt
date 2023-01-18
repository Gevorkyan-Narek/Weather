package com.weather.android.utils

import androidx.viewbinding.ViewBinding

private const val EMPTY_STRING = ""

fun emptyString() = EMPTY_STRING

fun ViewBinding.getString(id: Int, vararg formatArgs: Any): String {
    return root.context.getString(id, formatArgs)
}