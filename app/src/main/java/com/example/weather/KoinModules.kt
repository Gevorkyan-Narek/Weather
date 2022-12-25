package com.example.weather

import com.example.startscreen.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::StartScreenViewModel)
}

val koinModules = listOf(
    viewModelsModule
)