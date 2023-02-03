package com.weather.weather.modules

import com.weather.navigation.NavigationGraph
import com.weather.weather.navigation.NavigationGraphImpl
import org.koin.dsl.module

val navigationModule = module {

    single<NavigationGraph> {
        NavigationGraphImpl()
    }

}