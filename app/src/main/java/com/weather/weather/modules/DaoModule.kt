package com.weather.weather.modules

import com.weather.weather.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module

private val Scope.appDatabase
    get() = get<AppDatabase>()

val daoModule = module {

    single { AppDatabase.createDatabase(androidContext()) }

    single { appDatabase.forecastDao() }
    single { appDatabase.cityDao() }

}