package com.weather.weather.modules

import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.impl.ForecastUseCaseImpl
import com.weather.core.domain.impl.GeoUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    single<ForecastUseCase> {
        ForecastUseCaseImpl(
            repository = get(),
            geoRepository = get()
        )
    }

    single<GeoUseCase> {
        GeoUseCaseImpl(
            repository = get()
        )
    }

}