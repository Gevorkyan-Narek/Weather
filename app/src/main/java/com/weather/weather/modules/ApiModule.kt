package com.weather.weather.modules

import com.weather.base.utils.nullOrFalse
import com.weather.core.datasource.net.forecast.ForecastApi
import com.weather.core.datasource.net.geo.GeoApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single<GeoApi> {
        provideRetrofit(
            baseUrl = "https://wft-geo-db.p.rapidapi.com",
            interceptors = listOf(provideGeoInterceptor(), provideBasicLoggingInterceptor())
        )
    }

    single<ForecastApi> {
        provideRetrofit(
            baseUrl = "https://api.openweathermap.org/data/2.5/"
        )
    }

}

inline fun <reified T> provideRetrofit(
    baseUrl: String,
    interceptors: List<Interceptor>? = null,
): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(createOkHttpClient(interceptors))
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(T::class.java)
}

private fun provideGeoInterceptor(): Interceptor {
    return Interceptor { chain ->
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
        chain.proceed(request)

    }
}

private fun provideBasicLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
}

fun createOkHttpClient(interceptors: List<Interceptor>? = null): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        interceptors?.forEach { interceptor ->
            addInterceptor(interceptor)
        }
        if (interceptors?.contains(HttpLoggingInterceptor()).nullOrFalse()) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()
}