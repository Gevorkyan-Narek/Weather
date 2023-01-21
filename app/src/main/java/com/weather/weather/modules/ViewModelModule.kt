package com.weather.weather.modules

import com.weather.main.screen.main.MainScreenViewModel
import com.weather.main.screen.today.TodayViewModel
import com.weather.startscreen.StartScreenFragment
import com.weather.startscreen.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    scope<StartScreenFragment> {
        viewModel {
            StartScreenViewModel(
                geoUseCase = get(),
                geoMapper = get(),
            )
        }
    }

    viewModel {
        MainScreenViewModel(
            forecastUseCase = get()
        )
    }

    viewModel {
        TodayViewModel(
            forecastUseCase = get(),
            mapper = get()
        )
    }

}
