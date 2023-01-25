package com.weather.main.screen.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.forecast.WeatherMetricsDomain
import com.weather.main.screen.forecast.adapter.ForecastItemPres
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import kotlin.math.abs

class ForecastViewModel(
    forecastUseCase: ForecastUseCase,
) : ViewModel() {

    val forecastLiveData = forecastUseCase.getForecast()
        .map(::groupByDate)
        .map(::uniteWeatherDomain)
        .map(::mapToForecastItemList)
        .asLiveData()


    private fun groupByDate(domainList: List<WeatherDomain>): Map<LocalDate, List<WeatherDomain>> {
        return domainList.groupBy { domain -> domain.dateTime.toLocalDate() }
    }

    private fun uniteWeatherDomain(dateMap: Map<LocalDate, List<WeatherDomain>>): Map<LocalDate, ForecastItemPres> {
        return dateMap.mapValues { entry ->
            ForecastItemPres(
                dateTime = entry.key,
                icon = entry.value.first().shortInfo.first().icon,
                description = entry.value.first().shortInfo.first().description,
                tempMax = entry.value.map(WeatherDomain::metrics)
                    .maxBy { metrics -> abs(metrics.temp) }.temp,
                tempMin = entry.value.map(WeatherDomain::metrics)
                    .minBy { metrics -> abs(metrics.temp) }.temp,
                humidity = entry.value.map(WeatherDomain::metrics)
                    .map(WeatherMetricsDomain::humidity).average().toInt()
            )
        }
    }

    private fun mapToForecastItemList(map: Map<LocalDate, ForecastItemPres>): List<ForecastItemPres> {
        return map.values.toList()
    }

}