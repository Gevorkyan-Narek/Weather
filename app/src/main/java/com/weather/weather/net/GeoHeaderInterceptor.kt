package com.weather.weather.net

import okhttp3.Interceptor
import okhttp3.Response

class GeoHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "X-RapidAPI-Key",
                "a5e150c4b0msh19c16faf0999953p16c6fbjsn6225c801e0a9"
            )
            .addHeader(
                "X-RapidAPI-Host",
                "wft-geo-db.p.rapidapi.com"
            )
            .build()

        return chain.proceed(request)
    }
}