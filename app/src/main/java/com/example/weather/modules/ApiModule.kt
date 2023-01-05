package com.example.weather.modules

import com.example.core.datasource.net.geo.GeoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    factory { provideGeoOkHttpClient() }
    single {
        provideGeoRetrofit(
            okHttpClient = get()
        )
    }

}

fun provideGeoRetrofit(okHttpClient: OkHttpClient): GeoApi {
    return Retrofit.Builder()
        .baseUrl("https://wft-geo-db.p.rapidapi.com/v1/geo/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GeoApi::class.java)
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