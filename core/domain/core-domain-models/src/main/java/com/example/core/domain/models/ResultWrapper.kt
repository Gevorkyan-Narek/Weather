package com.example.core.domain.models

sealed class ResultWrapper<out H> {

    object Loading : ResultWrapper<Nothing>()

    data class Success<out T>(val result: T) : ResultWrapper<T>()

    data class Error(val error: Throwable) : ResultWrapper<Nothing>()
}
