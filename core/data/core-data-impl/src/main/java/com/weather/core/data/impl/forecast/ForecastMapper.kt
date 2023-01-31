package com.weather.core.data.impl.forecast

import com.weather.base.utils.DateFormatter
import com.weather.core.datasource.db.forecast.model.WeatherEntity
import com.weather.core.datasource.db.forecast.model.WeatherMetricsEntity
import com.weather.core.datasource.db.forecast.model.WeatherShortInfoEntity
import com.weather.core.datasource.db.forecast.model.WeatherWindEntity
import com.weather.core.datasource.net.forecast.model.WeatherMetricsResponse
import com.weather.core.datasource.net.forecast.model.WeatherResponse
import com.weather.core.datasource.net.forecast.model.WeatherShortInfoResponse
import com.weather.core.datasource.net.forecast.model.WeatherWindResponse
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.forecast.WeatherMetricsDomain
import com.weather.core.domain.models.forecast.WeatherShortInfoDomain
import com.weather.core.domain.models.forecast.WeatherWindDomain
import org.threeten.bp.DateTimeUtils
import java.sql.Timestamp

class ForecastMapper {

    fun toEntity(net: WeatherResponse): WeatherEntity = net.run {
        WeatherEntity(
            date = DateFormatter.getDefaultFormatDate(dateTime * 1000),
            dateTime = dateTime * 1000,
            metrics = toEntity(metrics, clouds.cloudiness, pop, visibility),
            wind = toEntity(wind),
            shortInfo = toEntity(weather.firstOrNull()),
        )
    }

    fun toEntity(net: WeatherShortInfoResponse?): WeatherShortInfoEntity? = net?.run {
        WeatherShortInfoEntity(
            description = description,
            icon = icon
        )
    }

    fun toEntity(
        net: WeatherMetricsResponse,
        cloudiness: Int,
        pop: Double,
        visibility: Int,
    ): WeatherMetricsEntity = net.run {
        WeatherMetricsEntity(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            humidity = humidity,
            cloudiness = cloudiness,
            pop = pop,
            visibility = visibility
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
            dateTime = DateTimeUtils.toLocalDateTime(Timestamp(dateTime)),
            metrics = toDomain(metrics),
            wind = toDomain(wind),
            shortInfo = toDomain(shortInfo),
        )
    }

    fun toDomain(entity: WeatherShortInfoEntity?): WeatherShortInfoDomain? = entity?.run {
        WeatherShortInfoDomain(
            description = description,
            icon = icon
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
            pop = pop,
            visibility = visibility
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