package com.example.weather.modules

import com.example.core.data.impl.GeoMapper
import com.example.startscreen.GeoPresMapper
import org.koin.dsl.module

val mapperModule = module {

    single {
        GeoMapper()
    }
    single {
        GeoPresMapper()
    }

}