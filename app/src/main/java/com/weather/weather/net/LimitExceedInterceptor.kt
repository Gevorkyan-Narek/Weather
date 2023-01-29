package com.weather.weather.net

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.closeQuietly
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
            i++
            logger.info("Retry. Count $i/$RETRY_COUNT")
            Thread.sleep(WAIT)
            resp.closeQuietly()
            resp = chain.proceed(chain.request())
        }
        return resp
    }
}