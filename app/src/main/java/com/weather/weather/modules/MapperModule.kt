package com.weather.weather.modules

import com.weather.core.data.impl.GeoMapper
import com.weather.startscreen.GeoPresMapper
import org.koin.dsl.module

val mapperModule = module {

    single {
        GeoMapper()
    }
    single {
        GeoPresMapper()
    }

}