package com.weather.weather.modules

import com.weather.startscreen.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::StartScreenViewModel)

}
