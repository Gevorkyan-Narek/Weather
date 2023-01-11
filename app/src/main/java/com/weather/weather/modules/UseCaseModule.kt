package com.weather.weather.modules

import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.impl.ForecastUseCaseImpl
import com.weather.core.domain.impl.GeoUseCaseImpl
import com.weather.startscreen.StartScreenFragment
import org.koin.dsl.module

val useCaseModule = module {

    scope<StartScreenFragment> {
        scoped<GeoUseCase> {
            GeoUseCaseImpl(
                repository = get()
            )
        }
        scoped<ForecastUseCase> {
            ForecastUseCaseImpl(
                repository = get()
            )
        }
    }

}