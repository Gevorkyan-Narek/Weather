package com.weather.main.screen.mapper

import com.weather.base.utils.toPercent
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.forecast.WeatherMetricsDomain
import com.weather.core.domain.models.forecast.WeatherWindDomain
import com.weather.main.screen.model.WeatherMetricsPres
import com.weather.main.screen.model.WeatherPres
import com.weather.main.screen.model.WeatherTimeEnum
import com.weather.main.screen.model.WeatherWindPres

class ForecastPresMapper {

    fun toPres(domain: WeatherDomain): WeatherPres = domain.run {
        WeatherPres(
            dateTime = dateTime,
            metrics = toPres(metrics),
            weatherDescription = weatherDescription,
            wind = toPres(wind),
            weatherTimeEnum = WeatherTimeEnum.valueOf(dateTime)
        )
    }

    fun toPres(domain: WeatherMetricsDomain): WeatherMetricsPres = domain.run {
        WeatherMetricsPres(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            humidity = humidity,
            cloudiness = cloudiness,
            pop = pop.toPercent()
        )
    }

    fun toPres(domain: WeatherWindDomain): WeatherWindPres = domain.run {
        WeatherWindPres(
            speed = speed,
            degree = degree,
            gust = gust
        )
    }
}