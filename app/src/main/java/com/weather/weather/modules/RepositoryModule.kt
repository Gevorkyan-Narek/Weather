package com.weather.weather.modules

import com.weather.core.data.api.ForecastRepository
import com.weather.core.data.api.GeoRepository
import com.weather.core.data.impl.forecast.ForecastRepositoryImpl
import com.weather.core.data.impl.geo.GeoRepositoryImpl
import com.weather.startscreen.StartScreenFragment
import org.koin.dsl.module

val repositoryModule = module {

    scope<StartScreenFragment> {
        scoped<GeoRepository> {
            GeoRepositoryImpl(
                api = get(),
                inMemoryStore = get(),
                mapper = get()
            )
        }
        scoped<ForecastRepository> {
            ForecastRepositoryImpl(
                api = get(),
                mapper = get()
            )
        }
    }

}