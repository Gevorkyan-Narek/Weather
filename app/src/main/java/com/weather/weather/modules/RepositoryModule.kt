package com.weather.weather.modules

import com.weather.core.data.api.GeoRepository
import com.weather.core.data.impl.GeoRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<GeoRepository> {
        GeoRepositoryImpl(
            api = get(),
            mapper = get()
        )
    }

}