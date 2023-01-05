package com.weather.weather.modules

import com.weather.core.domain.api.GeoUseCase
import com.weather.core.domain.impl.GeoUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    single<GeoUseCase> {
        GeoUseCaseImpl(
            repository = get()
        )
    }

}