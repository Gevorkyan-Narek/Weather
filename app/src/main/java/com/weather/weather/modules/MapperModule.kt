package com.weather.weather.modules

import com.weather.core.data.impl.forecast.ForecastMapper
import com.weather.core.data.impl.geo.GeoMapper
import com.weather.main.screen.mapper.CityPresMapper
import com.weather.main.screen.mapper.DownloadStateMapper
import com.weather.main.screen.mapper.ForecastPresMapper
import com.weather.startscreen.mapper.GeoPresMapper
import org.koin.dsl.module

val mapperModule = module {

    single {
        ForecastMapper()
    }
    single {
        ForecastPresMapper()
    }
    single {
        GeoMapper()
    }
    single {
        GeoPresMapper()
    }
    single {
        CityPresMapper()
    }
    single {
        DownloadStateMapper()
    }

}