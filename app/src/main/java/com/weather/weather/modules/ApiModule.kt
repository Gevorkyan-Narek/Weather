package com.weather.weather.modules

import com.weather.core.datasource.net.forecast.ForecastApi
import com.weather.core.datasource.net.geo.GeoApi
import com.weather.weather.net.GeoHeaderInterceptor
import com.weather.weather.net.LimitExceedInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val geoInterceptors = listOf(
    GeoHeaderInterceptor(),
    LimitExceedInterceptor(),
    provideHttpLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC)
)

val apiModule = module {

    single<GeoApi> {
        provideRetrofit(
            baseUrl = "https://wft-geo-db.p.rapidapi.com",
            interceptors = geoInterceptors
        )
    }

    single<ForecastApi> {
        provideRetrofit(
            baseUrl = "https://api.openweathermap.org/data/2.5/",
            interceptors = listOf(provideHttpLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC))
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

fun provideHttpLoggingInterceptor(logLevel: HttpLoggingInterceptor.Level) =
    HttpLoggingInterceptor().apply {
        level = logLevel
    }

fun createOkHttpClient(interceptors: List<Interceptor>? = null): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        interceptors?.forEach { interceptor ->
            addInterceptor(interceptor)
        }
    }.build()
}