package com.weather.android.utils

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

suspend inline fun <reified T> ResultWrapper<T>.checkResult(
    crossinline success: suspend (T) -> Unit,
) {
    checkResult(
        fail = { emptyFun() },
        success = success
    )
}

suspend inline fun <reified T> ResultWrapper<T>.checkResult(
    crossinline fail: suspend (Throwable) -> Unit,
    crossinline success: suspend (T) -> Unit,
) {
    val logger: Logger = LoggerFactory.getLogger(T::class.java)
    when (this) {
        is ResultWrapper.Success -> {
            logger.info("success: $result")
            success(this.result)
        }
        is ResultWrapper.Error -> {
            logger.error("error: $error")
            fail(this.error)
        }
    }
}