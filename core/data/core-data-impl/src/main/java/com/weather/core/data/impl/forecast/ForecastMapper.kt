package com.weather.core.data.impl.forecast

import com.weather.core.datasource.db.WeatherEntity
import com.weather.core.datasource.db.WeatherMetricsEntity
import com.weather.core.datasource.net.forecast.model.*
import com.weather.core.domain.models.forecast.*
import java.util.*

class ForecastMapper {

    fun toDomain(net: ForecastResponse): ForecastDomain = net.run {
        ForecastDomain(
            forecast = forecast.map(::toDomain)
        )
    }

    fun toDomain(net: WeatherResponse): WeatherDomain = net.run {
        WeatherDomain(
            dateTime = Date(dateTime),
            metrics = toDomain(metrics),
            weatherDescription = weatherDescription.map(::toDomain),
            clouds = toDomain(clouds),
            wind = toDomain(wind),
            pop = pop
        )
    }

    fun toDomain(net: WeatherMetricsResponse): WeatherMetricsDomain = net.run {
        WeatherMetricsDomain(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            humidity = humidity
        )
    }

    fun toDomain(net: WeatherDescriptionResponse): WeatherDescriptionDomain = net.run {
        WeatherDescriptionDomain(
            description = description
        )
    }

    fun toDomain(net: WeatherCloudsResponse): WeatherCloudsDomain = net.run {
        WeatherCloudsDomain(
            cloudiness = cloudiness
        )
    }

    fun toDomain(net: WeatherWindResponse): WeatherWindDomain = net.run {
        WeatherWindDomain(
            speed = speed
        )
    }

    fun toEntity(net: WeatherResponse): WeatherEntity = net.run {
        WeatherEntity(
            dateTime = dateTime,
            metrics = toEntity(metrics, clouds.cloudiness, wind.speed, pop),
            description = weatherDescription.map(WeatherDescriptionResponse::description)
        )
    }

    fun toEntity(
        net: WeatherMetricsResponse,
        cloudiness: Int,
        windSpeed: Double,
        pop: Double,
    ): WeatherMetricsEntity = net.run {
        WeatherMetricsEntity(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            humidity = humidity,
            cloudiness = cloudiness,
            windSpeed = windSpeed,
            pop = pop
        )
    }

}