package com.weather.weather.modules

import com.weather.core.datasource.inmemory.GeoInMemoryStore
import com.weather.startscreen.StartScreenFragment
import org.koin.dsl.module

val inMemoryModules = module {

    scope<StartScreenFragment> {
        scoped {
            GeoInMemoryStore()
        }
    }

}