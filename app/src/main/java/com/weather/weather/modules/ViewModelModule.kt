package com.weather.weather.modules

import com.weather.main.screen.forecast.ForecastViewModel
import com.weather.main.screen.main.MainScreenViewModel
import com.weather.main.screen.main.bottom.sheet.CityBottomSheetViewModel
import com.weather.main.screen.today.TodayViewModel
import com.weather.startscreen.StartScreenFragment
import com.weather.startscreen.StartScreenViewModel
import com.weather.weather.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        MainViewModel(
            connectionUseCase = get()
        )
    }

    scope<StartScreenFragment> {
        viewModel {
            StartScreenViewModel(
                geoUseCase = get(),
                geoMapper = get(),
                navigationGraph = get()
            )
        }
    }

    viewModel {
        MainScreenViewModel(
            geoUseCase = get(),
            navigationGraph = get()
        )
    }

    viewModel {
        TodayViewModel(
            forecastUseCase = get(),
            mapper = get()
        )
    }

    viewModel {
        ForecastViewModel(
            forecastUseCase = get()
        )
    }

    viewModel {
        CityBottomSheetViewModel(
            geoUseCase = get(),
            mapper = get()
        )
    }

}
