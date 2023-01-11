package com.weather.android.utils.fragment

import com.weather.core.domain.models.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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

inline fun <reified T, R> ResultWrapper<T>.checkResult(
    crossinline loading: () -> Unit = {},
    crossinline fail: (Throwable) -> Unit = {},
    crossinline success: (T) -> R,
): R? {
    val logger: Logger = LoggerFactory.getLogger(T::class.java)
    return when (this) {
        is ResultWrapper.Success -> {
            logger.info("success: $result")
            success(this.result)
        }
        is ResultWrapper.Error -> {
            logger.error("error: $error")
            fail(this.error)
            null
        }
        is ResultWrapper.Loading -> {
            logger.info("loading")
            loading()
            null
        }
    }
}