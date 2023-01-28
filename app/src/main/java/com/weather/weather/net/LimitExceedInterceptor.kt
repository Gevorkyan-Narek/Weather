package com.weather.weather.net

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LimitExceedInterceptor : Interceptor {

    companion object {
        private const val LIMIT_EXCEEDED = 429
        private const val WAIT = 1500L
        private const val RETRY_COUNT = 3
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun intercept(chain: Interceptor.Chain): Response {
        var i = 1
        var resp = chain.proceed(chain.request())
        while (resp.code == LIMIT_EXCEEDED && i <= RETRY_COUNT) {
            runBlocking {
                i++
                logger.info("Retry. Count $i/$RETRY_COUNT")
                delay(WAIT)
                resp = chain.proceed(chain.request())
            }
        }
        return resp
    }
}