package com.weather.weather.modules

import com.weather.core.data.impl.forecast.ForecastMapper
import com.weather.core.data.impl.geo.GeoMapper
import com.weather.startscreen.GeoPresMapper
import com.weather.startscreen.StartScreenFragment
import org.koin.dsl.module

val mapperModule = module {

    scope<StartScreenFragment> {
        scoped {
            GeoMapper()
        }
        scoped {
            GeoPresMapper()
        }
        scoped {
            ForecastMapper()
        }
    }

}