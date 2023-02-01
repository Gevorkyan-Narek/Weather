package com.weather.base.utils

fun <T> List<T>.exist(predicate: (T) -> Boolean): Boolean = find(predicate) != null