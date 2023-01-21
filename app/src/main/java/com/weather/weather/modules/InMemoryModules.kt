package com.weather.weather.modules

import com.weather.core.datasource.inmemory.GeoInMemoryStore
import org.koin.dsl.module

val inMemoryModules = module {

    single {
        GeoInMemoryStore()
    }

}