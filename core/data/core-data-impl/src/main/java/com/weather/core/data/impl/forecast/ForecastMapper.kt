package com.weather.core.data.impl.forecast

import com.weather.base.utils.DateFormatter
import com.weather.core.datasource.db.WeatherEntity
import com.weather.core.datasource.db.WeatherMetricsEntity
import com.weather.core.datasource.db.WeatherWindEntity
import com.weather.core.datasource.net.forecast.model.WeatherDescriptionResponse
import com.weather.core.datasource.net.forecast.model.WeatherMetricsResponse
import com.weather.core.datasource.net.forecast.model.WeatherResponse
import com.weather.core.datasource.net.forecast.model.WeatherWindResponse
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.forecast.WeatherMetricsDomain
import com.weather.core.domain.models.forecast.WeatherWindDomain
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class ForecastMapper {

    fun toEntity(net: WeatherResponse): WeatherEntity = net.run {
        WeatherEntity(
            date = DateFormatter.getDefaultFormatDate(dateTime * 1000),
            dateTime = dateTime,
            metrics = toEntity(metrics, clouds.cloudiness, pop),
            wind = toEntity(wind),
            description = weatherDescription.map(WeatherDescriptionResponse::description)
        )
    }

    fun toEntity(
        net: WeatherMetricsResponse,
        cloudiness: Int,
        pop: Double,
    ): WeatherMetricsEntity = net.run {
        WeatherMetricsEntity(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            humidity = humidity,
            cloudiness = cloudiness,
            pop = pop
        )
    }

    fun toEntity(net: WeatherWindResponse): WeatherWindEntity = net.run {
        WeatherWindEntity(
            speed = speed,
            degree = degree,
            gust = gust
        )
    }

    fun toDomain(entity: WeatherEntity): WeatherDomain = entity.run {
        WeatherDomain(
            dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateTime), ZoneId.systemDefault()
            ),
            metrics = toDomain(metrics),
            wind = toDomain(wind),
            weatherDescription = description
        )
    }

    fun toDomain(entity: WeatherMetricsEntity): WeatherMetricsDomain = entity.run {
        WeatherMetricsDomain(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            humidity = humidity,
            cloudiness = cloudiness,
            pop = pop
        )
    }

    fun toDomain(entity: WeatherWindEntity): WeatherWindDomain = entity.run {
        WeatherWindDomain(
            speed = speed,
            degree = degree,
            gust = gust
        )
    }

}