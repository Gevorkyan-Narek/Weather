package com.weather.weather.modules

import com.weather.core.data.api.ConnectionManager
import com.weather.core.data.impl.connection.ConnectionManagerImpl
import com.weather.core.domain.api.ConnectionUseCase
import com.weather.core.domain.impl.ConnectionUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {

    single<ConnectionManager> {
        ConnectionManagerImpl(androidContext())
    }

    single<ConnectionUseCase> {
        ConnectionUseCaseImpl(
            connectionManager = get()
        )
    }
}