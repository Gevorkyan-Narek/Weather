package com.weather.main.screen.mapper

import com.weather.base.utils.toPercent
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.forecast.WeatherMetricsDomain
import com.weather.core.domain.models.forecast.WeatherShortInfoDomain
import com.weather.core.domain.models.forecast.WeatherWindDomain
import com.weather.main.screen.model.*

class ForecastPresMapper {

    fun toPres(domain: WeatherDomain): WeatherPres = domain.run {
        WeatherPres(
            dateTime = dateTime,
            metrics = toPres(metrics),
            shortInfo = toPres(shortInfo),
            wind = toPres(wind)
        )
    }

    fun toPres(domain: WeatherShortInfoDomain?): WeatherShortInfoPres? = domain?.run {
        WeatherShortInfoPres(
            description = description.replaceFirstChar(Char::titlecase),
            icon = WeatherIconsEnum.getDrawable(icon)
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
            pop = pop.toPercent(),
            visibility = visibility
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