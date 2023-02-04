package com.weather.main.screen.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.weather.base.utils.DateFormatter
import com.weather.core.domain.api.ForecastUseCase
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.forecast.WeatherMetricsDomain
import com.weather.main.screen.forecast.adapter.ForecastItemPres
import com.weather.main.screen.model.WeatherIconsEnum
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate

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
            val domain =
                entry.value.find { domain -> DateFormatter.timeIsMatch(domain.dateTime, 16) }
                    ?: entry.value.first()
            val description = domain.shortInfo?.description.orEmpty()
            ForecastItemPres(
                dateTime = entry.key,
                icon = WeatherIconsEnum.getDrawable(domain.shortInfo?.icon),
                description = description,
                tempMax = entry.value.map(WeatherDomain::metrics)
                    .maxBy { metrics -> metrics.temp }.temp,
                tempMin = entry.value.map(WeatherDomain::metrics)
                    .minBy { metrics -> metrics.temp }.temp,
                humidity = entry.value.map(WeatherDomain::metrics)
                    .map(WeatherMetricsDomain::humidity).average().toInt()
            )
        }
    }

    private fun mapToForecastItemList(map: Map<LocalDate, ForecastItemPres>): List<ForecastItemPres> {
        return map.values.toList()
    }

}