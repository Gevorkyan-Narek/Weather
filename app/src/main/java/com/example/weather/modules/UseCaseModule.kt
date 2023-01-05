package com.example.weather.modules

import com.example.core.domain.api.GeoUseCase
import com.example.core.domain.impl.GeoUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    single<GeoUseCase> {
        GeoUseCaseImpl(
            repository = get()
        )
    }

}