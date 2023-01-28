package com.weather.weather.modules

import com.weather.core.data.api.ForecastRepository
import com.weather.core.data.api.GeoRepository
import com.weather.core.data.impl.forecast.ForecastRepositoryImpl
import com.weather.core.data.impl.geo.GeoRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<ForecastRepository> {
        ForecastRepositoryImpl(
            api = get(),
            dao = get(),
            mapper = get()
        )
    }

    single<GeoRepository> {
        GeoRepositoryImpl(
            api = get(),
            dao = get(),
            mapper = get()
        )
    }

}