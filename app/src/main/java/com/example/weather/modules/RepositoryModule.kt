package com.example.weather.modules

import com.example.core.data.api.GeoRepository
import com.example.core.data.impl.GeoRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<GeoRepository> {
        GeoRepositoryImpl(
            api = get(),
            mapper = get()
        )
    }

}