package com.weather.core.domain.models

sealed class ResultWrapper<out H> {

    data class Success<out T>(val result: T) : ResultWrapper<T>()

    data class Error(val error: Throwable) : ResultWrapper<Nothing>()

}
