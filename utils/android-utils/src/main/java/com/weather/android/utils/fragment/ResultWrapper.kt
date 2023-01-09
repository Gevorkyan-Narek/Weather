package com.weather.android.utils.fragment

import com.weather.core.domain.models.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = withContext(Dispatchers.IO) {
            apiCall.invoke()
        }
        ResultWrapper.Success(result)
    } catch (e: Exception) {
        ResultWrapper.Error(e)
    }
}

inline fun <T, R> ResultWrapper<T>.checkResult(
    crossinline loading: () -> Unit = {},
    crossinline fail: (Throwable) -> Unit = {},
    crossinline success: (T) -> R
): R? {
    return when (this) {
        is ResultWrapper.Success -> {
            success(this.result)
        }
        is ResultWrapper.Error -> {
            fail(this.error)
            null
        }
        is ResultWrapper.Loading -> {
            loading()
            null
        }
    }
}