package com.weather.weather.modules

import com.weather.core.datasource.net.forecast.ForecastApi
import com.weather.core.datasource.net.geo.GeoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single<GeoApi> {
        provideRetrofit(
            baseUrl = "https://wft-geo-db.p.rapidapi.com",
            okHttpClient = provideGeoOkHttpClient()
        )
    }

    single<ForecastApi> {
        provideRetrofit(
            baseUrl = "api.openweathermap.org/data/2.5"
        )
    }

}

inline fun <reified T> provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient? = null): T {
    return Retrofit.Builder().apply {
        baseUrl(baseUrl)

        okHttpClient?.let {
            client(okHttpClient)
        }

        addConverterFactory(GsonConverterFactory.create())
    }.build().create(T::class.java)
}

fun provideGeoOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
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
        .build()
}