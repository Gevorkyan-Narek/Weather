package com.weather.weather.modules

import com.weather.weather.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val daoModule = module {

    single { AppDatabase.createDatabase(androidContext()) }

    single { get<AppDatabase>().forecastDao() }

}