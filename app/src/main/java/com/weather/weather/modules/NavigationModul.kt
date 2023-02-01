package com.weather.weather.modules

import com.weather.navigation.IssueGraphNavigation
import com.weather.weather.navigation.IssueGraphNavigationImpl
import org.koin.dsl.module

val navigationModule = module {

    single<IssueGraphNavigation> {
        IssueGraphNavigationImpl()
    }

}