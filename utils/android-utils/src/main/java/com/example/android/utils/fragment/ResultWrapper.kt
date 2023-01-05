package com.example.android.utils.fragment

import com.example.core.domain.models.ResultWrapper
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

inline fun <T> ResultWrapper<T>.checkResult(
    crossinline loading: () -> Unit = {},
    crossinline fail: (Throwable) -> Unit = {},
    crossinline success: (T) -> Unit = {}
) {
    when (this) {
        is ResultWrapper.Success -> {
            success(this.result)
        }
        is ResultWrapper.Error -> {
            fail(this.error)
        }
        is ResultWrapper.Loading -> {
            loading()
        }
    }
}